package com.vscoding.apps.yugioh.boundary.bean;

import java.util.List;

public record YugiohCollectionSearchResponse(long hits, List<CollectionCardDTO> items) {
}
