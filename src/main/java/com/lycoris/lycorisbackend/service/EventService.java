package com.lycoris.lycorisbackend.service;

import com.lycoris.lycorisbackend.entity.event.Activity;
import com.lycoris.lycorisbackend.entity.event.Event;
import com.lycoris.lycorisbackend.entity.event.Room;
import com.lycoris.lycorisbackend.repository.ActivityRepository;
import com.lycoris.lycorisbackend.repository.EventRepository;
import com.lycoris.lycorisbackend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final RoomRepository roomRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public EventService(EventRepository eventRepository, RoomRepository roomRepository, ActivityRepository activityRepository) {
        this.eventRepository = eventRepository;
        this.roomRepository = roomRepository;
        this.activityRepository = activityRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> getEventsByName(String name) {
        return eventRepository.findByName(name);
    }

    public List<Event> getEventsByRoomId(Long roomId) {
        return eventRepository.findByRoom_Id(roomId);
    }

    public List<Event> getEventsByActivityId(Long activityId) {
        return eventRepository.findByActivity_Id(activityId);
    }

    public Event createEvent(Event event) {
        // Check if the room ID is provided and if the room exists in the database
        if (event.getRoom() != null && event.getRoom().getId() != null) {
            Optional<Room> roomOptional = roomRepository.findById(event.getRoom().getId());
            roomOptional.ifPresent(event::setRoom); // Set room if found
        }

        // Check if the activity ID is provided and if the activity exists in the database
        if (event.getActivity() != null && event.getActivity().getId() != null) {
            Optional<Activity> activityOptional = activityRepository.findById(event.getActivity().getId());
            activityOptional.ifPresent(event::setActivity); // Set activity if found
        }

        // You might want to perform validation here before saving the event
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        return eventRepository.findById(id).map(event -> {
            event.setBegin_time(eventDetails.getBegin_time());
            event.setEnd_time(eventDetails.getEnd_time());
            event.setRoom(eventDetails.getRoom());
            event.setName(eventDetails.getName());
            event.setActivity(eventDetails.getActivity());
            return eventRepository.save(event);
        }).orElseThrow(() -> new RuntimeException("Event not found with id " + id));
    }
}
