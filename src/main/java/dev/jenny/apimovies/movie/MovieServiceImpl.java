package dev.jenny.apimovies.movie;

import java.util.List;
import java.util.Set;

import dev.jenny.apimovies.actor.ActorEntity;
import dev.jenny.apimovies.actor.ActorRepository;
import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.genre.GenreRepository;
import dev.jenny.apimovies.implementations.InterfaceGenericEditService;
import dev.jenny.apimovies.movie.dtos.MovieDTORequest;
import dev.jenny.apimovies.movie.dtos.MovieDTOResponse;
import dev.jenny.apimovies.movie.exceptions.MovieExceptionNotFound;
import dev.jenny.apimovies.movie.mappers.MovieMapper;
import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;
import dev.jenny.apimovies.releaseyear.ReleaseYearRepository;
import dev.jenny.apimovies.releaseyear.exceptions.ReleaseYearExceptionNotFound;

import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements InterfaceMovieService,
        InterfaceGenericEditService<MovieDTORequest, MovieDTOResponse> {

    private final MovieRepository repository;
    private final GenreRepository genreRepository;
    private final ReleaseYearRepository releaseYearRepository;
    private final ActorRepository actorRepository;

    public MovieServiceImpl(MovieRepository repository, GenreRepository genreRepository,
            ReleaseYearRepository releaseYearRepository, ActorRepository actorRepository) {
        this.repository = repository;
        this.genreRepository = genreRepository;
        this.releaseYearRepository = releaseYearRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public List<MovieDTOResponse> getEntities() {
        return repository.findAll().stream()
                .map(MovieMapper::toDTO)
                .toList();
    }

    @Override
    public MovieDTOResponse getById(Long id) {
        MovieEntity movie = repository.findById(id)
                .orElseThrow(() -> new MovieExceptionNotFound(
                        "Movie not found. Id " + id + " does not exist."));
        return MovieMapper.toDTO(movie);
    }

    @Override
    public MovieDTOResponse storeEntity(MovieDTORequest dto) {
        ReleaseYearEntity releaseYear = releaseYearRepository.findById(dto.releaseYearId())
                .orElseThrow(() -> new ReleaseYearExceptionNotFound(
                        "Release year not found. Id " + dto.releaseYearId() + " does not exist."));

        Set<GenreEntity> genres = Set.copyOf(genreRepository.findAllById(dto.genreIds()));
        Set<ActorEntity> actors = Set.copyOf(actorRepository.findAllById(dto.actorIds()));

        if (repository.existsByTitleAndReleaseYear(dto.title(), releaseYear))
            return null;

        MovieEntity movieToSave = MovieMapper.toEntity(dto, genres, releaseYear, actors);
        MovieEntity movieSaved = repository.save(movieToSave);
        return MovieMapper.toDTO(movieSaved);
    }

    @Override
    public MovieDTOResponse updateEntity(Long id, MovieDTORequest dto) {
        if (!repository.existsById(id))
            throw new MovieExceptionNotFound("Movie not found. Id " + id + " does not exist.");

        ReleaseYearEntity releaseYear = releaseYearRepository.findById(dto.releaseYearId())
                .orElseThrow(() -> new ReleaseYearExceptionNotFound(
                        "Release year not found. Id " + dto.releaseYearId() + " does not exist."));

        if (repository.existsByTitleAndReleaseYearAndIdNot(dto.title(), releaseYear, id))
            return null;

        Set<GenreEntity> genres = Set.copyOf(genreRepository.findAllById(dto.genreIds()));
        Set<ActorEntity> actors = Set.copyOf(actorRepository.findAllById(dto.actorIds()));

        MovieEntity movieToSave = MovieMapper.toEntity(dto, genres, releaseYear, actors);
        movieToSave.setId(id);
        MovieEntity movieSaved = repository.save(movieToSave);
        return MovieMapper.toDTO(movieSaved);
    }
}