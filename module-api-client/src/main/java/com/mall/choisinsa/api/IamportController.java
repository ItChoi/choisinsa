package com.mall.choisinsa.api;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/iamport")
public class IamportController {
    @PostMapping("/phone-certifications/{impUid}")
    public ResponseWrapper getPhoneCertifications(@PathVariable Long impUid,
                                                  Map map) {

        return ResponseWrapper.ok();
    }
}
