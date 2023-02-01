package searchengine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.IndexResponse;
import searchengine.dto.statistics.SearchResponse;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.StatisticsService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final StatisticsService statisticsService;

    public ApiController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<IndexResponse> startIndexing(){
        return ResponseEntity.ok(statisticsService.startIndexing());
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<IndexResponse> stopIndexing(){
        return ResponseEntity.ok(statisticsService.stopIndexing());
    }

    @PostMapping ("/indexPage")
    public ResponseEntity<IndexResponse> indexPage(@RequestParam String url){
        return ResponseEntity.ok(statisticsService.indexPage(url));
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(
            @RequestParam (name="query", required=false, defaultValue="") String query,
            @RequestParam(name="site", required=false, defaultValue="") String site) {
        return ResponseEntity.ok(statisticsService.search(query, site));
    }
}
