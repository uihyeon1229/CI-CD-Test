package com.project.sangil_be.service;

import com.project.sangil_be.S3.S3Service;
import com.project.sangil_be.dto.*;
import com.project.sangil_be.utils.DistanceToUser;
import com.project.sangil_be.model.BookMark;
import com.project.sangil_be.model.Mountain100;
import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.model.User;
import com.project.sangil_be.repository.BookMarkRepository;
import com.project.sangil_be.repository.Mountain100Repository;
import com.project.sangil_be.repository.MountainCommentRepository;
import com.project.sangil_be.repository.UserRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    private final Mountain100Repository mountain100Repository;
    private final MountainCommentRepository mountainCommentRepository;
    private final S3Service s3Service;


    public ResponseDto registerUser(SignUpRequestDto requestDto) {
        Boolean result = true;
        String username = requestDto.getUsername();

        Optional<User> foundname = userRepository.findByUsername(username);

        if (foundname.isPresent()) {
            result = false;
            return new ResponseDto(result);
        }

        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = "없음";
        String userImageUrl = "없음";
        String userTitle = "없음";
        String userTitleImgUrl = "없음";
        Long socialId = 0L;

        User user = new User(username, socialId, password, nickname, userImageUrl, userTitle, userTitleImgUrl);
        userRepository.save(user);

        ResponseDto responseDto = new ResponseDto(result);

        return responseDto;

    }

    @Transactional
    public void editname(UsernameRequestDto usernameRequestDto, User user) {
        user.editusername(usernameRequestDto);
        userRepository.save(user);

    }


//    @Transactional
//    public void firstimage(MultipartFile multipartFile, User user) {
//
//        String profileImageUrl = s3Service.upload(multipartFile, "profileimage");
//
//        user.editimage(profileImageUrl);
//        userRepository.save(user);
//    }

    @Transactional
    public void editimage(MultipartFile multipartFile, User user) {

        String[] key = user.getUserImgUrl().split(".com/");
        String imageKey = key[key.length - 1];
        System.out.println(imageKey);
        String profileImageUrl = s3Service.reupload(multipartFile, "profileimage", imageKey);

        user.editimage(profileImageUrl);
        userRepository.save(user);
    }

    //유저가 즐겨찾기한 산 가져오는 즐겨찾기
    @Transactional
    public List<BookMarkResponseDto> getBookMarkMountain(double lat, double lng, UserDetailsImpl userDetails) {
        List<BookMark> bookMarkList = bookMarkRepository.findAllByUserId(userDetails.getUser().getUserId());
        List<BookMarkResponseDto> bookMarkResponseDtos = new ArrayList<>();

        //프런트에서 받아오기 전 테스트 위도, 경도
//        Double lat = 37.553877;
//        Double lng = 126.971188;

        for (BookMark bookMark : bookMarkList) {
            boolean bookMarkChk = bookMarkRepository.existsByMountain100IdAndUserId(bookMark.getMountain100Id(),
                    userDetails.getUser().getUserId());
            Mountain100 mountain100 = mountain100Repository.findById(bookMark.getMountain100Id()).orElseThrow(
                    () -> new IllegalArgumentException("해당하는 산이 없습니다.")
            );

            //유저와 즐겨찾기한 산과의 거리 계산
            Double distance = DistanceToUser.distance(lat, lng, mountain100.getLat(),
                    mountain100.getLng(), "kilometer");

            int star = 0;
            float starAvr = 0f;

            for (int i = 0; i < 10; i++) {
                List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountain100Id(bookMark.getMountain100Id());
                if (mountainComments.size() == 0) {
                    starAvr = 0;
                } else {
                    star += mountainComments.get(i).getStar();
                    starAvr = (float) star / mountainComments.size();
                }
            }
            bookMarkResponseDtos.add(new BookMarkResponseDto(mountain100, bookMarkChk, starAvr, distance));
        }
        return bookMarkResponseDtos;
    }

    public String usernameCheck(UsernameRequestDto usernameRequestDto, UserDetailsImpl userDetails) {

        User user = userRepository.findByUserId(userDetails.getUser().getUserId());
        System.out.println(user.getUsername());
        String username = usernameRequestDto.getUsername();


        if (user.getUsername().equals(usernameRequestDto.getUsername())) {
            return "false";
        } else {
            if (!username.matches("^[a-zA-Zㄱ-ㅎ0-9-_]{3,11}$")) {
//                throw new IllegalArgumentException("아이디는 영어와 숫자로 3~10자리로 입력하셔야 합니다!");
                return "아이디는 영어와 숫자로 3~10자리로 입력하셔야 합니다!";
            }
            return "true";
        }
    }
}