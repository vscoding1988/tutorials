package com.vscoding.urlshortener.boundary;

import com.vscoding.urlshortener.control.StatisticsService;
import com.vscoding.urlshortener.control.UrlService;
import com.vscoding.urlshortener.entity.UrlId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class RedirectController {

  private final UrlService urlService;
  private final StatisticsService statisticsService;

  /**
   * Will redirect user to the target url
   *
   * @return redirect view or error page
   */
  @GetMapping("/{domain}/{shortUrl}")
  public Object redirectToUrl(@PathVariable String domain, @PathVariable String shortUrl, HttpServletRequest request) {
    var urlId = new UrlId(domain, shortUrl);
    var redirect = urlService.getUrl(urlId);

    if (redirect.isPresent()) {
      statisticsService.registerRedirect(urlId, request);
      return new RedirectView(redirect.get().getTargetUrl());
    }

    return "error.html"; // TODO check for a better way
  }
}
