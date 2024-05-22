package com.lycoris.lycorisbackend.entity.event;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    @Column(name = "room_capacity", nullable = false)
    private Integer roomCapacity;

    public Room() {
        // Default constructor for JPA
    }

    public Room(String name, Integer roomCapacity) {
        this.name = name;
        this.roomCapacity = roomCapacity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public Integer getRoomCapacity() {
        return roomCapacity;
    }

    public void addEvent(Event event) {
        this.events.add(event);
        event.setRoom(this); // Ensure the relationship is set on both sides
    }

    public void removeEvent(Event event) {
        this.events.remove(event);
        event.setRoom(null); // Remove the relationship from the event side
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoomCapacity(Integer roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
