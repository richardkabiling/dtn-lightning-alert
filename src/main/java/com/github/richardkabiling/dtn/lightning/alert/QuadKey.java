package com.github.richardkabiling.dtn.lightning.alert;

import static org.geotools.tile.impl.bing.BingTileUtil.lonLatToPixelXY;
import static org.geotools.tile.impl.bing.BingTileUtil.pixelXYToTileXY;
import static org.geotools.tile.impl.bing.BingTileUtil.tileXYToQuadKey;

import java.util.Objects;

public record QuadKey(String key) {

  public QuadKey(String key) {
    this.key = Objects.requireNonNull(key);
  }

  public static final int IMPLICIT_ZOOM_LEVEL = 12;

  @Override
  public String toString() {
    return key;
  }

  public static QuadKey from(String key) {
    return new QuadKey(key);
  }

  public static QuadKey from(double latitude, double longitude) {
    var pixelXY = lonLatToPixelXY(longitude, latitude, IMPLICIT_ZOOM_LEVEL);
    var tileXY = pixelXYToTileXY(pixelXY[0], pixelXY[1]);
    return QuadKey.from(tileXYToQuadKey(tileXY[0], tileXY[1], IMPLICIT_ZOOM_LEVEL));
  }
}
