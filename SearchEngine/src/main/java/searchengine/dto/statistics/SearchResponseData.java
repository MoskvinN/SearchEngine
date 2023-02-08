package searchengine.dto.statistics;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponseData extends SearchResponse{
    private int count;
    private List<DetailedSearchData> data;
}
