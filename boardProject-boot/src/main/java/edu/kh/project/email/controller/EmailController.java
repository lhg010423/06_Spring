package edu.kh.project.email.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.project.email.model.service.EmailService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("email")
@RequiredArgsConstructor // final 필드/@NotNull 필드에 자동으로 의존성 주입 (@Autowired 생성자 방식 코드 자동완성)
public class EmailController {
		
	private final EmailService service;
	// @Autowired
	// private EmailService service;
	// 이렇게 안써도 됨, final 붙이고 클래스 위에
	// @RequiredArgsConstructor 만 붙이면 됨
	
	@ResponseBody
	@PostMapping("signup")
	public int signup(@RequestBody String email) {
		
		String authKey = service.sendEmail("signup", email);
		
		return 0;
	}
	
}

/* @Autowired를 이용한 의존성 주입 방법은 3가지 존재
 * 1) 필드
 * 2) setter
 * 3) 생성자 (권장)
 * 
 * Lombok 라이브러리에서 제공하는
 * 
 * @RequiredArgsConstructor 를 이용하면
 * 
 * 필드 중
 * 1) 초기화 되지 않은 final이 붙은 필드
 * 2) 초기화 되지 않은 @NotNull 이 붙은 필드
 * 
 * 1,2 에 해당하는 필드에 대한
 * @Autowired 생성자 구문을 자동 완성
 * 클래스에 쓰는거다
 * 
 * 
 * */

/*
1) 필드에 의존성 주입하는 방법 (권장 X)

@Autowired // 의존성 주입(DI)
private EmailService service;


2)setter 이용

private EmailService service;

@Autowired
public void setService(EmailServicd service) {
	this.service = service;
}


3) 생성자
private EmailService service;
private MemberService service2;

@Autowired
public EmailController(EmailService service, MemberService service2) {
	this.service = service;
	this.service2 = service2;

}


*/












