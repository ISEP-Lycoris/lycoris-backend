package com.lycoris.lycorisbackend.entity.event;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventScheduler {

    public static List<Event> scheduleEvents(List<Room> rooms, List<Event> events, Boolean parallel) {

        // Sort events by duration in descending order
        events.sort(Comparator.comparingInt(Event::getDuration).reversed());

        if (parallel) {
            // Iterate through each event
            for (Event event : events) {
                // Find available room or room with earliest available time slot
                Room availableRoom = findAvailableRoom(rooms);

                if (availableRoom != null) {
                    // Assign event to room
                    availableRoom.addEvent(event);
                    // Update room's availability
                    availableRoom.setDisponible(false);
                    // Set event start and end times
                    setEventTimes(availableRoom, event);
                } else {
                    // Find the room with the earliest end time
                    Room earliestRoom = findEarliestRoom(rooms);
                    // Assign event to earliest room
                    earliestRoom.addEvent(event);
                    // Update room's availability
                    earliestRoom.setDisponible(false);
                    // Set event start and end times
                    setEventTimes(earliestRoom, event);
                }
            }
        }

        else{
            // Trier les salles par heure d'ouverture croissante
            rooms.sort(Comparator.comparing(Room::getOuvertureTime));

            Room room = rooms.get(0);
            Time currentTime = room.getOuvertureTime();

            for (Event event:events){
                if(event.getDuration()*60000+currentTime.getTime()<room.getFermetureTime().getTime()){
                    room.addEvent(event);
                    event.setBegin_time(currentTime);
                    event.setEnd_time(new Time(currentTime.getTime()+event.getDuration()*60000));
                    currentTime = event.getEnd_time();
                }
                else{
                    room = rooms.get(rooms.indexOf(room)+1);
                    currentTime = room.getOuvertureTime();
                    room.addEvent(event);
                    event.setBegin_time(currentTime);
                    event.setEnd_time(new Time(currentTime.getTime()+event.getDuration()*60000));
                    currentTime = event.getEnd_time();
                }
            }
        }
        return events;
    }

    private static Room findAvailableRoom(List<Room> rooms) {
        for (Room room : rooms) {
            if (room.isDisponible()) {
                return room;
            }
        }
        return null;
    }

    private static Room findEarliestRoom(List<Room> rooms) {
        Room earliestRoom = null; // Initialize earliestRoom to null initially
        long earliestEndTime = Long.MAX_VALUE; // Initialize earliest end time to maximum value

        for (Room room : rooms) {

                long lastEndTime = room.getLastEventEndTime();
                if (lastEndTime < earliestEndTime) {
                    earliestEndTime = lastEndTime;
                    earliestRoom = room;
                }

        }
        return earliestRoom;
    }

    private static void setEventTimes(Room room, Event event) {
        // If this is the first event in the room, set start time to global begin time
        if (room.getEvents().size()==1) {
            event.setBegin_time(room.getOuvertureTime());
            event.setEnd_time(new Time(event.getBegin_time().getTime() + event.getDuration() * 60000)); // Duration to milliseconds
        }
        else {

            Event lastEvent = room.getEvents().get(room.getEvents().size()-2);
            // Set start time of the event to the end time of the last event
            event.setBegin_time(new Time(lastEvent.getEnd_time().getTime()));
            event.setEnd_time(new Time(event.getBegin_time().getTime() + event.getDuration() * 60000)); // Duration to milliseconds
            }
        }
    }

