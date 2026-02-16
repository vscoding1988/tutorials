package com.vscoding.apps.mangareader.control.connector;

import com.vscoding.apps.mangareader.control.HTTPClient;
import com.vscoding.apps.mangareader.control.bean.ChapterData;
import com.vscoding.apps.mangareader.entity.MangaRequestEntity;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MangaTownConnector implements MangaConnector {
  private static final String DOMAIN = "https://www.mangatown.com";
  private final HTTPClient httpClient;


  public List<ChapterData> getChapters(MangaRequestEntity mangaRequest) throws Exception {
    var lastChapter = Jsoup.parse(httpClient.readHtml(mangaRequest.getFirstChapter(), DOMAIN));
    var chapters = lastChapter.select("#top_chapter_list option");

    return chapters.stream().map(el -> {
      var url = el.val();

      var chapterName = mangaRequest.getName() + " - " + el.text();
      return new ChapterData(chapterName, url);
    }).collect(Collectors.toList());
  }

  @Override
  public List<String> getChapterImages(String chapterUrl) throws Exception {
    var links = new ArrayList<String>();
    var chapter = Jsoup.parse(httpClient.readHtml(chapterUrl, DOMAIN));

    var pageUrls = chapter.select(".go_page .page_select select option").stream()
            .map(Element::val)
            .collect(Collectors.toSet());

    for (var pageUrl : pageUrls) {
      var page = Jsoup.parse(httpClient.readHtml(pageUrl, DOMAIN));
      var imageLink = page.select("#viewer img");

      if (!imageLink.isEmpty()) {
        var imageUrl = imageLink.first().attr("src");
        links.add(imageUrl);
      }
    }

    return links;
  }

  @Override
  public String getDomain() {
    return DOMAIN;
  }
}
