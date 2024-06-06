package com.lycoris.lycorisbackend.controller;

import com.lycoris.lycorisbackend.entity.person.Person;
import com.lycoris.lycorisbackend.entity.person.Roles;
import com.lycoris.lycorisbackend.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private static final Map<String, Roles> ROLE_MAP = new HashMap<>();
    static {
        ROLE_MAP.put("Spectateur", Roles.SPECTATOR);
        ROLE_MAP.put("Animateur", Roles.ANIMATOR);
    }

    // Your existing controller methods...

    @PostMapping("/add-from-sheet")
    public ResponseEntity<String> addPersonsFromSheet(@RequestBody List<List<String>> sheetData) {
        for (List<String> rowData : sheetData) {
            String firstName = rowData.get(0);
            String lastName = rowData.get(1);
            String roleString = rowData.get(2);

            // Convert role string to enum
            Roles role = ROLE_MAP.get(roleString);

            if (role == null) {
                // Role not found in map
                return ResponseEntity.badRequest().body("Invalid role: " + roleString);
            }

            // Check if person already exists in the database
            List<Person> existingPersons = personService.getPersonsByFirstNameAndLastName(firstName, lastName);

            if (existingPersons.isEmpty()) {
                // Person does not exist, create a new one
                Person person = new Person();
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setRole(role);
                personService.savePerson(person);
            }
        }

        return ResponseEntity.ok("Persons added from sheet successfully.");
    }
}
