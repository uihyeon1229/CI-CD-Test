package com.project.sangil_be.repository;

import com.project.sangil_be.model.Completed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompletedRepository extends JpaRepository<Completed,Long> {

    Completed findByCompleteId(Long completedId);

    void deleteByCompleteId(Long completedId);

    List<Completed> findAllByUserId(Long userId);
}
