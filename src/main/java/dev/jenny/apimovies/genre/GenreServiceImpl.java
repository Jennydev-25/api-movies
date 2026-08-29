package dev.jenny.apimovies.genre;

import java.util.List;

import dev.jenny.apimovies.genre.exceptions.GenreExceptionNotFound;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements InterfaceGenreService {

    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GenreEntity> getEntities() {
        return repository.findAll();
    }

    @Override
    public GenreEntity getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new GenreExceptionNotFound("Genre not found. Id " + id + " does not exist."));
    }
}