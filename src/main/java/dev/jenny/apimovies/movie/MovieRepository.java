package dev.jenny.apimovies.movie;

import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    boolean existsByTitleAndReleaseYearAndIdNot(String title, ReleaseYearEntity releaseYear, Long id);
}