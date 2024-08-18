package tech.dev.protoapi.services;

import tech.dev.protoapi.models.Person;

import java.util.Optional;
import java.util.Set;

public interface PeopleService {

    Set<Person> getPeople();
    Optional<Person> getPersonById(String id);
    Set<Person> getPeopleByName(String name);
    Person createPerson(Person person);
    Person updatePerson(String id, Person person);
    void deletePerson(String id);
    void generatePeople(Integer quantity);
}
