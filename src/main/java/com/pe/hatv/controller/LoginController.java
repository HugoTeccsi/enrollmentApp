package com.pe.hatv.controller;

import com.pe.hatv.security.JwtUtil;
import com.pe.hatv.security.model.AuthRequest;
import com.pe.hatv.security.model.AuthResponse;
import com.pe.hatv.security.model.ErrorLogin;
import com.pe.hatv.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final JwtUtil jwtUtil;

	private final UserService userService;

	@PostMapping("/login")
	public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest){

		return userService.searchByUser(authRequest.getUsername())
				.map(user -> {
					if (BCrypt.checkpw(authRequest.getPassword(), user.getPassword())){
						var token = jwtUtil.generateToke(user);
						var expiration = jwtUtil.getExpirationDateFromToken(token);

						return ResponseEntity.ok(new AuthResponse(token, expiration));
					}else {
						return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
								.body(new ErrorLogin("Bad credentials",new Date()));
					}
				}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

}
