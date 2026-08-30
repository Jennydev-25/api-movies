package dev.jenny.apimovies.releaseyear;

import dev.jenny.apimovies.releaseyear.builder.ReleaseYearEntityBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "release_years")
public class ReleaseYearEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer releaseYear;

    public ReleaseYearEntity() {
    }

    public ReleaseYearEntity(Long id, Integer releaseYear) {
        this.id = id;
        this.releaseYear = releaseYear;
    }

    public Long getId() {
        return id;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public static ReleaseYearEntityBuilder builder() {
        return new ReleaseYearEntityBuilder();
    }
}