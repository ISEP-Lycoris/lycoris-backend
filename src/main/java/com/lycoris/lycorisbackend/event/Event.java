package com.lycoris.lycorisbackend.event;

import java.util.Date;

public class Event {
    private Time begin;
    private Time end;
    private Room room;
    private String name;
    private int duration;
    private Activity activity;

    //si salle spécifiée
    public Event(Time begin, Time end, Room room, String name, Activity activity) {
        this.begin = begin;
        this.end = end;
        this.room = room;
        this.name = name;
        this.activity = activity;
        duration = end.timeSince(begin);
    }

    //si aucune durée et salle
    public Event(Time duration, String name, Activity activity) {
        this.begin = begin;
        this.end = end;
        this.room = room;
        this.name = name;
        this.activity = activity;
    }

    //si aucune salle spécifiée
    public Event(Time begin, Time end, String name, Activity activity) {
        this.begin = begin;
        this.end = end;
        this.name = name;
        this.activity = activity;
    }


    public Time getBegin() {
        return begin;
    }

    public Time getEnd() {
        return end;
    }

    public Room getRoom() {
        return room;
    }

    public String getName() {
        return name;
    }

    public Activity getActivity() {
        return activity;
    }

    public int getDuration() {
        return end.timeSince(begin);
    }

    public void setBegin(Time begin) {
        this.begin = begin;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void printEventInfo() {
        System.out.println("event name: " + this.name +
                "\n event Room: " + this.room.getName() +
                "\n animator: " + this.activity.getAnimator().get(0).getFirstName() + " " + this.activity.getAnimator().get(0).getLastName() +
                "\n duration: " + this.getDuration() +
                "\n spectator: " + this.activity.getSpectators().get(0).getFirstName() + " " + this.activity.getSpectators().get(0).getLastName()
        );
    }
}