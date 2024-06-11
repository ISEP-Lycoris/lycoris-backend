package com.lycoris.lycorisbackend.controller;


import com.lycoris.lycorisbackend.entity.event.Activity;
import com.lycoris.lycorisbackend.entity.event.Event;
import com.lycoris.lycorisbackend.entity.event.Room;
import com.lycoris.lycorisbackend.service.ActivityService;
import com.lycoris.lycorisbackend.service.EventService;
import com.lycoris.lycorisbackend.service.RoomService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final ActivityService activityService;
    private final RoomService roomService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public List<Event> getEventsByName(@PathVariable String name) {
        return eventService.getEventsByName(name);
    }

    @GetMapping("/room/{roomId}")
    public List<Event> getEventsByRoomId(@PathVariable Long roomId) {
        return eventService.getEventsByRoomId(roomId);
    }

    @GetMapping("/activity/{activityId}")
    public List<Event> getEventsByActivityId(@PathVariable Long activityId) {
        return eventService.getEventsByActivityId(activityId);
    }



    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Map<String, Object> requestData) {
        try {
            System.out.println("Request data: " + requestData); // Debugging statement

            Long roomId = Long.parseLong(requestData.get("room_id").toString());
            Long activityId = Long.parseLong(requestData.get("activity_id").toString());

            System.out.println("Fetching room with ID: " + roomId); // Debugging statement
            Room room = roomService.getRoomById(roomId)
                    .orElseThrow(() -> new EntityNotFoundException("Room not found"));

            System.out.println("Fetching activity with ID: " + activityId); // Debugging statement
            Activity activity = activityService.findActivityById(activityId)
                    .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

            Event event = new Event();
            event.setBegin_time(Time.valueOf(requestData.get("begin_time").toString()));
            event.setEnd_time(Time.valueOf(requestData.get("end_time").toString()));
            event.setName(requestData.get("name").toString());
            event.setRoom(room);
            event.setActivity(activity);

            System.out.println("Creating event: " + event); // Debugging statement
            Event createdEvent = eventService.createEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        } catch (EntityNotFoundException e) {
            e.printStackTrace(); // Print the stack trace for debugging
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }






    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDetails);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
