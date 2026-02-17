package com.vscoding.apps.yugioh.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface YugiohDataSetRepository extends JpaRepository<YugiohDataSet, Long> {

  @Query("select i from YugiohDataSet i where i.id = (select min(i2.id) from YugiohDataSet i2 where i2.setName = i.setName)")
  List<YugiohDataSet> findOnePerName();
}
