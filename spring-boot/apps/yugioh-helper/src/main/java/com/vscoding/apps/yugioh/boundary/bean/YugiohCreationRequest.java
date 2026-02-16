package com.vscoding.apps.yugioh.boundary.bean;

import java.util.List;

public record YugiohCreationRequest(String name, String description, List<String> cards) {
}
