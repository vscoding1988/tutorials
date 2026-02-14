package com.vscoding.apps.yugioh.boundary.bean;

import java.util.List;

public record YugiohDeckCreationResponse(DeckDTO deck, List<String> unparsable) {
}
