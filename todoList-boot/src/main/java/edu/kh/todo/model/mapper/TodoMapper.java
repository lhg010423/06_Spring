package edu.kh.todo.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.todo.model.dto.Todo;

/* @Mapper
 * - Mybatis에서 제공하는 어노테이션
 * - 해당 어노테이션이 작성된 인터페이스는
 *   namespace에 해당 인터페이스가 작성된
 *   mapper.xml 파일과 연결되어 SQL 호출/수행/결과
 *   반한 가능
 * 
 * - Mybatis에서 제공하는 Mapper 상속 객체가 Bean으로 등록됨
 * 
 * 
 * */

// @@ 04-04 14:20 : Bean 설명 듣기

@Mapper
public interface TodoMapper {
	// todo-mapper.xml의 <mapper> 랑 자동으로 연결됨

	/* Mapper의 메서드명 == mapper.xml 파일 내 태그의 id
	 * 
	 * 메서드명과 id 가 같은 태그가 서로 연결됨
	 * 
	 * */
	
	
	
	/** 할 일 목록 조회
	 * @return todoList
	 */
	List<Todo> selectAll();

	/** 완료된 할 일 개수 조회
	 * @return
	 */
	int getCompleteCount();

	/** 할 일 추가
	 * @param todo
	 * @return
	 */
	int addTodo(Todo todo);
	
	
	
	
	
}
