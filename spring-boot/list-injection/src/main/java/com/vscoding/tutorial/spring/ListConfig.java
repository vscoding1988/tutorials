package com.vscoding.tutorial.spring;

import com.vscoding.tutorial.spring.bean.ListItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListConfig {

  @Bean
  public List<ListItem> list1() {
    return Arrays.asList(new ListItem("item-1"), new ListItem("item-2"));
  }

  @Bean
  public List<ListItem> list2() {
    return Arrays.asList(new ListItem("item-3"), new ListItem("item-4"));
  }

  @Bean
  public ListItem bean1() {
    return new ListItem("item-5");
  }

  @Bean
  public ListItem bean2() {
    return new ListItem("item-6");
  }

  /**
   * Merge all beans in one list, the list can be accessed through the @Qualifier annotation, to get
   * all beans in one list
   *
   * @param items contains bean1 and bean2
   * @param lists contains list1 and list2
   * @return merged list of all beans
   */
  @Bean
  public List<ListItem> mergedList(List<ListItem> items, List<ListItem>... lists) {
    var mergedList = new ArrayList<>(items);

    Arrays.stream(lists).forEach(mergedList::addAll);

    return mergedList;
  }
}
