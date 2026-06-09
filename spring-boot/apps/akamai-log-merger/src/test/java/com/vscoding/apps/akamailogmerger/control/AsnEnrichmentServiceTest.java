package com.vscoding.apps.akamailogmerger.control;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsnEnrichmentServiceTest {

  @Test
  void test() throws Exception{
    // Given
    var log = "{cliIP:\"8.8.8.8\"}\n{cliIP:\"1.1.1.1\"}";

    // When
    var enrich = new AsnEnrichmentService().enrich(log);

    // Then
    var split = enrich.split("\n");


    assertEquals("{\"cliIP\":\"8.8.8.8\",\"asn\":15169,\"asnOrg\":\"Google LLC\"}",split[0]);
    assertEquals("{\"cliIP\":\"1.1.1.1\",\"asn\":13335,\"asnOrg\":\"Cloudflare, Inc.\"}",split[1]);

  }
}
