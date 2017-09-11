package com.webflux.repositry;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.webflux.domain.Person;

import reactor.core.publisher.Flux;

@Repository
public class JdbcTemplateRepository {

    private static final String QUERY_FIND_ALL_PERSON_ENTRIES = "SELECT p.id as 'id', "
            + "p.firstName as 'playlistName', t.lastName as 'lastName' " + "FROM person p  ";

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateRepository() {
        super();
    }

    @Autowired
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Flux<Person> findLargeCollectionPersonEntries() {

        return Flux.fromIterable(this.jdbcTemplate.query(QUERY_FIND_ALL_PERSON_ENTRIES, (resultSet, i) -> {
            return new Person(Long.valueOf(resultSet.getLong("id")), resultSet.getString("firstName"),
                    resultSet.getString("lastName"));
        })).repeat(300).delayElements(Duration.ofSeconds(1L));

    }

}