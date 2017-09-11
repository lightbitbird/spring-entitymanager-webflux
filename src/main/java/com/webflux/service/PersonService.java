package com.webflux.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webflux.domain.Person;
import com.webflux.repositry.JdbcTemplateRepository;
import com.webflux.repositry.PersonRepository;

import reactor.core.publisher.Flux;

@Component
public class PersonService {
    
    @Autowired
    private JdbcTemplateRepository jdbcTemplateRepository;
    
    @Autowired
    private PersonRepository personRepository;
    
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public Flux<Person> findLargeCollectionPersonEntries() {
        return jdbcTemplateRepository.findLargeCollectionPersonEntries();
    }

}
