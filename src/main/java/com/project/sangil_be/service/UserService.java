package com.project.sangil_be.service;

import com.project.sangil_be.S3.S3Service;
import com.project.sangil_be.dto.*;
import com.project.sangil_be.utils.DistanceToUser;
import com.project.sangil_be.model.BookMark;
import com.project.sangil_be.model.Mountain;
import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.model.User;
import com.project.sangil_be.repository.BookMarkRepository;
import com.project.sangil_be.repository.MountainRepository;
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
    private final MountainRepository mountainRepository;
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
        String userTitleImgUrl="없음";
        Long socialId = 0L;

        User user = new User(username, socialId,password, nickname, userImageUrl, userTitle,userTitleImgUrl);
        userRepository.save(user);

        ResponseDto responseDto = new ResponseDto(result);

        return responseDto;

    }

    @Transactional
    public UserResponseDto editname(UsernameRequestDto usernameRequestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findByUserId(userDetails.getUser().getUserId());
        user.editname(usernameRequestDto);
        return new UserResponseDto(user);
        }

    public String usernameCheck(UsernameRequestDto usernameRequestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findByUserId(userDetails.getUser().getUserId());
        if(user.getNickname().equals(usernameRequestDto.getNickname())){
            return "false";
        }else{
            return "true";
        }
    }

    @Transactional
    public void editimage(MultipartFile multipartFile, User user) {

        String[] key = user.getUserImgUrl().split(".com/");
        String imageKey = key[key.length - 1];
        String profileImageUrl = s3Service.reupload(multipartFile, "profileimage", imageKey);

        user.editimage(profileImageUrl);
        userRepository.save(user);
    }

    //유저가 즐겨찾기한 산 가져오는 즐겨찾기
    @Transactional
    public List<BookMarkResponseDto> getBookMarkMountain(double lat,double lng,UserDetailsImpl userDetails) {
        List<BookMark> bookMarkList = bookMarkRepository.findAllByUserId(userDetails.getUser().getUserId());
        List<BookMarkResponseDto> bookMarkResponseDtos = new ArrayList<>();

        //프런트에서 받아오기 전 테스트 위도, 경도
//        Double lat = 37.553877;
//        Double lng = 126.971188;

        for (BookMark bookMark : bookMarkList) {
            boolean bookMarkChk = bookMarkRepository.existsByMountainIdAndUserId(bookMark.getMountainId(),
                    userDetails.getUser().getUserId());
            Mountain mountain = mountainRepository.findById(bookMark.getMountainId()).orElseThrow(
                    () -> new IllegalArgumentException("해당하는 산이 없습니다.")
            );

            //유저와 즐겨찾기한 산과의 거리 계산
            Double distance = DistanceToUser.distance(lat, lng, mountain.getLat(),
                                                      mountain.getLng(), "kilometer");

            int star = 0;
            float starAvr = 0f;

            for (int i = 0; i < 10; i++) {
                List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountainId(bookMark.getMountainId());
                if (mountainComments.size() == 0) {
                    starAvr = 0;
                } else {
                    star += mountainComments.get(i).getStar();
                    starAvr = (float) star / mountainComments.size();
                }
            }
            bookMarkResponseDtos.add(new BookMarkResponseDto(mountain, bookMarkChk, starAvr, distance));
        }
        return bookMarkResponseDtos;
    }

}