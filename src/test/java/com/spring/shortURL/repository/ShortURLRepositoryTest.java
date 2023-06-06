package com.spring.shortURL.repository;

import com.spring.shortURL.entity.ShortURL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLDecoder;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShortURLRepositoryTest {

    ShortURLRepository shortURLRepository;

    @BeforeEach
    public void setShortURLRepository(){
        shortURLRepository = new ShortURLRepositoryImpl();
    }

    @Test
    public void findAllTest(){
        // Arrange
        // Act
        List<ShortURL> shortURLList = shortURLRepository.findAll();
        // Assert
        assertEquals(2, shortURLList.size());
    }

    @Test
    public void findByIndexTest(){
        // Arrange
        long index = 1;
        // Act
        ShortURL shortURL = shortURLRepository.findByIndex(index);
        // Assert
        assertEquals("abcdfaad", shortURL.getShortUrl());
        assertEquals("abcadfad", shortURL.getOriginUrl());
    }

    @Test
    public void insertTest(){
        // Arrange
        // Act
        ShortURL shortURL = ShortURL.builder()
                .shortUrl("qwert")
                .originUrl("sdfgh")
                .title("poiu")
                .writer("mkiju")
                .build();
        boolean insertResult = shortURLRepository.insert(shortURL);

        List<ShortURL> shortURLList = shortURLRepository.findAll();
        // Assert
        assertTrue(insertResult);
        assertEquals(3, shortURLList.size());
    }

    @Test
    public void deleteByIndexTest(){
        // Arrange
        long index = 0;
        // Act
        boolean deleteResult = shortURLRepository.deleteByIndex(index);
        ShortURL shortURL = shortURLRepository.findByIndex(index);
        // Assert
        assertTrue(deleteResult);
        assertNotNull(shortURL.getRemoveDate());
    }

    @Test
    public void updateTest(){
        // Arrange
        long index = 1;
        // Act
        ShortURL shortURL = shortURLRepository.findByIndex(index);
        shortURL.setTitle("변경변경");
        shortURL.setOriginUrl("URL변경");
        shortURL.setShortUrl("변경 숏");
        boolean updateResult = shortURLRepository.update(shortURL);

        shortURL = shortURLRepository.findByIndex(index);
        // Assert
        assertTrue(updateResult);
        assertEquals("변경변경", shortURL.getTitle());
    }

    @Test
    public void getTitleTest(){
        // Arrange
        String url = "https://m.search.naver.com/search.naver?sm=mtp_hty.top&where=m&query=%EC%98%A4%EB%B8%8C%EC%A0%9D%ED%8A%B8+%EC%B1%85";
        // Act
        String title = shortURLRepository.getTitle(url);
        // Assert
        assertEquals("오브젝트 책 : 네이버 통합검색", title);
    }

    @Test
    public void setBase56EncodeTest(){
        int number = 537426;
        String str = shortURLRepository.setBase56Encode(number);
        System.out.println(str);
        assertEquals("3CQ", str);
    }

    @Test
    public void getBase56DecodeTest(){
        String url = "3CQ";
        int index = shortURLRepository.getBase56Decode(url);
        assertEquals(7389, index);
    }

    @Test
    public void checkShortUrlTest(){
        String url = "6FfQ";
        boolean checkResult = shortURLRepository.checkShortUrl(url);

        assertTrue(checkResult);
    }
}
