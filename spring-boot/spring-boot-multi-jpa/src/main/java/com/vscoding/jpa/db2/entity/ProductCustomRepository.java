package com.vscoding.jpa.db2.entity;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * For using custom repository, you have to use qualifier for enitity manager and for transaction manager
 */
@Repository
@Transactional(transactionManager = "db2TransactionManager")
public class ProductCustomRepository extends SimpleJpaRepository<ProductEntity2, Integer> {
  private final EntityManager entityManager;

  public ProductCustomRepository(@Qualifier("db2EntityManagerFactory") EntityManager entityManager) {
    super(ProductEntity2.class, entityManager);
    this.entityManager = entityManager;
  }

  public List<ProductEntity2> customQuery() {
    var query = entityManager.createQuery("SELECT p FROM ProductEntity2 p WHERE p.name='special'",ProductEntity2.class);

    return query.getResultList();
  }
}
