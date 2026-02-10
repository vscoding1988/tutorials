package com.vscoding.apps.yugioh.boundary.bean;

import java.util.List;

public record YugiohDeckCreationRequest(String name, List<String> cards) {
}
