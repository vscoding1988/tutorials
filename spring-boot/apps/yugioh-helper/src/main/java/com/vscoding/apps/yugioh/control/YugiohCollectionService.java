package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.*;
import com.vscoding.apps.yugioh.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class YugiohCollectionService {
  private static final String DEFAULT_COLLECTION_NAME = "My Collection";


  private final YugiohDataCardRepository cardRepository;
  private final YugiohCardCollectionWrapperRepository collectionWrapperRepository;
  private final YugiohCardCollectionRepository collectionRepository;
  private final YugiohDataSetRepository setRepository;
  private final YugiohMapper mapper = new YugiohMapper();

  public void addCardsToCollection(List<AddCardDTO> cards) {
    var collection = getDefaultCollection();

    cards.forEach(card -> {
      var cardInDB = cardRepository.findById(card.id());

      if (cardInDB.isPresent()) {
        var set = cardInDB.map(YugiohDataCard::getCardSets).orElse(List.of()).stream()
                .filter(setDTO -> setDTO.getSetCode().startsWith(card.set()))
                .findFirst();

        if (set.isEmpty()) {
          log.error("No set found for card '{}' falling back to first set.", cardInDB.get().getName());
          return;
        }

        var wrapper = getWrapper(cardInDB.get(), set.get(), collection);

        wrapper.setCount(wrapper.getCount() + card.count());
      }
    });
    collectionRepository.save(collection);
  }

  private YugiohCardCollection getDefaultCollection() {
    return collectionRepository.findById(DEFAULT_COLLECTION_NAME).orElseGet(() -> {
      var collection = new YugiohCardCollection();
      collection.setName(DEFAULT_COLLECTION_NAME);
      collection.setId(DEFAULT_COLLECTION_NAME);
      collection.setDescription("");
      collection.setCards(new ArrayList<>());
      collection.setCreationDate(new Date());

      return collectionRepository.save(collection);
    });
  }

  private YugiohCardCollectionWrapper getWrapper(YugiohDataCard card, YugiohDataSet set, YugiohCardCollection collection) {
    return collectionWrapperRepository.findByCardAndSet(card, set).orElseGet(() -> {
      var wrapper = new YugiohCardCollectionWrapper();

      wrapper.setCard(card);
      wrapper.setSet(set);
      wrapper = collectionWrapperRepository.save(wrapper);
      collection.getCards().add(wrapper);

      return wrapper;
    });
  }

  public YugiohCollectionSearchResponse search(YugiohCollectionSearchRequest request) {
    // TODO Pagination and Search
    //var pageable = PageRequest.of(request.getPage(), request.getLimit());

    var collection = collectionRepository.findById(request.getCollection());

    if (collection.isEmpty()) {
      return new YugiohCollectionSearchResponse(0, List.of());
    }

    var cards = collection.get().getCards().stream().map(mapper::mapLazy).toList();

    return new YugiohCollectionSearchResponse(cards.size(), cards);
  }

  public List<CollectionDTO> getAllCollections() {
    return collectionRepository.findAll().stream()
            .map(mapper::mapLazy)
            .toList();
  }

  public List<CollectionSetDTO> getSetsInCollection(String collectionId) {
    var collection = collectionRepository.findById(collectionId);

    if (collection.isEmpty()) {
      return List.of();
    }

    var lookup = new HashMap<YugiohDataSet, List<YugiohCardCollectionWrapper>>();

    collection.get().getCards().forEach(cardWrapper -> {
      var set = cardWrapper.getSet();

      if (lookup.containsKey(set)) {
        lookup.get(set).add(cardWrapper);
      } else {
        lookup.put(set, new ArrayList<>(List.of(cardWrapper)));
      }
    });

    return lookup.entrySet().stream().map(entry -> {
      var cardsInSet = setRepository.countBySetNameIs(entry.getKey().getSetName());
      var cardsCollected = entry.getValue().stream().mapToInt(YugiohCardCollectionWrapper::getCount).sum();

      return new CollectionSetDTO(
              entry.getKey().getSetName().split("-")[0],
              entry.getKey().getSetCode(),
              cardsCollected,
              entry.getValue().size(),
              cardsInSet
      );
    }).toList();
  }
}
