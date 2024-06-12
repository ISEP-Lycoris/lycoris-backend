package com.lycoris.lycorisbackend.entity.event;

import com.lycoris.lycorisbackend.entity.person.Person;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    // Liste des animateurs pour l'activité
    @ManyToMany
    private List<Person> animators = new ArrayList<>();

    // Liste des spectateurs pour l'activité
    @ManyToMany
    private List<Person> spectators = new ArrayList<>();

    // Nom de l'activité
    @Column
    private String name;

    // Ajoute un animateur à la liste des animateurs
    public void addAnimator(Person animator) {
        animators.add(animator);
    }

    // Ajoute un spectateur à la liste des spectateurs
    public void addSpectator(Person spectator) {
        spectators.add(spectator);
    }
}
