package com.github.richardkabiling.dtn.lightning.alert;

import com.github.richardkabiling.dtn.lightning.alert.LightningStrike.FlashType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LightningStrikeAlertService {

  private final AssetStore assetStore;
  private final AssetLightningAlertPublisher publisher;

  public LightningStrikeAlertService(AssetStore assetStore,
      AssetLightningAlertPublisher publisher) {
    this.assetStore = assetStore;
    this.publisher = publisher;
  }

  public Flux<AssetLightningAlert> process(Flux<LightningStrike> lightningStrikes) {
    return lightningStrikes
        .filter(ls -> ls.flashType() != FlashType.HEARTBEAT)
        .flatMap(this::joinWithAsset)
        .distinct(AssetLightningAlert::quadKey)
        .flatMap(publisher::publish);
  }

  private Mono<AssetLightningAlert> joinWithAsset(LightningStrike strike) {
    var quadKey = QuadKey.from(strike.latitude(), strike.longitude())
        .toString();
    return assetStore.getAsset(quadKey)
        .flatMap(asset -> joinWithAsset(asset, strike));
  }

  private Mono<AssetLightningAlert> joinWithAsset(Asset asset, LightningStrike strike) {
      return Mono.just(new AssetLightningAlert(asset.quadKey(),
          asset.assetName(),
          asset.assetOwner(),
          strike.flashType(),
          strike.latitude(),
          strike.longitude(),
          strike.strikeTime(),
          strike.peakAmps(),
          strike.reserved(),
          strike.icHeight(),
          strike.receivedTime(),
          strike.numberOfSensors(),
          strike.multiplicity()
      ));
  }

}
