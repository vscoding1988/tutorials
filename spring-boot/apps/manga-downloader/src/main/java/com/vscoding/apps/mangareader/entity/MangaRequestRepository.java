package com.vscoding.apps.mangareader.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MangaRequestRepository extends JpaRepository<MangaRequestEntity, Long> {

  Optional<MangaRequestEntity> findByName(String name);
}
