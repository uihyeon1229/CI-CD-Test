package com.project.sangil_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Attend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long partyId;

    public Attend(Long userId, Long partyId) {
        this.userId = userId;
        this.partyId = partyId;
    }

}
