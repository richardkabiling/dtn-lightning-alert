package com.github.richardkabiling.dtn.lightning.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final LightningStrikeFluxFactory fluxFactory;
	private final LightningStrikeAlertService service;

	public Application(LightningStrikeFluxFactory fluxFactory,
			LightningStrikeAlertService service) {
		this.fluxFactory = fluxFactory;
		this.service = service;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		var lightningStrikeFlux = fluxFactory.getLightningStrikeFlux();
		service.process(lightningStrikeFlux)
			.subscribe(alert -> {},
					error -> logger.error("Unexpected error encountered", error),
					() -> logger.info("Done."));
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
