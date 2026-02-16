package com.vscoding.apps.mangareader.control.connector;

import com.vscoding.apps.mangareader.control.HTTPClient;
import com.vscoding.apps.mangareader.control.bean.ChapterData;
import com.vscoding.apps.mangareader.entity.MangaRequestEntity;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MangaReadOrgConnector implements MangaConnector {
  private static final String DOMAIN = "https://www.mangaread.org";
  private final HTTPClient httpClient;


  public List<ChapterData> getChapters(MangaRequestEntity mangaRequest) throws Exception {
    var lastChapter = Jsoup.parse(httpClient.readHtml(mangaRequest.getFirstChapter(), DOMAIN));
    var chapters = lastChapter.select("#manga-reading-nav-head .single-chapter-select option");

    var chapterData = chapters.stream().map(el -> {
      var url = el.attr("data-redirect");
      var chapterName = el.text();
      return new ChapterData(chapterName, url);
    }).collect(Collectors.toList());

    Collections.reverse(chapterData);

    return chapterData;
  }

  @Override
  public List<String> getChapterImages(String chapterUrl) throws Exception {
    var chapter = Jsoup.parse(httpClient.readHtml(chapterUrl, DOMAIN));

    var images = chapter.select(".reading-content img");
    return images.stream().map(image -> {
      var imageUrl = image.attr("data-src");

      if (imageUrl.isEmpty()) {
        imageUrl = image.attr("src");
      }

      return imageUrl;
    }).toList();
  }

  @Override
  public String getDomain() {
    return DOMAIN;
  }
}
