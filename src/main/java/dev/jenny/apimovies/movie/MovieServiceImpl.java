package dev.jenny.apimovies.movie;

import java.util.List;

import dev.jenny.apimovies.movie.dtos.MovieDTOResponse;
import dev.jenny.apimovies.movie.mappers.MovieMapper;

import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements InterfaceMovieService {

    private final MovieRepository repository;

    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MovieDTOResponse> getEntities() {
        return repository.findAll().stream()
                .map(MovieMapper::toDTO)
                .toList();
    }

    @Override
    public MovieDTOResponse getById(Long id) {
        return null;
    }
}