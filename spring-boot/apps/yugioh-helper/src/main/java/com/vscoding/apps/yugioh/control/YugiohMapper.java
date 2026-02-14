package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.CardDTO;
import com.vscoding.apps.yugioh.boundary.bean.DeckDTO;
import com.vscoding.apps.yugioh.entity.YugiohDataCard;
import com.vscoding.apps.yugioh.entity.YugiohDeck;

import java.util.List;

public class YugiohMapper {

  public DeckDTO mapLazy(YugiohDeck src) {
    return new DeckDTO(
            src.getId(),
            src.getName(),
            List.of(),
            List.of(),
            List.of()
    );
  }

  public DeckDTO map(YugiohDeck src) {
    return new DeckDTO(
            src.getId(),
            src.getName(),
            src.getMainDeck().stream().map(this::mapLazy).toList(),
            src.getExtraDeck().stream().map(this::mapLazy).toList(),
            src.getSideDeck().stream().map(this::mapLazy).toList()
    );
  }

  public CardDTO mapLazy(YugiohDataCard src) {
    return new CardDTO(
            src.getId(),
            src.getName(),
            src.getType()
    );
  }

  public CardDTO map(YugiohDataCard src) {
    return new CardDTO(
            src.getId(),
            src.getName(),
            src.getType()
    );
  }
}
