package com.lycoris.lycorisbackend.entity.event;


import jakarta.persistence.*;

import java.sql.Time;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Time begin;
    private Time end;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private String name;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    // Constructor with room specified
    public Event(Time begin, Time end, Room room, String name, Activity activity) {
        this.begin = begin;
        this.end = end;
        this.room = room;
        this.name = name;
        this.activity = activity;
    }

    // Constructor without room specified
    public Event(Time begin, Time end, String name, Activity activity) {
        this.begin = begin;
        this.end = end;
        this.name = name;
        this.activity = activity;
    }

    public Event() {
        // Default constructor for JPA
    }

    public Time getBegin() {
        return begin;
    }

    public void setBegin(Time begin) {
        this.begin = begin;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Double getDuration() {
        return Double.valueOf((end.getHours() - begin.getHours()) * 60 + (end.getMinutes() - begin.getMinutes()));
    }

    public void printEventInfo() {
        System.out.println("event name: " + this.name +
                "\n event Room: " + (this.room != null ? this.room.getName() : "No room specified") +
                "\n animator: " + this.activity.getAnimator().get(0).getFirstName() + " " + this.activity.getAnimator().get(0).getLastName() +
                "\n duration: " + this.getDuration() +
                "\n spectator: " + this.activity.getSpectators().get(0).getFirstName() + " " + this.activity.getSpectators().get(0).getLastName()
        );
    }
}
