package searchengine.model;

import javax.persistence.*;

@Entity
public class Lemma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "site_id", nullable = false)
    private Site siteId;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String lemma;

    @Column(nullable = false)
    private int frequency;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Site getSiteId() {
        return siteId;
    }

    public void setSiteId(Site siteId) {
        this.siteId = siteId;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
