package com.spring.shortURL.repository;

import com.spring.shortURL.entity.ShortURL;
import org.springframework.cglib.core.Local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.*;

public class ShortURLRepositoryImpl implements ShortURLRepository{

    private static final Map<Long, ShortURL> urlMap;

    private static int sequence;

    static {
        urlMap = new HashMap<>();
        ShortURL link1 = ShortURL.builder()
                .index(0)
                .shortUrl("abcd")
                .originUrl("abcd")
                .title("asdf")
                .writer("asdf")
                .count(0)
                .registrationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        ShortURL link2 = ShortURL.builder()
                .index(1)
                .shortUrl("abcdfaad")
                .originUrl("abcadfad")
                .title("asddfadff")
                .writer("asddfadff")
                .count(2)
                .registrationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();

        urlMap.put(link1.getIndex(), link1);
        urlMap.put(link2.getIndex(), link2);
    }

    @Override
    public List<ShortURL> findAll() {

        List<ShortURL> shortURLList = new ArrayList<>();

        for(ShortURL shortURL : urlMap.values()){
            shortURLList.add(shortURL);
        }

        return shortURLList;
    }

    @Override
    public ShortURL findByIndex(long index) {
        return urlMap.get(index);
    }

    @Override
    public boolean insert(ShortURL shortURL) {
        long index = urlMap.size();

        // DB에서 처리해 주는 것들
        shortURL.setIndex(index);
        shortURL.setCount(0);
        shortURL.setRegistrationDate(LocalDateTime.now());
        shortURL.setLastUpdateDate(LocalDateTime.now());

        // DB였으면 insert 실패시 false 리턴 했을 것
        try{
            urlMap.put(index, shortURL);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteByIndex(Long index) {
        try{
            ShortURL shortURL =  urlMap.get(index);
            shortURL.setRemoveDate(LocalDateTime.now());
            urlMap.put(index, shortURL);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(ShortURL shortURL) {
        try{
            shortURL.setLastUpdateDate(LocalDateTime.now());
            urlMap.put(shortURL.getIndex(), shortURL);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
