package com.vscoding.jpa.entity;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface DB connection
 */
public interface ProductRepository extends CrudRepository<ProductEntity,Integer> {
}
