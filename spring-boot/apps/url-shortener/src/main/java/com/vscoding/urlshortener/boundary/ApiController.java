package com.vscoding.urlshortener.boundary;

import com.vscoding.urlshortener.boundary.bean.UrlCreationRequest;
import com.vscoding.urlshortener.boundary.bean.UrlDTO;
import com.vscoding.urlshortener.boundary.bean.UrlRequest;
import com.vscoding.urlshortener.boundary.bean.UrlResponse;
import com.vscoding.urlshortener.control.UrlService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = "Short Url API",
        version = "1.0.0",
        description = "Short Url API"),
        servers = {
                @Server(
                        description = "local basePath",
                        url = "http://localhost:8080/"
                )
        })
@Tag(name = "ShortUrl", description = "Main short url API")
public class ApiController {

  private final UrlService urlService;

  /**
   * Will search urls based on the user request
   *
   * @param urlRequest request
   * @return search result
   */
  @PostMapping("urls")
  public UrlResponse findUrls(@RequestBody UrlRequest urlRequest) {
    return urlService.getUrls(urlRequest);
  }

  @PostMapping("create")
  public UrlDTO create(@RequestBody UrlCreationRequest creationRequest) {
    return urlService.create(creationRequest);
  }
}
