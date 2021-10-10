package com.github.richardkabiling.dtn.lightning.alert;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
class ApplicationITConfig {

  @Primary
  @Bean
  public Supplier<List<Asset>> testAssetsSupplier() {
    return LightningStrikeAlertFixtures::assets;
  }

  @Bean
  public ByteArrayOutputStream testByteArrayOutputStream() {
    return new ByteArrayOutputStream();
  }

  @Primary
  @Bean
  public AssetLightningAlertPublisher testAssetLightningAlertPublisher(PrintWriter writer) {
    return new AssetLightningAlertPublisher(writer);
  }

  @Primary
  @Bean
  public PrintWriter printWriter(ByteArrayOutputStream outputStreamSupplier) {
    return new PrintWriter(outputStreamSupplier);
  }

  @Primary
  @Bean
  public LightningStrikeFluxFactory testLightningStrikeFluxFactory(ObjectMapper objectMapper) {
    return new LightningStrikeFluxFactory(objectMapper, new BufferedReader(new StringReader(inputString())));
  }

  private String inputString() {
    return """
        {"flashType":1,"strikeTime":1446761098753,"latitude":32.8501354,"longitude":-98.6268688,"peakAmps":2707,"reserved":"000","icHeight":12926,"receivedTime":1446761110182,"numberOfSensors":19,"multiplicity":20}
        {"flashType":1,"strikeTime":1446761099160,"latitude":33.217524,"longitude":-97.9474601,"peakAmps":2491,"reserved":"000","icHeight":18197,"receivedTime":1446761111198,"numberOfSensors":18,"multiplicity":21}
        {"flashType":1,"strikeTime":1446761100302,"latitude":33.1563258,"longitude":-97.6717756,"peakAmps":-695,"reserved":"000","icHeight":18305,"receivedTime":1446761112250,"numberOfSensors":7,"multiplicity":1}
        abcde
        {"flashType":1,"strikeTime":1446761109891,"latitude":33.8897226,"longitude":-96.4850085,"peakAmps":1589,"reserved":"000","icHeight":15275,"receivedTime":1446761121392,"numberOfSensors":14,"multiplicity":18}
        vwxyz
        {"flashType":1,"strikeTime":1446761112829,"latitude":35.2188571,"longitude":-96.0724577,"peakAmps":-4663,"reserved":"000","icHeight":14359,"receivedTime":1446761126430,"numberOfSensors":20,"multiplicity":2}
        """;
  }

}
