package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.DeckDTO;
import com.vscoding.apps.yugioh.boundary.bean.YugiohDeckCreationRequest;
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
  private final YugiohDataCardRepository cardRepository;
  private final YugiohMapper mapper = new YugiohMapper();

  public YugiohDeckCreationResponse createDeck(YugiohDeckCreationRequest request) {
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
          var card = findCard(cardName);

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

  private Optional<YugiohDataCard> findCard(String name) {
    // Check if it is only ID
    try {
      var id = Long.parseLong(name);
      return cardRepository.findById(id);
    } catch (Exception e) {
      // DO NOTHING
    }

    // Check only by name
    return cardRepository.findTopByName(name);
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
