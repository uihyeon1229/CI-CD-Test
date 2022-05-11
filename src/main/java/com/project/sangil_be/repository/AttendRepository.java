package com.project.sangil_be.repository;

import com.project.sangil_be.model.Attend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendRepository extends JpaRepository<Attend, Long> {

    void deleteByPartyIdAndUserId(Long partyId, Long userId);

    Attend findByPartyIdAndUserId(Long partyId, Long userId);


    List<Attend> findAllByUserId(Long userId);

    List<Attend> findByPartyId(Long partyId);
}
