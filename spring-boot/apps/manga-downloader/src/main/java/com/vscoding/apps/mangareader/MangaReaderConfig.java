package com.vscoding.apps.mangareader;

import com.vscoding.apps.mangareader.entity.MangaRequestEntity;
import com.vscoding.apps.mangareader.entity.MangaRequestRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MangaReaderConfig {
  @Bean
  public InitializingBean addRequest(@Value("${application.add.name}") String name,
                                     @Value("${application.add.lastChapter}") String lastChapter,
                                     @Value("${application.add.firstChapter}") String firstChapter,
                                     MangaRequestRepository mangaRequestRepository) {
    return () -> {
      if (name.isEmpty()) {
        return;
      }

      var byName = mangaRequestRepository.findByName(name);

      if (byName.isEmpty()) {
        var request = new MangaRequestEntity();

        request.setName(name);
        request.setLastChapter(lastChapter);
        request.setFirstChapter(firstChapter);

        mangaRequestRepository.save(request);
      }
    };
  }
}
