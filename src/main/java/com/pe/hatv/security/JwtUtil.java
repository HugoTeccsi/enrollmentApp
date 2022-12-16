package com.pe.hatv.security;

import com.pe.hatv.security.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

	@Value("${jjwt.secret}")
	private String secret;

	@Value("${jjwt.expiration}")
	private String expirationTime;

	public String generateToke(User user){
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles",user.getRoles());
		claims.put("username",user.getUsername());

		return doGenerateToken(claims, user);
	}

	private String doGenerateToken(Map<String, Object> claims, User user){

		Long expirationTimeLong = Long.parseLong(expirationTime);

		var createdDate = new Date();

		var expirationDate = new Date(createdDate.getTime()+expirationTimeLong*1000);

		var key = Keys.hmacShaKeyFor(this.secret.getBytes());

		return Jwts.builder().setClaims(claims).setSubject(user.getUsername())
				.setIssuedAt(createdDate).setExpiration(expirationDate)
				.signWith(key).compact();
	}

	private Boolean isTokenExpired(String token){
		var expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateToken(String token){
		return !isTokenExpired(token);
	}

	public String getUserNameFromToken(String token){
		return getAllClaimsFromToken(token).getSubject();
	}

	public Date getExpirationDateFromToken(String token){
		return getAllClaimsFromToken(token).getExpiration();
	}

	public Claims getAllClaimsFromToken(String token){
		return Jwts.parserBuilder().setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
				.build().parseClaimsJws(token).getBody();
	}
}
