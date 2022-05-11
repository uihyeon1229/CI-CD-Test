package com.project.sangil_be.repository;

import com.project.sangil_be.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByMountain100Id(Long mountainId);
}
