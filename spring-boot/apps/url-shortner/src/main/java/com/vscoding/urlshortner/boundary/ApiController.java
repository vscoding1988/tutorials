package com.vscoding.urlshortner.boundary;

import com.vscoding.urlshortner.bean.UrlDTO;
import com.vscoding.urlshortner.control.UrlService;
import com.vscoding.urlshortner.entity.UrlId;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

  private final UrlService urlService;

  public ApiController(UrlService urlService) {
    this.urlService = urlService;
  }

  /**
   * Will return all existing urls
   *
   * @return list of products
   */
  @GetMapping("urls")
  public List<UrlDTO> getAllUrls() {
    return urlService.getUrls();
  }
}
