package test.nisum.user.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import test.nisum.user.models.MessageResponse;
import test.nisum.user.models.exception.ExceptionNissum;
import test.nisum.user.models.jpa.User;
import test.nisum.user.service.IUserService;
import test.nisum.user.utils.JwtUtil;

@RestController()
@RequestMapping("/users")
public class UserController {
	
	private final IUserService userService;
	private final Long minutsToken;
	private final JwtUtil jwtUtil;
	

	public UserController(IUserService userService, @Value("${token.minut}")String minutsToken, JwtUtil jwtUtil) {
		this.minutsToken =  Long.parseLong(minutsToken);
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}


	@ApiOperation(value = "This method is used to create user.")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registrationUser(@RequestBody User user){
		try {
			return new ResponseEntity<Object>(userService.save(user),HttpStatus.CREATED);
		}catch (ExceptionNissum e) {
			return new ResponseEntity<Object>(new MessageResponse(e.getMessage()),e.getStatus());
		}
		
	}
	
	@ApiOperation(value = "This method is used to get users.")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUsers(@RequestHeader("user_id")String userId, 
			@RequestHeader("token") String token){
		try {
			jwtUtil.validaJwt(userId,token,minutsToken);
			return new ResponseEntity<Object>(userService.get(),HttpStatus.OK);
		}catch (ExceptionNissum e) {
			return new ResponseEntity<Object>(new MessageResponse(e.getMessage()),e.getStatus());
		}
		
	}
	
	@ApiOperation(value = "This method is used to update user.")
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateUser(@RequestHeader("user_id")String userId, 
			@RequestHeader("token") String token, @RequestBody User user){
		try {
			jwtUtil.validaJwt(userId,token,minutsToken);
			return new ResponseEntity<Object>(userService.update(userId, user, token),HttpStatus.OK);
		}catch (ExceptionNissum e) {
			return new ResponseEntity<Object>(new MessageResponse(e.getMessage()),e.getStatus());
		}
		
	}
	
	@ApiOperation(value = "This method is used to delete users.")
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteUser(@RequestHeader("user_id")String userId, 
			@RequestHeader("token") String token){
		try {
			jwtUtil.validaJwt(userId,token,minutsToken);
			userService.delete(userId);
			return new ResponseEntity<Object>(new MessageResponse(MessageResponse.USER_DELETE),HttpStatus.OK);
		}catch (ExceptionNissum e) {
			return new ResponseEntity<Object>(new MessageResponse(e.getMessage()),e.getStatus());
		}
		
	}
}
