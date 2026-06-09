package com.vscoding.tutorial.spring.control.bean;

public record SharePointFilterConfig(String filter, String fields, String sort, String siteId, String listId,
                                     int pageSize) {
}
