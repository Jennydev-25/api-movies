package dev.jenny.apimovies.actor.mappers;

import dev.jenny.apimovies.actor.ActorEntity;
import dev.jenny.apimovies.actor.dtos.ActorDTOResponse;

public class ActorMapper {

    private ActorMapper() {
    }

    public static ActorDTOResponse toDTO(ActorEntity entity) {
        return new ActorDTOResponse(entity.getId(), entity.getName(), entity.getNationality(), entity.getBirthDate());
    }
}