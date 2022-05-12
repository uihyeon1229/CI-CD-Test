package com.project.sangil_be.service;

import com.project.sangil_be.api.WeatherService;
import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.*;
import com.project.sangil_be.repository.*;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MountainService {
    private final MountainRepository mountainRepository;
    private final MountainCommentRepository mountainCommentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final WeatherService weatherService;
    private final BookMarkRepository bookMarkRepository;

    // 검색 전 페이지
    public List<SearchDto> Show10() {
        List<Mountain> mountainList = mountainRepository.findAll();

        List<Mountain100Dto> mountain100DtoList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Mountain100Dto mountain100Dto = new Mountain100Dto(mountainList.get(i));
            mountain100DtoList.add(mountain100Dto);
        }
        Collections.shuffle(mountain100DtoList);

        List<SearchDto> searchDto = new ArrayList<>();

        int star = 0;
        float starAvr;
        for (int i = 0; i < 10; i++) {
            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(mountain100DtoList.get(i).getMountainId());
            if (mountainComments.size() == 0) {
                starAvr = 0;
            } else {
                for (int j = 0; j < mountainComments.size(); j++) {
                    star += mountainComments.get(j).getStar();
                }
                starAvr = (float) star / mountainComments.size();
            }
            SearchDto mountainInfo = new SearchDto(String.format("%.1f",starAvr),mountain100DtoList.get(i));

            searchDto.add(mountainInfo);
        }

        return searchDto;

    }

    // 검색 후 페이지
    public Page<SearchDto> searhMountain(String keyword, int pageNum) {
        List<Mountain> mountainList = mountainRepository.searchAllByMountain(keyword);
        Pageable pageable = getPageable(pageNum);
        List<SearchDto> searchDtoList = new ArrayList<>();
        setSearchList(mountainList, searchDtoList);

        int start = pageNum * 5;
        int end = Math.min((start + 5), mountainList.size());

        Page<SearchDto> page = new PageImpl<>(searchDtoList.subList(start, end), pageable, searchDtoList.size());
        return page;
    }

    private Pageable getPageable(int pageNum) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(pageNum, 5, sort);
    }

    private void setSearchList(List<Mountain> mountainList, List<SearchDto> searchDtoList) {
        int star = 0;
        float starAvr;
        for (Mountain mountain : mountainList) {
            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(mountain.getMountainId());
            if (mountainComments.size() == 0) {
                starAvr = 0;
            } else {
                for (int i = 0; i < mountainComments.size(); i++) {
                    star += mountainComments.get(i).getStar();
                }
                starAvr = (float) star / mountainComments.size();
            }

            SearchDto searchDto = new SearchDto(
                    mountain.getMountainId(),
                    mountain.getMountain(),
                    mountain.getMountainAddress(),
                    mountain.getMountainImgUrl(),
                    String.format("%.1f",starAvr),
                    mountain.getLat(),
                    mountain.getLng()
            );
            searchDtoList.add(searchDto);
        }
    }

    // 페이징 처리 수정
    // 산 상세 페이지
    public MountainResponseDto detailMountain(Long mountainId, int pageNum) throws IOException, ParseException {
        Mountain mountain = mountainRepository.findById(mountainId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 글입니다.")
        );
        WeatherDto weatherDto = weatherService.weather(mountain.getLat(),mountain.getLng());

        List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainIdOrderByCreatedAtDesc(mountainId);
        Pageable pageable = getPageable(pageNum);
        List<CommentListDto> commentLists = new ArrayList<>();
        int star = 0;
        float starAvr = 0;
        for (int i = 0; i < mountainComments.size(); i++) {
            if (mountainComments.size() == 0) {
                starAvr = 0;
            } else {
                star += mountainComments.get(i).getStar();
                starAvr = (float) star / mountainComments.size();
            }
            User user = userRepository.findById(mountainComments.get(i).getUserId()).orElse(null);
            CommentListDto comments = new CommentListDto(
                    mountainComments.get(i),
                    user
            );
            commentLists.add(comments);
        }

        int start = pageNum * 6;
        int end = Math.min((start + 6), mountainComments.size());
        Page<CommentListDto> page = new PageImpl<>(commentLists.subList(start, end), pageable, commentLists.size());
        CommentDto commentDto = new CommentDto(page);

        List<Course> courses = courseRepository.findAllByMountainId(mountainId);
        List<CourseListDto> courseLists = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            CourseListDto courseListDto = new CourseListDto(courses.get(i));
            courseLists.add(courseListDto);
        }
        return new MountainResponseDto(mountain, weatherDto,String.format("%.1f",starAvr), courseLists, commentDto);
    }

    //북마크 생성
    public String myBookMark(Long mountainId, UserDetailsImpl userDetails) {
        BookMark bookMark = bookMarkRepository.findByMountainIdAndUserId(mountainId, userDetails.getUser().getUserId());
        if(bookMark == null) {
            BookMark saveBookMark = new BookMark(mountainId, userDetails.getUser().getUserId());
            bookMarkRepository.save(saveBookMark);
            return "true";
        }else {
            bookMarkRepository.deleteById(bookMark.getBookMarkId());
            return "false";
        }
    }

}
