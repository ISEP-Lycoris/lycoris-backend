package com.lycoris.lycorisbackend.event;

import java.util.ArrayList;
import java.util.List;

public class FreeTime {
    private Time begin;
    private Time end;
    private Room room;
    private List<Event> events = new ArrayList<Event>();

    public FreeTime(Time begin, Time end,Room room) {
        this.begin = new Time (begin);
        this.end = new Time (end);
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
            if (event.getName().equals("Activité 35")){
                int forpoint;
            }
            event.setBegin(new Time(now));
            now.addTime(event.getDuration());
            event.setEnd(new Time(now));
            room.addEvent(event);
        }
    }
}
