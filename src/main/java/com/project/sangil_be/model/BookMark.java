package com.project.sangil_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long mountainId;

    public BookMark(Long mountainId, Long userId) {
        this.mountainId = mountainId;
        this.userId = userId;
    }
}
