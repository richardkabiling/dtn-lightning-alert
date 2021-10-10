package com.github.richardkabiling.dtn.lightning.alert;

import static com.github.richardkabiling.dtn.lightning.alert.LightningStrikeAlertFixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AssetStoreTest {

  List<Asset> assets;

  AssetStore store;

  @BeforeEach
  void beforeEach() {
    assets = assets();
    store = new AssetStore(Caffeine.newBuilder().build(), () -> assets);
  }

  @ParameterizedTest
  @MethodSource
  void testGet(String quadKey, Asset expectedResult) {
    var result = store.getAsset(quadKey).block();

    assertThat(result).isEqualTo(expectedResult);
  }

  static Stream<Arguments> testGet() {
    var assets = assets();

    return Stream.of(
        arguments(assets.get(0).quadKey(), assets.get(0)),
        arguments(assets.get(1).quadKey(), assets.get(1)),
        arguments(assets.get(2).quadKey(), assets.get(2)),
        arguments("non-existent key", null)
    );
  }
}