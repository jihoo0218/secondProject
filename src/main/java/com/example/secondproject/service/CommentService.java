package com.example.secondproject.service;

import com.example.secondproject.dto.CommentDto;
import com.example.secondproject.entity.Article;
import com.example.secondproject.entity.Comment;
import com.example.secondproject.repository.ArticleRepository;
import com.example.secondproject.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        /* ---------리팩토링
        //조회
        List<Comment> comments= commentRepository.findByArticleId(articleId);
        //변환 엔티티-> DTO
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for (int i=0; i<comments.size();i++){
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
                    dtos.add(dto);
        }*/
        //반환

        return commentRepository.findByArticleId(articleId).stream().map(comment -> CommentDto.createCommentDto(comment)).collect(Collectors.toList());
    }
    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {

        //게시글 조회 및 예외 발생
        Article article = articleRepository.
                findById(articleId).orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패! 해당 게시글이 없습니다."));
        //댓글 엔티티 생성
        Comment comment=Comment.createCommnet(dto, article);
        //댓글 엔티티 DB로 저장
        Comment created =commentRepository.save(comment);
        //DTO로 변경하여 반환

        return CommentDto.createCommentDto(created);

    }
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        //댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("댓글 수정 실패! 대상글이 없습니다."));
        //댓글 수정
        target.patch(dto);
        //수정된 댓글 DB로 갱신
        Comment updated = commentRepository.save(target);
        //댓글 엔터티 DTO로 변환 반환
        return CommentDto.createCommentDto(updated);
    }
    @Transactional
    public CommentDto delete(Long id) {
        //댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("댓글 수정 실패! 대상글이 없습니다."));
        //댓글 DB에서 삭제
        commentRepository.delete(target);
        //삭제 댓글을 DTO로 전달
        return CommentDto.createCommentDto(target);
    }
}
