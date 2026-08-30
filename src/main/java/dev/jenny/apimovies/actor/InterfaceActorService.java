package dev.jenny.apimovies.actor;

import java.util.List;

import dev.jenny.apimovies.actor.dtos.ActorDTOResponse;

public interface InterfaceActorService {
    
    List<ActorDTOResponse> getEntities();

    ActorDTOResponse getById(Long id);
}