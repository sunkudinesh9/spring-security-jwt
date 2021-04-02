package com.dineshlearnings.springsecurityjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dineshlearnings.springsecurityjwt.model.User;
import com.dineshlearnings.springsecurityjwt.model.UserReposnse;
import com.dineshlearnings.springsecurityjwt.model.UserRequest;
import com.dineshlearnings.springsecurityjwt.utils.JwtUtils;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	// 1. Save user date in database
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User userData = userService.saveUser(user);
		return new ResponseEntity<User>(userData, HttpStatus.CREATED);
	}

	// 2. validate user and generate token
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<UserReposnse> login(@RequestBody UserRequest userRequest) {
		// Validate userName/password
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequest.getUserName(), userRequest.getPassword()));

		// generate token
		String token = jwtUtils.generateToken(userRequest.getUserName());
		return new ResponseEntity<UserReposnse>(new UserReposnse(token, "Success!!"), HttpStatus.CREATED);
	}

	// 3. Welcome page after login
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ResponseEntity<String> welcomePage() {
		return new ResponseEntity<String>("Welcome to JWT", HttpStatus.OK);
	}
}
