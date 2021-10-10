package com.github.richardkabiling.dtn.lightning.alert;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.List;
import java.util.function.Supplier;
import reactor.core.publisher.Mono;

public class AssetStore {

  private Cache<String, Asset> cache;

  public AssetStore(Cache<String, Asset> cache, Supplier<List<Asset>> assetsSupplier) {
    this.cache = cache;
    assetsSupplier.get()
        .forEach(a -> cache.put(a.quadKey(), a));
  }

  public Mono<Asset> getAsset(String quadKey) {
    return Mono.justOrEmpty(cache.getIfPresent(quadKey));
  }

}
