package com.lycoris.lycorisbackend.repository;

import com.lycoris.lycorisbackend.entity.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByName(String name);
    List<Event> findByRoom_Id(Long roomId);
    List<Event> findByActivity_Id(Long activityId);
}