package com.vscoding.apps.yugioh.control;

import com.google.gson.Gson;
import com.vscoding.apps.yugioh.entity.YugiohDataCardRepository;
import com.vscoding.apps.yugioh.entity.YugiohDataResponse;
import com.vscoding.apps.yugioh.entity.YugiohDataSet;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

@Slf4j
@Service
public class YugiohDatabaseUpdater {
  private final String databaseUrl;
  private final boolean enable;
  private final YugiohDataCardRepository repository;

  public YugiohDatabaseUpdater(@Value("${application.updater.url}") String databaseUrl, @Value("${application.updater.enable}") boolean enable, YugiohDataCardRepository repository) {
    this.databaseUrl = databaseUrl;
    this.enable = enable;
    this.repository = repository;
  }

  @PostConstruct
  public void update() {
    if (!enable) {
      log.info("Skip database update.");
      return;
    }

    try {
      log.info("Start Data sync");

      var url = new URL(databaseUrl);
      var reader = new InputStreamReader(url.openStream());
      var data = new Gson().fromJson(reader, YugiohDataResponse.class).getData().subList(0, 100);

      data.forEach(card -> {
        var cardSetCodes = card.getCardSets().stream().map(YugiohDataSet::getSetCode).collect(Collectors.toSet());

        // Store the set codes in a comma separated string for easier search INFO-DE02,DS-DE12
        card.setSetNames(String.join(",", cardSetCodes));
      });

      repository.saveAll(data);
      log.info("Finished Data sync");
    } catch (Exception e) {
      log.error("Could not update database", e);
    }
  }
}
