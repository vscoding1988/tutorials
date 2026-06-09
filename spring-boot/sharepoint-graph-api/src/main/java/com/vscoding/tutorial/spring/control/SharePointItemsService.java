package com.vscoding.tutorial.spring.control;

import com.microsoft.graph.models.ListItem;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.vscoding.tutorial.spring.control.bean.SharePointFilterConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SharePointItemsService {
  private final GraphServiceClient graphClient;
  private final SharePointFilterConfig config;

  /**
   * Get all items from SharePoint list
   *
   * @return list of {@link ListItem}
   */
  public List<ListItem> getItems() {
    var response = graphClient.sites()
            .bySiteId(config.siteId())
            .lists()
            .byListId(config.listId())
            .items()
            .get(requestConfiguration -> {
              if (requestConfiguration.queryParameters != null) {
                requestConfiguration.queryParameters.filter = String.format(config.filter());
                requestConfiguration.queryParameters.expand = new String[]{config.fields()};
                requestConfiguration.queryParameters.select = new String[]{"fields"};
                requestConfiguration.queryParameters.orderby = new String[]{config.sort()};
                // allow to filter non indexed columns
                requestConfiguration.headers.add("Prefer", "HonorNonIndexedQueriesWarningMayFailRandomly");
              }
            });

    if (response == null) {
      log.warn("Could not get items from SharePoint, response is null");

      return List.of();
    }

    return response.getValue();
  }
}
