package com.lycoris.lycorisbackend.entity.event;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventScheduler {

    /**
     * Schedules events in rooms based on the given mode (parallel or series).
     *
     * @param rooms    List of rooms available for scheduling.
     * @param events   List of events to be scheduled.
     * @param parallel Boolean indicating if events should be scheduled in parallel or series.
     * @return List of scheduled events.
     */
    public static List<Event> scheduleEvents(List<Room> rooms, List<Event> events, boolean parallel) {
        // Sort events by duration in descending order
        events.sort(Comparator.comparingInt(Event::getDuration).reversed());

        if (parallel) {
            scheduleEventsInParallel(rooms, events);
        } else {
            scheduleEventsInSeries(rooms, events);
        }

        return events;
    }

    /**
     * Schedules events in parallel across multiple rooms.
     *
     * @param rooms  List of rooms available for scheduling.
     * @param events List of events to be scheduled.
     */
    private static void scheduleEventsInParallel(List<Room> rooms, List<Event> events) {
        for (Event event : events) {
            Room availableRoom = findAvailableRoom(rooms);

            if (availableRoom != null) {
                assignEventToRoom(availableRoom, event);
            } else {
                Room earliestRoom = findEarliestRoom(rooms);
                assignEventToRoom(earliestRoom, event);
            }
        }
    }

    /**
     * Schedules events in series within a single room until it's full, then moves to the next room.
     *
     * @param rooms  List of rooms available for scheduling.
     * @param events List of events to be scheduled.
     */
    private static void scheduleEventsInSeries(List<Room> rooms, List<Event> events) {
        // Sort rooms by opening time
        rooms.sort(Comparator.comparing(Room::getOuvertureTime));

        Room currentRoom = rooms.get(0);
        Time currentTime = currentRoom.getOuvertureTime();

        for (Event event : events) {
            if (canFitEventInRoom(currentRoom, event, currentTime)) {
                assignEventToRoomAtTime(currentRoom, event, currentTime);
                currentTime = event.getEnd_time();
            } else {
                currentRoom = getNextRoom(rooms, currentRoom);
                currentTime = currentRoom.getOuvertureTime();
                assignEventToRoomAtTime(currentRoom, event, currentTime);
                currentTime = event.getEnd_time();
            }
        }
    }

    /**
     * Finds an available room.
     *
     * @param rooms List of rooms.
     * @return The available room, or null if no room is available.
     */
    private static Room findAvailableRoom(List<Room> rooms) {
        return rooms.stream().filter(Room::isDisponible).findFirst().orElse(null);
    }

    /**
     * Finds the room with the earliest end time.
     *
     * @param rooms List of rooms.
     * @return The room with the earliest end time.
     */
    private static Room findEarliestRoom(List<Room> rooms) {
        return rooms.stream().min(Comparator.comparingLong(Room::getLastEventEndTime)).orElse(null);
    }

    /**
     * Assigns an event to a room and updates the room's availability.
     *
     * @param room  The room to which the event will be assigned.
     * @param event The event to be assigned.
     */
    private static void assignEventToRoom(Room room, Event event) {
        room.addEvent(event);
        room.setDisponible(false);
        setEventTimes(room, event);
    }

    /**
     * Assigns an event to a room at a specific time.
     *
     * @param room       The room to which the event will be assigned.
     * @param event      The event to be assigned.
     * @param startTime The time at which the event will start.
     */
    private static void assignEventToRoomAtTime(Room room, Event event, Time startTime) {
        room.addEvent(event);
        event.setBegin_time(startTime);
        event.setEnd_time(new Time(startTime.getTime() + event.getDuration() * 60000)); // Duration in milliseconds
    }

    /**
     * Sets the start and end times for an event in a room.
     *
     * @param room  The room where the event will be scheduled.
     * @param event The event to be scheduled.
     */
    private static void setEventTimes(Room room, Event event) {
        if (room.getEvents().size() == 1) {
            event.setBegin_time(room.getOuvertureTime());
        } else {
            Event lastEvent = room.getEvents().get(room.getEvents().size() - 2);
            event.setBegin_time(new Time(lastEvent.getEnd_time().getTime()));
        }
        event.setEnd_time(new Time(event.getBegin_time().getTime() + event.getDuration() * 60000)); // Duration to milliseconds
    }

    /**
     * Checks if an event can fit in a room given the current time.
     *
     * @param room        The room to check.
     * @param event       The event to be scheduled.
     * @param currentTime The current time.
     * @return True if the event can fit, false otherwise.
     */
    private static boolean canFitEventInRoom(Room room, Event event, Time currentTime) {
        return event.getDuration() * 60000 + currentTime.getTime() < room.getFermetureTime().getTime();
    }

    /**
     * Gets the next room in the list of rooms.
     *
     * @param rooms       List of rooms.
     * @param currentRoom The current room.
     * @return The next room in the list.
     */
    private static Room getNextRoom(List<Room> rooms, Room currentRoom) {
        int nextIndex = rooms.indexOf(currentRoom) + 1;
        return rooms.get(nextIndex);
    }
}
