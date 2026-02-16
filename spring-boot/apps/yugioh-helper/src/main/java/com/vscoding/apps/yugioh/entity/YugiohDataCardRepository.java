package com.vscoding.apps.yugioh.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YugiohDataCardRepository extends JpaRepository<YugiohDataCard, Long> {

  Optional<YugiohDataCard> findTopByName(String name);

  Page<YugiohDataCard> findAllByNameContainingIgnoreCaseOrNameEnContainingIgnoreCase(String query, String queryEn, Pageable pageable);
}
