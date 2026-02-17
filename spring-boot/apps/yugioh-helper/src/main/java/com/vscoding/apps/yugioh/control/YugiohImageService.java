package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.entity.YugiohDataCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
public class YugiohImageService {
  private final String imageFolder;
  private final YugiohDataCardRepository repository;

  public YugiohImageService(@Value("${application.updater.imageFolder}") String imageFolder,
                            YugiohDataCardRepository repository) {
    this.imageFolder = imageFolder;
    this.repository = repository;
    log.info("Start Image service with folder: {}", Path.of(imageFolder).toFile().getAbsolutePath());
  }

  public byte[] getImage(long cardId) throws IOException {
    var path = Path.of(imageFolder, cardId + ".jpg");

    if (!path.toFile().exists()) {
      path = downloadFile(cardId, path);
    }

    if (path == null) {
      return new byte[0];
    }

    return Files.readAllBytes(path);
  }

  private Path downloadFile(long cardId, Path path) {
    var card = repository.findById(cardId);

    if (card.isEmpty()) {
      log.error("Could not find card with id {}", cardId);
      return null;
    }

    var cardImages = card.get().getCardImages();

    if (cardImages.isEmpty()) {
      log.error("Could not find images for card card '{}' ({})", card.get().getName(), cardId);
      return null;
    }

    var imageUrlSmall = cardImages.getFirst().getImageUrlSmall();
    try {
      var inputStream = new URL(imageUrlSmall).openStream();

      return Files.write(path, IOUtils.toByteArray(inputStream));
    } catch (Exception e) {
      log.error("Could not download image for '{}' ({})", card.get().getName(), cardId, e);
    }

    return null;
  }
}
