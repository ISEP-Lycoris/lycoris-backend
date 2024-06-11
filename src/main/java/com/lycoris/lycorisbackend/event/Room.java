package com.lycoris.lycorisbackend.event;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private List<Event> events= new ArrayList<>();
    private List<Event> unsortedEvents= new ArrayList<>();
    private Integer roomCapacity;

    public Room(String name, Integer roomCapacity) {
        this.name = name;
        this.roomCapacity = roomCapacity;
    }

    public String getName() {
        return name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }


}