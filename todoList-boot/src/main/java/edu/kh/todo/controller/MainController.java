package edu.kh.todo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 객체 자동 생성
@Controller // 요청/응닫 제어 역할 명시 + Bean 등록
public class MainController {

	
	@Autowired // DI (의존성 주입)
	private TodoService service; // 상속관계이거나 같은 관계를 가져온다
	
	
	@RequestMapping("/") // 메인페이지 요청할때는 / 써도됨
	public String mainPage(Model model) { // 전달인자해결사
	
		// 의존성 주입(DI) 확인 (진짜 Service 객체 들어옴)
		// service : edu.kh.todo.model.service.TodoServiceImpl@495aa530
		log.debug("service : " + service);
		
		// Service 메서드 호출 후 결과 반환 받기
		Map<String, Object> map = service.selectAll(); // 이거 하나로 2가지 기능을 쓰고 싶음
		// 그럼 Map<k, v> 을 사용함, 뭐든 받을 수 있게 Object를 씀
		
		// map에 담긴 내용 추출
		// 위에 map을 만들 때 Object로 만들어서 다운캐스팅을 해야한다
		List<Todo> todoList = (List<Todo>)map.get("todoList");
		int completeCount = (int)map.get("completeCount");
		
		// @@ 04-04 15:27 설명 듣기
		
		// Model : 값 전달용 객체(request scope) + session 변환 가능
		model.addAttribute("todoList", todoList);
		model.addAttribute("completeCount", completeCount);
		
		
		
		// classpath:/templates/
		// common/main
		// .html
		// -> 이쪽으로 forward
		return "common/main";
	}
	
	
	
}
