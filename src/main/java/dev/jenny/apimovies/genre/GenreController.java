package dev.jenny.apimovies.genre;

import java.util.List;

import dev.jenny.apimovies.genre.dtos.GenreDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api-endpoint}/genres")
public class GenreController {

    private final InterfaceGenreService service;

    public GenreController(InterfaceGenreService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<GenreDTO> index() {
        return service.getEntities();
    }

    @GetMapping("{id}")
    public GenreDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }
}