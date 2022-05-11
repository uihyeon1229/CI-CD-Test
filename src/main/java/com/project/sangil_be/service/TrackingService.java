package com.project.sangil_be.service;

import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.Completed;
import com.project.sangil_be.model.Mountain100;
import com.project.sangil_be.model.Tracking;
import com.project.sangil_be.repository.CompletedRepository;
import com.project.sangil_be.repository.Mountain100Repository;
import com.project.sangil_be.repository.MountainCommentRepository;
import com.project.sangil_be.repository.TrackingRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.utils.DistanceToUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrackingService {
    private final TrackingRepository trackingRepository;
    private final CompletedRepository completedRepository;
    private final Mountain100Repository mountain100Repository;
    private final MountainCommentRepository mountainCommentRepository;

    // 트래킹 시작
    @Transactional
    public StartTrackingResponseDto startMyLocation(Long mountainId, StartTrackingRequestDto startTrackingRequestDto, UserDetailsImpl userDetails) {
        Completed completed = new Completed(mountainId,startTrackingRequestDto.getSend(),userDetails.getUser().getUserId());
        completedRepository.save(completed);
        return new StartTrackingResponseDto(completed.getCompleteId());
    }

    // 맵 트래킹 5초 마다 저장
    @Transactional
    public DistanceResponseDto saveMyLocation(Long completedId, TrackingRequestDto trackingRequestDto, UserDetailsImpl userDetails) {
        List<Tracking> trackinglist = trackingRepository.findAllByCompletedId(completedId);
        Completed completed = completedRepository.findByCompleteId(completedId);
        Mountain100 mountain100 = mountain100Repository.findByMountain100Id(completed.getMountain100Id());
        Tracking saveTracking = new Tracking();
        saveTracking.setMountain100(mountain100);
        saveTracking.setUser(userDetails.getUser());
        DistanceResponseDto distanceResponseDto = new DistanceResponseDto();
        if(trackinglist.size()==0){
            saveTracking.setCompletedId(completedId);
            saveTracking.setLat(trackingRequestDto.getLat());
            saveTracking.setLng(trackingRequestDto.getLng());
            Double distanceM = 0d;
            Double distanceK = 0d;
            saveTracking.setDistanceM(distanceM);
            saveTracking.setDistanceK(distanceK);
            trackingRepository.save(saveTracking);
            distanceResponseDto.setDistanceK(String.format("%.2f",distanceK));
            distanceResponseDto.setDistanceM(String.format("%.2f",distanceM));
        }else {
            for (int i = trackinglist.size() - 1; i < trackinglist.size(); i++) {
                Double distanceM = DistanceToUser.distance(trackinglist.get(i).getLat(), trackinglist.get(i).getLng(), trackingRequestDto.getLat(), trackingRequestDto.getLng(), "meter");
                Double distanceK = DistanceToUser.distance(trackinglist.get(i).getLat(), trackinglist.get(i).getLng(), trackingRequestDto.getLat(), trackingRequestDto.getLng(), "kilometer");
                saveTracking.setCompletedId(completedId);
                saveTracking.setLat(trackingRequestDto.getLat());
                saveTracking.setLng(trackingRequestDto.getLng());
                saveTracking.setDistanceM(distanceM);
                saveTracking.setDistanceK(distanceK);
                trackingRepository.save(saveTracking);
                distanceResponseDto.setDistanceK(String.format("%.2f",distanceK));
                distanceResponseDto.setDistanceM(String.format("%.2f",distanceM));
            }
        }
        return distanceResponseDto;
    }

    // 트래킹 완료 후 저장
    @Transactional
    public String saveTracking(Long completedId, CompleteRequestDto completeRequestDto, UserDetailsImpl userDetails) {
        try {
            Completed completed = completedRepository.findByCompleteId(completedId);
            if (mountainCommentRepository.existsByUserIdAndMountain100Id(completed.getUserId(), completed.getMountain100Id())) {
                completed.update(completeRequestDto);
                return "true";
            } else {
                completed.update(completeRequestDto);
                return "false";
            }
        } catch (Exception e) {
            return "오류";
        }
    }

    // 맵트래킹 삭제 (10분 이하)
    @Transactional
    public String deleteTracking(Long completedId) {
        try {
            completedRepository.deleteByCompleteId(completedId);
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }

    // 맵 트래킹 상세페이지
    @Transactional
    public TrackingListDto detailTracking(Long completedId, UserDetailsImpl userDetails) {
        List<Tracking> trackingList = trackingRepository.findAllByCompletedId(completedId);
        Completed completed = completedRepository.findByCompleteId(completedId);
        List<TrackingResponseDto> trackingResponseDtoList = new ArrayList<>();
        Mountain100 mountain100 = mountain100Repository.findByMountain100Id(completed.getMountain100Id());
        for (Tracking tracking : trackingList) {
            TrackingResponseDto trackingResponseDto = new TrackingResponseDto(tracking.getLat(), tracking.getLng());
            trackingResponseDtoList.add(trackingResponseDto);
        }
        return new TrackingListDto(userDetails,completedId,mountain100,completed,trackingResponseDtoList);
    }

    // 맵트래킹 마이페이지
    public List<CompletedListDto> myPageTracking(UserDetailsImpl userDetails) {
        List<Completed> completed = completedRepository.findAllByUserId(userDetails.getUser().getUserId());
        List<CompletedListDto> completedListDtos = new ArrayList<>();
        for (Completed complete : completed) {
            Mountain100 mountain100 = mountain100Repository.findByMountain100Id(complete.getMountain100Id());
            CompletedListDto completedListDto = new CompletedListDto(complete,mountain100);
            completedListDtos.add(completedListDto);
        }
        return completedListDtos;
    }
}
