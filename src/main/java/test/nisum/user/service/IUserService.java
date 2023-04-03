package test.nisum.user.service;

import java.util.List;

import test.nisum.user.models.exception.ExceptionNissum;
import test.nisum.user.models.jpa.User;

public interface IUserService {
	
	public User save (User user) throws ExceptionNissum;
	public List<User> get() throws ExceptionNissum;
	public User update(String userId, User user, String token) throws ExceptionNissum;
	public void delete(String userId) throws ExceptionNissum;
	public String getTokenUser(String userId) throws ExceptionNissum;

}
