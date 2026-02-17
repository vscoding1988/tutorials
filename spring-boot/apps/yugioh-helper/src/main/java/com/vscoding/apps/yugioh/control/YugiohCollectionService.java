package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.AddCardDTO;
import com.vscoding.apps.yugioh.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

  public void addCardsToCollection(List<AddCardDTO> cards) {
    var collection = getDefaultCollection();

    cards.forEach(card -> {
      var cardInDB = cardRepository.findById(card.id());

      if (cardInDB.isPresent()) {
        var set = cardInDB.map(YugiohDataCard::getCardSets).orElse(List.of()).stream()
                .filter(setDTO -> setDTO.getSetCode().equals(card.set()))
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
      collection.setDescription("");
      collection.setCards(new ArrayList<>());

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
}
