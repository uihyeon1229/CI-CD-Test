package com.project.sangil_be.model;

import com.project.sangil_be.dto.ChangeTitleRequestDto;
import com.project.sangil_be.dto.UsernameRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

//    @Column(nullable = false)
//    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String userImgUrl;

    @Column
    private String userTitle;

    @Column
    private String userTitleImgUrl;

    @Column
    private Long socialId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Party> parties;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//  @JoinColumn(name = "feedId") //있으면 안된다
    private List<Feed> feeds;

    public User(String username, Long socialId, String encodedPassword, String nickname, String userImageUrl, String userTitle, String userTitleImgUrl) {
        this.username = username;
        this.socialId = socialId;
        this.password = encodedPassword;
        this.nickname = nickname;
        this.userImgUrl = userImageUrl;
        this.userTitle = userTitle;
        this.userTitleImgUrl=userTitleImgUrl;
    }

    public User(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    public void editimage(String profileImageUrl) {
        this.userImgUrl = profileImageUrl;
    }

    public void update(ChangeTitleRequestDto requestDto) {
        this.userTitle= requestDto.getUserTitle();
    }

    public void editname(UsernameRequestDto usernameRequestDto) {
        this.nickname=usernameRequestDto.getNickname();
    }
}
