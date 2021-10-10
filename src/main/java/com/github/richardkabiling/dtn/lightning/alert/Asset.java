package com.github.richardkabiling.dtn.lightning.alert;

import java.util.Objects;

public record Asset(String assetName,
                    String quadKey,
                    String assetOwner) {

  public Asset(String assetName, String quadKey, String assetOwner) {
    this.assetName = Objects.requireNonNull(assetName);
    this.quadKey = Objects.requireNonNull(quadKey);
    this.assetOwner = Objects.requireNonNull(assetOwner);
  }
}
