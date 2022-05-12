package com.project.sangil_be.repository;

import com.project.sangil_be.model.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MountainRepository extends JpaRepository<Mountain, Long> {
    @Query("select u from Mountain u where u.mountain like %:keyword% or u.mountainAddress like %:keyword%")
    List<Mountain> searchAllByMountain(@Param("keyword") String keyword);

    @Query("select e from Mountain e where e.lat between :x2 and :x1 and e.lng between :y2 and :y1")
    List<Mountain> findAll(@Param("x2")double x2, @Param("x1") double x1, @Param("y2") double y2, @Param("y1") double y1);

    Mountain findByMountainId(Long mountainId);
}
