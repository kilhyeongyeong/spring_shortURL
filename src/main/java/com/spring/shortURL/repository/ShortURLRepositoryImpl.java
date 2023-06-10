package com.spring.shortURL.repository;

import com.spring.shortURL.entity.ShortURL;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.util.*;
import java.time.*;

public class ShortURLRepositoryImpl implements ShortURLRepository{

    protected final String BASE56 = "3456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
    protected long BASE = BASE56.length();
    protected static final Map<Long, ShortURL> urlMap;
    protected final static String shortUrlFront = "shortUrl.pj/";

    // urlMap Setting
    static {
        urlMap = new HashMap<>();
        ShortURL link1 = ShortURL.builder()
                .index(537426)
                .shortUrl("shortUrl.pj/6FfQ")
                .shortUrlBack("6FfQ")
                .shortUrlFront("shortUrl.pj")
                .originUrl("http://www.ktword.co.kr/test/view/view.php?m_temp1=356")
                .title("토폴로지")
                .writer("하잉")
                .count(0)
                .registrationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        ShortURL link2 = ShortURL.builder()
                .index(751659)
                .shortUrl("shortUrl.pj/7XVY")
                .shortUrlFront("shortUrl.pj")
                .shortUrlBack("7XVY")
                .originUrl("https://m.search.naver.com/search.naver?sm=mtp_hty.top&where=m&query=%EC%98%A4%EB%B8%8C%EC%A0%9D%ED%8A%B8+%EC%B1%85")
                .title("오브젝트 책 : 네이버 통합검색")
                .writer("하잉")
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
            if(Objects.isNull(shortURL.getRemoveDate())) shortURLList.add(shortURL);
        }

        return shortURLList;
    }

    @Override
    public ShortURL findByIndex(long index) {
        return urlMap.get(index);
    }

    @Override
    public ShortURL findByEncodedIndex(String encodedIndex) {
        return findByIndex(getBase56Decode(encodedIndex));
    }

    @Override
    public void insert(ShortURL shortURL) {

        shortURL.setCount(0);
        shortURL.setRegistrationDate(LocalDateTime.now());
        shortURL.setLastUpdateDate(LocalDateTime.now());

        urlMap.put(shortURL.getIndex(), shortURL);
    }

    @Override
    public void settingInsertBefore(ShortURL shortURL) {

        long index;
        String urlBack = shortURL.getShortUrlBack();

        if("".equals(urlBack)){
            index = getRandomIndex();
            urlBack = getBase56Encode(index);
        }else{
            index = getBase56Decode(urlBack);
        }

        ShortURL shortURLBuilder = ShortURL.builder()
                        .index(index)
                        .shortUrl(shortUrlFront + urlBack)
                        .shortUrlFront(shortUrlFront)
                        .shortUrlBack(urlBack)
                        .originUrl(shortURL.getOriginUrl())
                        .title("".equals(shortURL.getTitle()) ? getTitle(shortURL.getOriginUrl()) : shortURL.getTitle())
                        .writer("하잉")
                        .build();

        insert(shortURLBuilder);
    }


    @Override
    public void deleteByIndex(Long index) {

        ShortURL shortURL =  urlMap.get(index);
        shortURL.setRemoveDate(LocalDateTime.now());

        urlMap.put(index, shortURL);
    }

    @Override
    public void deleteByEncodedIndex(String encodedIndex) {
        deleteByIndex(getBase56Decode(encodedIndex));
    }

    @Override
    public void update(ShortURL shortURL) {
        System.out.println(shortURL);

        ShortURL shortURLOrigin = findByIndex(shortURL.getIndex());
        urlMap.remove(shortURLOrigin.getIndex());

        // 원본 : Title의 빈칸X -> 변경 : Title이 빈칸O = 원본을 그대로 넣음
        if(!"".equals(shortURLOrigin.getTitle()) && "".equals(shortURL.getTitle()))
            shortURL.setTitle(shortURLOrigin.getTitle());

        // shorten Url의 뒷 부분이 달라졌다면 index, shortUrl 수정
        if(!shortURL.getShortUrlBack().equals(shortURLOrigin.getShortUrlBack())){
            String urlBack = shortURL.getShortUrlBack();
            shortURL.setIndex(getBase56Decode(urlBack));
            shortURL.setShortUrl(shortUrlFront + urlBack);
        }

        shortURL.setLastUpdateDate(LocalDateTime.now());
        urlMap.put(shortURL.getIndex(), shortURL);
    }

    @Override
    public String getTitle(String url) {
        try {
            Connection con = Jsoup.connect(URLDecoder.decode(url, "UTF-8"));

            Document document = con.get();
            Elements elements = document.getElementsByTag("title");
            return elements.get(0).text();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public int getRandomIndex() {
        StringBuilder sb;
        while(true) {
            sb= new StringBuilder("");
            for (int i = 0; i < 6; i++) {
                sb.append((int) (Math.random() * 10));
            }
            if(!urlMap.containsKey(Integer.parseInt(sb.toString()))) break;
        }

        return Integer.parseInt(sb.toString());
    }

    @Override
    public boolean checkShortUrl(String url) {
        return urlMap.containsKey(getBase56Decode(url))
                && Objects.isNull(urlMap.get((long)getBase56Decode(url)).getRemoveDate());
    }

    @Override
    public CheckUrlEnum checkOriginUrl(String url) {
        List<ShortURL> list = findAll();
        for(ShortURL su : list){
            if(url.equals(su.getOriginUrl())) return CheckUrlEnum.ALREADY;
        }
        return CheckUrlEnum.OK;
    }

    @Override
    public String getBase56Encode(long randomNumber) {
        StringBuilder sb = new StringBuilder();
        while(randomNumber > 0){
            long mod = randomNumber % BASE;
            sb.insert(0, BASE56.charAt((int)mod));
            randomNumber /=  BASE;
        }

        return sb.toString();
    }

    @Override
    public long getBase56Decode(String url) {
        long result = BASE * BASE56.indexOf(url.charAt(0));
        if (url.length() >= 2){
            result += BASE56.indexOf(url.charAt(1));
            for(int i=2; i<url.length(); i++){
                result = BASE * result + BASE56.indexOf(url.charAt(i));
            }
        }
        return result;
    }

    @Override
    public void increaseCount(ShortURL shortURL) {
        shortURL.setCount(shortURL.getCount() + 1);
    }
}
