package dev.jenny.apimovies.releaseyear.builder;

import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;

public interface IReleaseYearBuilder {

    public ReleaseYearEntityBuilder id(Long id);

    public ReleaseYearEntityBuilder releaseYear(Integer releaseYear);

    public ReleaseYearEntity build();
}