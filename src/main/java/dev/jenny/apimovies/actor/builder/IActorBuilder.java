package dev.jenny.apimovies.actor.builder;

import dev.jenny.apimovies.actor.ActorEntity;

import java.time.LocalDate;

public interface IActorBuilder {

    public ActorEntityBuilder id(Long id);

    public ActorEntityBuilder name(String name);

    public ActorEntityBuilder nationality(String nationality);

    public ActorEntityBuilder birthDate(LocalDate birthDate);

    public ActorEntity build();
}