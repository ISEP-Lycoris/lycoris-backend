package com.lycoris.lycorisbackend.event;

import java.util.Date;

public class Event {
    private Time begin;
    private Time end;
    private Room room;
    private String name;
    private Activity activity;

    public Event(Time begin, Time end, Room room, String name, Activity activity) {
        this.begin = begin;
        this.end = end;
        this.room = room;
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

    public Double getDuration() {
        return Double.valueOf((end.getHeures() - begin.getHeures()) * 60 + (end.getMinutes() - begin.getMinutes()));
    }
}