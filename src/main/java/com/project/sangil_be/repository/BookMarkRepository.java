package com.project.sangil_be.repository;

import com.project.sangil_be.model.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository <BookMark, Long> {

    BookMark findByMountain100IdAndUserId(Long mountainId, Long userId);

    boolean existsByMountain100IdAndUserId(Long mountain100Id, Long userId);

    List<BookMark> findAllByUserId(Long userId);

    int countAllByMountain100Id(Long mountain100Id);

//    Boolean existsByMountain100Id(Long mountain100Id);
}
