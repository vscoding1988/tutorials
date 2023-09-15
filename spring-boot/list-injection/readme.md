# Spring List injection

## Task

Inject beans with same class but defined as lists and single bean in one list.

### Config

```java

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
}
```

### Service

```java

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
```

### Test

```java

@SpringBootTest
class ApplicationTest {

  @Autowired
  private ListService listService;

  @Test
  void test_autowire() {
    // When
    var listItems = listService.getListItems();

    // Then
    assertEquals(6, listItems.size());
  }
}
```

## Problem

When executed, the test fail, only `bean-5` and `bean-6` are injected.

## Solution

We have to merge the beans in one list and use `@Qualifier` annotation in service

```java

@Configuration
public class ListConfig {

  // [...] Bean configs

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
```

## Good To Know

When we delete the `ListItem` bean definition and only keep the list definitions, we will get
injection error `expected single matching bean but found 2: list1,list2`, so it look like, Spring
will first try to merge all simple beans in a list and this list is higher on the priority level as
user defined list.  
