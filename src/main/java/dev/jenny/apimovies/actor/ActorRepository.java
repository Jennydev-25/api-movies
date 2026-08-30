package dev.jenny.apimovies.actor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
    boolean existsByNameAndBirthDateAndIdNot(String name, LocalDate birthDate, Long id);
}