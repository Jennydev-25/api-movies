package dev.jenny.apimovies.actor;

import java.util.List;

import dev.jenny.apimovies.actor.dtos.ActorDTORequest;
import dev.jenny.apimovies.actor.dtos.ActorDTOResponse;
import dev.jenny.apimovies.implementations.InterfaceGenericEditService;
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
@RequestMapping(path = "${api-endpoint}/actors")
public class ActorController {

    private final InterfaceActorService service;
    private final InterfaceGenericEditService<ActorDTORequest, ActorDTOResponse> editService;

    public ActorController(InterfaceActorService service,
            InterfaceGenericEditService<ActorDTORequest, ActorDTOResponse> editService) {
        this.service = service;
        this.editService = editService;
    }

    @GetMapping("")
    public List<ActorDTOResponse> index() {
        return service.getEntities();
    }

    @GetMapping("{id}")
    public ActorDTOResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<ActorDTOResponse> store(@Valid @RequestBody ActorDTORequest dto) {
        ActorDTOResponse dtoResponse = editService.storeEntity(dto);

        if (dtoResponse == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        return ResponseEntity.status(201).body(dtoResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ActorDTOResponse> update(@PathVariable Long id, @Valid @RequestBody ActorDTORequest actor) {
        ActorDTOResponse actorDTOResponse = editService.updateEntity(id, actor);
        if (actorDTOResponse == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(actorDTOResponse);
    }
}