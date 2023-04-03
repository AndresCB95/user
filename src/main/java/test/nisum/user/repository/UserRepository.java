package test.nisum.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.nisum.user.models.jpa.User;

@Repository
public interface UserRepository  extends JpaRepository<User, String> {

	List<User> findByEmail(String email);
}
