package com.lycoris.lycorisbackend.repository;

import com.lycoris.lycorisbackend.entity.event.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Find a room by its name
    Room findByName(String name);

    // Find rooms with a capacity greater than or equal to the specified value
    List<Room> findByRoomCapacityGreaterThanEqual(Integer capacity);
}
