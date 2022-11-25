package com.ssaffy.happyapi.config;

import java.util.Arrays;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@MapperScan(basePackages = { "com.ssaffy.happyapi" })
public class MainConfig implements WebMvcConfigurer {
//	@Autowired
//	private MainInterceptor interceptor;
//	
//	List<String> patterns = Arrays.asList("/user/myPage");
//	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(interceptor).addPathPatterns(patterns);
//	}
}
