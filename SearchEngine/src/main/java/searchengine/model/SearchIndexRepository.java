package searchengine.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchIndexRepository extends CrudRepository<SearchIndex, Integer> {
    List<SearchIndex> findByLemmaId(Lemma lemma);
    List<SearchIndex> findByPageId(Page page);

    SearchIndex findByLemmaIdAndPageId(Lemma lemma, Page page);
}
