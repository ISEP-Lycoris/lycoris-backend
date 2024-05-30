package com.lycoris.lycorisbackend.service;

import com.lycoris.lycorisbackend.entity.event.Room;
import com.lycoris.lycorisbackend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room getRoomByName(String name) {
        return roomRepository.findByName(name);
    }

    public List<Room> getRoomsByCapacity(Integer capacity) {
        List<Room> allRooms = roomRepository.findAll();
        return allRooms.stream()
                .filter(room -> room.getRoomCapacity() >= capacity)
                .collect(Collectors.toList());
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
