package dev.jenny.apimovies.genre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    boolean existsByNameAndIdNot(String name, Long id);
}