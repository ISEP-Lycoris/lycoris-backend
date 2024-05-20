package com.lycoris.lycorisbackend.event;

import com.lycoris.lycorisbackend.person.Person;

import java.util.ArrayList;
import java.util.List;

public class Activity {
    private List<Person> animators= new ArrayList<>();
    private List<Person> spectators=new ArrayList<>();

    public List<Person> getAnimator() {
        return animators;
    }

    public List<Person> getSpectators() {
        return spectators;
    }

    public Activity( List<Person> animators, List<Person> spectators) {
        this.spectators = spectators;
        this.animators = animators;
    }

    public void addAnimator(Person animator) {
        this.animators.add(animator);
    }

    public void addSpectator(Person spectator) {
        this.spectators.add(spectator);
    }
}