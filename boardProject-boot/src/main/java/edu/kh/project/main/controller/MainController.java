package edu.kh.project.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.project.member.model.dto.Member;

@Controller
public class MainController {
	
	@RequestMapping("/") // "/" 요청 매핑(method(get, post) 가리지 않음)
	public String mainPage() {
		
		// 접두사/접미사 제외
		return "common/main";
	}
	
	
	
	
}
