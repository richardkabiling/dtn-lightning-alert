package com.github.richardkabiling.dtn.lightning.alert;

import static com.github.richardkabiling.dtn.lightning.alert.LightningStrikeAlertFixtures.alerts;
import static com.github.richardkabiling.dtn.lightning.alert.LightningStrikeAlertFixtures.lightningStrikes;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.DefaultApplicationArguments;
import reactor.core.publisher.Flux;


@ExtendWith(MockitoExtension.class)
class ApplicationTest {

  @InjectMocks
  Application application;

  @Mock
  LightningStrikeFluxFactory factory;

  @Mock
  LightningStrikeAlertService service;

  @Test
  void testRun_connectsInputLightningStrikesWithAlertService() throws Exception {
    var lightningStrikes = Flux.fromIterable(lightningStrikes());
    when(factory.getLightningStrikeFlux()).thenReturn(lightningStrikes);

    var alerts = Flux.fromIterable(alerts());
    when(service.process(lightningStrikes)).thenReturn(alerts);

    application.run(new DefaultApplicationArguments());

    verify(service).process(lightningStrikes);
  }
}