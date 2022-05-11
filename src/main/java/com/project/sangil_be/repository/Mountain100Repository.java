package com.project.sangil_be.repository;

import com.project.sangil_be.model.Mountain100;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Mountain100Repository extends JpaRepository<Mountain100, Long> {
    @Query("select u from Mountain100 u where u.mountain like %:keyword% or u.mountainAddress like %:keyword%")
    List<Mountain100> searchAllByMountain(@Param("keyword") String keyword);

    @Query("select e from Mountain100 e where e.lat between :x2 and :x1 and e.lng between :y2 and :y1")
    List<Mountain100> findAll(@Param("x2")double x2,@Param("x1") double x1,@Param("y2") double y2,@Param("y1") double y1);

    Mountain100 findByMountain100Id(Long mountain100Id);
}
