package com.lycoris.lycorisbackend.entity.person;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String lastName;
    @Column
    private String firstName;
    @Column
    private Roles role;

    @Column
    private String firstChoice="";
    @Column
    private String secondChoice="";
    @Column
    private String thirdChoice="";


    public void addChoices(String firstChoice, String secondChoice, String thirdChoice) {
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;
        this.thirdChoice = thirdChoice;
    }

}