package searchengine.services;


import searchengine.dto.statistics.IndexResponse;
import searchengine.dto.statistics.SearchResponse;
import searchengine.dto.statistics.StatisticsResponse;

public interface StatisticsService {
    StatisticsResponse getStatistics();

    IndexResponse startIndexing();

    IndexResponse stopIndexing();

    IndexResponse indexPage(String url);

    SearchResponse search(String query, String url, int offset, int limit);
}
