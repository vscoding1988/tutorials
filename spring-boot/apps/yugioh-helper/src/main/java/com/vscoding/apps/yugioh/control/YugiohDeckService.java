package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.DeckDTO;
import com.vscoding.apps.yugioh.boundary.bean.YugiohCreationRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohDeckCreationRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohDeckCreationResponse;
import com.vscoding.apps.yugioh.entity.YugiohDataCard;
import com.vscoding.apps.yugioh.entity.YugiohDataCardRepository;
import com.vscoding.apps.yugioh.entity.YugiohDeck;
import com.vscoding.apps.yugioh.entity.YugiohDeckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class YugiohDeckService {
  private final YugiohDeckRepository repository;
  private final YugiohDataCardRepository cardRepository;
  private final YugiohCardParser cardParser;
  private final YugiohMapper mapper;

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

  public YugiohDeckCreationResponse updateDeck(YugiohDeckCreationRequest request) {
    var deck = repository.findById(request.id()).orElse(null);

    if (deck == null) {
      log.warn("Deck with id {} not found, cannot update", request.id());
      return null;
    }

    var unparsable = new ArrayList<String>();

    deck.setName(request.name());
    deck.setDescription(request.description());

    updateCards(request.main(), deck.getMainDeck(), unparsable);
    updateCards(request.extra(), deck.getExtraDeck(), unparsable);
    updateCards(request.side(), deck.getSideDeck(), unparsable);

    repository.save(deck);

    return new YugiohDeckCreationResponse(mapper.map(deck), unparsable);
  }

  private void updateCards(List<Long> cardIds, List<YugiohDataCard> target, List<String> unparsable) {
    target.clear();

    for (var cardName : cardIds) {
      var card = cardRepository.findById(cardName);

      if (card.isEmpty()) {
        unparsable.add(String.valueOf(cardName));
      } else {
        target.add(card.get());
      }
    }
  }
}
