package com.project.sangil_be.repository;

import com.project.sangil_be.model.MountainComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MountainCommentRepository extends JpaRepository<MountainComment, Long> {

    List<MountainComment> findAllByUserId(Long userId);

    List<MountainComment> findAllByMountainId(Long mountainId);

    List<MountainComment> findAllByMountainIdOrderByCreatedAtDesc(Long mountainId);

    boolean existsByUserIdAndMountainId(Long userId, Long mountainId);

}
