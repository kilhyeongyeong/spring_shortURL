package com.spring.shortURL.repository;

import com.spring.shortURL.entity.ShortURL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShortURLRepositoryTest {
    
    ShortURLRepository shortURLRepository;

    @BeforeEach
    public void setShortURLRepository(){
        shortURLRepository = new ShortURLRepositoryImpl();
    }

    @Test
    @DisplayName("전체 개수가 2인지 확인")
    public void findAllTest(){
        // Arrange
        // Act
        List<ShortURL> shortURLList = shortURLRepository.findAll();
        // Assert
        assertEquals(2, shortURLList.size());
    }

    @Test
    @DisplayName("인덱스 751659번 내용 확인 - shortenUrl, originUrl")
    public void findByIndexTest(){
        // Arrange
        long index = 751659;
        // Act
        ShortURL shortURL = shortURLRepository.findByIndex(index);
        // Assert
        assertEquals("bit.ly/7XVY", shortURL.getShortUrl());
        assertEquals("https://m.search.naver.com/search.naver?sm=mtp_hty.top&where=m&query=%EC%98%A4%EB%B8%8C%EC%A0%9D%ED%8A%B8+%EC%B1%85", shortURL.getOriginUrl());
    }

    @Test
    @DisplayName("insert 후 전체 리스트 1증가 확인")
    public void insertTest(){
        // Arrange
        // Act
        ShortURL shortURL = ShortURL.builder()
                .shortUrl("qwert")
                .originUrl("sdfgh")
                .title("poiu")
                .writer("mkiju")
                .build();
        shortURLRepository.insert(shortURL);
        
        List<ShortURL> shortURLList = shortURLRepository.findAll();
        // Assert
        assertEquals(3, shortURLList.size());
    }

    @Test
    @DisplayName("인덱스 번호로 지운 후 지워졌는지 확인")
    public void deleteByIndexTest(){
        // Arrange
        long index = 751659;
        // Act
        shortURLRepository.deleteByIndex(index);

        ShortURL shortURL = shortURLRepository.findByIndex(index);
        // Assert
        assertNotNull(shortURL.getRemoveDate());
    }

    @Test
    @DisplayName("update 확인")
    public void updateTest(){
//        // Arrange
//        long index = 1;
//        String title = "";
//        String
//        // Act
//        ShortURL shortURL = shortURLRepository.findByIndex(index);
//        shortURL.setTitle("변경변경");
//        shortURL.setOriginUrl("URL변경");
//        shortURL.setShortUrl("변경 숏");
//        shortURLRepository.update(shortURL);
//
//        shortURL = shortURLRepository.findByIndex(index);
//        // Assert
//        assertEquals("변경변경", shortURL.getTitle());
    }

    @Test
    @DisplayName("스크립에서 Title이 제대로 파싱 되는지 확인")
    public void getTitleTest(){
        // Arrange
        String url = "https://m.search.naver.com/search.naver?sm=mtp_hty.top&where=m&query=%EC%98%A4%EB%B8%8C%EC%A0%9D%ED%8A%B8+%EC%B1%85";
        // Act
        String title = shortURLRepository.getTitle(url);
        // Assert
        assertEquals("오브젝트 책 : 네이버 통합검색", title);
    }

    @Test
    @DisplayName("Base56으로 인코딩이 잘 되는지 확인")
    public void getBase56EncodeTest(){
        int number = 537426;
        String str = shortURLRepository.getBase56Encode(number);
        assertEquals("6FfQ", str);
    }

    @Test
    @DisplayName("Base56으로 디코딩이 잘 되는지 확인")
    public void getBase56DecodeTest(){
        String url = "6FfQ";
        long index = shortURLRepository.getBase56Decode(url);
        assertEquals(537426, index);
    }

    @Test
    @DisplayName("Shorten Url 중복 되면 true")
    public void checkShortUrlTest(){
        String url = "6FfQ";
        boolean checkResult = shortURLRepository.checkShortUrl(url);

        assertTrue(checkResult);
    }

    @Test
    @DisplayName("원본 Url이 중복 되면 true")
    public void checkedOriginUrl(){
        String url = "http://www.ktword.co.kr/test/view/view.php?m_temp1=356";
        CheckUrlEnum checkedResult = shortURLRepository.checkOriginUrl(url);

        assertThat(checkedResult).isEqualTo(checkedResult);
    }
}
