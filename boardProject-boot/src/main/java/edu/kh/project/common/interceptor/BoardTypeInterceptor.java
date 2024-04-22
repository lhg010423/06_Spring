package edu.kh.project.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.kh.project.board.model.service.BoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/*
Interceptor : 요청/응답 가로채는 객체 (Spring 지원)

Cline <-> Filter <-> Dispatcher Servlet <-> Interceptor <-> Controller ...

* HandlerInterceptor 인터페이스를 상속 받아서 구현 해야 한다.

- preHandel  (전처리) : Dispatcher Servlet -> Controller 사이 수행

- postHandle (후처리) : Controller -> Dispatcher Servlet 사이 수행

- afterCompletion (뷰 완성(forward 코드 해석) 후) : View Resolver -> Dispatcher Servlet 사이 수행

*/

@Slf4j
public class BoardTypeInterceptor implements HandlerInterceptor{
	
	// BoardService 의존성 주입
	@Autowired
	private BoardService service;
	// 04/22 10:28
	// BoardTypeInterceptor 의 기본생성자를 사용해야하는데 
	// @RequiredArgsConstructor 이거를 쓰면 사용을 못한다
	

	// 전처리
	@Override
	public boolean preHandle(HttpServletRequest request, 
							HttpServletResponse response, 
							Object handler)
		// 전처리니 요청과 응답이 있음 (request, response)
			throws Exception {
		
		// application scope :
		// - 서버 종료 시 까지 유지되는 Servlet 내장 객체
		// - 서버 내에 딱 한 개만 존재!
		// 		--> 모든 클라이언트가 공용으로 사용
		
		// application scope 객체 얻어오기
		ServletContext application = request.getServletContext();
		
		// application scope에 "boardTypeList" 가 없을 경우
		if(application.getAttribute("boardTypeList") == null) {
			
			log.info("BoardTypeInterceptor - preHandle(전처리) 동작 실행");
			// Dispatcher Servlet에서 Controller 사이에 가로챈뒤 Controller로
			// 가져다 주는게 아니라 service로 가서 정보처리후 Controller로 간다
			
			// boardTypeList 조회 서비스 호출
			List<Map<String, Object>> boardTypeList = service.selectBoardTypeList();
			
			
			// 조회 결과를 application scope에 추가
			application.setAttribute("boardTypeList", boardTypeList);
			
		}
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler,
			ModelAndView modelAndView) throws Exception {
		// ModelAndView : 어디로 forward 할건지, 응답을 받고 forward 할때 가로챌 수 있다
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, 
			Object handler, Exception ex)throws Exception {
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	// HandlerInterceptor 를 쓰는데 오버라이딩 강제화가 안됨
	// HandlerInterceptor 에 들어가보면 메소드 전부 default로 되어있음
	
	
	
}
