package com.vscoding.apps.mangareader.control.connector;

import com.vscoding.apps.mangareader.control.bean.ChapterData;
import com.vscoding.apps.mangareader.entity.MangaRequestEntity;

import java.util.List;

public interface MangaConnector {
  default boolean applies(String url) {
    return url.contains(getDomain());
  }

  String getDomain();

  List<ChapterData> getChapters(MangaRequestEntity mangaRequest) throws Exception;

  List<String> getChapterImages(String chapterUrl) throws Exception;
}
