package dev.jenny.apimovies.actor;

import java.util.List;

import dev.jenny.apimovies.actor.dtos.ActorDTORequest;
import dev.jenny.apimovies.actor.dtos.ActorDTOResponse;
import dev.jenny.apimovies.implementations.InterfaceGenericEditService;

import org.springframework.web.bind.annotation.GetMapping;
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
}