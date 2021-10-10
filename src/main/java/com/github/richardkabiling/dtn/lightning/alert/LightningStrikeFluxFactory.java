package com.github.richardkabiling.dtn.lightning.alert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class LightningStrikeFluxFactory {

  public static final String QUIT_TOKEN = "quit";
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final ObjectMapper objectMapper;
  private final BufferedReader reader;

  public LightningStrikeFluxFactory(ObjectMapper objectMapper, BufferedReader reader) {
    this.objectMapper = objectMapper;
    this.reader = reader;
  }

  public Flux<LightningStrike> getLightningStrikeFlux() {
    return linesFlux()
        .flatMap(this::parse);
  }

  private Flux<String> linesFlux() {
    return Flux.generate(sink -> {
      try {
        var line = reader.readLine();
        if (line != null && !line.equals(QUIT_TOKEN)) {
          sink.next(line);
        } else {
          sink.complete();
        }
      } catch (IOException e) {
        sink.error(e);
      }
    });
  }

  private Mono<LightningStrike> parse(String line) {
    try {
      return Mono.just(objectMapper.readValue(line, LightningStrike.class));
    } catch (JsonProcessingException e) {
      logger.warn("Failed to parse lightning strike reading. Skipping.", e);
      return Mono.empty();
    }
  }

}
