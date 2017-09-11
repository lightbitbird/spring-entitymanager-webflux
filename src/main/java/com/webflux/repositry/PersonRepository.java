package com.webflux.repositry;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webflux.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Override
    Optional<Person> findById(Long id);

    @Query("select m from Person m ")
    Stream<Person> findAllAsStream();
}
