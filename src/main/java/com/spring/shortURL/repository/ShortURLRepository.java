package com.spring.shortURL.repository;

import com.spring.shortURL.entity.ShortURL;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ShortURLRepository {
    // 1. 전체 조회
    List<ShortURL> findAll();

    // 2. primaryKey 로 한 개 조회
    ShortURL findByIndex(long index);

    // 3. 신규저장
    boolean insert(ShortURL shortURL);

    // 4. 삭제
    boolean deleteByIndex(Long Index);

    // 5. 업데이트
    boolean update(ShortURL shortURL);

    // 6. HTML에서 Title값 얻어오기
    String getTitle(String url);

    // 7. shortURL 얻기
    String getShortUrl(int index);

    int getRandomIndex();

    String setBase56Encode(int randomNumber);

    public boolean checkShortUrl(String url);

    public int getBase56Decode(String url);
}
