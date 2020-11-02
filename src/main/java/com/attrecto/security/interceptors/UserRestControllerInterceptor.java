package com.attrecto.security.interceptors;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.attrecto.security.UserDetailsImpl;

public class UserRestControllerInterceptor extends HandlerInterceptorAdapter  {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Integer userId = userDetails.getId();
		
		log.info("=========================== request begin ================================================");
        log.info("URI         : {}", request.getRequestURI());
        log.info("Method      : {}", request.getMethod());
        log.info("User:       : {}", userDetails.getUsername());
        log.info("==========================r equest end ================================================");

        boolean isAdmin = userDetails.getAuthorities().stream()
		.map(a -> a.getAuthority())
		.collect(Collectors.toSet()).contains("ROLE_ADMIN");
		
		if(isAdmin) {
			return true;
		}
		
		if(request.getRequestURI().contains("/users/" + userId + "/")) {
			return true;
		}
		
		if(request.getRequestURI().toString().endsWith("/users/" + userId)) {
			return true;
		}
		
		return false;
	}
}
