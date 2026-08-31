package dev.jenny.apimovies.movie;

import java.util.List;

import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    boolean existsByTitleAndReleaseYear(String title, ReleaseYearEntity releaseYear);

    boolean existsByTitleAndReleaseYearAndIdNot(String title, ReleaseYearEntity releaseYear, Long id);

    List<MovieEntity> findByTitleContainingIgnoreCase(String title);

    List<MovieEntity> findByGenres_NameContainingIgnoreCase(String genreName);
}