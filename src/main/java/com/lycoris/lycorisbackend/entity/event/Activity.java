package com.lycoris.lycorisbackend.entity.event;

import com.lycoris.lycorisbackend.entity.person.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @ManyToMany
    private List<Person> animators;

    @ManyToMany
    private List<Person> spectators;

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