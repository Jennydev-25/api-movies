package dev.jenny.apimovies.releaseyear.mappers;

import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;
import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTORequest;
import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTOResponse;

public class ReleaseYearMapper {

    private ReleaseYearMapper() {
    }

    public static ReleaseYearDTOResponse toDTO(ReleaseYearEntity entity) {
        return new ReleaseYearDTOResponse(entity.getId(), entity.getReleaseYear());
    }

    public static ReleaseYearEntity toEntity(ReleaseYearDTORequest dtoRequest) {
        ReleaseYearEntity releaseYear = new ReleaseYearEntity();
        releaseYear.setReleaseYear(dtoRequest.releaseYear());
        return releaseYear;
    }
}