package com.vscoding.urlshortener.control;

import com.vscoding.urlshortener.entity.StatisticsModel;
import com.vscoding.urlshortener.entity.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Merges statistics to reduce the number of entries in the database. The job runs once a day.
 * The rules are:
 * - Merge last 7 days data to 15 Minutes chunks.
 * - Merge last 30 days to 1 Hour chunks.
 * - Merge last 365 days to one day chunks.
 * - Merge older data one entry.
 * For more information see readme.md
 */
@Profile("!disable-merger")
@Service
@RequiredArgsConstructor
public class StatisticsMerger {
  private final StatisticsRepository statisticsRepository;

  /**
   * Merges the statistics. The job runs once a day at midnight.
   */
  @Scheduled(cron = "0 0 0 * * *")
  public void merge() {
    mergeThisYear();
    mergeLastMonth();
    mergeLastWeek();
  }

  /**
   * Merges the last 7 days data to 15 minutes chunks. If a call is made at 12:07 it will be added to the chunk 12:15.
   */
  protected void mergeLastWeek() {
    var lastHour = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    var lastWeek = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS)
            .minusDays(7)
            .plusMinutes(1);

    var lastWeekStats = getStatisticsMapForTimeFrame(lastWeek, lastHour);

    lastWeekStats.forEach((key, value) -> {
      var summaryByTime = value.stream()
              .collect(Collectors.groupingBy(stat -> {
                        var minute = stat.getTimestamp().truncatedTo(ChronoUnit.MINUTES).getMinute();
                        var minuteRounded = minute - (minute % 15) + 15;

                        return stat.getTimestamp().truncatedTo(ChronoUnit.HOURS).plusMinutes(minuteRounded);
                      }
              ));
      merge(summaryByTime);
    });
  }

  /**
   * Merges the last year (excluding last month) data to 1 day chunks. If a call is made at 02.12.2024 12:07 it will be added to the chunk 02.12.2024.
   */
  protected void mergeThisYear() {
    var oneYearAgo = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusYears(1);
    var oneMothAgo = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS)
            .minusDays(30);

    var lastMonthStats = getStatisticsMapForTimeFrame(oneYearAgo, oneMothAgo);

    lastMonthStats.forEach((key, value) -> {
      var summaryByTime = value.stream()
              .collect(Collectors.groupingBy(stat -> stat.getTimestamp().truncatedTo(ChronoUnit.DAYS)));
      merge(summaryByTime);
    });
  }

  /**
   * Merges the last month (excluding last 7 days) data to 1 hour chunks. If a call is made at 12:07 it will be added to the chunk 13:00.
   */
  protected void mergeLastMonth() {
    var sevenDaysAgo = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).minusDays(7);
    var oneMothAgo = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS)
            .minusDays(30)
            .plusMinutes(1);

    var lastMonthStats = getStatisticsMapForTimeFrame(oneMothAgo, sevenDaysAgo);

    lastMonthStats.forEach((key, value) -> {
      var summaryByTime = value.stream()
              .collect(Collectors.groupingBy(stat -> stat.getTimestamp().truncatedTo(ChronoUnit.HOURS).plusHours(1)));
      merge(summaryByTime);
    });
  }

  /**
   * Merge the statistics by time. Each entry should be merged to one record
   *
   * @param summaryByTime map with time as key and list of statistics as value
   */
  private void merge(Map<LocalDateTime, List<StatisticsModel>> summaryByTime) {
    summaryByTime.forEach((time, stats) -> {
      if (stats.size() == 1) {
        // there is nothing to combine, so we can keep date unchanged
        return;
      }

      // Combine all statistics to the first entry and set the timestamp to the time
      var first = stats.get(0);
      var sum = stats.stream().skip(1).mapToInt(StatisticsModel::getCount).sum();

      first.setCount(first.getCount() + sum);
      first.setTimestamp(time);

      statisticsRepository.save(first);
      stats.stream().skip(1).forEach(statisticsRepository::delete);
    });
  }

  /**
   * Get the statistics map for a given time frame, grouped by domain and short url.
   *
   * @param from start time
   * @param to   end time
   * @return map with domain/shortUrl as key and a list of statistics as value
   */
  private Map<String, List<StatisticsModel>> getStatisticsMapForTimeFrame(LocalDateTime from, LocalDateTime to) {
    var result = new HashMap<String, List<StatisticsModel>>();
    var stats = statisticsRepository.findAllByTimestampBetweenOrderByTimestamp(from, to);

    for (var stat : stats) {
      var key = stat.getDomain() + "/" + stat.getShortUrl();

      if (!result.containsKey(key)) {
        result.put(key, new ArrayList<>());
      }

      result.get(key).add(stat);
    }

    return result;
  }
}
