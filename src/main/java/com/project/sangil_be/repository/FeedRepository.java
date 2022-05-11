package com.project.sangil_be.repository;

import com.project.sangil_be.dto.FeedResponseDto;
import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findByUser(User user);

}
