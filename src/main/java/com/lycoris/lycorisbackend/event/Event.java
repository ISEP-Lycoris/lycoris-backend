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
        this.begin = new Time (begin);
        this.end = new Time (end);
        this.room = room;
        this.name = name;
        this.activity = activity;
        duration = end.timeSince(begin);
    }

    //si aucun horraire et salle
    public Event(int duration, String name, Activity activity) {
        this.duration = duration;
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

    //si aucun horraire
    public Event(int duration,Room room, String name, Activity activity) {
        this.duration = duration;
        this.name = name;
        this.activity = activity;
        this.room = room;
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

    public void addRoom(Room room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public Activity getActivity() {
        return activity;
    }

    public int getDuration() {
        if(duration!=0) return duration;
        duration = end.timeSince(begin);
        return end.timeSince(begin);
    }

    public void setBegin(Time begin) {
        this.begin = new Time(begin);
    }

    public void setEnd(Time end) {
        this.end = new Time (end);
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