package com.lycoris.lycorisbackend.controller;

import com.lycoris.lycorisbackend.entity.event.Activity;
import com.lycoris.lycorisbackend.entity.event.Event;
import com.lycoris.lycorisbackend.entity.event.Room;
import com.lycoris.lycorisbackend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//public static void test() {
//    List<Room> rooms = new ArrayList<>();
//    for (int i = 1; i <= 22; i++) {
//        rooms.add(new Room(i,"Salle " + i, 0));
//    }
//
//    Time globalBeginLocalTime = Time.valueOf("03:00:00");
//    Time globalEndLocalTime = Time.valueOf("17:00:00");
//
//    List<Event> events = new ArrayList<>();
//
//    Activity activity = new Activity(1L, new ArrayList<>(), new ArrayList<>(), "Yoga");
//
//    events.add(new Event(null, "Activite 1", rooms.get(0), activity, Time.valueOf("03:00:00"), Time.valueOf("04:00:00")));
//    events.add(new Event(null, "Activite 2", rooms.get(0), activity, Time.valueOf("04:00:00"), Time.valueOf("04:30:00")));
//    events.add(new Event(null, "Activite 3", rooms.get(0), activity, Time.valueOf("03:00:00"), Time.valueOf("05:30:00")));
//    events.add(new Event(null, "Activite 4", rooms.get(0), activity, Time.valueOf("02:30:00"), Time.valueOf("03:00:00")));
//    events.add(new Event(null, "Activite 5", rooms.get(0), activity, Time.valueOf("10:00:00"), Time.valueOf("11:00:00")));
//    events.add(new Event(null, "Activite 6", rooms.get(0), activity, Time.valueOf("18:00:00"), Time.valueOf("20:00:00")));
//    events.add(new Event(null, "Activite 7", rooms.get(0), activity, Time.valueOf("12:00:00"), Time.valueOf("17:00:00")));
//    events.add(new Event(null, "Activite 8", rooms.get(0), activity, Time.valueOf("05:00:00"), Time.valueOf("10:00:00")));
//
//    System.out.println("before");
//    for (Event event : events) {
//        System.out.println(event);
//    }
//    System.out.println();
//
//    CalendarMaker calendarMaker = new CalendarMaker(rooms, events, globalBeginLocalTime, globalEndLocalTime);
//
//    System.out.println("after");
//    for (Event event : events) {
//        System.out.println(event);
//    }
//}

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomService.getRoomById(id);
        return room.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Room> getRoomByName(@PathVariable String name) {
        Room room = roomService.getRoomByName(name);
        return room != null ? ResponseEntity.ok(room) : ResponseEntity.notFound().build();
    }

    @GetMapping("/capacity/{capacity}")
    public List<Room> getRoomsByCapacity(@PathVariable Integer capacity) {
        return roomService.getRoomsByCapacity(capacity);
    }

    @GetMapping("/available")
    public List<Room> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    @GetMapping("/unavailable")
    public List<Room> getUnavailableRooms() {
        return roomService.getUnavailableRooms();
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.saveRoom(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        Optional<Room> roomOptional = roomService.getRoomById(id);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            room.setName(roomDetails.getName());
            room.setRoomCapacity(roomDetails.getRoomCapacity());
            return ResponseEntity.ok(roomService.saveRoom(room));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
