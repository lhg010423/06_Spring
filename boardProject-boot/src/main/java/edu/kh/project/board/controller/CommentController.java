package edu.kh.project.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.project.board.model.dto.Comment;
import edu.kh.project.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;

/*
@RestController (REST API 구축을 위해서 사용하는 컨트롤러)

= @Controller(요청/응답 제어 + Bean 등록)
	+ @ResponseBody (응답 본문으로 데이터 자체를 반환)
	
-> 모든 응답을 응답 본문(ajax)으로 반환하는 컨트롤러

*/

//@Controller // Controller 명시 + Bean으로 등록

@RestController // 비동기 요청만 받는 컨트롤러
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController {

	// fetch - 비동기요청
	// "comment" 요청이 오면 해당 컨트롤러에서 잡아서 처리함
	// @ResponseBody를 매번 메서드에 추가..
	
	private final CommentService service;
	
	@GetMapping("") // get요청온거를 잡아줌
	public List<Comment> select(@RequestParam("boardNo") int boardNo) {
		
		// 04/29 12:28 여기서부터 전부 듣기
		// HttpMessageConverter가 
		// List -> JSON (문자열)로 변환해서 응답
		return service.select(boardNo);
	}
	
	
	/** 댓글/답글 등록
	 * @return
	 */
	@PostMapping("")
	public int insert(@RequestBody Comment comment) {
		// Comment DTO랑 js에서 body로 보낸 변수명이 같아서
		// 자동으로 매칭된다
		
		return service.insert(comment);
	}
	
	
	/** 댓글 수정
	 * @return
	 */
	@PutMapping("")
	public int update(@RequestBody Comment comment) {
		return service.update(comment);
	}
	
	
	/** 댓글 삭제
	 * @param comment
	 * @return
	 */
	@DeleteMapping("")
	public int delete(@RequestBody int commentNo) {
		// js의 body에서 보낸값이 commentNo 하나이다
		// int commentNo 말고 Comment comment 처럼 DTO객체를
		// 불러오면 js에서 commentNo는 int형인데 
		// Comment 객체를 불러오면 key:value형태라 안됨
		return service.delete(commentNo);
	}
	
	
	
}
