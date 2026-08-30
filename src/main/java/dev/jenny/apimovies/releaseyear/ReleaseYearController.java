package dev.jenny.apimovies.releaseyear;

import java.util.List;

import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTOResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api-endpoint}/release-years")
public class ReleaseYearController {

    private final InterfaceReleaseYearService service;

    public ReleaseYearController(InterfaceReleaseYearService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ReleaseYearDTOResponse> index() {
        return service.getEntities();
    }
}
