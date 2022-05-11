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
    private Long mountain100Id;

    public BookMark(Long mountainId, Long userId) {
        this.mountain100Id = mountainId;
        this.userId = userId;
    }
}
