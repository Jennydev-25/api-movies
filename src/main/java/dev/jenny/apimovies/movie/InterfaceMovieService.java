package dev.jenny.apimovies.movie;

import java.util.List;

import dev.jenny.apimovies.movie.dtos.MovieDTOResponse;

public interface InterfaceMovieService {

    List<MovieDTOResponse> getEntities();

    MovieDTOResponse getById(Long id);
}