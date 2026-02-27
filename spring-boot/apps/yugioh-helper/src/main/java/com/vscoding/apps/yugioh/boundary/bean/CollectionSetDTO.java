package com.vscoding.apps.yugioh.boundary.bean;

public record CollectionSetDTO(String setCode, String setName, int collectedCardCount, int collectedUniqueCardCount, int setCardCount) {
}
