    package com.lycoris.lycorisbackend.entity.event;


    import jakarta.persistence.*;
    import lombok.*;

    import java.sql.Time;
    import java.time.Duration;
    import java.time.LocalTime;
    import java.time.temporal.ChronoUnit;


    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Event {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;


        @Column
        private String name;

        @ManyToOne
        @JoinColumn(name = "room_id")
        private Room room;

        @ManyToOne
        @JoinColumn(name = "activity_id")
        private Activity activity;


        @Column
        private Time begin_time;

        @Column
        private Time end_time;

        private int duration;

        // Constructors, getters, setters, and other methods


        public Event (Long id, String name, Room room, Activity activity,int duration){
            this.id = id;
            this.name = name;
            this.room = room;
            this.activity = activity;
            this.duration = duration;
        }

        public void addRoom(Room room){
            this.room = room;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", room=" + room.getId() +
                    ", activity=" + activity.getName() +
                    ", duration=" + duration +
                    ", startTime=" + begin_time +
                    ", endTime=" + end_time +
                    '}';
        }
    }
