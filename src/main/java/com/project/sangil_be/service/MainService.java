package com.project.sangil_be.service;

import com.project.sangil_be.utils.Direction;
import com.project.sangil_be.utils.DistanceToUser;
import com.project.sangil_be.utils.GeometryUtil;
import com.project.sangil_be.utils.Location;
import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.*;
import com.project.sangil_be.repository.*;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MainService {

    private final PartyRepository partyRepository;
    private final MountainRepository mountainRepository;
    private final MountainCommentRepository mountainCommentRepository;
    private final BookMarkRepository bookMarkRepository;
    private final AttendRepository attendRepository;
    private final FeedRepository feedRepository;
    private final GoodRepository goodRepository;

    @Transactional
    public PlanResponseDto getPlan(UserDetailsImpl userDetails) {
        List<Attend> attend = attendRepository.findAllByUserId(userDetails.getUser().getUserId());
        List<Party> partyList = new ArrayList<>();

        for (int i = 0; i < attend.size(); i++) {
            partyList.add(partyRepository.findByPartyIdOrderByPartyDateAsc(attend.get(i).getPartyId()));
            System.out.println(partyList.get(i).getPartyDate());
            System.out.println(partyList.get(i).getPartyDate().getClass());
            System.out.println(attend.size());
        }
        Collections.sort(partyList, new PartyDateComparator());

        List<PlanListDto> planListDtos = new ArrayList<>();
        for (Party party : partyList) {
            LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
            String year = String.valueOf(now).split("-")[0];
            String month = String.valueOf(now).split("-")[1];
            String day = String.valueOf(now).split("-")[2];
            String date = year + "-" + month + "-" + day;
            int compare = date.compareTo(String.valueOf(party.getPartyDate()));
            String msg;
            if (compare > 0) {
                attendRepository.deleteByPartyIdAndUserId(party.getPartyId(), userDetails.getUser().getUserId());
                msg = "false";
            } else {
                msg = "true";
            }
            planListDtos.add(new PlanListDto(party, msg));
        }

        return new PlanResponseDto(planListDtos);
    }

    @Transactional
    public TwoPartyListResponseDto getTwoParty() {

        List<Party> partyList = partyRepository.findAllByOrderByCreatedAtDesc();
        List<TwoPartyListDto> partyListDtos = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            partyListDtos.add(new TwoPartyListDto(partyList.get(i)));
        }
        return new TwoPartyListResponseDto(partyListDtos);
    }

    // ?????? ?????? ??????
    public List<Top10MountainDto> get10Mountains(UserDetailsImpl userDetails) {

        List<Mountain> mountainList = mountainRepository.findAll();

        List<Mountain10ResponseDto> mountain10ResponseDtos = new ArrayList<>();
        int star = 0;
        float starAvr;

        if (userDetails == null) {
            for (int i = 0; i < mountainList.size(); i++) {
                int bookMarkCnt = bookMarkRepository.countAllByMountainId(mountainList.get(i).getMountainId());
                Boolean bookMark = false;
                List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(mountainList.get(i).getMountainId());
                if (mountainComments.size() == 0) {
                    starAvr = 0;
                } else {
                    star += mountainComments.get(i).getStar();
                    starAvr = (float) star / mountainComments.size();
                }
                Mountain10ResponseDto mountain10ResponseDto = new Mountain10ResponseDto(mountainList.get(i), String.format("%.1f", starAvr), bookMark, bookMarkCnt);
                mountain10ResponseDtos.add(mountain10ResponseDto);
            }
        } else {
            for (int i = 0; i < mountainList.size(); i++) {
                int bookMarkCnt = bookMarkRepository.countAllByMountainId(mountainList.get(i).getMountainId());
                Boolean bookMark = bookMarkRepository.existsByMountainIdAndUserId(mountainList.get(i).getMountainId(), userDetails.getUser().getUserId());
                List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(mountainList.get(i).getMountainId());
                if (mountainComments.size() == 0) {
                    starAvr = 0;
                } else {
                    star += mountainComments.get(i).getStar();
                    starAvr = (float) star / mountainComments.size();
                }
                Mountain10ResponseDto mountain10ResponseDto = new Mountain10ResponseDto(mountainList.get(i), String.format("%.1f", starAvr), bookMark, bookMarkCnt);
                mountain10ResponseDtos.add(mountain10ResponseDto);
            }
        }
        Collections.sort(mountain10ResponseDtos, new CntComparator().reversed());

        List<Top10MountainDto> Top10MountainDtos = new ArrayList<>();

        int star2 = 0;
        float starAvr2 = 0;
        if (userDetails == null) {
            for (int i = 0; i < 10; i++) {
                boolean bookMark2 = false;
                List<MountainComment> mountainComments2 = mountainCommentRepository.findAllByMountainId(mountain10ResponseDtos.get(i).getMountainId());
                if (mountainComments2.size() == 0) {
                    starAvr2 = 0;
                } else {
                    star2 += mountainComments2.get(i).getStar();
                    starAvr2 = (float) star2 / mountainComments2.size();
                }

                Top10MountainDto mountain10ResponseDto = new Top10MountainDto(mountain10ResponseDtos.get(i), String.format("%.1f", starAvr2), bookMark2);
                Top10MountainDtos.add(mountain10ResponseDto);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                boolean bookMark2 = bookMarkRepository.existsByMountainIdAndUserId(mountain10ResponseDtos.get(i).getMountainId(), userDetails.getUser().getUserId());
                List<MountainComment> mountainComments2 = mountainCommentRepository.findAllByMountainId(mountain10ResponseDtos.get(i).getMountainId());
                if (mountainComments2.size() == 0) {
                    starAvr2 = 0;
                } else {
                    star2 += mountainComments2.get(i).getStar();
                    starAvr2 = (float) star2 / mountainComments2.size();
                }

                Top10MountainDto mountain10ResponseDto = new Top10MountainDto(mountain10ResponseDtos.get(i), String.format("%.1f", starAvr2), bookMark2);
                Top10MountainDtos.add(mountain10ResponseDto);

            }
        }
        return Top10MountainDtos;
    }


    public FeedListResponseDto mainfeeds(int pageNum, UserDetailsImpl userDetails) {

        List<Feed> feed = feedRepository.findAll();

        List<FeedResponseDto> feedResponseDtos = new ArrayList<>();
        if (userDetails == null) {
            for (Feed feeds : feed) {

                int goodCnt = goodRepository.findByFeedId(feeds.getFeedId()).size();
                boolean goodStatus = false;

                feedResponseDtos.add(new FeedResponseDto(feeds, goodCnt, goodStatus));

            }
        } else {
            for (Feed feeds : feed) {

                int goodCnt = goodRepository.findByFeedId(feeds.getFeedId()).size();
                boolean goodStatus = goodRepository.existsByFeedIdAndUserId(feeds.getFeedId(), userDetails.getUser().getUserId());
                feedResponseDtos.add(new FeedResponseDto(feeds, goodCnt, goodStatus));

            }

        }

        Pageable pageable = getPageable(pageNum);

        int start = pageNum * 15;
        int end = Math.min((start + 15), feed.size());

        Page<FeedResponseDto> page = new PageImpl<>(feedResponseDtos.subList(start, end), pageable, feedResponseDtos.size());
        return new FeedListResponseDto(page);
    }

    private Pageable getPageable(int pageNum) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(pageNum, 15, sort);
    }


    // ?????? ?????? ???
    public NearbyMountainDto nearby(double lat, double lng, int pageNum, UserDetailsImpl userDetails) {
//        double lat = 37.45988; // ??? ?????? y
//        double lng = 126.9519; // ??? ?????? x
        double distance = 7; // km ?????? // ?????? ?????? 5km ????????? ?????? ???

        Location northEast = GeometryUtil.calculate(lat, lng, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.calculate(lat, lng, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLat();
        double y1 = northEast.getLng();
        double x2 = southWest.getLat();
        double y2 = southWest.getLng();

        List<Mountain> mountains = mountainRepository.findAll(x2, x1, y2, y1);
        List<NearbyMountainListDto> nearbyMountainListDtos = new ArrayList<>();
        int star = 0;
        double starAvr = 0;

        if (userDetails == null) {
            for (Mountain mountain : mountains) {
                List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(mountain.getMountainId());
                if (mountainComments.size() == 0) {
                    starAvr = 0;
                } else {
                    for (int i = 0; i < mountainComments.size(); i++) {
                        star += mountainComments.get(i).getStar();
                    }
                    starAvr = (float) star / mountainComments.size();
                }
                Boolean bookmark = false;
                double dis = DistanceToUser.distance(lat, lng, mountain.getLat(), mountain.getLng(), "kilometer");
                NearbyMountainListDto nearbyMountainListDto = new NearbyMountainListDto(mountain, String.format("%.1f", starAvr), bookmark, String.format("%.1f", dis));
                nearbyMountainListDtos.add(nearbyMountainListDto);
            }
        } else {
            for (Mountain mountain : mountains) {
                List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(mountain.getMountainId());
                if (mountainComments.size() == 0) {
                    starAvr = 0;
                } else {
                    for (int i = 0; i < mountainComments.size(); i++) {
                        star += mountainComments.get(i).getStar();
                    }
                    starAvr = (float) star / mountainComments.size();
                }
                Boolean bookmark = bookMarkRepository.existsByMountainIdAndUserId(mountain.getMountainId(), userDetails.getUser().getUserId());
                double dis = DistanceToUser.distance(lat, lng, mountain.getLat(), mountain.getLng(), "kilometer");
                NearbyMountainListDto nearbyMountainListDto = new NearbyMountainListDto(mountain, String.format("%.1f", starAvr), bookmark, String.format("%.1f", dis));
                nearbyMountainListDtos.add(nearbyMountainListDto);
            }
        }
        Pageable pageable = getPageable(pageNum);
        int start = pageNum * 7;
        int end = Math.min((start + 7), mountains.size());
        Page<NearbyMountainListDto> page = new PageImpl<>(nearbyMountainListDtos.subList(start, end), pageable, nearbyMountainListDtos.size());
        return new NearbyMountainDto(page);
    }


    private class PartyDateComparator implements Comparator<Party> {
        @Override
        public int compare(Party d1, Party d2) {
            return d1.getPartyDate().compareTo(d2.getPartyDate());
        }
    }

    private class CntComparator implements Comparator<Mountain10ResponseDto> {
        @Override
        public int compare(Mountain10ResponseDto t1, Mountain10ResponseDto t2) {
            if (t1.getBookMarkCnt() > t2.getBookMarkCnt()) {
                return 1;
            } else if (t1.getBookMarkCnt() < t2.getBookMarkCnt()) {
                return -1;
            }
            return 0;
        }
    }

}
