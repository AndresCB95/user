package test.nisum.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import test.nisum.user.configuration.FormattedLogger;
import test.nisum.user.controller.UserController;
import test.nisum.user.models.exception.ExceptionNissum;
import test.nisum.user.models.jpa.Phone;
import test.nisum.user.models.jpa.User;
import test.nisum.user.repository.UserRepository;
import test.nisum.user.utils.JwtUtil;

@Service
public class UserServiceImpl implements IUserService {
	
	private final UserRepository userRepository;
	private final Long minutToken;
	private final JwtUtil jwtUtil;
	private final String regexEmail;
	private final String regexPassword;
	/** Logger. */
    private final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    /** Formatter to set the log in a specific format and add the body as part
     * of the same log. */
    private final FormattedLogger logFormatter;

	public UserServiceImpl(UserRepository userRepository, @Value("${token.minut}") String minutToke, JwtUtil jwtUtil, FormattedLogger logFormatter, @Value("${regex.email}") String regexEmail,@Value("${regex.password}") String regexPassword) {
		this.minutToken = Long.parseLong(minutToke);
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
		this.jwtUtil.setUserService(this);
		this.regexEmail = regexEmail;
		this.logFormatter = logFormatter;
		this.regexPassword = regexPassword;
	}

	@Override
	public User save(User user) throws ExceptionNissum {
		validateUserEmail(user);
		validateFieldRegex(user.getEmail(),regexEmail);
		validateFieldRegex(user.getPassword(),regexPassword);
		Map<String, Object> params = new HashMap<>();
		try {
			String uuid = UUID.randomUUID().toString();
			Date now = new Date();
			setFieldUser(user,jwtUtil.generateJwt(user, minutToken),true,uuid,now,now,now);
			params.put("user", user);
			logFormatter.logInfo(logger, "init save", "Save User", params);
			userRepository.save(user);
			logFormatter.logInfo(logger, "finish save", "Save User", params);
		}catch (Exception e) {
			logFormatter.logError(logger, "save", "Error save User", params, e);
			throw new ExceptionNissum(ExceptionNissum.MESSAGE_ERROR);
		}
		return user;
	}
	
	private void validateFieldRegex(String value, String regex) throws ExceptionNissum {
		Map<String, Object> params = new HashMap<>();
		params.put("regex", regex);
		params.put("value", value);
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		if(!matcher.matches()) {
			ExceptionNissum e = new ExceptionNissum(ExceptionNissum.CAMPO_NO_VALIDO, HttpStatus.BAD_REQUEST);
			logFormatter.logError(logger, "save", "Error save User", params, e);
			throw e; 
		}
	}
	
	private void validateUserEmail(User user) throws ExceptionNissum{
		Map<String, Object> params = new HashMap<>();
		params.put("user", user);
		if(getUser(user.getEmail()).size()> 0) {
			ExceptionNissum e = new ExceptionNissum(ExceptionNissum.USER_DUPLICATE, HttpStatus.CONFLICT);
			logFormatter.logError(logger, "save", "Error save User", params, e);
			throw e;
		}
	}

	private void setFieldUser(User user,String token,  boolean active, String userId, Date create, Date modified, Date lastLogin) {
		user.setCreate(create);
		user.setModified(modified);
		user.setLastLogin(lastLogin);
		user.setActive(active);
		user.setUserId(userId);
		user.setToken(token);
		setUserToPhone(user);
	}
	
	private void setUserToPhone(User user) {
		Map<String, Object> params = new HashMap<>();
		params.put("user", user);
		params.put("phones", user.getPhones());
		logFormatter.logInfo(logger, "setUserToPhone", "setting user in phone", params);
		for (Phone phone : user.getPhones()) {
			phone.setUser(user);
		}
	}
	
	@Override
	public List<User> get() throws ExceptionNissum{
		Map<String, Object> params = new HashMap<>();
	logFormatter.logInfo(logger, "get", "get users", params);
	
		List<User> users= userRepository.findAll();
		if( users.size() > 0) { 
			return users; 
		}else { 
			throw new ExceptionNissum(ExceptionNissum.USER_NO, HttpStatus.NOT_FOUND);
		}
	}
	
	public List<User> getUser(String email) {
		Map<String, Object> params = new HashMap<>();
		params.put("emai", email);
		logFormatter.logInfo(logger, "getUser", "get user by email", params);
		return  userRepository.findByEmail(email);	 
	}
	

	@Override
	public User update(String userId, User user, String token) throws ExceptionNissum {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("user", user);
		logFormatter.logInfo(logger, "update", "update user", params);
		token = jwtUtil.renovateToken(userId,token,minutToken);
		List<User> users= getUser(user.getEmail());
		if(users.size() == 0) {
			ExceptionNissum e = new ExceptionNissum(ExceptionNissum.USER_NO_EXISTE,HttpStatus.NO_CONTENT);
			logFormatter.logError(logger, "update", "error user no exist", params, e);
			throw e;
		}
		User userDb = users.get(0);
		Date now = new Date();
		setFieldUser(user,token, user.isActive(),userId,userDb.getCreate(),now,userDb.getLastLogin());
		userRepository.save(user);
		return user;
	}
	
	@Override
	public void delete(String userId) throws ExceptionNissum {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		logFormatter.logInfo(logger, "delete", "delete user", params);
		getUSer(userId);
		userRepository.deleteById(userId);
	}

	private User getUSer(String userId) throws ExceptionNissum {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		logFormatter.logInfo(logger, "getUSer", "get user", params);
		Optional<User> user= userRepository.findById(userId);
		if(user.isEmpty()) {
			ExceptionNissum e = new ExceptionNissum(ExceptionNissum.USER_NO_EXISTE,HttpStatus.NO_CONTENT);
			logFormatter.logError(logger, "getUSer", "Error user no exist", params, e);
			throw e;
		}
		return user.get();
	}

	@Override
	public String getTokenUser(String userId) throws ExceptionNissum {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		logFormatter.logInfo(logger, "getTokenUser", "get token user", params);
		User user = getUSer(userId);
		return user.getToken()!=null? user.getToken():"";
	}
	

}
