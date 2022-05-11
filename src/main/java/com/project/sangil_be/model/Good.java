package com.project.sangil_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goodId;

    @Column(nullable = false)
    private Long feedId;

    @Column(nullable = false)
    private Long userId;

    public Good(Feed feed, User user) {
        this.feedId = feed.getFeedId();
        this.userId = user.getUserId();
    }

}
