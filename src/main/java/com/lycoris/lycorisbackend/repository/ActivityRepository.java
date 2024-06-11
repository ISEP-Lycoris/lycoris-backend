package com.lycoris.lycorisbackend.repository;

import com.lycoris.lycorisbackend.entity.event.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByAnimators_FirstName(String firstName);
    List<Activity> findByAnimators_LastName(String lastName);
    List<Activity> findBySpectators_FirstName(String firstName);
    List<Activity> findBySpectators_LastName(String lastName);
}
