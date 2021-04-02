package com.dineshlearnings.springsecurityjwt;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dineshlearnings.springsecurityjwt.model.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bcrytPasswordEncode;

	// Save the user and encode the password
	public User saveUser(User user) {
		user.setPassword(bcrytPasswordEncode.encode(user.getPassword()));
		User savedUser = userRepository.save(user);
		return savedUser;
	}

	public User findByUserName(String userName) {
		Optional<User> optionalUser = userRepository.findByUserName(userName);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("wrong username" + userName));
		return optionalUser.get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUserName(username);
		return new org.springframework.security.core.userdetails.User(
				username,
				user.getPassword(),
				user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList())
				);
	}
}
