package com.vscoding.tutorial.spring.control;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

/**
 * See https://docs.microsoft.com/en-us/azure/azure-monitor/logs/data-collector-api#java-sample
 */
@Slf4j
@Service
public class AzureClient {

  private static final String AUTHORISATION_HEADER = "POST\n%s\napplication/json\nx-ms-date:%s\n/api/logs";
  private static final String RFC_1123_DATE = "EEE, dd MMM yyyy HH:mm:ss z";


  /**
   * Log analytics workspace id
   */
  private final String workspaceId;

  /**
   * Use either the primary or the secondary Connected Sources client authentication key
   */
  private final String connectionKey;

  public AzureClient(
          @Value("${app.azure.workspaceId:}") String workspaceId,
          @Value("${app.azure.connectionKey:}") String connectionKey) {
    this.workspaceId = workspaceId;
    this.connectionKey = connectionKey;
  }

  public void sendLogs(String logName, String json) throws Exception {
    var httpPost = getPost(logName, json);

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      var response = httpClient.execute(httpPost);
      int statusCode = response.getStatusLine().getStatusCode();

      log.info("Logs send with StatusCode: {}", statusCode);
    }
  }

  private HttpPost getPost(String logName, String json) throws Exception {
    var url =
            "https://" + workspaceId + ".ods.opinsights.azure.com/api/logs?api-version=2016-04-01";
    var date = getServerTime();
    var httpPost = new HttpPost(url);

    httpPost.setHeader("Authorization", getAuthorisation(date, json));
    httpPost.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    httpPost.setHeader("Log-Type", logName);
    httpPost.setHeader("x-ms-date", date);
    httpPost.setEntity(new StringEntity(json));

    return httpPost;
  }

  /**
   * Get server time for header
   *
   * @return server time as RFC_1123 date
   */
  private String getServerTime() {
    var calendar = Calendar.getInstance();
    var dateFormat = new SimpleDateFormat(RFC_1123_DATE, Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(calendar.getTime());
  }

  private String getAuthorisation(String date, String json) throws Exception {
    var stringToHash = String.format(AUTHORISATION_HEADER,
            json.getBytes(StandardCharsets.UTF_8).length,
            date);

    var hashedString = getHMAC256(stringToHash);
    return "SharedKey " + workspaceId + ":" + hashedString;
  }

  /**
   * Will create a hashedString for authorisation
   *
   * @param input AUTHORISATION_HEADER with values
   * @return hashed HmacSHA256 string
   * @throws Exception encoding exceptions
   */
  private String getHMAC256(String input) throws NoSuchAlgorithmException, InvalidKeyException {
    var sha256HMAC = Mac.getInstance("HmacSHA256");
    var secretKey = new SecretKeySpec(
            Base64.getDecoder().decode(connectionKey.getBytes(StandardCharsets.UTF_8)), "HmacSHA256");
    sha256HMAC.init(secretKey);

    return new String(
            Base64.getEncoder().encode(sha256HMAC.doFinal(input.getBytes(StandardCharsets.UTF_8))));
  }
}
