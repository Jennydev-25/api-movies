package dev.jenny.apimovies.releaseyear;

import java.util.List;

import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTOResponse;

public interface InterfaceReleaseYearService {

    List<ReleaseYearDTOResponse> getEntities();
}
