package searchengine.dto.statistics;

import lombok.Data;

@Data
public class IndexResponseError extends IndexResponse {
    private String error;
}
