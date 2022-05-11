package com.project.sangil_be.repository;

import com.project.sangil_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    User findByUserId(Long userId);


    User findBySocialId(Long socialId);
}
