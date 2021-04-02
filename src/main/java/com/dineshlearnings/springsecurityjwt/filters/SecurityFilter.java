package com.dineshlearnings.springsecurityjwt.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dineshlearnings.springsecurityjwt.UserService;
import com.dineshlearnings.springsecurityjwt.utils.JwtUtils;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			String username = jwtUtils.getUserName(token);
			UserDetails user = userService.loadUserByUsername(username);
			Boolean isValid = jwtUtils.validateToken(token, user.getUsername());
			if (isValid) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
						user.getPassword(), user.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
