package com.lycoris.lycorisbackend.event;

import java.util.ArrayList;
import java.util.List;

public class CalendarMaker {
    private List<Room> rooms;
    private List<Event> events;
    private Time begin;
    private Time end;

    public CalendarMaker(List<Room> rooms, List<Event> events, Time begin, Time end) {
        this.rooms = rooms;
        this.events = events;
        this.begin = begin;
        this.end = end;
        makeCalendar();
    }

    public void makeCalendar(){
        List<Event> staticEvents = new ArrayList<>();
        List<Event> staticRoomEvent = new ArrayList<>();
        List<Event> staticHourEvent = new ArrayList<>();
        List<Event> freeEvents = new ArrayList<>();
        List<FreeTimes> roomsFreeTimes = new ArrayList<>();
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
        for (Room room : rooms) {//to verify
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
        for (FreeTimes freeTimes : roomsFreeTimes) freeTimes.sortEvent();
    }

    public void findRoomForStaticHourEvents(Event staticHourEvent,List<FreeTimes> allFreeTimes){
        int i = 0;
        while (true) {
            while (i < allFreeTimes.size() && !allFreeTimes.get(i).eventIsPossible(staticHourEvent)) i++;
            if (i >= allFreeTimes.size()) return;
            FreeTimes testFreeTimes = allFreeTimes.get(i);
            List<FreeTime> freeTime = testFreeTimes.getFreeTimes();
            int k = 0;
            while (k < freeTime.size() && !freeTime.get(k).eventIsPossible(staticHourEvent)) k++;
            if (k < freeTime.size() && freeTime.get(k).eventIsPossible(staticHourEvent)) {
                testFreeTimes.getRoom().addEvent(staticHourEvent);
                testFreeTimes.freeTimesUpdates();
                return;
            } else i++;
        }
    }

    public void findRoomForFreeEvents(Event freeEvent,List<FreeTimes> allFreeTimes){

        int i = 0;
        while (i<allFreeTimes.size() && !allFreeTimes.get(i).eventIsPossible(freeEvent))i++;

        if (allFreeTimes.get(i).eventIsPossible(freeEvent)){
            allFreeTimes.get(i).addEvent(freeEvent);
            freeEvent.addRoom(allFreeTimes.get(i).getRoom());
        }

    }

    public List<FreeTimes> getAllFreeTimes(List<Room> rooms){
        List<FreeTimes> freeTimes = new ArrayList<>();
        for (Room room : rooms) {
            freeTimes.add(new FreeTimes(room, begin, end));
        }
        return freeTimes;
    }

    private FreeTimes getFreeTimesOfRoom(Room room, List<FreeTimes> allFreeTimes){
        for (FreeTimes freeTimes : allFreeTimes) {
            if (freeTimes.getRoom()==room) return freeTimes;
        }
        return null;
    }
    public static void main(String[] args) {
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1); // Assurez-vous de sortir avec un code d'erreur en cas d'exception
        }
    }
    public static void test(){

        // Déclaration des salles
        List<Room> rooms = new ArrayList<>();
        for (int i = 1; i <= 22; i++) {
            rooms.add(new Room("Salle " + i, 0));
        }

        // Déclaration des temps de début et de fin pour le calendrier global
        Time globalBeginTime = new Time(2024, 6, 5, 17, 0);
        Time globalEndTime = new Time(2024, 6, 6, 3, 0);

        // Déclaration des événements
        List<Event> events = new ArrayList<>();

        // Création d'activités avec des listes vides
        Activity activity = new Activity(new ArrayList<>(), new ArrayList<>());

        // 20% avec tout défini
        events.add(new Event(new Time(2024, 6, 5, 19, 0), new Time(2024, 6, 5, 20, 30), rooms.get(0), "Activité 1", activity));
        events.add(new Event(new Time(2024, 6, 5, 19, 30), new Time(2024, 6, 5, 21, 0), rooms.get(1), "Activité 2", activity));
        events.add(new Event(new Time(2024, 6, 5, 20, 0), new Time(2024, 6, 5, 21, 30), rooms.get(2), "Activité 3", activity));
        events.add(new Event(new Time(2024, 6, 5, 20, 30), new Time(2024, 6, 5, 22, 0), rooms.get(3), "Activité 4", activity));
        events.add(new Event(new Time(2024, 6, 5, 21, 0), new Time(2024, 6, 5, 22, 30), rooms.get(4), "Activité 5", activity));
        events.add(new Event(new Time(2024, 6, 5, 21, 30), new Time(2024, 6, 5, 23, 0), rooms.get(5), "Activité 6", activity));
        events.add(new Event(new Time(2024, 6, 5, 22, 0), new Time(2024, 6, 5, 23, 30), rooms.get(6), "Activité 7", activity));
        events.add(new Event(new Time(2024, 6, 5, 22, 30), new Time(2024, 6, 6, 0, 0), rooms.get(7), "Activité 8", activity));

        // 20% seulement la salle
        events.add(new Event(90, rooms.get(0), "Activité 9", activity)); // 1h30
        events.add(new Event(60, rooms.get(1), "Activité 10", activity)); // 1h
        events.add(new Event(120, rooms.get(2), "Activité 11", activity)); // 2h
        events.add(new Event(150, rooms.get(3), "Activité 12", activity)); // 2h30
        events.add(new Event(180, rooms.get(4), "Activité 13", activity)); // 3h
        events.add(new Event(45, rooms.get(5), "Activité 14", activity)); // 45m
        events.add(new Event(90, rooms.get(6), "Activité 15", activity)); // 1h30
        events.add(new Event(30, rooms.get(7), "Activité 16", activity)); // 30m
        // 10% seulement les horaires
        events.add(new Event(new Time(2024, 6, 5, 23, 0), new Time(2024, 6, 6, 0, 30), "Activité 17", activity));
        events.add(new Event(new Time(2024, 6, 5, 23, 30), new Time(2024, 6, 6, 1, 0), "Activité 18", activity));
        events.add(new Event(new Time(2024, 6, 6, 0, 0), new Time(2024, 6, 6, 1, 30), "Activité 19", activity));
        events.add(new Event(new Time(2024, 6, 6, 0, 30), new Time(2024, 6, 6, 2, 0), "Activité 20", activity));
        // 50% ni salle ni horaires
        events.add(new Event(90, "Activité 21", activity)); // 1h30
        events.add(new Event(60, "Activité 22", activity)); // 1h
        events.add(new Event(120, "Activité 23", activity)); // 2h
        events.add(new Event(150, "Activité 24", activity)); // 2h30
        events.add(new Event(180, "Activité 25", activity)); // 3h
        events.add(new Event(45, "Activité 26", activity)); // 45m
        events.add(new Event(90, "Activité 27", activity)); // 1h30
        events.add(new Event(30, "Activité 28", activity)); // 30m
        events.add(new Event(120, "Activité 29", activity)); // 2h
        events.add(new Event(150, "Activité 30", activity)); // 2h30
        events.add(new Event(180, "Activité 31", activity)); // 3h
        events.add(new Event(45, "Activité 32", activity)); // 45m
        events.add(new Event(90, "Activité 33", activity)); // 1h30
        events.add(new Event(60, "Activité 34", activity)); // 1h
        events.add(new Event(120, "Activité 35", activity)); // 2h
        events.add(new Event(150, "Activité 36", activity)); // 2h30
        events.add(new Event(180, "Activité 37", activity)); // 3h
        events.add(new Event(45, "Activité 38", activity)); // 45m
        events.add(new Event(90, "Activité 39", activity)); // 1h30
        events.add(new Event(30, "Activité 40", activity)); // 30m

        // Création du calendrier
        CalendarMaker calendarMaker = new CalendarMaker(rooms, events, globalBeginTime, globalEndTime);
        int a = 0;
    }
}
