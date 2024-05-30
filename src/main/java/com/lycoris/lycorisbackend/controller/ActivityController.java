package com.lycoris.lycorisbackend.controller;

import com.lycoris.lycorisbackend.entity.event.Activity;
import com.lycoris.lycorisbackend.entity.person.Person;
import com.lycoris.lycorisbackend.service.ActivityService;
import com.lycoris.lycorisbackend.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final PersonService personService;

    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.findAllActivities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        Optional<Activity> activity = activityService.findActivityById(id);
        return activity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityService.saveActivity(activity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activityDetails) {
        try {
            Activity updatedActivity = activityService.updateActivity(id, activityDetails);
            return ResponseEntity.ok(updatedActivity);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivityById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/animator/firstname/{firstName}")
    public List<Activity> getActivitiesByAnimatorFirstName(@PathVariable String firstName) {
        return activityService.findActivitiesByAnimatorFirstName(firstName);
    }

    @GetMapping("/animator/lastname/{lastName}")
    public List<Activity> getActivitiesByAnimatorLastName(@PathVariable String lastName) {
        return activityService.findActivitiesByAnimatorLastName(lastName);
    }

    @GetMapping("/spectator/firstname/{firstName}")
    public List<Activity> getActivitiesBySpectatorFirstName(@PathVariable String firstName) {
        return activityService.findActivitiesBySpectatorFirstName(firstName);
    }

    @GetMapping("/spectator/lastname/{lastName}")
    public List<Activity> getActivitiesBySpectatorLastName(@PathVariable String lastName) {
        return activityService.findActivitiesBySpectatorLastName(lastName);
    }

    @PostMapping("/{id}/animators")
    public ResponseEntity<Activity> addAnimator(@PathVariable Long id, @RequestBody Person animator) {
        Optional<Activity> activityOptional = activityService.findActivityById(id);
        if (activityOptional.isPresent()) {
            Activity activity = activityOptional.get();

            // Check if the person exists by first name, last name, and role
            List<Person> existingPersons = personService.getPersonsByFirstNameAndLastNameAndRole(animator.getFirstName(), animator.getLastName(), animator.getRole());

            if (!existingPersons.isEmpty()) {
                // Person exists, use the first one found
                Person existingAnimator = existingPersons.get(0);
                activity.addAnimator(existingAnimator);
            } else {
                // Person doesn't exist, save them first
                Person savedAnimator = personService.savePerson(animator);
                activity.addAnimator(savedAnimator);
            }

            // Save the updated activity
            activityService.saveActivity(activity);

            // Return the updated activity in the response
            return ResponseEntity.ok(activity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }





    @PostMapping("/{id}/spectators")
    public ResponseEntity<Activity> addSpectator(@PathVariable Long id, @RequestBody Person spectator) {
        Optional<Activity> activityOptional = activityService.findActivityById(id);
        if (activityOptional.isPresent()) {
            Activity activity = activityOptional.get();

            // Check if the spectator exists by first name, last name, and role
            List<Person> existingSpectators = personService.getPersonsByFirstNameAndLastNameAndRole(spectator.getFirstName(), spectator.getLastName(), spectator.getRole());

            if (!existingSpectators.isEmpty()) {
                // Spectator exists, use the first one found
                Person existingSpectator = existingSpectators.get(0);
                activity.addSpectator(existingSpectator);
            } else {
                // Spectator doesn't exist, save them first
                Person savedSpectator = personService.savePerson(spectator);
                activity.addSpectator(savedSpectator);
            }

            // Save the updated activity
            activityService.saveActivity(activity);

            // Return the updated activity in the response
            return ResponseEntity.ok(activity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}/animators")
    public ResponseEntity<List<Person>> getAnimatorsByActivityId(@PathVariable Long id) {
        Optional<Activity> activityOptional = activityService.findActivityById(id);
        if (activityOptional.isPresent()) {
            List<Person> animators = activityOptional.get().getAnimators();
            return ResponseEntity.ok(animators);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/spectators")
    public ResponseEntity<List<Person>> getSpectatorsByActivityId(@PathVariable Long id) {
        Optional<Activity> activityOptional = activityService.findActivityById(id);
        if (activityOptional.isPresent()) {
            List<Person> spectators = activityOptional.get().getSpectators();
            return ResponseEntity.ok(spectators);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{activityId}/animators/{animatorId}")
    public ResponseEntity<Void> deleteAnimatorFromActivity(@PathVariable Long activityId, @PathVariable Long animatorId) {
        activityService.deleteAnimatorFromActivity(activityId, animatorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{activityId}/spectators/{spectatorId}")
    public ResponseEntity<Void> deleteSpectatorFromActivity(@PathVariable Long activityId, @PathVariable Long spectatorId) {
        activityService.deleteSpectatorFromActivity(activityId, spectatorId);
        return ResponseEntity.noContent().build();
    }


}
