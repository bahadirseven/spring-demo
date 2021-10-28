package com.demo.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.demo.entity.User;
import com.demo.service.UserService;

import io.jsonwebtoken.JwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private static final String BEARER = "Bearer ";

	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	private JwtTokenService jwtTokenService;

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			final String authorizationHeader = request.getHeader(AUTHORIZATION);

			if (authorizationHeader != null && authorizationHeader.contains(BEARER) && jwtTokenService.tokenValidate(authorizationHeader.substring(7))) {
				String username = jwtTokenService.getClaims(authorizationHeader.substring(7)).getSubject();

				User user = userService.findByUsername(username);

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),
						user.getPassword(),
						new ArrayList<>());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				SecurityContextHolder.clearContext();
			}

			filterChain.doFilter(request, response);
		} catch (JwtException e) {
			resolver.resolveException(request, response, null, e);
		}
	}
}