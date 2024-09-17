package com.codeWithAryan.BackendServiceBooking.controller;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithAryan.BackendServiceBooking.Services.authentication.AuthService;
import com.codeWithAryan.BackendServiceBooking.Services.jwt.UserDetailsServiceImpl;
import com.codeWithAryan.BackendServiceBooking.dto.AuthenticationRequest;
import com.codeWithAryan.BackendServiceBooking.dto.SignupReuestDTO;
import com.codeWithAryan.BackendServiceBooking.dto.UserDto;
import com.codeWithAryan.BackendServiceBooking.entity.Users;
import com.codeWithAryan.BackendServiceBooking.repositry.UserRepositry;
import com.codeWithAryan.BackendServiceBooking.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthService authService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserRepositry userRepositry;
	
	public static final String TOKEN_PREFIX="Bearer ";
	
	public static final String HEADER_STRING="Authorization";

	@PostMapping("/client/sign-up")
	public ResponseEntity<?> signupClient(@RequestBody SignupReuestDTO signupReuestDTO){
		if (authService.presentByEmail(signupReuestDTO.getEmail())) {
			return new ResponseEntity<>("CLient Already exist with the given username",HttpStatus.NOT_ACCEPTABLE);
		}
		
		UserDto createdUser =  authService.signupClient(signupReuestDTO);
		
		return new ResponseEntity<>(createdUser,HttpStatus.OK);
	}
	
	@PostMapping("/company/sign-up")
	public ResponseEntity<?> signupCompany(@RequestBody SignupReuestDTO signupReuestDTO){
		if (authService.presentByEmail(signupReuestDTO.getEmail())) {
			return new ResponseEntity<>("Company Already exist with the given username",HttpStatus.NOT_ACCEPTABLE);
		}
		
		UserDto createdUser =  authService.signupCompany(signupReuestDTO);
		
		return new ResponseEntity<>(createdUser,HttpStatus.OK);
	}
	
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
		 try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
					authenticationRequest.getPassword()
					));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect UserName or Password",e);
		}
		 
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt= jwtUtil.generateToken(userDetails.getUsername());
		Users user= userRepositry.findFirstByEmail(authenticationRequest.getUsername());
		
		response.getWriter().write(new JSONObject()
				.put("userId", user.getId())
				.put("role", user.getRole())
				.toString()
				);
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
		response.addHeader(HEADER_STRING,TOKEN_PREFIX + jwt);
	}
}
