package com.lycoris.lycorisbackend.service;

import com.lycoris.lycorisbackend.entity.event.Event;
import com.lycoris.lycorisbackend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        return eventRepository.findById(id).map(event -> {
            event.setBegin(eventDetails.getBegin());
            event.setEnd(eventDetails.getEnd());
            event.setRoom(eventDetails.getRoom());
            event.setName(eventDetails.getName());
            event.setActivity(eventDetails.getActivity());
            return eventRepository.save(event);
        }).orElseThrow(() -> new RuntimeException("Event not found with id " + id));
    }
}
