package com.lycoris.lycorisbackend.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FreeTimes {
    private List<FreeTime> freeTimes = new ArrayList<FreeTime>();;
    private List<Event> events = new ArrayList<>();
    private Room room;
    private Time begin;
    private Time end;

    public FreeTimes(Room room, Time begin, Time end) {
        this.room = room;
        this.begin = begin;
        this.end = end;
        addFreeTimes();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<FreeTime> getFreeTimes() {
        return freeTimes;
    }
    public Room getRoom() {
        return room;
    }

    private void addFreeTimes() {
        List<Time> open = new ArrayList<>();
        List<Time> close = new ArrayList<>();
        boolean hadFreeTime = true;
        for (Event event : room.getEvents()) {
            open.add(event.getEnd());
            close.add(event.getBegin());
            if (event.getBegin().isBefore(begin) && event.getEnd().isAfter(end)) {
                hadFreeTime = false;
            }
        }
        if (hadFreeTime) {
            if (open.size()==0) open.add(begin);
            open = duringTheSession(begin, end, open);
            if (close.size()==0) close.add(end);
            close = duringTheSession(begin, end, close);

            addLimit(open,close);
            for (int k = 0; k < open.size(); k++) {
                freeTimes.add(new FreeTime(open.get(k), close.get(k),room));
            }
        }
    }

    private List<Time> duringTheSession(Time begin, Time end, List<Time> times) {
        List<Time> result = new ArrayList<>();

        for (Time time : times ) {
            if(time.isAfter(begin) && time.isBefore(end)){
                result.add(time);
            }
        }
        return result;
    }
    private void addLimit(List<Time> open, List<Time> close) {
        if (open.size() == 0 || close.size() == 0) {return;}
        if (open.get(0).isAfter(close.get(0))) {
            open.add(0,begin);
        }
        if (open.get(0).isBefore(close.get(0))) {
            close.add(end);
        }
    }
    public boolean eventIsPossible(Event event) {
        List<Integer> openTime = new ArrayList<>();
        List<Integer> eventTime = new ArrayList<>();
        eventTime.add(event.getDuration());
        for (Event eventToSort : events) eventTime.add(eventToSort.getDuration());
        for (FreeTime freeTime : freeTimes) openTime.add(freeTime.getDuration());


        Collections.sort(openTime, Collections.reverseOrder());
        Collections.sort(eventTime, Collections.reverseOrder());
        List<Integer> remainingDurations = new ArrayList<>(openTime);

        for (int duration : eventTime) {
            boolean placed = false;
            for (int i = 0; i < remainingDurations.size(); i++) {
                if (remainingDurations.get(i) >= duration) {
                    remainingDurations.set(i, remainingDurations.get(i) - duration);
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                return false;
            }
        }
        return true;
    }
    public void freeTimesUpdates(){
        freeTimes.clear();
        addFreeTimes();
    }

    public void sortEvent() {
        List<Integer> openTime = new ArrayList<>();
        List<Integer> eventTime = new ArrayList<>();
        for (Event eventToSort : events) eventTime.add(eventToSort.getDuration());
        for (FreeTime freeTime : freeTimes) openTime.add(freeTime.getDuration());
        eventTime = organizeLists(openTime, eventTime);
        List<Event> result = new ArrayList<>();
        for (int time : eventTime) {
            int i = 0;
            while (events.get(i).getDuration() != time) i++;
            result.add(events.get(i));
            events.remove(i);
        }
        setTime(result);
    }

    public List<Integer> organizeLists(List<Integer> condition, List<Integer> toSort) {
        Collections.sort(toSort);
        List<Integer> result = new ArrayList<>();
        int index = 0;
        for (int maxSum : condition) {
            int currentSum = 0;
            while (index < toSort.size() && currentSum + toSort.get(index) <= maxSum) {
                result.add(toSort.get(index));
                currentSum += toSort.get(index);
                index++;
            }
        }
        return result;
    }

    private void setTime(List<Event> events) {
        for (FreeTime freeTime : freeTimes) {
            int duration = 0;
            while (events.size()>0 && duration+events.get(0).getDuration() < freeTime.getDuration()) {
                freeTime.addEvents(events.get(0));
                duration += events.get(0).getDuration();
                events.remove(0);
            }
            freeTime.eventSetTime();
        }
    }
}
