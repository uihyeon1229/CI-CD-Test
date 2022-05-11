package com.project.sangil_be.model;

import com.project.sangil_be.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Party extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String partyContent;

    @Column(nullable = false)
    private String mountain;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate partyDate;

    @Column(nullable = false)
    private Integer maxPeople;

    @Column(nullable = false)
    private Integer curPeople;

    @Column(nullable = false)
    private LocalTime partyTime;

    @Column(nullable = false)
    private Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public Party(String title, String mountain, String address, LocalDate partyDate,
                 LocalTime partyTime, int maxPeople, int curPeople, String partyContent,
                 boolean completed, User user) {
        this.title = title;
        this.mountain = mountain;
        this.address = address;
        this.partyDate = partyDate;
        this.partyTime = partyTime;
        this.maxPeople = maxPeople;
        this.curPeople = curPeople;
        this.partyContent = partyContent;
        this.completed = completed;
        this.user = user;
    }

    public void update(LocalDate partyDate, LocalTime partyTime, int maxPeople, String partyContent) {
        this.partyDate = partyDate;
        this.partyTime = partyTime;
        this.maxPeople = maxPeople;
        this.partyContent = partyContent;
    }

    public void updateCurpeople(int result) {

        this.curPeople = result;
    }

}
