package com.project.sangil_be.service;

import com.project.sangil_be.model.BookMark;
import com.project.sangil_be.repository.BookMarkRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;

    //북마크 생성
    public String myBookMark(Long mountainId, UserDetailsImpl userDetails) {
        BookMark bookMark = bookMarkRepository.findByMountain100IdAndUserId(mountainId, userDetails.getUser().getUserId());
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
