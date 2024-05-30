package com.lycoris.lycorisbackend.entity.event;

import com.lycoris.lycorisbackend.entity.event.Event;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    

    @Column(nullable = false)
    private Integer roomCapacity;
}