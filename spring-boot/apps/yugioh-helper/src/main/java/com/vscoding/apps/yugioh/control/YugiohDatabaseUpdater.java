package com.vscoding.apps.yugioh.control;

import com.google.gson.Gson;
import com.vscoding.apps.yugioh.entity.YugiohDataCardRepository;
import com.vscoding.apps.yugioh.entity.YugiohDataResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.net.URL;

@Slf4j
@Service
public class YugiohDatabaseUpdater {
  private final String databaseUrl;
  private final boolean enable;
  private final YugiohDataCardRepository repository;

  public YugiohDatabaseUpdater(@Value("${application.updater.url}") String databaseUrl,
                               @Value("${application.updater.enable}") boolean enable,
                               YugiohDataCardRepository repository) {
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
      var data = new Gson().fromJson(reader, YugiohDataResponse.class);

      repository.saveAll(data.getData());
      log.info("Finished Data sync");
    } catch (Exception e) {
      log.error("Could not update database",e);
    }
  }
}
