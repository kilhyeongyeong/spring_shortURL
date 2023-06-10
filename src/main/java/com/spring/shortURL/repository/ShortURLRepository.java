package com.spring.shortURL.repository;

import com.spring.shortURL.entity.ShortURL;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ShortURLRepository {
    // 전체 조회
    List<ShortURL> findAll();

    // primaryKey 로 한 개 조회
    ShortURL findByIndex(long index);

    // 인코딩 된 인덱스로 한 개 조회
    ShortURL findByEncodedIndex(String encodedIndex);

    // 신규저장
    void insert(ShortURL shortURL);

    // 저장 전 세팅
    void settingInsertBefore(ShortURL shortURL);

    // 삭제
    void deleteByIndex(Long Index);

    // 인코딩 된 인덱스로 삭제
    void deleteByEncodedIndex(String encodedIndex);

    // 업데이트
    void update(ShortURL shortURL);

    // HTML에서 Title값 얻어오기
    String getTitle(String url);

    // 난수 6자리 생성
    int getRandomIndex();

    // 짧은 URL 중복 검사
    boolean checkShortUrl(String url);

    // 원본 URL 중복 검사
    CheckUrlEnum checkOriginUrl(String url);

    // base56으로 인코더
    String getBase56Encode(long randomNumber);

    // base56으로 디코더
    long getBase56Decode(String url);

    void increaseCount(ShortURL shortURL);
}
