package com.attrecto.security.interceptors;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.attrecto.security.UserDetailsImpl;

public class UserRestControllerInterceptor extends HandlerInterceptorAdapter  {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Integer userId = userDetails.getId();
		
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
