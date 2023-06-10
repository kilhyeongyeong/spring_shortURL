package com.spring.shortURL.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Setter @Getter @ToString
@AllArgsConstructor @NoArgsConstructor @Builder
public class ShortURL {
    private long index;                     // primaryKey : 인덱스
    private String shortUrl;                // shortUrl
    private String shortUrlFront;           // shortUrl 앞부분("shortUrl.pj")고정
    private String shortUrlBack;            // shortUrl 뒷부분(BASE56으로 인코딩된 인덱스)
    private String originUrl;               // 원본Url
    private String title;                   // Title
    private String writer;                  // 글쓴이
    private long count;                     // 요청 횟수 조회
    private LocalDateTime registrationDate; // 최초 등록 날짜
    private LocalDateTime lastUpdateDate;   // 마지막 업데이트 날짜
    private LocalDateTime removeDate;       // 삭제 날짜
}
