package searchengine.dto.statistics;

import lombok.Data;

@Data
public class SearchResponseData extends SearchResponse{
    private int count;
    private SearchData searchData;
}
