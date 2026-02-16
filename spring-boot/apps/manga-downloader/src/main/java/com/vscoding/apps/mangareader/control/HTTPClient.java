package com.vscoding.apps.mangareader.control;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class HTTPClient {

  public String readHtml(String url, String domain) throws Exception {
    var in = readInputStream(url, domain);
    return StreamUtils.copyToString(in, StandardCharsets.UTF_8);
  }

  public InputStream readInputStream(String url, String domain) throws Exception {
    var in = new URL(makeUrlAbsolute(url, domain)).openConnection();

    in.setRequestProperty("Referer", url);
    in.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:145.0) Gecko/20100101 Firefox/144.0");
    in.setRequestProperty("Accept", "*/*");
    in.setRequestProperty("Alt-Used", "www.mangaread.org");

    return in.getInputStream();
  }

  private String makeUrlAbsolute(String url, String domain) {
    if (url.startsWith("https")) {
      return url;
    } else if (url.startsWith("//")) {
      url = "https:" + url;
    } else if (!url.contains(domain)) {
      url = domain + url;
    }

    return url;
  }
}
