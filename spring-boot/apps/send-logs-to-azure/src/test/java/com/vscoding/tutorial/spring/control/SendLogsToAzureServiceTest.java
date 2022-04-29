package com.vscoding.tutorial.spring.control;

import java.text.SimpleDateFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("credentials")
class SendLogsToAzureServiceTest {

  @Autowired
  private SendLogsToAzureService service;

  @Test
  @Disabled("miss used for local execution")
  void sendLogs() throws Exception{
    service.sendLogs();

    Assertions.assertTrue(true);
  }
}
