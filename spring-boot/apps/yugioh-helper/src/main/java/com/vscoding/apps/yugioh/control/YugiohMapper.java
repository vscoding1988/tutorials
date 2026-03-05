package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.boundary.bean.*;
import com.vscoding.apps.yugioh.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class YugiohMapper {
  private static final Pattern SET_CODE_PATTERN = Pattern.compile("^([A-Za-z0-9]+(?:-[A-Za-z]+)*)-?\\d{3}$");
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
            src.getCardSets().stream().map(s -> this.map(s, cardInCollection)).toList()
    );
  }

  public CardDTO mapLazy(YugiohCardCollectionWrapper src) {
    var card = src.getCard();

    return new CardDTO(
            card.getId(),
            card.getName(),
            card.getType(),
            card.getCardSets().stream().map(s -> this.map(s, List.of(src))).toList()
    );
  }

  public CardDTO map(YugiohDataCard src) {
    return mapLazy(src);
  }

  public SetDTO map(YugiohDataSet src, List<YugiohCardCollectionWrapper> wrapper) {
    var count = wrapper.stream()
            .filter(w -> w.getSet().equals(src))
            .findFirst()
            .map(YugiohCardCollectionWrapper::getCount)
            .orElse(0);

    return new SetDTO(
            src.getSetName(),
            getSetCode(src),
            count
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

  /**
   * Possible set Ids:
   * - SDY-001
   * - SDY-E001
   * - SDY-EN001
   *
   * @param set set to get code from
   * @return code of the set, e.g. SDY, or SDY-E, or SDY-EN
   */
  protected String getSetCode(YugiohDataSet set) {
    var matcher = SET_CODE_PATTERN.matcher(set.getSetCode());

    if (matcher.matches()) {
      return matcher.group(1);
    }

    return set.getSetCode();
  }
}
