package com.vscoding.urlshortner.boundary;

import com.vscoding.urlshortner.control.UrlService;
import com.vscoding.urlshortner.entity.UrlId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@CrossOrigin
public class RedirectController {

  private final UrlService urlService;

  public RedirectController(UrlService urlService) {
    this.urlService = urlService;
  }

  /**
   * Will redirect user to the target url
   *
   * @return list of products
   */
  @GetMapping("/{domain}/{shortUrl}")
  public Object redirectToUrl(@PathVariable String domain, @PathVariable String shortUrl) {
    var urlId = new UrlId(domain, shortUrl);
    var redirect = urlService.getUrl(urlId);

    if (redirect.isPresent()) {
      urlService.addRedirectCall(urlId);
      return new RedirectView(redirect.get().getTargetUrl());
    }
    return "error.html"; // TODO check for a better way
  }
}
