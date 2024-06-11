package com.lycoris.lycorisbackend.controller;

import com.lycoris.lycorisbackend.entity.event.Event;
import com.lycoris.lycorisbackend.entity.event.Room;

import java.util.List;

public class ScheduleRequest {

    private List<Room> rooms;
    private List<Event> events;
    private boolean parallel;

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public boolean isParallel() {
        return parallel;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }
}