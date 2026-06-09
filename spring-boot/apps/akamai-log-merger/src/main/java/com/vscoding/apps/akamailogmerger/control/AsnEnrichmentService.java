package com.vscoding.apps.akamailogmerger.control;

import com.google.gson.JsonParser;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AsnResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AsnEnrichmentService {
  private static final String ASN_DB_PATH = "GeoLite2-ASN.mmdb";
  private final DatabaseReader reader;
  private final Map<String, AsnResponse> cache = new HashMap<>();

  public AsnEnrichmentService() throws IOException {
    var database = this.getClass().getClassLoader().getResourceAsStream(ASN_DB_PATH);
    this.reader = new DatabaseReader.Builder(database).build();
  }

  public String enrich(String dayLogShard) {
    return Arrays.stream(dayLogShard.split("\n"))
            .parallel()
            .filter(StringUtils::hasLength)
            .map(this::enrichLine)
            .reduce((a, b) -> a + "\n" + b)
            .orElse("");
  }

  private String enrichLine(String line) {
    var ob = JsonParser.parseString(line).getAsJsonObject();
    var ip = ob.get("cliIP").getAsString();

    try {
      var response = cache.get(ip);

      if (response == null) {
        var ipAddress = InetAddress.getByName(ip);
        response = reader.asn(ipAddress);

        cache.put(ip, response);
      }

      var asn = response.getAutonomousSystemNumber();
      var org = response.getAutonomousSystemOrganization();

      ob.addProperty("asn", asn);
      ob.addProperty("asnOrg", org);
      return ob.toString();
    } catch (IOException | GeoIp2Exception e) {
      cache.put(ip, new AsnResponse(0L, "Unknown", null, null));

      ob.addProperty("asn", 0);
      ob.addProperty("asnOrg", "Unknown");
    }

    return line;
  }
}
