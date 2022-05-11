package com.project.sangil_be.dto;

import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.Getter;

@Getter
public class MCommentResponseDto {
    private Long mountainId;
    private Long mountainCommentId;
    private String mountainComment;
    private String userTitle;
    private String username;
    private int star;
    private String msg;

    public MCommentResponseDto(Long mountainId, MountainComment mountainComment, UserDetailsImpl userDetails, String msg) {
        this.mountainId = mountainId;
        this.mountainCommentId = mountainComment.getMountainCommentId();
        this.mountainComment = mountainComment.getMountainComment();
        this.userTitle = userDetails.getUser().getUserTitle();
        this.username = userDetails.getUsername();
        this.star = mountainComment.getStar();
        this.msg = msg;
    }

    public MCommentResponseDto(MountainComment mountainComment, UserDetailsImpl userDetails) {
        this.mountainId = mountainComment.getMountain100Id();
        this.mountainComment = mountainComment.getMountainComment();
        this.userTitle = userDetails.getUser().getUserTitle();
        this.username = userDetails.getUser().getUsername();
        this.star = mountainComment.getStar();
    }
}

