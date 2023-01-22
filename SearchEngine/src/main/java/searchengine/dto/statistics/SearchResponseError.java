package searchengine.dto.statistics;

import lombok.Data;

@Data
public class SearchResponseError extends SearchResponse{
    private String error;
}
