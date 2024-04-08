package edu.kh.todo.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.mapper.TodoMapper;

// ----------------------------------------
// @Transactional
// - 트랜잭션 처리를 수행하라고 지시하는 어노테이션
// ( == 선언적 트랙잭션 처리)

// - 정상 코드 수행 시 COMMIT

// - 기본값 : Service 내부 코드 수행 중 RuntimeException 발생 시 rollback

// - rollbackFor 속성 : 어떤 예외가 발생했을 때 rollback 할 지 지정
// ----------------------------------------

// 모든 종류의 예외 발생 시 rollback 수행
@Transactional(rollbackFor = Exception.class)
@Service // 비즈니스 로직(데이터를 가공, 트랜잭션 처리) 역할 명시 + Bean 등록
public class TodoServiceImpl implements TodoService{
	
	@Autowired // DI 적용
	private TodoMapper mapper;

	// 할 일 목록 + 완료된 할 일 개수 조회
	@Override
	public Map<String, Object> selectAll() {
		
		// TodoMapper 가 인터페이스여서 기존 DAO에서 하던걸 여기서함
		// 1. 할 일 목록 조회
		List<Todo> todoList = mapper.selectAll();
		
		// 2. 완료된 할 일 개수 조회("Y"인 개수를 count로 구하니까 int형이다)
		int completeCount = mapper.getCompleteCount();
		
		// Map으로 묶어서 반환
		Map<String, Object> map = new HashMap<>();
		
		map.put("todoList", todoList);
		map.put("completeCount", completeCount);
		
		
		return map;
	}

	// 할 일 추가
	@Override
	public int addTodo(String todoTitle, String todoContent) {
		
		// Connection 생성 / 반환 X
		// 트랜젝션 제어 처리 -> @Transactional 어노테이션
		
		// 마이바티스에서 SQL에 전달할 수 있는 파라미터의 개수는
		// 오직 1개!!!
		//-> todoTitle, todoContent를 Todo DTO로 묶어서 전달
		Todo todo = new Todo();
		todo.setTodoTitle(todoTitle);
		todo.setTodoContent(todoContent);
		
		/*
		int result = mapper.addTodo(todo);
		return result;  그냥 바로 return 함*/
		
		return mapper.addTodo(todo);
	}

	
	// 할 일 상세 조회
	@Override
	public Todo todoDetail(int todoNo) {
		
		return mapper.todoDetail(todoNo);
	}

	// 완료된 할 일 개수 조회
	@Override
	public int changeComplete(Todo todo) {
		
		return mapper.changeComplete(todo);
	}

	
	// 할 일 수정
	@Override
	public int todoUpdate(Todo todo) {
		// todoTitle, todoContent, todoNo 이세가지를 return해야하는데
		// return은 1개 밖에 안돼니까 Todo를 쓴다
		
		return mapper.todoUpdate(todo);
	}

	
	// 할 일 삭제
	@Override
	public int todoDelete(int todoNo) {
		
		return mapper.todoDelete(todoNo);
	}

	
	// 전체 할 일 개수 조회
	@Override
	public int getTotalCount() {
		return mapper.getTotalCount();
	}

	// 완료된 할 일 개수 조회
	@Override
	public int getCompleteCount() {
		return mapper.getCompleteCount();
	}

	// 할 일 목록 조회
	@Override
	public List<Todo> selectList() {
		return mapper.selectAll();
	}
	
	
	
	public int updateComplete(int todoNo) {
		return mapper.updateComplete(todoNo);
	}
	
	
	
	
	
	
	
	
}
