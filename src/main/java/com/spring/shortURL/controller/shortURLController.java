package com.spring.shortURL.controller;

import com.spring.shortURL.entity.ShortURL;
import com.spring.shortURL.repository.ShortURLRepository;
import com.spring.shortURL.repository.ShortURLRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/shortUrl")
public class shortURLController {
    ShortURLRepository shortURLRepository;

    ShortURL shortURL;

    @Autowired
    public shortURLController(){
        shortURLRepository = new ShortURLRepositoryImpl();
        shortURL = new ShortURL();
    }

    @GetMapping("/main")
    public String main(Model model){
        model.addAttribute("shortUrlList", shortURLRepository.findAll());
        System.out.println(shortURLRepository.findAll());
        return "/shortURL/main";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long index, Model model){
        shortURLRepository.deleteByIndex(index);
        model.addAttribute("shortUrlList", shortURLRepository.findAll());
        return "/shortURL/main";
    }

    @GetMapping("/createPage")
    public String createPage(){
        return "/shortURL/create";
    }

    @GetMapping("/create/calculation")
    public String calculation(@RequestParam("originUrl") String originUrl, Model model){
        // 인덱스
        int index = shortURLRepository.getRandomIndex();
        // 짧은 URL
        String shortUrl = shortURLRepository.getShortUrl(index);
        // 제목
        String title = shortURLRepository.getTitle(originUrl);

        shortURL = ShortURL.builder()
                .index(index)
                .shortUrl(shortUrl)
                .title(title)
                .originUrl(originUrl)
                .build();

        model.addAttribute("shortUrl", shortURL);
        return "/shortURL/create";
    }

    @RequestMapping(value = "/create", method = {RequestMethod.GET, RequestMethod.POST})
    public String create(){
        System.out.println(shortURL);
        shortURL.setIndex(shortURLRepository.getBase56Decode(shortURL.getShortUrl()));
        shortURL.setShortUrl("shortUrl.pj/" + shortURL.getShortUrl());
        shortURLRepository.insert(shortURL);
        return "redirect:/shortUrl/main";
    }

    @GetMapping("/create/checkShortUrl")
    public String checkShortUrl(String shortUrl, Model model){
        shortURL.setShortUrl(shortUrl);
        model.addAttribute("checkShortUrl", shortURLRepository.checkShortUrl(shortUrl));
        model.addAttribute("shortUrl", shortURL);
        return "/shortURL/create";
    }

    @GetMapping("/shortUrl.pj/{pathID}")
    public String connectLink(@PathVariable(name = "pathID") String pathID){

        System.out.println("pathId : " + pathID);
        int index = shortURLRepository.getBase56Decode(pathID);
        shortURL = shortURLRepository.findByIndex(index);

        return "redirect:" + shortURL.getOriginUrl();
    }

    @GetMapping("/update")
    public String update(String shortUrl, Model model){
        String decodeIndex = shortUrl.split("/")[1];
        int index = shortURLRepository.getBase56Decode(decodeIndex);
        shortURL = shortURLRepository.findByIndex(index);
        shortURL.setShortUrl(decodeIndex);
        model.addAttribute("shortUrl", shortURL);
        return "/shortURL/update";
    }
}
