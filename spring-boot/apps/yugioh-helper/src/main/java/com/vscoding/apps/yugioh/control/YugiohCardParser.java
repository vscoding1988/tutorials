package com.vscoding.apps.yugioh.control;

import com.vscoding.apps.yugioh.entity.YugiohCardCollectionWrapper;
import com.vscoding.apps.yugioh.entity.YugiohDataCard;
import com.vscoding.apps.yugioh.entity.YugiohDataCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class YugiohCardParser {
  private final YugiohDataCardRepository repository;

  public Optional<YugiohDataCard> findCard(String name) {
    // Check if it is only ID
    try {
      var id = Long.parseLong(name);
      return repository.findById(id);
    } catch (Exception e) {
      // DO NOTHING
    }

    // Check only by name
    return repository.findTopByName(name);
  }

  /**
   * For now, I expect the following patterns
   * - $count x $cardName/id $rarity $set
   * - $count $cardName/id $rarity $set
   * - $count x $cardName/id $rarity
   * - $count $cardName/id $rarity
   * - $count x $cardName/id
   * - $count $cardName/id
   * - $cardName/id
   * @param cardName card definition in supported format
   * @return parsed card data
   */
  public YugiohCardCollectionWrapper parse(String cardName) {
    return null;
  }
}
