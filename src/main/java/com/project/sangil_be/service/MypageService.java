package com.project.sangil_be.service;

import com.project.sangil_be.dto.ChangeTitleDto;
import com.project.sangil_be.dto.ChangeTitleRequestDto;
import com.project.sangil_be.dto.UserTitleDto;
import com.project.sangil_be.model.GetTitle;
import com.project.sangil_be.model.User;
import com.project.sangil_be.model.UserTitle;
import com.project.sangil_be.repository.GetTitleRepository;
import com.project.sangil_be.repository.UserRepository;
import com.project.sangil_be.repository.UserTitleRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MypageService {
    private final UserTitleRepository userTitleRepository;
    private final GetTitleRepository getTitleRepository;
    private final UserRepository userRepository;

    // 칭호 리스트
    public List<UserTitleDto> getUserTitle(UserDetailsImpl userDetails) {
        List<UserTitle> userTitles = userTitleRepository.findAll();
        List<GetTitle> getTitles = getTitleRepository.findAllByUser(userDetails.getUser());
        HashMap<String,Boolean> title = new HashMap<>();
        for (int i = 0; i < userTitles.size(); i++) {
            title.put(userTitles.get(i).getUserTitle(), false);
            for (int j = 0; j < getTitles.size(); j++) {
                if (userTitles.get(i).getUserTitle().equals(getTitles.get(j).getUserTitle())) {
                    title.replace(userTitles.get(i).getUserTitle(), false,true);
                }
            }
        }
        List<UserTitleDto> userTitleDtos = new ArrayList<>();
        for (int i = 0; i < userTitles.size(); i++) {
            UserTitleDto userTitleDto = new UserTitleDto(userTitles.get(i),title.get(userTitles.get(i).getUserTitle()));
            userTitleDtos.add(userTitleDto);
        }
        return userTitleDtos;
    }

    // 칭호 변경
    @Transactional
    public ChangeTitleDto putUserTitle(ChangeTitleRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findByUserId(userDetails.getUser().getUserId());
        user.update(requestDto);
        return new ChangeTitleDto(userDetails,requestDto);
    }
}

