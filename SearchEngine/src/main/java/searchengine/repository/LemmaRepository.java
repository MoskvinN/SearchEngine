package searchengine.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import searchengine.model.Lemma;
import searchengine.model.Site;

import java.util.List;

public interface LemmaRepository extends CrudRepository<Lemma, Integer> {
    Lemma findByLemma(String lemma);
    List<Lemma> findBySiteId(Site site);

    Lemma findByLemmaAndSiteId(String lemma, Site site);
}
