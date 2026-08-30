package dev.jenny.apimovies.releaseyear.builder;

import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;

public class ReleaseYearEntityBuilder implements IReleaseYearBuilder {

    private final ReleaseYearEntity entity;

    public ReleaseYearEntityBuilder() {
        entity = new ReleaseYearEntity();
    }

    @Override
    public ReleaseYearEntityBuilder id(Long id) {
        entity.setId(id);
        return this;
    }

    @Override
    public ReleaseYearEntityBuilder releaseYear(Integer releaseYear) {
        entity.setReleaseYear(releaseYear);
        return this;
    }

    @Override
    public ReleaseYearEntity build() {
        return entity;
    }
}