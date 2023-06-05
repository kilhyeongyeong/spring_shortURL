package com.spring.shortURL.repository;

import com.spring.shortURL.entity.ShortURL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
}
