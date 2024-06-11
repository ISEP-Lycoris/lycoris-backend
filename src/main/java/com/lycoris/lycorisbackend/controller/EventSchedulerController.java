package com.lycoris.lycorisbackend.controller;

import com.lycoris.lycorisbackend.entity.event.EventScheduler;
import com.lycoris.lycorisbackend.entity.event.Room;
import com.lycoris.lycorisbackend.entity.event.Event;
import com.lycoris.lycorisbackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class EventSchedulerController {

    @Autowired
    private EventService eventService; // Autowire your EventService

    @PostMapping("/events")
    public ResponseEntity<List<Event>> scheduleEvents(@RequestBody ScheduleRequest request) {
        try {


            List<Event> scheduledEvents = EventScheduler.scheduleEvents(request.getRooms(), request.getEvents(), request.isParallel());

            for (Event event : scheduledEvents) {
                System.out.println(event);
            }

            // Update each scheduled event in the database
            for (Event event : scheduledEvents) {
                // Assuming you have an ID for each event, retrieve it and pass it along with the event object
                Long eventId = event.getId();
                Event updatedEvent = eventService.updateEvent(eventId, event); // Pass the ID along with the event object
            }

            return ResponseEntity.ok(scheduledEvents);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
