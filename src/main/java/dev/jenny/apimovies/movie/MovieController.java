package dev.jenny.apimovies.movie;

import java.util.List;

import dev.jenny.apimovies.movie.dtos.MovieDTOResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api-endpoint}/movies")
public class MovieController {

    private final InterfaceMovieService service;

    public MovieController(InterfaceMovieService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<MovieDTOResponse> index() {
        return service.getEntities();
    }
}