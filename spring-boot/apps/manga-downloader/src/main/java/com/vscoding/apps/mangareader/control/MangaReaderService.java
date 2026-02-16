package com.vscoding.apps.mangareader.control;

import com.vscoding.apps.mangareader.control.bean.ChapterData;
import com.vscoding.apps.mangareader.control.connector.MangaConnector;
import com.vscoding.apps.mangareader.entity.MangaRequestEntity;
import com.vscoding.apps.mangareader.entity.MangaRequestRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MangaReaderService {
  private final HTTPClient httpClient;
  private final List<MangaConnector> connectors;
  private final MangaRequestRepository repository;
  private final String downloadPath;

  public MangaReaderService(HTTPClient httpClient, List<MangaConnector> connectors, MangaRequestRepository repository, @Value("${application.download.path}") String downloadPath) {
    this.httpClient = httpClient;
    this.connectors = connectors;
    this.repository = repository;
    this.downloadPath = downloadPath;
  }

  @PostConstruct
  public void run() {
    repository.findAll().forEach(this::download);
  }

  public void download(MangaRequestEntity config) {
    try {
      var connector = connectors.stream()
              .filter(c -> c.applies(config.getFirstChapter()))
              .findAny()
              .orElse(null);
      if (connector == null) {
        log.warn("Could not find connector for {}", config.getFirstChapter());
        return;
      }

      log.info("Download: {} with {}", config.getName(), connector.getClass().getSimpleName());
      var chapters = connector.getChapters(config);
      new File(downloadPath + "/" + config.getName()).mkdir();

      log.info("Found {} Chapters", chapters.size());

      var afterLastChapter = config.getLastChapter().isEmpty();

      for (var chapter : chapters) {
        if (afterLastChapter) {
          downloadChapter(chapter, config, connector);
        }

        if (!afterLastChapter && chapter.chapterUrl().equals(config.getLastChapter())) {
          afterLastChapter = true;
        }
      }
    } catch (Exception e) {
      log.error("error downloading '{}'", config.getName(), e);
      throw new RuntimeException("Could not download " + config.getName());
    }
  }

  private void downloadChapter(ChapterData chapterData, MangaRequestEntity config, MangaConnector connector) throws Exception {
    var chapterFolder = new File(downloadPath + "/" + config.getName() + "/" + chapterData.chapterName());
    var zipFile = new File(chapterFolder.getAbsolutePath() + ".cbz");

    try {
      if (zipFile.exists()) {
        return;
      }
      var chapterImages = connector.getChapterImages(chapterData.chapterUrl());

      chapterFolder.mkdir();
      log.info("Download '{}' - found {} Pages", chapterData.chapterName(), chapterImages.size());

      var success = chapterImages.parallelStream()
              .allMatch(image -> downloadImage(image, chapterFolder.getAbsolutePath(), connector.getDomain()));

      if (success) {
        ZipUtils.zipFolder(chapterFolder, zipFile);
        config.setLastChapter(chapterData.chapterUrl());
        repository.save(config);
      } else {
        log.warn("Could not download some Pages for {}, enable debug log for more information", chapterData.chapterName());
      }

      Thread.sleep(10000);
    } finally {
      try {
        FileUtils.deleteDirectory(chapterFolder);
      } catch (IOException e) {
        log.error("Could not delete '{}'", chapterData.chapterName());
      }
    }
  }

  private boolean downloadImage(String imageUrl, String chapterPath, String domain) {
    try {
      var fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
      var in = httpClient.readInputStream(imageUrl, domain);

      Files.copy(in, Paths.get(chapterPath, fileName), StandardCopyOption.REPLACE_EXISTING);
      return true;
    } catch (Exception e) {
      log.error("Could not download {}", imageUrl, e);
      return false;
    }
  }
}
