package com.vscoding.apps.yugioh.boundary.bean;

import java.util.List;

public record YugiohDeckCreationRequest(String id,
                                        String name,
                                        String description,
                                        List<Long> main,
                                        List<Long> extra,
                                        List<Long> side) {
}
