package com.lycoris.lycorisbackend;

import com.lycoris.lycorisbackend.event.Activity;
import com.lycoris.lycorisbackend.event.Event;
import com.lycoris.lycorisbackend.event.Room;
import com.lycoris.lycorisbackend.event.Time;
import com.lycoris.lycorisbackend.person.Person;
import com.lycoris.lycorisbackend.person.Roles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

@RestController
public class HelloController {

    @GetMapping("api/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }

    @GetMapping("api/createCalendarEvent")
    public String createCalendarEvent() {
        Room room = new Room("Room 1", 10);

        Person animator = new Person("John", "Doe", Roles.ANIMATOR);
        Person spectator = new Person("Jane", "Doe", Roles.SPECTATOR);

        Activity activity = new Activity(Collections.singletonList(animator), Collections.singletonList(spectator));

        Event event = new Event(new Time(10,0,0),(new Time(11,30,0)), room, "Event 1", activity);
        room.addEvent(event);

        return ("event name: "+event.getName()+
                "\n event Room: "+ event.getRoom().getName()+
                "\n animator: "+event.getActivity().getAnimator().get(0).getFirstName()+" "+event.getActivity().getAnimator().get(0).getLastName() +
                "\n duration: "+event.getDuration()+
                "\n spectator: "+event.getActivity().getSpectators().get(0).getFirstName()+" "+event.getActivity().getSpectators().get(0).getLastName());

    }
}