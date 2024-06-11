package com.lycoris.lycorisbackend.event;

import java.util.ArrayList;
import java.util.List;

public class FreeTime {
    private Time begin;
    private Time end;
    private Room room;
    private List<Event> events = new ArrayList<Event>();

    public FreeTime(Time begin, Time end,Room room) {
        this.begin = begin;
        this.end = end;
        this.room = room;
    }

    public int getDuration(){
        return end.timeSince(begin);
    }

    public boolean eventIsPossible(Event event){
        if (event.getBegin() == null)  {

        }
        return begin.isBefore(event.getBegin()) && end.isAfter(event.getEnd());
    }
    public List<Event> getEvents() {
        return events;
    }
    public void addEvents(Event events) {
        this.events.add(events);
    }
    public void eventSetTime(){
        Time now = new Time(begin);
        for (Event event : events) {
            event.setBegin(now);
            now.addTime(event.getDuration());
            event.setEnd(now);
            room.addEvent(event);
        }
    }
}
