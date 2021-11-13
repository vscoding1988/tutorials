package com.vscoding.jpa.db2.entity;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface DB connection
 */
public interface ProductRepository2 extends CrudRepository<ProductEntity2,Integer>,CustomRepository {
}
