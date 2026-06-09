package com.vscoding.tutorial.spring;

import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.core.authentication.AzureIdentityAuthenticationProvider;
import com.microsoft.graph.core.requests.GraphClientFactory;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.vscoding.tutorial.spring.control.bean.SharePointFilterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SharePointGraphAPIConfig {
  /**
   * See <a href="https://learn.microsoft.com/en-us/graph/sdks/customize-client?tabs=java">Microsoft Documentation</a>
   *
   * @param clientId     client id
   * @param clientSecret client secret
   * @param tenantId     tenant id
   * @param baseUrl      base url
   * @return graph service client
   */
  @Bean
  public GraphServiceClient graphServiceClient(@Value("${sharepoint.clientId}") String clientId,
                                               @Value("${sharepoint.clientSecret}") String clientSecret,
                                               @Value("${sharepoint.tenantId}") String tenantId,
                                               @Value("${sharepoint.baseUrl}") String baseUrl) {
    var credential = new ClientSecretCredentialBuilder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .tenantId(tenantId)
            .build();
    var authProvider = new AzureIdentityAuthenticationProvider(credential, new String[0], "https://graph.microsoft.com/.default");

    var graphClient = new GraphServiceClient(authProvider, GraphClientFactory.create().build());

    // Had to be done, can't find a better way to set the base url
    graphClient.getRequestAdapter().setBaseUrl(baseUrl);

    return graphClient;
  }

  @Bean
  public SharePointFilterConfig sharePointFilterConfig(@Value("${sharepoint.filter}") String filter,
                                                       @Value("${sharepoint.fields}") String fields,
                                                       @Value("${sharepoint.sort}") String sort,
                                                       @Value("${sharepoint.listId}") String listId,
                                                       @Value("${sharepoint.siteId}") String siteId) {
    return new SharePointFilterConfig(filter, fields, sort, siteId, listId, 25);
  }
}
