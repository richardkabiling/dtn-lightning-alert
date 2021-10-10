package com.github.richardkabiling.dtn.lightning.alert;

import com.github.richardkabiling.dtn.lightning.alert.LightningStrike.FlashType;

public record AssetLightningAlert(String quadKey,
                                  String assetName,
                                  String assetOwner,
                                  FlashType flashType,
                                  double latitude,
                                  double longitude,
                                  long strikeTime,
                                  int peakAmps,
                                  String reserved,
                                  int icHeight,
                                  long receivedTime,
                                  int numberOfSensors,
                                  int multiplicity) {

}
