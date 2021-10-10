package com.github.richardkabiling.dtn.lightning.alert;

import java.io.PrintWriter;
import reactor.core.publisher.Mono;

public class AssetLightningAlertPublisher {

  private final PrintWriter writer;

  public AssetLightningAlertPublisher(PrintWriter writer) {
    this.writer = writer;
  }

  public Mono<AssetLightningAlert> publish(AssetLightningAlert alert) {
    return Mono.defer(() -> {
      writer.println("lightning alert for %s:%s".formatted(alert.assetOwner(), alert.assetName()));
      return Mono.just(alert);
    });
  }

}
