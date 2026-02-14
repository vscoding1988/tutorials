package com.vscoding.apps.yugioh.boundary.bean;

import java.util.List;

public record DeckDTO(String id, String name, String description, List<CardDTO> mainDeck, List<CardDTO> sideDeck, List<CardDTO> extraDeck) {
}
