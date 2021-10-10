package com.github.richardkabiling.dtn.lightning.alert.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.richardkabiling.dtn.lightning.alert.Asset;
import com.github.richardkabiling.dtn.lightning.alert.AssetLightningAlertPublisher;
import com.github.richardkabiling.dtn.lightning.alert.AssetStore;
import com.github.richardkabiling.dtn.lightning.alert.LightningStrikeFluxFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ApplicationConfig {

  @Bean
  public LightningStrikeFluxFactory lightningStrikeFluxFactory(ObjectMapper objectMapper) {
    return new LightningStrikeFluxFactory(objectMapper, standardInputBufferedReader());
  }

  @Bean(destroyMethod = "close")
  public BufferedReader standardInputBufferedReader() {
    return new BufferedReader(new InputStreamReader(System.in));
  }

  @Bean
  public AssetLightningAlertPublisher assetLightningAlertPublisher(PrintWriter printWriter) {
    return new AssetLightningAlertPublisher(printWriter);
  }

  @Bean(destroyMethod = "close")
  public PrintWriter standardOutputWriter() {
    return new PrintWriter(System.out);
  }

  @Bean
  public Supplier<List<Asset>> assetsSupplier(ObjectMapper objectMapper) {
    return () -> {
      try {
        var resource = new ClassPathResource("assets.json");
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Asset>>() {
        });
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    };
  }

  @Bean
  public AssetStore assetStore(Supplier<List<Asset>> assetsSupplier) {
    var underlyingCache = Caffeine.newBuilder()
        .<String, Asset>build();

    return new AssetStore(underlyingCache, assetsSupplier);
  }
}
