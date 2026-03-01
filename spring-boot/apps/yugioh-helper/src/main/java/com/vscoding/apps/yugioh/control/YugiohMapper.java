package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.*;
import com.vscoding.apps.yugioh.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YugiohMapper {
  private final YugiohCardCollectionWrapperRepository cardWrapperRepository;

  public DeckDTO mapLazy(YugiohDeck src) {
    return new DeckDTO(
            src.getId(),
            src.getName(),
            src.getDescription(),
            List.of(),
            List.of(),
            List.of()
    );
  }

  public DeckDTO map(YugiohDeck src) {
    return new DeckDTO(
            src.getId(),
            src.getName(),
            src.getDescription(),
            src.getMainDeck().stream().map(this::mapLazy).toList(),
            src.getExtraDeck().stream().map(this::mapLazy).toList(),
            src.getSideDeck().stream().map(this::mapLazy).toList()
    );
  }

  public CardDTO mapLazy(YugiohDataCard src) {
    var cardInCollection = cardWrapperRepository.findAllByCardIs(src);

    return new CardDTO(
            src.getId(),
            src.getName(),
            src.getType(),
            src.getCardSets().stream().map(this::map).toList(),
            cardInCollection.stream().mapToInt(YugiohCardCollectionWrapper::getCount).sum()
    );
  }

  public CardDTO map(YugiohDataCard src) {
    return mapLazy(src);
  }

  public CollectionCardDTO mapLazy(YugiohCardCollectionWrapper src) {
    var card = src.getCard();
    var set = src.getSet();

    return new CollectionCardDTO(
            card.getId(),
            card.getName(),
            card.getType(),
            set.getSetCode(),
            src.getCount()
    );
  }

  public SetDTO map(YugiohDataSet src) {
    return new SetDTO(
            src.getSetName(),
            src.getSetCode().split("-")[0]
    );
  }

  public CollectionDTO mapLazy(YugiohCardCollection src) {
    return new CollectionDTO(
            src.getId(),
            src.getName(),
            src.getDescription(),
            src.getCards().stream().mapToInt(YugiohCardCollectionWrapper::getCount).sum()
    );
  }
}
