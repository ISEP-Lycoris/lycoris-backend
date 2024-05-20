package com.lycoris.lycorisbackend.person;


public class Person {

    private String lastName;
    private String firstName;
    private Roles role;

    private String firstChoice="";
    private String secondChoice="";
    private String thirdChoice="";

    public Person(String name, String firstName, Roles role) {
        this.lastName = name;
        this.firstName = firstName;
        this.role = role;
    }


    public void addChoices(String firstChoice, String secondChoice, String thirdChoice) {
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;
        this.thirdChoice = thirdChoice;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Roles getRole() {
        return role;
    }

    public String getFirstChoice() {
        return firstChoice;
    }

    public String getThirdChoice() {
        return thirdChoice;
    }

    public String getSecondChoice() {
        return secondChoice;
    }
}