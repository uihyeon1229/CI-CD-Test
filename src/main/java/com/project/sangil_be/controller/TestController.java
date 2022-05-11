package com.project.sangil_be.controller;

import com.project.sangil_be.utils.geocoder.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final ServiceImpl service;

    @PutMapping("/api/test")
    public void updateXY() {
        service.updateXY();
    }
}
