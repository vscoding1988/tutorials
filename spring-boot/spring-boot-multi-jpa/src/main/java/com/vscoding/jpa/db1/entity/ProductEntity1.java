package com.vscoding.jpa.db1.entity;

import javax.persistence.*;

@Entity
public class ProductEntity1 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  public ProductEntity1() {
  }

  public ProductEntity1(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
