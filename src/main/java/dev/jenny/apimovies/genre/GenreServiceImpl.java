package dev.jenny.apimovies.genre;

import java.util.List;

import dev.jenny.apimovies.genre.dtos.GenreDTOResponse;
import dev.jenny.apimovies.genre.exceptions.GenreExceptionNotFound;
import dev.jenny.apimovies.genre.mappers.GenreMapper;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements InterfaceGenreService {

    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GenreDTOResponse> getEntities() {
        return repository.findAll().stream()
                .map(GenreMapper::toDTO)
                .toList();
    }

    @Override
    public GenreDTOResponse getById(Long id) {
        GenreEntity genre = repository.findById(id)
                .orElseThrow(() -> new GenreExceptionNotFound("Genre not found. Id " + id + " does not exist."));
        return GenreMapper.toDTO(genre);
    }
}