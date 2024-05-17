package edu.kh.project.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/*
스프링 예외 처리 방법(우선 순위별로 작성)

1. 메서드에서 직접 처리 ( try-catch, throws )

2. 컨트롤러 클래스에서 클래스 단위로 모아서 처리
	( @ExceptionHandler 어노테이션을 지닌 메서드를 작성 )

3. 별도 클래스를 만들어 프로젝트 단위로 모아서 처리
	( @ControllerAdvice 어노테이션을 지닌 클래스를 작성 )


*/

// @ControllerAdvice
// - 전역적 예외 처리


@ControllerAdvice
public class ExceptionController {
	// boardProject 의 모든 예외는 여기로 온다
	
	// @ExceptionHandler(예외 종류)
	
	// 예외 종류 : 메서들별로 처리할 예외를 지정
	//		ex) @ExceptionHandler(SQLException.class) - SQL 관련 예외만 처리
	//			@ExceptionHandler(IOException.class)  - 입출력 관련 예외만 처리
	//			@ExceptionHandler(Exception.class)    - 모든 예외 처리
	
	
	// 404 에러 (자원을 찾을 수 없다)
	@ExceptionHandler(NoResourceFoundException.class)
	public String notFound() {
		return "error/404";
	}
	
	
	
	// 예외 처리하는 메서드에서 사용가능한 매개변수 ( Controller에서 사용하는 모든 매개변수 작성 가능 )
	
	// 프로젝트에서 발생하는 모든 종류의 예외를 처리
	@ExceptionHandler(Exception.class)
	public String allExceptonHandler(Exception e, Model model) {
		
		e.printStackTrace();
		model.addAttribute("e", e);
		
		return "error/500";
	}
	
	
	
	
	
	
	
	
	
}
