package com.lycoris.lycorisbackend.service;

import com.lycoris.lycorisbackend.entity.event.Activity;
import com.lycoris.lycorisbackend.repository.ActivityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> findAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> findActivityById(Long id) {
        return activityRepository.findById(id);
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public void deleteActivityById(Long id) {
        activityRepository.deleteById(id);
    }

    public List<Activity> findActivitiesByAnimatorFirstName(String firstName) {
        return activityRepository.findByAnimators_FirstName(firstName);
    }

    public List<Activity> findActivitiesByAnimatorLastName(String lastName) {
        return activityRepository.findByAnimators_LastName(lastName);
    }

    public List<Activity> findActivitiesBySpectatorFirstName(String firstName) {
        return activityRepository.findBySpectators_FirstName(firstName);
    }

    public List<Activity> findActivitiesBySpectatorLastName(String lastName) {
        return activityRepository.findBySpectators_LastName(lastName);
    }

    public Activity updateActivity(Long id, Activity activityDetails) {
        return activityRepository.findById(id).map(activity -> {
            activity.setAnimators(activityDetails.getAnimators());
            activity.setSpectators(activityDetails.getSpectators());
            // Include other fields to update here
            return activityRepository.save(activity);
        }).orElseThrow(() -> new RuntimeException("Activity not found with id " + id));
    }

    public Activity deleteAnimatorFromActivity(Long activityId, Long animatorId) {
        // Find the activity by its ID
        Optional<Activity> activityOptional = activityRepository.findById(activityId);
        if (activityOptional.isPresent()) {
            Activity activity = activityOptional.get();
            // Remove the animator with the specified ID from the list of animators
            activity.getAnimators().removeIf(animator -> animator.getId().equals(animatorId));
            // Save the updated activity
            return activityRepository.save(activity);
        } else {
            throw new EntityNotFoundException("Activity not found with id: " + activityId);
        }
    }

    // Method to remove a spectator from an activity
    public Activity deleteSpectatorFromActivity(Long activityId, Long spectatorId) {
        // Find the activity by its ID
        Optional<Activity> activityOptional = activityRepository.findById(activityId);
        if (activityOptional.isPresent()) {
            Activity activity = activityOptional.get();
            // Remove the spectator with the specified ID from the list of spectators
            activity.getSpectators().removeIf(spectator -> spectator.getId().equals(spectatorId));
            // Save the updated activity
            return activityRepository.save(activity);
        } else {
            throw new EntityNotFoundException("Activity not found with id: " + activityId);
        }
    }

}
