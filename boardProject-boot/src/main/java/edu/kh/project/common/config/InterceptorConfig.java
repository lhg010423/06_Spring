package edu.kh.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.kh.project.common.interceptor.BoardTypeInterceptor;

// 인터셉터가 어떤 요청을 가로챌지 설정하는 클래스

@Configuration // 서버가 켜지면 내부 메서드를 모두 수행
public class InterceptorConfig implements WebMvcConfigurer{
// WebMvcConfigurer : 스프링이랑 관련된 각종 override 해서 쓸수있는 기능들을 제공해줌
// fileConfig에서도 씀
	
	// 인터셉터 클래스 Bean 등록
	
	@Bean // 개발자가 만들어서 반환하는 객체를 Bean 등록
		// -> 관리는 Spring Container 가 수행
	public BoardTypeInterceptor boardTypeInterceptor() {
		return new BoardTypeInterceptor();
		// BoardTypeInterceptor기본생성자 이용한거
		// 그래서 @RequiredArgsConstructor 이거를 사용못한다는 거였음
	}
	
	// 동작할 인터셉터 객체를 추가하는 메서드
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// Bean으로 등록된 BoardTypeInterceptor 를 얻어와서 매개변수로 전달
		registry.addInterceptor(boardTypeInterceptor())
		.addPathPatterns("/**") // 가로챌 요청 주소를 지정
								// /** : / 이하 모든 요청 주소
		
		// 가로채지 않을 주소를 지정
		.excludePathPatterns("/css/**",
							"/js/**",
							"/images/**",
							"/favicon.ico");
	}
	
}
