package searchengine.model;

import javax.persistence.*;

@Entity
public class SearchIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "page_id", nullable = false)
    private Page pageId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lemma_id", nullable = false)
    private Lemma lemmaId;

    @Column(nullable = false)
    private float freqRank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Page getPageId() {
        return pageId;
    }

    public void setPageId(Page pageId) {
        this.pageId = pageId;
    }

    public Lemma getLemmaId() {
        return lemmaId;
    }

    public void setLemmaId(Lemma lemmaId) {
        this.lemmaId = lemmaId;
    }

    public float getFreqRank() {
        return freqRank;
    }

    public void setFreqRank(float freqRank) {
        this.freqRank = freqRank;
    }
}
