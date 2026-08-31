package dev.jenny.apimovies.actor.builder;

import dev.jenny.apimovies.actor.ActorEntity;

import java.time.LocalDate;

public class ActorEntityBuilder implements IActorBuilder {

    private final ActorEntity entity;

    public ActorEntityBuilder() {
        entity = new ActorEntity();
    }

    @Override
    public ActorEntityBuilder id(Long id) {
        entity.setId(id);
        return this;
    }

    @Override
    public ActorEntityBuilder name(String name) {
        entity.setName(name);
        return this;
    }

    @Override
    public ActorEntityBuilder nationality(String nationality) {
        entity.setNationality(nationality);
        return this;
    }

    @Override
    public ActorEntityBuilder birthDate(LocalDate birthDate) {
        entity.setBirthDate(birthDate);
        return this;
    }

    @Override
    public ActorEntity build() {
        return entity;
    }
}