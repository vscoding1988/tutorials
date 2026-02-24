package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.AddCardDTO;
import com.vscoding.apps.yugioh.boundary.bean.CollectionDTO;
import com.vscoding.apps.yugioh.boundary.bean.YugiohCollectionSearchRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohCollectionSearchResponse;
import com.vscoding.apps.yugioh.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class YugiohCollectionService {
  private static final String DEFAULT_COLLECTION_NAME = "My Collection";

  private final YugiohCardParser parser;
  private final YugiohDataCardRepository cardRepository;
  private final YugiohCardCollectionWrapperRepository collectionWrapperRepository;
  private final YugiohCardCollectionRepository collectionRepository;
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
}
