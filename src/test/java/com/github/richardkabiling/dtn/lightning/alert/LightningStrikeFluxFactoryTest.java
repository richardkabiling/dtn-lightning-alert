package com.github.richardkabiling.dtn.lightning.alert;

import static com.github.richardkabiling.dtn.lightning.alert.LightningStrikeAlertFixtures.lightningStrikes;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.Test;

class LightningStrikeFluxFactoryTest {

  ObjectMapper objectMapper = new ObjectMapper();

  LightningStrikeFluxFactory factory;


  @Test
  void testGet() {
    var source = """
        {"flashType":1,"strikeTime":1446761098753,"latitude":32.8501354,"longitude":-98.6268688,"peakAmps":2707,"reserved":"000","icHeight":12926,"receivedTime":1446761110182,"numberOfSensors":19,"multiplicity":20}
        {"flashType":1,"strikeTime":1446761099160,"latitude":33.217524,"longitude":-97.9474601,"peakAmps":2491,"reserved":"000","icHeight":18197,"receivedTime":1446761111198,"numberOfSensors":18,"multiplicity":21}
        {"flashType":1,"strikeTime":1446761100302,"latitude":33.1563258,"longitude":-97.6717756,"peakAmps":-695,"reserved":"000","icHeight":18305,"receivedTime":1446761112250,"numberOfSensors":7,"multiplicity":1}
        {"flashType":1,"strikeTime":1446761109891,"latitude":33.8897226,"longitude":-96.4850085,"peakAmps":1589,"reserved":"000","icHeight":15275,"receivedTime":1446761121392,"numberOfSensors":14,"multiplicity":18}
        {"flashType":1,"strikeTime":1446761112829,"latitude":35.2188571,"longitude":-96.0724577,"peakAmps":-4663,"reserved":"000","icHeight":14359,"receivedTime":1446761126430,"numberOfSensors":20,"multiplicity":2}
        """;

    factory = new LightningStrikeFluxFactory(objectMapper, new BufferedReader(new StringReader(source)));
    var results = factory.getLightningStrikeFlux()
        .collectList()
        .block();

    assertThat(results).isEqualTo(lightningStrikes());
  }

  @Test
  void testGet_endsOnQuitToken() {
    var source = """
        {"flashType":1,"strikeTime":1446761098753,"latitude":32.8501354,"longitude":-98.6268688,"peakAmps":2707,"reserved":"000","icHeight":12926,"receivedTime":1446761110182,"numberOfSensors":19,"multiplicity":20}
        {"flashType":1,"strikeTime":1446761099160,"latitude":33.217524,"longitude":-97.9474601,"peakAmps":2491,"reserved":"000","icHeight":18197,"receivedTime":1446761111198,"numberOfSensors":18,"multiplicity":21}
        {"flashType":1,"strikeTime":1446761100302,"latitude":33.1563258,"longitude":-97.6717756,"peakAmps":-695,"reserved":"000","icHeight":18305,"receivedTime":1446761112250,"numberOfSensors":7,"multiplicity":1}
        {"flashType":1,"strikeTime":1446761109891,"latitude":33.8897226,"longitude":-96.4850085,"peakAmps":1589,"reserved":"000","icHeight":15275,"receivedTime":1446761121392,"numberOfSensors":14,"multiplicity":18}
        {"flashType":1,"strikeTime":1446761112829,"latitude":35.2188571,"longitude":-96.0724577,"peakAmps":-4663,"reserved":"000","icHeight":14359,"receivedTime":1446761126430,"numberOfSensors":20,"multiplicity":2}
        quit
        """;

    factory = new LightningStrikeFluxFactory(objectMapper, new BufferedReader(new StringReader(source)));
    var results = factory.getLightningStrikeFlux()
        .collectList()
        .block();

    assertThat(results).isEqualTo(lightningStrikes());
  }

  @Test
  void testGet_skipsUnparseableLines() {
    var source = """
        {"flashType":1,"strikeTime":1446761098753,"latitude":32.8501354,"longitude":-98.6268688,"peakAmps":2707,"reserved":"000","icHeight":12926,"receivedTime":1446761110182,"numberOfSensors":19,"multiplicity":20}
        xyz
        {"flashType":1,"strikeTime":1446761100302,"latitude":33.1563258,"longitude":-97.6717756,"peakAmps":-695,"reserved":"000","icHeight":18305,"receivedTime":1446761112250,"numberOfSensors":7,"multiplicity":1}
        abc
        {"flashType":1,"strikeTime":1446761112829,"latitude":35.2188571,"longitude":-96.0724577,"peakAmps":-4663,"reserved":"000","icHeight":14359,"receivedTime":1446761126430,"numberOfSensors":20,"multiplicity":2}
        quit
        """;

    factory = new LightningStrikeFluxFactory(objectMapper, new BufferedReader(new StringReader(source)));
    var results = factory.getLightningStrikeFlux()
        .collectList()
        .block();

    var allStrikes = lightningStrikes();
    assertThat(results).isEqualTo(List.of(allStrikes.get(0), allStrikes.get(2), allStrikes.get(4)));
  }
}