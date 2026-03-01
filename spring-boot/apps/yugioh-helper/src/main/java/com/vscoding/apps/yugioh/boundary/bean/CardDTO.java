package com.vscoding.apps.yugioh.boundary.bean;

import java.util.List;

public record CardDTO(long id, String name, String type, List<SetDTO> sets, int count) {
}
