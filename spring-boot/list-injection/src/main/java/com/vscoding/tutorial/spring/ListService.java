package com.vscoding.tutorial.spring;

import com.vscoding.tutorial.spring.bean.ListItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ListService {

  private final List<ListItem> listItems;

  public ListService(@Qualifier("mergedList") List<ListItem> listItems) {
    this.listItems = listItems;
  }

  public List<ListItem> getListItems() {
    return listItems;
  }
}
