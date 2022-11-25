package com.ssaffy.happyapi.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class MainInterceptor implements HandlerInterceptor {
	
	
	private static final String secretKey =  "secretKey";
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String jwt = request.getHeader("Authentication");
		try {
            Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(jwt).getBody(); 
            return true;
        
        }catch(ExpiredJwtException e) {
            System.out.println("Token Expired");
            return false;
        
        }catch(JwtException e) {
        	System.out.println("Token Error");
            return false;
        }
	}
}
