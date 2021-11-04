package com.vscoding.urlshortner.entity;

import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<UrlModel,UrlId> {
}
