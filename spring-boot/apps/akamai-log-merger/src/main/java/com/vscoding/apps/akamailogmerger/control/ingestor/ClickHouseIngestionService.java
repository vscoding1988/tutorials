package com.vscoding.apps.akamailogmerger.control.ingestor;

import com.azure.storage.blob.models.BlobItem;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Date;
import java.util.List;

@Service
public class ClickHouseIngestionService implements Ingestor {

  private final RestClient restClient;
  private final boolean activate;

  public ClickHouseIngestionService(
          @Value("${application.ingestion.clickhouse}") boolean activate,
          @Value("${clickhouse.url}") String clickHouseUrl,
          @Value("${clickhouse.username}") String username,
          @Value("${clickhouse.password}") String password
  ) {
    this.activate = activate;
    this.restClient = RestClient.builder()
            .baseUrl(clickHouseUrl)
            .defaultHeaders(headers -> headers.setBasicAuth(username, password))
            .build();
  }

  @PostConstruct
  public void initClickHouseTables() {
    if (!activate) {
      return;
    }
    executeClickHouseQuery("""
            CREATE DATABASE IF NOT EXISTS logs
            """);

    executeClickHouseQuery("""
            CREATE TABLE IF NOT EXISTS logs.akamai_access
            (
                UA String,
                bytes String,
                cliIP String,
                country String,
                lastByte String,
                queryStr String,
                reqHost String,
                reqMethod String,
                reqPath String,
                reqTimeSec String,
                rspContentType String,
                statusCode String,
                totalBytes String,
                asn String,
                asnOrg String,
                transferTimeMSec String,
                version String,

                source_file String DEFAULT '',

                event_time DateTime64(3) MATERIALIZED fromUnixTimestamp64Milli(
                    toInt64(toFloat64OrZero(reqTimeSec) * 1000)
                ),

                status UInt16 MATERIALIZED toUInt16OrZero(statusCode),

                response_bytes UInt64 MATERIALIZED toUInt64OrZero(bytes),

                total_bytes UInt64 MATERIALIZED toUInt64OrZero(totalBytes),

                transfer_time_ms UInt32 MATERIALIZED toUInt32OrZero(transferTimeMSec)
            )
            ENGINE = MergeTree
            ORDER BY (event_time, reqHost, status)
            """);

    executeClickHouseQuery("""
            CREATE TABLE IF NOT EXISTS logs.imported_files
            (
                source_file String,
                imported_at DateTime DEFAULT now(),
                file_size UInt64,
                etag String
            )
            ENGINE = MergeTree
            ORDER BY source_file
            """);
  }

  public void ingest(Date date, String logData, List<BlobItem> blobItems) {
    if (!activate) {
      return;
    }

    var query = "INSERT INTO logs.akamai_access FORMAT JSONEachRow";


    restClient.post()
            .uri(uriBuilder -> uriBuilder
                    .path("/")
                    .queryParam("query", query)
                    .queryParam("input_format_skip_unknown_fields", "1")
                    .build())
            .body(logData)
            .retrieve()
            .toBodilessEntity();
  }

  private void executeClickHouseQuery(String query) {
    restClient.post()
            .uri(uriBuilder -> uriBuilder
                    .path("/")
                    .queryParam("query", query)
                    .build())
            .retrieve()
            .toBodilessEntity();
  }
}
