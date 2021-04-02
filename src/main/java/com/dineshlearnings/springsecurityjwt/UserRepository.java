package com.dineshlearnings.springsecurityjwt;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dineshlearnings.springsecurityjwt.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUserName(String username);
}
