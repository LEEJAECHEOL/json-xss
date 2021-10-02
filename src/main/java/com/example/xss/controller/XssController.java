package com.example.xss.controller;

import com.example.xss.dto.RequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  Json 으로 받을 때 XSS 방지 처리
 *   gradle 일 경우 : implementation 'org.apache.commons:commons-text:1.8'
 *
 *  참고 : https://jojoldu.tistory.com/470
 *
 *  루시 적용 같이 할 경우
 *  - https://medium.com/@dltkdals2202/spring-boot-%EC%97%90%EC%84%9C-json-%ED%83%80%EC%9E%85-xss-prevention-e9ce7b02c05b
 *
 *  이모지 필터 예외
 *  - https://hiphopddori.tistory.com/61
 */

@Slf4j
@RestController
public class XssController {

  @PostMapping("/xss")
  public RequestDto XssTest(@RequestBody RequestDto requestDto){
    log.info("RequestDto content : {}", requestDto.getContent());
    return requestDto;
  }

}
