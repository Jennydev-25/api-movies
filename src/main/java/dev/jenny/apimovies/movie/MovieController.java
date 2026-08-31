package dev.jenny.apimovies.movie;

import java.util.List;

import dev.jenny.apimovies.implementations.InterfaceGenericEditService;
import dev.jenny.apimovies.movie.dtos.MovieDTORequest;
import dev.jenny.apimovies.movie.dtos.MovieDTOResponse;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api-endpoint}/movies")
public class MovieController {

    private final InterfaceMovieService service;
    private final InterfaceGenericEditService<MovieDTORequest, MovieDTOResponse> editService;

    public MovieController(InterfaceMovieService service,
            InterfaceGenericEditService<MovieDTORequest, MovieDTOResponse> editService) {
        this.service = service;
        this.editService = editService;
    }

    @GetMapping("")
    public List<MovieDTOResponse> index() {
        return service.getEntities();
    }

    @GetMapping("{id}")
    public MovieDTOResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<MovieDTOResponse> store(@Valid @RequestBody MovieDTORequest dto) {
        MovieDTOResponse dtoResponse = editService.storeEntity(dto);
        if (dtoResponse == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.status(201).body(dtoResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<MovieDTOResponse> update(@PathVariable Long id, @Valid @RequestBody MovieDTORequest dto) {
        MovieDTOResponse dtoResponse = editService.updateEntity(id, dto);
        if (dtoResponse == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.ok(dtoResponse);
    }
}