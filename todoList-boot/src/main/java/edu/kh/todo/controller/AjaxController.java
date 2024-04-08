package edu.kh.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;

/* @ResponseBody
 * - 컨트롤러 메서드의 반환값을
 *   HTTP 응답 본문에 직접 바인딩하는 역할임을 명시
 * 
 * - 컨트롤러 메서드의 반환값을
 *   비동기 요청했던 HTML/JS 파일 부분에
 *   값을 돌려보낼것이다 명시
 *   
 * - forward/redirect 로 인식 X
 * 
 * - 비동기를 받아주는 메서드
 * 
 * 
 * 
 * @RequestBody
 * - 비동기 요청(ajax) 시 전달되는 데이터 중
 *   body 부분에 포함된 요청 데이터를
 *   알맞은 Java 객체 타입으로 바인딩하는 어노테이션
 * 
 * - 비동기 요청 시 body에 담긴 값을
 *   알맞은 타입으로 변환해서 매개변수에 저장
 * 
 * 
 * [HttpMessageConverter]
 * Spring 내장 객체
 * Spring에서 비동기 통신 시
 * - 전달되는 데이터의 자료형
 * - 응답하는 데이터의 자료형
 * 위 두가지를 알맞은 형태로 가공(변환)해주는 객체
 * 
 * - 문자열, 숫자 <-> TEXT
 * - DTO <-> JSON
 * - Map <-> JSON
 * 
 * (참고)
 * HttpMessageConverter가 동작하기 위해서는
 * Jackson-data-bind 라이브러리가 필요한데
 * Spring Boot 모듈에 내장되어 있음
 * (Jackson : 자바에서 JSON 다루는 방법 제공하는 라이브러리)
 * 
 * 
 * */


@Slf4j
@RequestMapping("ajax")
@Controller // 요청/응잡 제어 역할 명시 + Bean 등록
public class AjaxController {
	
	// @Autowired
	// 등록된 Bean 중 같은 타입 또는 상속관계인 Bean을
	// 해당 필드에 의존성 주입(DI)
	
	@Autowired
	private TodoService service;
	
	
	

	@GetMapping("main") // /ajax/main GET 요청 매핑
	public String ajaxMain() {
		
		// 접두사 : classpath:templates/
		// 접미사 : .html
		return "ajax/main";
	}
	
	
	// 전체 Todo 개수 조회
	// forward / redirect
	
	@ResponseBody // 값 그대로 돌려 보낼거야!!
	@GetMapping("totalCount")
	public int getTotalCount() {
		
		// 전체 할 일 개수 조회 서비스 호출 및 응답
		int totalCount = service.getTotalCount();
		
		return totalCount;
	}
	
	
	@ResponseBody
	@GetMapping("completeCount")
	public int getCompleteCount() {
		return service.getCompleteCount();
	}
	

	
	@ResponseBody // 비동기 요청 결과로 값 자체를 반환
	@PostMapping("add")
	public int addTodo(
			// JSON이 파라미터로 전달된 경우 아래 방법으로는 얻어오기 불가능
			// @RequestParam("todoTitle") String todoTitle,
			// @RequestParam("todoContent") String todoContent
			
			// Todo는 DTO말하는 거임, ajax-main.js에서 const param에
			// 저장한 js객체랑 변수명이 같으면 js객체의 value를
			// DTO에 변수명이 같은 값에 집어 넣는다
			// 위에 HttpMessageConverter 설명 볼 것
			@RequestBody Todo todo // 요청 body에 담긴 값을 Todo에 저장
			) {
		
		log.debug(todo.toString());
		
		return service.addTodo(todo.getTodoTitle(), todo.getTodoContent());
	}
	
	@ResponseBody
	@GetMapping("selectList")
	public List<Todo> selectList() {
		// selectAll()은 완료된 개수랑 completeCount까지
		// Map으로 가져와서 selectList를 따로 만든다
		List<Todo> todoList = service.selectList();
		
		return todoList;
		
		// List(Java 전용 타입) 를 반환
		// -> JS가 인식할 수 없기 때문에
		// HttpmessageConverter 가
		// JSON형태로 변환하여 반환
		// -> [{}, {}, {}] JSONArray, <- String형태다
	}
	
	
	@ResponseBody
	@GetMapping("detail")
	public Todo selectTodo(@RequestParam("todoNo") int todoNo) {
		
		// return 자료형 : Todo
		// -> 자바형태형 그대로 못보냄
		// -> HttpMessageConverter가 String(JSON)형태로 변환해서 반환
		return service.todoDetail(todoNo);
	}
	
	
	// GET/POST (동기/비동기)
	// DELETE/PUT 비동기)
	
	/* 04/08 15:30 설명 듣기
	 * REST API : 서로 다른 언어를 
	 * RESTFUL : 자원 중심
	 * 
	 * 
	 * HTTP 메서드
	 * - GET : 자원 조회
	 * - POST : 자원 생성
	 * - PUT : 자원 업데이트
	 * - DELETE : 자원 삭제
	 * 
	 * */
	
	// Delete 방식 요청 처리 (비동기 요청만 가능!!)
	@ResponseBody
	@DeleteMapping("delete")
	public int todoDelete(@RequestBody int todoNo) {
		return service.todoDelete(todoNo);
	}
	
	
	@ResponseBody
	@GetMapping("updateComplete")
	public int changeComplete(@RequestBody int todoNo) {
		return service.updateComplete(todoNo);
	}
	
	
	
}
