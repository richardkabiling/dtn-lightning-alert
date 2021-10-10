package com.github.richardkabiling.dtn.lightning.alert;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssetLightningAlertPublisherTest {

  AssetLightningAlertPublisher publisher;

  ByteArrayOutputStream out;

  PrintWriter writer;

  @BeforeEach
  void beforeEach() {
    this.out = new ByteArrayOutputStream();
    this.writer = new PrintWriter(out);
    this.publisher = new AssetLightningAlertPublisher(writer);
  }

  @AfterEach
  void afterEach() throws IOException {
    this.writer.close();
    this.out.close();
  }

  @Test
  void testPublish() {
    var alert = LightningStrikeAlertFixtures.alerts()
        .get(0);

    var result = publisher.publish(alert).block();
    writer.flush();
    var actual = out.toString();

    assertThat(result).isEqualTo(result);
    assertThat(actual).isEqualTo("lightning alert for 003:Glenda Circle\n");
  }

}