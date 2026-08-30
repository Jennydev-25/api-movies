package dev.jenny.apimovies.movie;

import java.util.Set;

import dev.jenny.apimovies.actor.ActorEntity;
import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movie")
    private Long id;

    private String title;

    @ManyToMany
    @JoinTable(name = "movies_genres", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<GenreEntity> genres;

    @ManyToOne
    @JoinColumn(name = "release_year_id")
    private ReleaseYearEntity releaseYear;

    @ManyToMany
    @JoinTable(name = "movies_actors", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<ActorEntity> actors;

    public MovieEntity() {
    }

    public MovieEntity(Long id, String title, Set<GenreEntity> genres, ReleaseYearEntity releaseYear,
            Set<ActorEntity> actors) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.actors = actors;
    }
}