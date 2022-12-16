package com.pe.hatv.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

	private final JwtUtil jwtUtil;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {

		var token = authentication.getCredentials().toString();

		var user = jwtUtil.getUserNameFromToken(token);

		if (Objects.nonNull(user) && jwtUtil.validateToken(token)){
			var claims = jwtUtil.getAllClaimsFromToken(token);

			List<String> roles = claims.get("roles", List.class);

			var auth = new UsernamePasswordAuthenticationToken(user,null,roles.stream()
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

			return Mono.just(auth);
		}
		return Mono.error(new InterruptedException("Token not valid or expired"));
	}
}
