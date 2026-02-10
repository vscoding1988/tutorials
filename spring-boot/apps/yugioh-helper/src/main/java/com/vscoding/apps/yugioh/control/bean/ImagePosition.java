package com.vscoding.apps.yugioh.control.bean;

public class ImagePosition {
  private final float x;
  private final float y;
  private final float width;
  private final float height;

  public ImagePosition(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }
}
