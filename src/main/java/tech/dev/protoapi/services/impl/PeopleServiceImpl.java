package tech.dev.protoapi.services.impl;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import tech.dev.protoapi.models.Person;
import tech.dev.protoapi.models.exceptions.PersonNotFoundException;
import tech.dev.protoapi.services.PeopleService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PeopleServiceImpl implements PeopleService {

    private final Set<Person> people = new HashSet<>();
    private final Faker faker = new Faker();

    @Override
    public Set<Person> getPeople() {
        return people;
    }

    @Override
    public Optional<Person> getPersonById(String id) {
        return people.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public Set<Person> getPeopleByName(String name) {
        return people.stream()
                .filter(
                        person -> person.getFullName()
                                .toLowerCase()
                                .contains(name.toLowerCase())
                ).collect(Collectors.toSet());
    }

    @Override
    public Person createPerson(Person person) {
        Person personToSave = Person.builder()
                .id(UUID.randomUUID().toString())
                .fullName(person.getFullName())
                .birthDate(person.getBirthDate())
                .age(person.getAge())
                .build();

        people.add(personToSave);

        return personToSave;
    }

    @Override
    public Person updatePerson(String id, Person person) {
        Person personToUpdate = this.getPersonById(id)
                .orElseThrow(() -> new PersonNotFoundException(String.format("person with id %s not found", id)));

        people.remove(personToUpdate);

        personToUpdate.setFullName(person.getFullName());
        personToUpdate.setBirthDate(person.getBirthDate());
        personToUpdate.setAge(person.getAge());

        people.add(personToUpdate);

        return personToUpdate;
    }

    @Override
    public void deletePerson(String id) {
        Person personToRemove = this.getPersonById(id)
                .orElseThrow(() -> new PersonNotFoundException(String.format("person with id %s not found", id)));
        people.remove(personToRemove);
    }

    @Override
    public void generatePeople(Integer quantity) {
        if (quantity <= 0) return;
        people.clear();

        for (int i = 0; i < quantity; i++) {
            people.add(
                    Person.builder()
                        .id(UUID.randomUUID().toString())
                        .fullName(faker.name().fullName())
                        .birthDate(faker.date().birthday())
                        .age(faker.number().numberBetween(1, 100))
                        .build()
            );
        }
    }
}
