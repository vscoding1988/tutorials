package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.YugiohCreationRequest;
import com.vscoding.apps.yugioh.entity.YugiohCardCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YugiohCollectionService {
  private YugiohCardParser parser;

  public List<String> createCollection(YugiohCreationRequest request) {
    var collection = new YugiohCardCollection();

    collection.setName(request.name());
    collection.setDescription(request.description());
    collection.setCards(new ArrayList<>());

    var unparsable = new ArrayList<String>();

    for (var cardName : request.cards()) {
      var card = parser.parse(cardName);

      if (card == null) {
        unparsable.add(cardName);
      } else {
        collection.getCards().add(card);
      }
    }

    return unparsable;
  }
}
