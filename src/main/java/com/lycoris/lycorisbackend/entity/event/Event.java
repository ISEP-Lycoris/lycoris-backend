package com.lycoris.lycorisbackend.entity.event;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Time begin_time;

    @Column
    private Time end_time;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    // Constructors, getters, setters, and other methods

    public Double getDuration() {
        return Double.valueOf((end_time.getHours() - begin_time.getHours()) * 60 + (end_time.getMinutes() - begin_time.getMinutes()));
    }
}
