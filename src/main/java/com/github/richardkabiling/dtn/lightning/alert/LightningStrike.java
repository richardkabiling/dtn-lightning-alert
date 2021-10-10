package com.github.richardkabiling.dtn.lightning.alert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;

public record LightningStrike(FlashType flashType,
                              long strikeTime,
                              double latitude,
                              double longitude,
                              int peakAmps,
                              String reserved,
                              int icHeight,
                              long receivedTime,
                              int numberOfSensors,
                              int multiplicity) {

  public enum FlashType {
    CLOUD_TO_GROUND(0),
    CLOUD_TO_CLOUD(1),
    HEARTBEAT(9);

    private final int value;

    FlashType(int value) {
      this.value = value;
    }

    @JsonValue
    public int getValue() {
      return value;
    }

    @JsonCreator
    public static FlashType fromValue(int value) {
      return Stream.of(FlashType.values())
          .filter(type -> type.getValue() == value)
          .findFirst()
          .orElse(null);
    }
  }



}
