package com.lycoris.lycorisbackend.entity.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer roomCapacity;

    @Column(nullable = false)
    private boolean disponible=true;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Event> events = new ArrayList<>();

    @Column
    private Time ouvertureTime;

    @Column
    private Time fermetureTime;

    public Room(Integer id,String name, Integer roomCapacity) {
        this.name = name;
        this.roomCapacity = roomCapacity;
        this.id= Long.valueOf(id);
    }

    // Getter and setter methods for all properties

    public void addEvent(Event event) {
        events.add(event);
        event.setRoom(this);
    }

    public void removeEvent(Event event) {
        events.remove(event);
        event.setRoom(null);
    }

    public long getLastEventEndTime() {
        long lastEndTime = 0;
        for (Event event : events) {
            if (event.getBegin_time() != null) { // Add null check here
                long eventEndTime = event.getBegin_time().getTime() + event.getDuration() * 60000;
                if (eventEndTime > lastEndTime) {
                    lastEndTime = eventEndTime;
                }
            }
        }
        return lastEndTime;
    }


}
