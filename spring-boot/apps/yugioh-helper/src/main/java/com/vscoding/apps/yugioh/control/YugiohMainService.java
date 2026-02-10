package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.YugiohDeckCreationRequest;
import com.vscoding.apps.yugioh.boundary.bean.YugiohDeckCreationResponse;
import com.vscoding.apps.yugioh.entity.YugiohDataCard;
import com.vscoding.apps.yugioh.entity.YugiohDataCardRepository;
import com.vscoding.apps.yugioh.entity.YugiohDeck;
import com.vscoding.apps.yugioh.entity.YugiohDeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class YugiohMainService {
  private final YugiohDeckRepository repository;
  private final YugiohDataCardRepository cardRepository;

  public YugiohDeckCreationResponse createDeck(YugiohDeckCreationRequest request) {
    var deck = new YugiohDeck();

    deck.setId(UUID.randomUUID().toString());
    deck.setName(request.name());

    var tempList = new ArrayList<YugiohDataCard>();
    var unparsable = new ArrayList<String>();

    request.cards().forEach(cardName -> {
      switch (cardName) {
        case "#main" -> tempList.clear();
        case "#extra" -> {
          deck.setMainDeck(new ArrayList<>(tempList));
          tempList.clear();
        }
        case "!side" -> {
          deck.setExtraDeck(new ArrayList<>(tempList));
          tempList.clear();
        }
        default -> {
          var card = findCard(cardName);

          if (card.isEmpty()) {
            unparsable.add(cardName);
          } else {
            tempList.add(card.get());
          }
        }
      }
    });

    deck.setSideDeck(new ArrayList<>(tempList));

    repository.save(deck);

    return new YugiohDeckCreationResponse(deck, unparsable);
  }

  private Optional<YugiohDataCard> findCard(String name) {
    // Check if it is only ID
    try {
      var id = Long.parseLong(name);
      return cardRepository.findById(id);
    } catch (Exception e) {
      // DO NOTHING
    }

    // TODO find by name

    return Optional.empty();
  }
}
