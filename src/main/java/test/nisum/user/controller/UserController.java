package test.nisum.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import test.nisum.user.configuration.FormattedLogger;
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
	/** Logger. */
    private final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    /** Formatter to set the log in a specific format and add the body as part
     * of the same log. */
    private final FormattedLogger logFormatter;
	

	public UserController(FormattedLogger logFormatter, IUserService userService, @Value("${token.minut}")String minutsToken, JwtUtil jwtUtil) {
		this.minutsToken =  Long.parseLong(minutsToken);
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.logFormatter = logFormatter;
	}


	@ApiOperation(value = "This method is used to create user.")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registrationUser(@RequestBody User user){
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("user", user);
			logFormatter.logInfo(logger, "registrationUser", "Init registration user", params);
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
			Map<String, Object> params = new HashMap<>();
			params.put("user_id", userId);
			params.put("token", token);
			logFormatter.logInfo(logger, "getUsers", "Init get users", params);
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
			Map<String, Object> params = new HashMap<>();
			params.put("user_id", userId);
			params.put("token", token);
			params.put("user", user);
			logFormatter.logInfo(logger, "updateUser", "Init update user", params);
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
			Map<String, Object> params = new HashMap<>();
			params.put("user_id", userId);
			params.put("token", token);
			logFormatter.logInfo(logger, "updateUser", "Init delete user", params);
			jwtUtil.validaJwt(userId,token,minutsToken);
			userService.delete(userId);
			logFormatter.logInfo(logger, "updateUser", "Finish delete user", params);
			return new ResponseEntity<Object>(new MessageResponse(MessageResponse.USER_DELETE),HttpStatus.OK);
		}catch (ExceptionNissum e) {
			return new ResponseEntity<Object>(new MessageResponse(e.getMessage()),e.getStatus());
		}
		
	}
}
