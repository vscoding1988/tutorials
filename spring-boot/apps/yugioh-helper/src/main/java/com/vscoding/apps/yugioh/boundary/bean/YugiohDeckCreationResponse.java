package com.vscoding.apps.yugioh.boundary.bean;

import com.vscoding.apps.yugioh.entity.YugiohDeck;

import java.util.List;

public record YugiohDeckCreationResponse(YugiohDeck deck, List<String> unparsable) {
}
