package com.vscoding.apps.yugioh.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YugiohDataCardRepository extends JpaRepository<YugiohDataCard,Long> {

  Optional<YugiohDataCard> findTopByName(String name);
}
