package com.github.richardkabiling.dtn.lightning.alert;

import static com.github.richardkabiling.dtn.lightning.alert.LightningStrikeAlertFixtures.alerts;
import static com.github.richardkabiling.dtn.lightning.alert.LightningStrikeAlertFixtures.assets;
import static com.github.richardkabiling.dtn.lightning.alert.LightningStrikeAlertFixtures.lightningStrikes;
import static com.github.richardkabiling.dtn.lightning.alert.LightningStrikeAlertFixtures.lightningStrikesWithoutAsset;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class LightningStrikeAlertServiceTest {


  @InjectMocks
  LightningStrikeAlertService service;

  @Mock
  AssetLightningAlertPublisher publisher;

  @Mock
  AssetStore store;

  @Test
  void testProcess() {
    var strikes = Flux.fromIterable(lightningStrikes());

    var assets = assets();
    assets.forEach(a -> when(store.getAsset(a.quadKey())).thenReturn(Mono.just(a)));

    var alerts = alerts();
    alerts.forEach(a -> when(publisher.publish(a)).thenReturn(Mono.just(a)));

    var results = service.process(strikes)
        .collectList()
        .block();

    assertThat(results).isEqualTo(alerts);
    alerts.forEach(a -> verify(publisher, times(1)).publish(a));
  }

  @Test
  void testProcess_skipsStrikeWhenNoAssetStrikes() {
    var noAssetLightningStrikes = lightningStrikesWithoutAsset();
    var strikes = Flux.just(lightningStrikes(), noAssetLightningStrikes)
        .flatMap(Flux::fromIterable);

    var assets = assets();
    assets.forEach(a -> when(store.getAsset(a.quadKey())).thenReturn(Mono.just(a)));
    noAssetLightningStrikes.stream().map(ls -> QuadKey.from(ls.latitude(), ls.longitude()).key())
        .forEach(k -> when(store.getAsset(k)).thenReturn(Mono.empty()));


    var alerts = alerts();
    alerts.forEach(a -> when(publisher.publish(a)).thenReturn(Mono.just(a)));

    var results = service.process(strikes)
        .collectList()
        .block();

    assertThat(results).isEqualTo(alerts);
    alerts.forEach(a -> verify(publisher, times(1)).publish(a));
  }

  @Test
  void testProcess_publishesOnlyOnceWhenDuplicateStrikes() {
    var strikes = Flux.just(lightningStrikes(), lightningStrikes(), lightningStrikes())
        .flatMap(Flux::fromIterable);

    var assets = assets();
    assets.forEach(a -> when(store.getAsset(a.quadKey())).thenReturn(Mono.just(a)));

    var alerts = alerts();
    alerts.forEach(a -> when(publisher.publish(a)).thenReturn(Mono.just(a)));

    var results = service.process(strikes)
        .collectList()
        .block();

    assertThat(results).isEqualTo(alerts);
    alerts.forEach(a -> verify(publisher, times(1)).publish(a));
  }
}