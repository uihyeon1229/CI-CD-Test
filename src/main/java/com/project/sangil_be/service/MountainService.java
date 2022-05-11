package com.project.sangil_be.service;

import com.project.sangil_be.api.WeatherService;
import com.project.sangil_be.dto.*;
import com.project.sangil_be.model.Course;
import com.project.sangil_be.model.Mountain100;
import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.model.User;
import com.project.sangil_be.repository.CourseRepository;
import com.project.sangil_be.repository.Mountain100Repository;
import com.project.sangil_be.repository.MountainCommentRepository;
import com.project.sangil_be.repository.UserRepository;
import com.project.sangil_be.utils.Validator;
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
    private final Mountain100Repository mountain100Repository;
    private final MountainCommentRepository mountainCommentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final WeatherService weatherService;
    private final Validator validator;

    // 검색 전 페이지
    public List<SearchDto> Show10() {

//        List <Mountain100> mountain100List = new ArrayList<>();
//
//        for (Long i = 0L; i<100; i++){
//
//            Mountain100 mountain100 = mountain100Repository.findByMountain100Id(i);
//            mountain100List.add(mountain100);
//
//        }

        List<Mountain100> mountain100List = mountain100Repository.findAll();

        Collections.shuffle(mountain100List);

        List<SearchDto> searchDto = new ArrayList<>();

        int star = 0;
        float starAvr;
        for (int i = 0; i < 10; i++) {
            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountain100Id(mountain100List.get(i).getMountain100Id());
            if (mountainComments.size() == 0) {
                starAvr = 0;
            } else {
                for (int j = 0; j < mountainComments.size(); j++) {
                    star += mountainComments.get(j).getStar();
                }
                starAvr = (float) star / mountainComments.size();
            }
            SearchDto mountainInfo = new SearchDto(
                    mountain100List.get(i).getMountain100Id(),
                    mountain100List.get(i).getMountain(),
                    mountain100List.get(i).getMountainAddress(),
                    mountain100List.get(i).getMountainImgUrl(),
                    String.format("%.1f",starAvr),
                    mountain100List.get(i).getLat(),
                    mountain100List.get(i).getLng());
            searchDto.add(mountainInfo);
        }

        return searchDto;

    }

    // 검색 후 페이지
    public Page<SearchDto> searhMountain(String keyword, int pageNum) {
        List<Mountain100> mountain100List = mountain100Repository.searchAllByMountain(keyword);
        Pageable pageable = getPageable(pageNum);
        List<SearchDto> searchDtoList = new ArrayList<>();
        setSearchList(mountain100List, searchDtoList);

        int start = pageNum * 5;
        int end = Math.min((start + 5), mountain100List.size());

        Page<SearchDto> page = new PageImpl<>(searchDtoList.subList(start, end), pageable, searchDtoList.size());
        return page;
    }

    private Pageable getPageable(int pageNum) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(pageNum, 5, sort);
    }

    private void setSearchList(List<Mountain100> mountain100List, List<SearchDto> searchDtoList) {
        int star = 0;
        float starAvr;
        for (Mountain100 mountain100 : mountain100List) {
            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountain100Id(mountain100.getMountain100Id());
            if (mountainComments.size() == 0) {
                starAvr = 0;
            } else {
                for (int i = 0; i < mountainComments.size(); i++) {
                    star += mountainComments.get(i).getStar();
                }
                starAvr = (float) star / mountainComments.size();
            }

            SearchDto searchDto = new SearchDto(
                    mountain100.getMountain100Id(),
                    mountain100.getMountain(),
                    mountain100.getMountainAddress(),
                    mountain100.getMountainImgUrl(),
                    String.format("%.1f",starAvr),
                    mountain100.getLat(),
                    mountain100.getLng()
            );
            searchDtoList.add(searchDto);
        }
    }

    // 페이징 처리 수정
    // 산 상세 페이지
    public MountainResponseDto detailMountain(Long mountainId, int pageNum) throws IOException, ParseException {
        Mountain100 mountain100 = mountain100Repository.findById(mountainId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 글입니다.")
        );
        WeatherDto weatherDto = weatherService.weather(mountain100.getLat(),mountain100.getLng());

        List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountain100IdOrderByCreatedAtDesc(mountainId);
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

        List<Course> courses = courseRepository.findAllByMountain100Id(mountainId);
        List<CourseListDto> courseLists = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            CourseListDto courseListDto = new CourseListDto(courses.get(i));
            courseLists.add(courseListDto);
        }
        return new MountainResponseDto(mountain100, weatherDto, String.format("%.1f",starAvr), courseLists, commentDto);
    }


}
