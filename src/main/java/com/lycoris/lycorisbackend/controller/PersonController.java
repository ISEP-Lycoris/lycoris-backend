package com.lycoris.lycorisbackend.controller;

import com.lycoris.lycorisbackend.entity.person.Person;
import com.lycoris.lycorisbackend.entity.person.Roles;
import com.lycoris.lycorisbackend.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public Iterable<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> person = personService.getPersonById(id);
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        Optional<Person> personOptional = personService.getPersonById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.setFirstName(personDetails.getFirstName());
            person.setLastName(personDetails.getLastName());
            person.setRole(personDetails.getRole());
            person.setFirstChoice(personDetails.getFirstChoice());
            person.setSecondChoice(personDetails.getSecondChoice());
            person.setThirdChoice(personDetails.getThirdChoice());
            return ResponseEntity.ok(personService.updatePerson(person));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePersonById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lastname/{lastName}")
    public List<Person> getPersonsByLastName(@PathVariable String lastName) {
        return personService.getPersonsByLastName(lastName);
    }

    @GetMapping("/firstname/{firstName}")
    public List<Person> getPersonsByFirstName(@PathVariable String firstName) {
        return personService.getPersonsByFirstName(firstName);
    }

    @GetMapping("/role/{role}")
    public List<Person> getPersonsByRole(@PathVariable Roles role) {
        return personService.getPersonsByRole(role);
    }

    @GetMapping("/fullname")
    public List<Person> getPersonsByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        return personService.getPersonsByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/firstchoice/{firstChoice}")
    public List<Person> getPersonsByFirstChoice(@PathVariable String firstChoice) {
        return personService.getPersonsByFirstChoice(firstChoice);
    }

    @GetMapping("/secondchoice/{secondChoice}")
    public List<Person> getPersonsBySecondChoice(@PathVariable String secondChoice) {
        return personService.getPersonsBySecondChoice(secondChoice);
    }

    @GetMapping("/thirdchoice/{thirdChoice}")
    public List<Person> getPersonsByThirdChoice(@PathVariable String thirdChoice) {
        return personService.getPersonsByThirdChoice(thirdChoice);
    }

    @GetMapping("/roleandfirstchoice")
    public List<Person> getPersonsByRoleAndFirstChoice(@RequestParam Roles role, @RequestParam String firstChoice) {
        return personService.getPersonsByRoleAndFirstChoice(role, firstChoice);
    }
}
