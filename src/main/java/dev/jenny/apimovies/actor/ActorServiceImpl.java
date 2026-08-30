package dev.jenny.apimovies.actor;

import java.util.List;

import dev.jenny.apimovies.actor.dtos.ActorDTORequest;
import dev.jenny.apimovies.actor.dtos.ActorDTOResponse;
import dev.jenny.apimovies.actor.exceptions.ActorExceptionNotFound;
import dev.jenny.apimovies.actor.mappers.ActorMapper;
import dev.jenny.apimovies.implementations.InterfaceGenericEditService;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl implements InterfaceActorService,
        InterfaceGenericEditService<ActorDTORequest, ActorDTOResponse> {

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

    @Override
    public ActorDTOResponse storeEntity(ActorDTORequest dto) {
        ActorEntity actorToSave = ActorMapper.toEntity(dto);

        Example<ActorEntity> example = Example.of(actorToSave);
        boolean isEmpty = repository.findAll(example).isEmpty();

        if (!isEmpty)
            return null;

        ActorEntity actorSaved = repository.save(actorToSave);
        return ActorMapper.toDTO(actorSaved);
    }

    @Override
    public ActorDTOResponse updateEntity(Long id, ActorDTORequest dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}