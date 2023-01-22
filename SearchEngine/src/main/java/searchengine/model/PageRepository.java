package searchengine.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends CrudRepository<Page, Integer> {
    Page findByPath(String path);
    List<Page> findBySiteId (Site site);

}
