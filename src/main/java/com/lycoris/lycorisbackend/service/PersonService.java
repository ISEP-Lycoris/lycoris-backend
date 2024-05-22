package com.lycoris.lycorisbackend.service;

import com.lycoris.lycorisbackend.entity.person.Person;
import com.lycoris.lycorisbackend.entity.person.Roles;
import com.lycoris.lycorisbackend.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    // Find by last name
    public List<Person> getPersonsByLastName(String lastName) {
        return personRepository.findByLastName(lastName);
    }

    // Find by first name
    public List<Person> getPersonsByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }

    // Find by role
    public List<Person> getPersonsByRole(Roles role) {
        return personRepository.findByRole(role);
    }

    // Find by first and last name
    public List<Person> getPersonsByFirstNameAndLastName(String firstName, String lastName) {
        return personRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    // Find by first choice
    public List<Person> getPersonsByFirstChoice(String firstChoice) {
        return personRepository.findByFirstChoice(firstChoice);
    }

    // Find by second choice
    public List<Person> getPersonsBySecondChoice(String secondChoice) {
        return personRepository.findBySecondChoice(secondChoice);
    }

    // Find by third choice
    public List<Person> getPersonsByThirdChoice(String thirdChoice) {
        return personRepository.findByThirdChoice(thirdChoice);
    }

    // Find all persons with a specific role and first choice
    public List<Person> getPersonsByRoleAndFirstChoice(Roles role, String firstChoice) {
        return personRepository.findByRoleAndFirstChoice(role, firstChoice);
    }

    // Save a new person
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    // Find person by ID
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    // Delete person by ID
    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }

    // Update an existing person
    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    // Find all persons
    public Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
