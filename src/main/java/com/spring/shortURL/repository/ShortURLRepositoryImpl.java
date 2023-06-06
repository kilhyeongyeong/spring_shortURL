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

    private final String BASE56 = "3456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
//    private final String BASE56 = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private final int BASE = BASE56.length();

    private static final Map<Long, ShortURL> urlMap;

    private static int sequence;

    static {
        urlMap = new HashMap<>();
        ShortURL link1 = ShortURL.builder()
                .index(537426)
                .shortUrl("bit.ly/6FfQ")
                .originUrl("http://www.ktword.co.kr/test/view/view.php?m_temp1=356")
                .title("토폴로지")
                .writer("하잉")
                .count(0)
                .registrationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        ShortURL link2 = ShortURL.builder()
                .index(1)
                .shortUrl("bit.ly/qwercccd")
                .originUrl("https://m.search.naver.com/search.naver?sm=mtp_hty.top&where=m&query=%EC%98%A4%EB%B8%8C%EC%A0%9D%ED%8A%B8+%EC%B1%85")
                .title("bit.ly/qwercccd")
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
    public boolean insert(ShortURL shortURL) {

        // DB에서 처리해 주는 것들
        shortURL.setCount(0);
        shortURL.setRegistrationDate(LocalDateTime.now());
        shortURL.setLastUpdateDate(LocalDateTime.now());
        shortURL.setWriter("하잉");

        // DB였으면 insert 실패시 false 리턴 했을 것
        try{
            urlMap.put(shortURL.getIndex(), shortURL);
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

    @Override
    public String getTitle(String url){
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
    public String getShortUrl(int index) {
        String shortUrl = setBase56Encode(index);
        return shortUrl;
    }

    @Override
    public int getRandomIndex(){
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
    public String setBase56Encode(int randomNumber){

        StringBuilder sb = new StringBuilder();
        while(randomNumber > 0){
            int mod = randomNumber % BASE;
            sb.insert(0, BASE56.charAt(mod));
            randomNumber /= BASE;
        }

        return sb.toString();
    }

    public boolean checkShortUrl(String url){
        return urlMap.containsKey((long)getBase56Decode(url))
                && Objects.isNull(urlMap.get((long)getBase56Decode(url)).getRemoveDate());
    }

    @Override
    public int getBase56Decode(String url){
        int result = BASE * BASE56.indexOf(url.charAt(0));
        if (url.length() >= 2){
            result += BASE56.indexOf(url.charAt(1));
            for(int i=2; i<url.length(); i++){
                result = BASE * result + BASE56.indexOf(url.charAt(i));
            }
        }
        return result;
    }
}
