package com.vscoding.apps.yugioh.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface YugiohCardCollectionWrapperRepository extends JpaRepository<YugiohCardCollectionWrapper, Long> {
  Optional<YugiohCardCollectionWrapper> findByCardAndSet(YugiohDataCard card, YugiohDataSet set);
}
