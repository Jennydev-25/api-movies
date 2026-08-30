package dev.jenny.apimovies.releaseyear;

import java.util.List;

import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTORequest;
import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTOResponse;
import dev.jenny.apimovies.releaseyear.exceptions.ReleaseYearExceptionNotFound;
import dev.jenny.apimovies.releaseyear.mappers.ReleaseYearMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ReleaseYearServiceImpl implements InterfaceReleaseYearService {
    private final ReleaseYearRepository repository;

    public ReleaseYearServiceImpl(ReleaseYearRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ReleaseYearDTOResponse> getEntities() {
        return repository.findAll().stream()
                .map(ReleaseYearMapper::toDTO)
                .toList();
    }

    @Override
    public ReleaseYearDTOResponse getById(Long id) {
        ReleaseYearEntity releaseYear = repository.findById(id)
                .orElseThrow(() -> new ReleaseYearExceptionNotFound(
                        "Release year not found. Id " + id + " does not exist."));
        return ReleaseYearMapper.toDTO(releaseYear);
    }

    public ReleaseYearDTOResponse storeEntity(ReleaseYearDTORequest dto) {
        ReleaseYearEntity releaseYearToSave = ReleaseYearMapper.toEntity(dto);

        Example<ReleaseYearEntity> example = Example.of(releaseYearToSave);
        boolean isEmpty = repository.findAll(example).isEmpty();

        if (!isEmpty)
            return null;

        ReleaseYearEntity releaseYearSaved = repository.save(releaseYearToSave);
        return ReleaseYearMapper.toDTO(releaseYearSaved);
    }

    public ReleaseYearDTOResponse updateEntity(Long id, ReleaseYearDTORequest dto) {
        ReleaseYearEntity releaseYearToUpdate = new ReleaseYearEntity(id, dto.releaseYear());
        ReleaseYearEntity releaseYearUpdated = repository.save(releaseYearToUpdate);
        return ReleaseYearMapper.toDTO(releaseYearUpdated);
    }
}
