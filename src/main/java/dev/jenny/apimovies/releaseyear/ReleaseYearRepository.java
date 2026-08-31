package dev.jenny.apimovies.releaseyear;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseYearRepository extends JpaRepository<ReleaseYearEntity, Long> {
    boolean existsByReleaseYearAndIdNot(Integer releaseYear, Long id);
}
