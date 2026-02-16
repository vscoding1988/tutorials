package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.DeckDTO;
import com.vscoding.apps.yugioh.boundary.bean.YugiohCreationRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohDeckCreationResponse;
import com.vscoding.apps.yugioh.entity.YugiohDataCard;
import com.vscoding.apps.yugioh.entity.YugiohDataCardRepository;
import com.vscoding.apps.yugioh.entity.YugiohDeck;
import com.vscoding.apps.yugioh.entity.YugiohDeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class YugiohDeckService {
  private final YugiohDeckRepository repository;
  private final YugiohCardParser cardParser;
  private final YugiohMapper mapper = new YugiohMapper();

  public YugiohDeckCreationResponse createDeck(YugiohCreationRequest request) {
    var deck = new YugiohDeck();

    deck.setId(UUID.randomUUID().toString());
    deck.setName(request.name());
    deck.setDescription(request.description());

    var aktiveList = deck.getMainDeck();
    var unparsable = new ArrayList<String>();

    for (String cardName : request.cards()) {
      switch (cardName) {
        case "#main" -> aktiveList.clear();
        case "#extra" -> aktiveList = deck.getExtraDeck();
        case "!side" -> aktiveList = deck.getSideDeck();
        default -> {
          var card = cardParser.findCard(cardName);

          if (card.isEmpty()) {
            unparsable.add(cardName);
          } else {
            aktiveList.add(card.get());
          }
        }
      }
    }

    repository.save(deck);

    return new YugiohDeckCreationResponse(mapper.map(deck), unparsable);
  }

  public List<DeckDTO> getDecks() {
    return repository.findAll().stream()
            .map(mapper::mapLazy)
            .sorted(Comparator.comparing(DeckDTO::name))
            .toList();
  }

  public void deleteDeck(String id) {
    repository.deleteById(id);
  }

  public DeckDTO getDeck(String id) {
    return repository.findById(id)
            .map(mapper::map)
            .orElse(null);
  }
}
