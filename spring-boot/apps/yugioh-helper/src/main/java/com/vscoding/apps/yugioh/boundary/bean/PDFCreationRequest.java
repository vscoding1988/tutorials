package com.vscoding.apps.yugioh.boundary.bean;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PDFCreationRequest(float marginLeft, float marginTop,
                                 float horizontalSpacing, float verticalSpacing,
                                 float cardWidthMM, float cardHeightMM,
                                 List<MultipartFile> images) {
}
