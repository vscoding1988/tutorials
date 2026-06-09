package com.vscoding.tutorial.spring.control;

import com.microsoft.graph.models.EntityType;
import com.microsoft.graph.models.SearchQuery;
import com.microsoft.graph.models.SearchRequest;
import com.microsoft.graph.search.query.QueryPostRequestBody;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.vscoding.tutorial.spring.control.bean.SharePointFilterConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to get the count of items in a SharePoint list, I could not find a way to get the count directly by using
 * .items.count() or item.get().totalCount() so I had to trick it by using the search API, where for filter I use the
 * list url.
 */
@Service
public class SharePointCountService {
  private static final String PATH_FILTER = "path:\"%s\"";
  private final GraphServiceClient graphClient;
  private final String listPathFilter;

  public SharePointCountService(GraphServiceClient graphClient, SharePointFilterConfig config) {
    this.graphClient = graphClient;
    this.listPathFilter = String.format(PATH_FILTER, getListPath(config));
  }

  /**
   * Get the count of items in the SharePoint list
   *
   * @return count of items
   */
  public Integer countItems() {
    var searchQuery = new SearchQuery();
    searchQuery.setQueryString(listPathFilter);

    var searchRequest = new SearchRequest();
    searchRequest.setEntityTypes(List.of(EntityType.ListItem));
    searchRequest.setSize(1);
    searchRequest.setRegion("EMEA");
    searchRequest.setQuery(searchQuery);

    var queryPostRequestBody = new QueryPostRequestBody();
    queryPostRequestBody.setRequests(List.of(searchRequest));

    var result = graphClient.search().query().post(queryPostRequestBody);

    if (result == null || result.getValue() == null || result.getValue().isEmpty()) {
      throw new IllegalArgumentException("Could not get items from SharePoint, response is null");
    }

    var containers = result.getValue().get(0).getHitsContainers();

    if (containers == null || containers.isEmpty()) {
      throw new IllegalArgumentException("Could not get items from SharePoint, response is empty");
    }

    return containers.get(0).getTotal();
  }

  /**
   * Get the list path from SharePoint
   *
   * @param config SharePointFilterConfig
   * @return list path
   */
  private String getListPath(SharePointFilterConfig config) {
    var response = graphClient.sites()
            .bySiteId(config.siteId())
            .lists()
            .byListId(config.listId())
            .get();

    if (response == null) {
      throw new IllegalArgumentException("Could not get list path");
    }

    return response.getWebUrl();
  }
}
