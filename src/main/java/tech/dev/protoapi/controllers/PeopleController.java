package tech.dev.protoapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.dev.protoapi.models.Person;
import tech.dev.protoapi.models.exceptions.PersonNotFoundException;
import tech.dev.protoapi.services.PeopleService;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {

    private final PeopleService peopleService;

    @Value("${api.version}")
    private String apiVersion;

    @GetMapping("/version")
    public String getApiVersion() {
        return apiVersion;
    }

    @GetMapping
    public ResponseEntity<Set<Person>> getPeople() {
        return ResponseEntity.ok(peopleService.getPeople());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                peopleService.getPersonById(id)
                        .orElseThrow(() -> new PersonNotFoundException(String.format("person with id %s not found", id)))
        );
    }

    @PostMapping("/create-people/{quantity}")
    public String createPeople(@PathVariable("quantity") int quantity) {
        peopleService.generatePeople(quantity);
        return String.format("created %d people", quantity);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person personSaved = peopleService.createPerson(person);
        return ResponseEntity.created(
                URI.create(String.format("/api/people/get-person/%s", personSaved.getId()))
        ).body(personSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") String id) {
        peopleService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") String id, @RequestBody Person person) {
        return ResponseEntity.ok(peopleService.updatePerson(id, person));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<Set<Person>> getPersonByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(peopleService.getPeopleByName(name));
    }
}
