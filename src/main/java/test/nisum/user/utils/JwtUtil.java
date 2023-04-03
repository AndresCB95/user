package test.nisum.user.utils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import test.nisum.user.models.exception.ExceptionNissum;
import test.nisum.user.models.jpa.User;
import test.nisum.user.service.IUserService;

@Component
public class JwtUtil {
	
	private String secret;

	private Key hmacKey;
	
	private IUserService userService;
	
	
	
	public JwtUtil(@Value("${token.secret}") String secret) {
		this.secret = secret;
		this.hmacKey = new SecretKeySpec(Base64.getDecoder().decode(this.secret), 
                SignatureAlgorithm.HS256.getJcaName());
	}

	public String generateJwt(User user, Long minuts) {
		Instant now = Instant.now(); 
		return Jwts.builder()
		        .setId(user.getUserId())
		        .setIssuedAt(Date.from(now))
		        .setExpiration(Date.from(now.plus(minuts, ChronoUnit.MINUTES)))
		        .signWith(hmacKey)
		        .compact();
	}
	
	public void validaJwt(String userId, String token, Long minuts) throws ExceptionNissum {
		try{
			Jwts.parserBuilder()
			      .setSigningKey(this.hmacKey)
			      .build()
			      .parseClaimsJws(token);
		}catch (Exception e) {
			throw new ExceptionNissum(ExceptionNissum.TOKEN_NO_VALIDO,HttpStatus.UNAUTHORIZED);
		}	
		validatTokeUser(userId,token);
	}
	
	private void validatTokeUser(String userId, String token) throws ExceptionNissum {
		try{
			String tokenUser = userService.getTokenUser(userId);
			if(!tokenUser.equals(token)) {
				throw new ExceptionNissum(ExceptionNissum.TOKEN_USER_INCORRECTO, HttpStatus.UNAUTHORIZED);
			}
			}catch (Exception e) {
				throw new ExceptionNissum(ExceptionNissum.MESSAGE_ERROR);
			}
	}
	
	public String renovateToken(String userId, String token, Long minuts) throws ExceptionNissum {
			validaJwt(userId, token, minuts);
			User user = new User();
			user.setUserId(userId);
			return generateJwt(user, minuts);
			
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
