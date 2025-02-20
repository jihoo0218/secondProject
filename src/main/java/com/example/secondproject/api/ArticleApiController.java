package com.example.secondproject.api;

import com.example.secondproject.dto.ArticleDto;
import com.example.secondproject.entity.Article;
import com.example.secondproject.repository.ArticleRepository;
import com.example.secondproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Service
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService   articleService;
    @Autowired
    private ArticleRepository articleRepository;
    // GET
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleService.index();
    }
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }
    // POST
    @PostMapping ("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleDto dto) {
        Article article = articleService.create(dto);
        return (article!=null)?ResponseEntity.status(HttpStatus.OK).body(article) :ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,@RequestBody ArticleDto dto){
        Article article = articleService.update(id,dto);

        return (article != null)?ResponseEntity.status(HttpStatus.OK).body(article):ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // DELETE
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article delete = articleService.delete(id);

        return (delete != null)? ResponseEntity.status(HttpStatus.OK).body(delete):ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
