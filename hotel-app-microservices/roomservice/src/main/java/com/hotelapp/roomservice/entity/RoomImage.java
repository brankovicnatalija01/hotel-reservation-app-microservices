package com.hotelapp.roomservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room_images")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "room") @EqualsAndHashCode(exclude = "room")
public class RoomImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private String url;
}
