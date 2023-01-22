package searchengine.dto.statistics;

import lombok.Data;

import java.util.List;

@Data
public class SearchData {
    List<DetailedSearchData> detailed;
}
