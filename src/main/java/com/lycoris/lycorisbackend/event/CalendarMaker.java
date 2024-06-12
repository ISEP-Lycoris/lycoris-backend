package com.lycoris.lycorisbackend.event;

import java.util.ArrayList;
import java.util.List;

public class CalendarMaker {
    private List<Room> rooms;
    private List<Event> events;
    private Time begin;
    private Time end;
    private boolean timeOptimisation;

    public CalendarMaker(List<Room> rooms, List<Event> events, Time begin, Time end, boolean timeOptimisation) {
        this.rooms = rooms;
        this.events = events;
        this.begin = begin;
        this.end = end;
        this.timeOptimisation = timeOptimisation;
        makeCalendar();
    }

    public void makeCalendar(){
        List<Event> staticEvents = new ArrayList<>();
        List<Event> staticRoomEvent = new ArrayList<>();
        List<Event> staticHourEvent = new ArrayList<>();
        List<Event> freeEvents = new ArrayList<>();
        List<FreeTimesDuringSession> roomsFreeTimes = new ArrayList<>();
        for (Event event : events) {
            if (event.getBegin() != null && event.getRoom() != null){
                staticEvents.add(event);
            } else if (event.getBegin() != null){
                staticHourEvent.add(event);
            } else if (event.getRoom() != null){
                staticRoomEvent.add(event);
            } else {
                freeEvents.add(event);
            }
        }
        roomsFreeTimes = getAllFreeTimes(rooms);
        for (Room room : rooms) {
            for (Event event : staticEvents) {
                if (event.getRoom() == room) {
                    room.addEvent(event);
                }
            }
            for (Event event : staticRoomEvent) {
                if (event.getRoom() == room) {
                    getFreeTimesOfRoom(room, roomsFreeTimes).addEvent(event);
                }
            }
        }
        for (Event event : staticHourEvent) {
            findRoomForStaticHourEvents(event, roomsFreeTimes);
        }
        for (Event event : freeEvents) {
            findRoomForFreeEvents(event, roomsFreeTimes);
        }
        for (FreeTimesDuringSession freeTimes : roomsFreeTimes) freeTimes.sortEvent();
    }

    public void findRoomForStaticHourEvents(Event staticHourEvent,List<FreeTimesDuringSession> allFreeTimes){
        int i = 0;
        int timeLeft = end.timeSince(begin);
        if (timeOptimisation) timeLeft = 0;
        boolean stop = false;
        FreeTimesDuringSession result = null;
        while (!stop) {
            while (i < allFreeTimes.size() && !allFreeTimes.get(i).eventIsPossible(staticHourEvent)) i++;
            if (i >= allFreeTimes.size()) {
                stop = true;
            }
            else {
                FreeTimesDuringSession testFreeTimes = allFreeTimes.get(i);
                List<FreeTime> freeTime = testFreeTimes.getFreeTimes();
                int k = 0;
                while (k < freeTime.size() && !freeTime.get(k).eventIsPossible(staticHourEvent)) k++;
                if (k < freeTime.size() && freeTime.get(k).eventIsPossible(staticHourEvent)) {
                    if (respectOptimisationConfiguration(timeLeft,testFreeTimes.minutesLeft())) {
                        result = testFreeTimes;
                        timeLeft = testFreeTimes.minutesLeft();
                    }
                }
                i++;
            }
        }
        result.getRoom().addEvent(staticHourEvent);
        staticHourEvent.addRoom(result.getRoom());
        result.freeTimesUpdates();
    }
    private boolean respectOptimisationConfiguration(int timeLeft,int timeToTest){
        return (!timeOptimisation && timeLeft>timeToTest) || (timeOptimisation && timeLeft<timeToTest);
    }

    public void findRoomForFreeEvents(Event freeEvent,List<FreeTimesDuringSession> allFreeTimes){
        int timeLeft = end.timeSince(begin);
        if (timeOptimisation) timeLeft = 0;
        int i = 0;
        boolean stop = false;
        FreeTimesDuringSession result = null;
        while (!stop) {
            while (i < allFreeTimes.size() && !allFreeTimes.get(i).eventIsPossible(freeEvent)) i++;
            if (i >= allFreeTimes.size()) stop = true;
            if (i < allFreeTimes.size() && allFreeTimes.get(i).eventIsPossible(freeEvent)) {
                if (respectOptimisationConfiguration(timeLeft,allFreeTimes.get(i).minutesLeft())) {
                    result = allFreeTimes.get(i);
                    timeLeft = result.minutesLeft();
                }
            }
            i++;
        }
        result.addEvent(freeEvent);
        freeEvent.addRoom(result.getRoom());

    }

    public List<FreeTimesDuringSession> getAllFreeTimes(List<Room> rooms){
        List<FreeTimesDuringSession> freeTimes = new ArrayList<>();
        for (Room room : rooms) {
            freeTimes.add(new FreeTimesDuringSession(room, begin, end));
        }
        return freeTimes;
    }

    private FreeTimesDuringSession getFreeTimesOfRoom(Room room, List<FreeTimesDuringSession> allFreeTimes){
        for (FreeTimesDuringSession freeTimes : allFreeTimes) {
            if (freeTimes.getRoom()==room) return freeTimes;
        }
        return null;
    }
}
