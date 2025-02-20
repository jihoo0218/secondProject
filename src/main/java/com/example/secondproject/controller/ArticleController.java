package com.example.secondproject.controller;

import com.example.secondproject.dto.ArticleDto;
import com.example.secondproject.dto.CommentDto;
import com.example.secondproject.entity.Article;
import com.example.secondproject.repository.ArticleRepository;
import com.example.secondproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping ("/articles")
    public String boardShow(Model model){
        List<Article> articles = articleRepository.findAll();

        model.addAttribute("articleList",articles);

        return "articles/index";
    }
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleDto dto){
        log.info(dto.toString());

        Article article = dto.toEntity();
        log.info(article.toString());
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/"+saved.getId();
    }
    @GetMapping("/articles/{id}") // 해당 URL요청을 처리 선언
    public String show(@PathVariable Long id,
                       Model model) { // URL에서 id를 변수로 가져옴
        log.info("id = " + id);

        // 1: id로 데이터를 가져옴!
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);

        // 2: 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentsDtos);

        // 3: 보여줄 페이지를 설정!
        return "articles/show";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){

        Article article = articleRepository.findById(id).orElse(null);

        model.addAttribute("article",article);
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleDto dto){
        log.info(dto.toString());

        Article article = dto.toEntity();
        log.info(article.toString());


        Article target = articleRepository.findById(article.getId()).orElse(null);

        if(target !=null){
            articleRepository.save(article);
        }
        return "redirect:/articles/"+article.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info(id+"번 게시물 삭제 요청");

        Article article = articleRepository.findById(id).orElse(null);

        if(article != null){
            articleRepository.delete(article);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }
        return  "redirect:/articles";
    }

}
