package com.project.sangil_be.service;

import com.project.sangil_be.dto.MCommentRequestDto;
import com.project.sangil_be.dto.MCommentResponseDto;
import com.project.sangil_be.model.Mountain100;
import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.repository.Mountain100Repository;
import com.project.sangil_be.repository.MountainCommentRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MCommentService {
    private final Mountain100Repository mountain100Repository;
    private final MountainCommentRepository mountainCommentRepository;
    private final Validator validator;

    // 댓글 작성
    public MCommentResponseDto writeComment(Long mountainId, MCommentRequestDto mCommentRequestDto, UserDetailsImpl userDetails) {
        List<MountainComment> comment = mountainCommentRepository.findAllByUserId(userDetails.getUser().getUserId());
        String msg;
        if (comment.size()>1) {
            msg = "중복";
        } else {
            msg = "작성 가능";
        }
        Mountain100 mountain100 = mountain100Repository.findById(mountainId).orElseThrow(
                () -> new IllegalArgumentException("산 정보가 존재하지 않습니다.")
        );
        validator.emptyMComment(mCommentRequestDto);

        MountainComment mountainComment = new MountainComment(mountainId, userDetails, mCommentRequestDto);
        mountainCommentRepository.save(mountainComment);
        MCommentResponseDto mCommentResponseDto = new MCommentResponseDto(
                mountainId,
                mountainComment,
                userDetails,
                msg
        );
        return mCommentResponseDto;
    }

    // 댓글 수정
    @Transactional
    public MCommentResponseDto updateComment(Long mountainCommentId, MCommentRequestDto mCommentRequestDto, UserDetailsImpl userDetails) {
        MountainComment mountainComment = mountainCommentRepository.findById(mountainCommentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        mountainComment.update(mCommentRequestDto);
        return new MCommentResponseDto(mountainComment, userDetails);
    }

    // 댓글 삭제
    public String deleteComment(Long mountainCommentId) {
        MountainComment mountainComment = mountainCommentRepository.findById(mountainCommentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        try {
            mountainCommentRepository.deleteById(mountainCommentId);
            return "true";
        } catch (IllegalArgumentException e) {
            return "false";
        }
    }
}
