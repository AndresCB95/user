package test.nisum.user;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import test.nisum.user.models.exception.ExceptionNissum;
import test.nisum.user.models.jpa.Phone;
import test.nisum.user.models.jpa.User;
import test.nisum.user.repository.UserRepository;
import test.nisum.user.service.IUserService;


@SpringBootTest
class UserApplicationTests {
	
	@Autowired IUserService userService;
	@Autowired UserRepository userRepository;

	@Test
	void saverUser() throws ExceptionNissum {
		
		User user = new User();
		user.setName("Juan Rodriguez");
	    user.setEmail("juan@rodriguez.org");
	    user.setPassword("hunter52");
	    
	    Phone phone = new Phone();
	    phone.setNumber("1234567");
        phone.setCityCode("1");
        phone.setContryCode("57");
        
        user.setPhones(Lists.list(phone));
        	
		User useReturn = userService.save(user);
		Date now = new Date();
		
		Optional<User> userDbOpt = userRepository.findById(useReturn.getUserId());
		assertTrue(userDbOpt.isPresent());
		User userDb = userDbOpt.get();
		assertEquals(userDb.getEmail(),user.getEmail());
		assertTrue(userDb.isActive());
		assertTrue(now.after(userDb.getCreate()));
		assertTrue(now.after(userDb.getLastLogin()));
		assertTrue(now.after(userDb.getModified()));
		
	}
	
	@Test
	void saverUserIncorrectEmail(){
		
		User user = new User();
		user.setName("Juan Rodriguez");
	    user.setEmail("juanrodriguez.org");
	    user.setPassword("hunter52");
	    
	    Phone phone = new Phone();
	    phone.setNumber("1234567");
        phone.setCityCode("1");
        phone.setContryCode("57");
        
        user.setPhones(Lists.list(phone));
        	
 
        Throwable exception = assertThrows(ExceptionNissum.class,() -> userService.save(user)) ;
		assertEquals(exception.getMessage(), ExceptionNissum.CAMPO_NO_VALIDO);
		
	}
	
	@Test
	void saverUserEmailExits(){
		
		User user = new User();
		user.setName("Juan Rodriguez");
	    user.setEmail("juan@rodriguez.org");
	    user.setPassword("hunter52");
	    
	    Phone phone = new Phone();
	    phone.setNumber("1234567");
        phone.setCityCode("1");
        phone.setContryCode("57");
        
        user.setPhones(Lists.list(phone));
        	
 
        Throwable exception = assertThrows(ExceptionNissum.class,() -> userService.save(user)) ;
		assertEquals(exception.getMessage(), ExceptionNissum.USER_DUPLICATE);
		
	}
	
	@Test
	void saverUserIncorrectPasswor(){
		
		User user = new User();
		user.setName("Juan Rodriguez");
	    user.setEmail("juan1@rodriguez.org");
	    user.setPassword("hunter");
	    
	    Phone phone = new Phone();
	    phone.setNumber("1234567");
        phone.setCityCode("1");
        phone.setContryCode("57");
        
        user.setPhones(Lists.list(phone));
        	
 
        Throwable exception = assertThrows(ExceptionNissum.class,() -> userService.save(user)) ;
		assertEquals(exception.getMessage(), ExceptionNissum.CAMPO_NO_VALIDO);
		
	}

}
