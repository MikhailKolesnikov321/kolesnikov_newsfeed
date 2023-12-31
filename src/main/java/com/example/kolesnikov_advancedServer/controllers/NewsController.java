package com.example.kolesnikov_advancedServer.controllers;

import com.example.kolesnikov_advancedServer.JwtToken.JwtUserDetails;
import com.example.kolesnikov_advancedServer.dtos.CreateNewsSuccessResponse;
import com.example.kolesnikov_advancedServer.dtos.CustomSuccessResponse;
import com.example.kolesnikov_advancedServer.dtos.NewsDto;
import com.example.kolesnikov_advancedServer.services.impl.NewsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/news")
public class NewsController {

    private final NewsServiceImpl newsService;

    @PostMapping
    public ResponseEntity createNews(Authentication authentication, @Valid @RequestBody NewsDto newsDto){
        UUID id = ((JwtUserDetails)(authentication.getPrincipal())).getId();
        return new ResponseEntity<>(CreateNewsSuccessResponse.ok(newsService.createNews(newsDto, id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getNews(@RequestParam int page, @RequestParam int perPage){
        return new ResponseEntity<>(CustomSuccessResponse.ok(newsService.getNews(page, perPage)), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity findNews(@RequestParam (required = false) String username, @RequestParam (required = false) String keyword,
                                   @RequestParam int page, @RequestParam int perPage,
                                   @RequestParam (required = false) List<String> tags){
        return new ResponseEntity<>(CustomSuccessResponse.ok(newsService.getNewsByParam(page, perPage, username, keyword, tags)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity putNewsNewData(@PathVariable Long id, @Valid @RequestBody NewsDto newsDto){
        return new ResponseEntity<>(newsService.changeNewsData(id, newsDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNews(@PathVariable Long id){
        return new ResponseEntity<>(newsService.deleteNews(id), HttpStatus.OK);
    }
}
