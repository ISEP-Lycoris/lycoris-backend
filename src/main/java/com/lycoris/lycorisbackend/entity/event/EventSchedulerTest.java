package com.lycoris.lycorisbackend.entity.event;

import com.lycoris.lycorisbackend.entity.event.Activity;
import com.lycoris.lycorisbackend.entity.event.Event;
import com.lycoris.lycorisbackend.entity.event.EventScheduler;
import com.lycoris.lycorisbackend.entity.event.Room;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;



public class EventSchedulerTest {
    public static void main(String[] args) {
        // Create rooms
        // Global time bounds
        Time globalBeginLocalTime = Time.valueOf("03:00:00");
        Time globalEndTime = Time.valueOf("17:00:00");

        List<Room> rooms = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Room room =new Room(i, "Salle " + i, 100);
            room.setOuvertureTime(globalBeginLocalTime);
            room.setFermetureTime(globalEndTime);
            rooms.add(room);
        }


        // Create events
        List<Event> events = new ArrayList<>();
        Activity activity = new Activity(1L, new ArrayList<>(), new ArrayList<>(), "Yoga");

        events.add(new Event(1L, "Activite 1", rooms.get(0), activity, 60)); // 1 hour event
        events.add(new Event(2L, "Activite 2", rooms.get(0), activity, 30)); // 30 minutes event
        events.add(new Event(3L, "Activite 3", rooms.get(0), activity, 150)); // 2.5 hours event
        events.add(new Event(4L, "Activite 4", rooms.get(0), activity, 30)); // 30 minutes event
        events.add(new Event(5L, "Activite 5", rooms.get(0), activity, 60)); // 1 hour event
        events.add(new Event(6L, "Activite 6", rooms.get(0), activity, 120)); // 2 hours event
        events.add(new Event(7L, "Activite 7", rooms.get(0), activity, 300)); // 5 hours event
        events.add(new Event(8L, "Activite 8", rooms.get(0), activity, 300)); // 5 hours event



        // Schedule events
        EventScheduler.scheduleEvents(rooms, events,true);

        // Display scheduled events
        for (Room room : rooms) {
            System.out.println("Room: " + room.getName());
            for (Event event : room.getEvents()) {
                System.out.println(event);
            }
            System.out.println();
        }
    }
}

