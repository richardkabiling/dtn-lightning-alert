package com.github.richardkabiling.dtn.lightning.alert;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.richardkabiling.dtn.lightning.alert.config.ApplicationConfig;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
    Application.class,
    ApplicationConfig.class,
    ApplicationITConfig.class
})
class ApplicationIT {

  @Autowired
  Application application;

  @Autowired
  ByteArrayOutputStream os;

  @Autowired
  PrintWriter printWriter;

  @Test
  void testWires() {
    assertThat(application).isNotNull();
  }

  @Test
  void testOutput() throws IOException, InterruptedException {
    var expected = """
        lightning alert for 003:Glenda Circle
        lightning alert for 5014:Edie Lodge
        lightning alert for 59276:Stefanie Street
        lightning alert for 9896:Milburn Loop
        lightning alert for 31010:Beer Freeway
        """;

    printWriter.flush();
    assertThat(os.toString()).isEqualTo(expected);
  }


}
