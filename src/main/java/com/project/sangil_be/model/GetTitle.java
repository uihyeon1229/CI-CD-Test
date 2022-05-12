package com.project.sangil_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class GetTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long getTitleId;

    @Column(nullable = false)
    private String userTitle;

    @Column(nullable = false)
    private String userTitleImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public GetTitle(String userTitle, String userTitleImgUrl, User kakaoUser) {
        this.userTitle=userTitle;
        this.userTitleImgUrl=userTitleImgUrl;
        this.user=kakaoUser;

    }
}