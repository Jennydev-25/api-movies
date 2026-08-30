package dev.jenny.apimovies.releaseyear;

import java.util.List;

import dev.jenny.apimovies.implementations.InterfaceGenericEditService;
import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTORequest;
import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTOResponse;

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
@RequestMapping(path = "${api-endpoint}/release-years")
public class ReleaseYearController {

    private final InterfaceReleaseYearService service;
    private final InterfaceGenericEditService<ReleaseYearDTORequest, ReleaseYearDTOResponse> editService;

    public ReleaseYearController(InterfaceReleaseYearService service,
            InterfaceGenericEditService<ReleaseYearDTORequest, ReleaseYearDTOResponse> editService) {
        this.service = service;
        this.editService = editService;
    }

    @GetMapping("")
    public List<ReleaseYearDTOResponse> index() {
        return service.getEntities();
    }

    @GetMapping("{id}")
    public ReleaseYearDTOResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<ReleaseYearDTOResponse> store(@Valid @RequestBody ReleaseYearDTORequest dto) {
        ReleaseYearDTOResponse dtoResponse = editService.storeEntity(dto);
        if (dtoResponse == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.status(201).body(dtoResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ReleaseYearDTOResponse> update(@PathVariable Long id,
            @Valid @RequestBody ReleaseYearDTORequest dto) {
        ReleaseYearDTOResponse dtoResponse = editService.updateEntity(id, dto);
        if (dtoResponse == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        return ResponseEntity.ok(dtoResponse);
    }
}