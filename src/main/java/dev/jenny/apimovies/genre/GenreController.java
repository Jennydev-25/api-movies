package dev.jenny.apimovies.genre;

import java.util.List;

import dev.jenny.apimovies.genre.dtos.GenreDTORequest;
import dev.jenny.apimovies.genre.dtos.GenreDTOResponse;
import dev.jenny.apimovies.implementations.InterfaceGenericEditService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api-endpoint}/genres")
public class GenreController {

    private final InterfaceGenreService service;
    private final InterfaceGenericEditService<GenreDTORequest, GenreDTOResponse> editService;

    public GenreController(InterfaceGenreService service,
            InterfaceGenericEditService<GenreDTORequest, GenreDTOResponse> editService) {
        this.service = service;
        this.editService = editService;
    }

    @GetMapping("")
    public List<GenreDTOResponse> index() {
        return service.getEntities();
    }

    @GetMapping("{id}")
    public GenreDTOResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<GenreDTOResponse> store(@Valid @RequestBody GenreDTORequest dto) {
        GenreDTOResponse dtoResponse = editService.storeEntity(dto);

        if (dtoResponse == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.status(201).body(dtoResponse);
    }
}