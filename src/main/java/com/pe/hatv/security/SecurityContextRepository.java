package com.pe.hatv.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityContextRepository implements ServerSecurityContextRepository {

	private final AuthenticationManager authenticationManager;

	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		return null;
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		var request = exchange.getRequest();
		var authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (Objects.nonNull(authHeader)) {
			if (authHeader.startsWith("Bearer ") || authHeader.startsWith("bearer ")) {
				log.info("exits bearer...");
				var authToken = authHeader.substring(7);
				var auth = new UsernamePasswordAuthenticationToken(null, authToken);
				return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
			}
		}
		return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized"));
	}
}
