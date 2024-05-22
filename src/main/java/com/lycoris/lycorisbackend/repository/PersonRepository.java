package com.lycoris.lycorisbackend.repository;

import com.lycoris.lycorisbackend.entity.person.Person;
import com.lycoris.lycorisbackend.entity.person.Roles;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    // Find by last name
    List<Person> findByLastName(String lastName);

    // Find by first name
    List<Person> findByFirstName(String firstName);

    // Find by role
    List<Person> findByRole(Roles role);

    // Find by first and last name
    List<Person> findByFirstNameAndLastName(String firstName, String lastName);

    // Find by first choice
    List<Person> findByFirstChoice(String firstChoice);

    // Find by second choice
    List<Person> findBySecondChoice(String secondChoice);

    // Find by third choice
    List<Person> findByThirdChoice(String thirdChoice);

    // Find all persons with a specific role and first choice
    List<Person> findByRoleAndFirstChoice(Roles role, String firstChoice);
}
