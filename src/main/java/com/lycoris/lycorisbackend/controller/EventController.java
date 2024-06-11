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
@RequestMapping("/event")
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
            Long roomId = Long.parseLong(requestData.get("roomId").toString());
            Long activityId = Long.parseLong(requestData.get("activityId").toString());

            // Fetch room and activity
            Room room = roomService.getRoomById(roomId)
                    .orElseThrow(() -> new EntityNotFoundException("Room not found"));
            Activity activity = activityService.findActivityById(activityId)
                    .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

            Event event = new Event();
            event.setName(requestData.get("name").toString());
            event.setRoom(room);
            event.setActivity(activity);

            // Check if begin_time and end_time are provided
            if (requestData.containsKey("beginTime") && requestData.get("beginTime") != null) {
                event.setBegin_time(Time.valueOf(requestData.get("beginTime").toString()));
            }
            if (requestData.containsKey("endTime") && requestData.get("endTime") != null) {
                event.setEnd_time(Time.valueOf(requestData.get("endTime").toString()));
            }

            // Set duration if both begin_time and end_time are null
            if (event.getBegin_time() == null && event.getEnd_time() == null) {
                Integer duration = Integer.parseInt(requestData.get("duration").toString());
                event.setDuration(duration);
            } else {
                // Calculate duration based on begin_time and end_time
                long durationMinutes = event.getEnd_time().toLocalTime().toSecondOfDay() / 60 - event.getBegin_time().toLocalTime().toSecondOfDay() / 60;
                event.setDuration((int) durationMinutes);
            }

            Event createdEvent = eventService.createEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
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
