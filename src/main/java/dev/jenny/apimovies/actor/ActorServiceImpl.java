package dev.jenny.apimovies.actor;

import java.util.List;

import dev.jenny.apimovies.actor.dtos.ActorDTOResponse;
import dev.jenny.apimovies.actor.exceptions.ActorExceptionNotFound;
import dev.jenny.apimovies.actor.mappers.ActorMapper;

import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl implements InterfaceActorService {

    private final ActorRepository repository;

    public ActorServiceImpl(ActorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ActorDTOResponse> getEntities() {
        return repository.findAll().stream()
                .map(ActorMapper::toDTO)
                .toList();
    }

    @Override
    public ActorDTOResponse getById(Long id) {
        ActorEntity actor = repository.findById(id)
                .orElseThrow(() -> new ActorExceptionNotFound(
                        "Actor not found. Id " + id + " does not exist."));
        return ActorMapper.toDTO(actor);
    }
}