package com.vscoding.urlshortener.control;

import com.vscoding.urlshortener.entity.StatisticsModel;
import com.vscoding.urlshortener.entity.StatisticsRepository;
import com.vscoding.urlshortener.entity.UrlId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticsMergerTest {
  @Mock
  private StatisticsRepository statisticsRepository;

  @InjectMocks
  private StatisticsMerger statisticsMerger;

  @Test
  void mergeLastWeek_empty() {
    // Given
    List<StatisticsModel> stats = Collections.emptyList();

    // Behavior
    when(statisticsRepository.findAllByTimestampBetweenOrderByTimestamp(any(), any())).thenReturn(stats);

    // When
    statisticsMerger.mergeLastWeek();

    // Then
    verify(statisticsRepository).findAllByTimestampBetweenOrderByTimestamp(any(), any());
    verify(statisticsRepository, never()).save(any());
  }

  @Test
  void mergeLastWeek_notNeeded() {
    // Given
    var sixDayAgo = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).minusDays(6);

    var statistics1 = getStatisticsModel("url1", sixDayAgo.withMinute(12));
    var statistics2 = getStatisticsModel("url1", sixDayAgo.withMinute(22));
    List<StatisticsModel> stats = List.of(statistics1, statistics2);

    // Behavior
    when(statisticsRepository.findAllByTimestampBetweenOrderByTimestamp(any(), any())).thenReturn(stats);

    // When
    statisticsMerger.mergeLastWeek();

    // Then
    verify(statisticsRepository).findAllByTimestampBetweenOrderByTimestamp(any(), any());
    verify(statisticsRepository, never()).save(any());
  }

  @Test
  @DisplayName("Merge two entries to one")
  void mergeLastWeek_mergeNeeded() {
    // Given
    var sixDayAgo = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).minusDays(6);

    var statistics1 = getStatisticsModel("url1", sixDayAgo.withMinute(12));
    var statistics2 = getStatisticsModel("url1", sixDayAgo.withMinute(13));
    List<StatisticsModel> stats = List.of(statistics1, statistics2);

    // Behavior
    when(statisticsRepository.findAllByTimestampBetweenOrderByTimestamp(any(), any())).thenReturn(stats);

    // When
    statisticsMerger.mergeLastWeek();

    // Then
    verify(statisticsRepository).findAllByTimestampBetweenOrderByTimestamp(any(), any());
    verify(statisticsRepository, times(1)).save(statistics1);
    verify(statisticsRepository, times(1)).delete(statistics2);

    assertEquals(2, statistics1.getCount());
    assertEquals(sixDayAgo.withMinute(15), statistics1.getTimestamp());
  }

  @Test
  @DisplayName("Merge two entries to one, which are older than a week")
  void mergeLastMonth_mergeNeeded() {
    // Given
    var twoWeeksAgo = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).minusDays(14);

    var statistics1 = getStatisticsModel("url1", twoWeeksAgo.withMinute(1));
    var statistics2 = getStatisticsModel("url1", twoWeeksAgo.withMinute(57));
    List<StatisticsModel> stats = List.of(statistics1, statistics2);

    // Behavior
    when(statisticsRepository.findAllByTimestampBetweenOrderByTimestamp(any(), any())).thenReturn(stats);

    // When
    statisticsMerger.mergeLastMonth();

    // Then
    verify(statisticsRepository).findAllByTimestampBetweenOrderByTimestamp(any(), any());
    verify(statisticsRepository, times(1)).save(statistics1);
    verify(statisticsRepository, times(1)).delete(statistics2);

    var startOfNextHour = twoWeeksAgo.withMinute(0).withHour(twoWeeksAgo.getHour() + 1);
    assertEquals(startOfNextHour, statistics1.getTimestamp());
    assertEquals(2, statistics1.getCount());
  }

  @Test
  @DisplayName("Merge two entries to one, which are older than a month")
  void mergeThisYear_mergeNeeded() {
    // Given
    var twoMonthsAgo = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(60);

    var statistics1 = getStatisticsModel("url1", twoMonthsAgo.withHour(3));
    var statistics2 = getStatisticsModel("url1", twoMonthsAgo.withHour(7));
    List<StatisticsModel> stats = List.of(statistics1, statistics2);

    // Behavior
    when(statisticsRepository.findAllByTimestampBetweenOrderByTimestamp(any(), any())).thenReturn(stats);

    // When
    statisticsMerger.mergeThisYear();

    // Then
    verify(statisticsRepository).findAllByTimestampBetweenOrderByTimestamp(any(), any());
    verify(statisticsRepository, times(1)).save(statistics1);
    verify(statisticsRepository, times(1)).delete(statistics2);

    assertEquals(twoMonthsAgo, statistics1.getTimestamp());
    assertEquals(2, statistics1.getCount());
  }

  @Test
  @DisplayName("Do not merge entries with different urls")
  void mergeLastWeek_mergeNotNeededDifferentUrls() {
    // Given
    var sixDayAgo = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).minusDays(6);

    var statistics1 = getStatisticsModel("url1", sixDayAgo.withMinute(12));
    var statistics2 = getStatisticsModel("url2", sixDayAgo.withMinute(13));
    List<StatisticsModel> stats = List.of(statistics1, statistics2);

    // Behavior
    when(statisticsRepository.findAllByTimestampBetweenOrderByTimestamp(any(), any())).thenReturn(stats);

    // When
    statisticsMerger.mergeLastWeek();

    // Then
    verify(statisticsRepository).findAllByTimestampBetweenOrderByTimestamp(any(), any());
    verify(statisticsRepository, never()).save(statistics1);
    verify(statisticsRepository, never()).delete(statistics2);
  }

  private StatisticsModel getStatisticsModel(String shortUrl, LocalDateTime timestamp) {
    return new StatisticsModel(new UrlId("r", shortUrl), "") {{
      setTimestamp(timestamp);
    }};
  }
}
