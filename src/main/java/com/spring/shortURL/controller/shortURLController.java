package com.spring.shortURL.controller;

import com.spring.shortURL.entity.ShortURL;
import com.spring.shortURL.repository.ShortURLRepository;
import com.spring.shortURL.repository.ShortURLRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/shortUrl")
public class shortURLController {
    ShortURLRepository repository;

    public shortURLController(){
        repository = new ShortURLRepositoryImpl();
    }

    @GetMapping("/searchPage")
    public String searchPage(){
        return "/shortURL/search";
    }

//    @PostMapping("/search")
//    public String serch(String shortenUrl){
//        System.out.println(shortenUrl);
//        ShortURL shortURL = repository.findByEncodedIndex(shortenUrl.split("/")[1]);
//        if(Objects.isNull(shortURL)){
//            return "/shortURL/search";
//        }
//        return "redirect:/shortUrl/" + shortURL.getShortUrl();
//    }

    @GetMapping("/main")
    public String main(Model model){
        model.addAttribute("shortUrlList", repository.findAll());
        return "/shortURL/main";
    }

    @GetMapping("/delete")
    public String delete(String shortUrl){
        repository.deleteByEncodedIndex(shortUrl.split("/")[1]);
        return "redirect:/shortUrl/main";
    }

    @GetMapping("/createPage")
    public String createPage(Model model){
        model.addAttribute("pageKind", "c");
        return "/shortURL/createOrUpdate";
    }

    @PostMapping("/checkOriginUrl")
    public String checkOriginUrl(ShortURL shortURL, String pageKind, Model model){

        model.addAttribute("pageKind", pageKind);
        model.addAttribute("checkOriginUrl", repository.checkOriginUrl(shortURL.getOriginUrl()));
        model.addAttribute("shortUrl", shortURL);

        return "/shortURL/createOrUpdate";
    }

    @PostMapping("/checkShortUrl")
    public String checkShortUrl(ShortURL shortURL, String pageKind, Model model){

        model.addAttribute("pageKind", pageKind);
        model.addAttribute("checkShortUrl", repository.checkShortUrl(shortURL.getShortUrlBack()));
        model.addAttribute("shortUrl", shortURL);

        return "/shortURL/createOrUpdate";
    }

    @PostMapping("/create")
    public String create(ShortURL shortURL){
        repository.settingInsertBefore(shortURL);
        return"redirect:/shortUrl/main";
    }

    @GetMapping("/updatePage")
    public String update(String shortUrl, Model model){

        model.addAttribute("pageKind", "u");
        model.addAttribute("shortUrl", repository.findByEncodedIndex(shortUrl.split("/")[1]));

        return "/shortURL/createOrUpdate";
    }

    @PostMapping("/update")
    public String update(ShortURL shortUrl){
        System.out.println("shortUrl : " + shortUrl);
        repository.update(shortUrl);

        return"redirect:/shortUrl/main";
    }

    @GetMapping("/shortUrl.pj/{pathID}")
    public String connectLink(@PathVariable(name = "pathID") String pathID){

        System.out.println("pathId : " + pathID);

        long index = repository.getBase56Decode(pathID);
        ShortURL shortURL = repository.findByIndex(index);
        repository.increaseCount(shortURL);

        return "redirect:" + shortURL.getOriginUrl();
    }
}
