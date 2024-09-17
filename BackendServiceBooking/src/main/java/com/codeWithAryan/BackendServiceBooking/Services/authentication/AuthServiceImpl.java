package com.codeWithAryan.BackendServiceBooking.Services.authentication;

import java.security.Signature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codeWithAryan.BackendServiceBooking.dto.SignupReuestDTO;
import com.codeWithAryan.BackendServiceBooking.dto.UserDto;
import com.codeWithAryan.BackendServiceBooking.entity.Users;
import com.codeWithAryan.BackendServiceBooking.enums.UserRole;
import com.codeWithAryan.BackendServiceBooking.repositry.UserRepositry;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UserRepositry userRepositry;
	
	public UserDto signupClient(SignupReuestDTO signupRequestDTO) {
		
		Users user= new Users();
		user.setName(signupRequestDTO.getName());
		user.setLastName(signupRequestDTO.getLastName());
		user.setEmail(signupRequestDTO.getEmail());
		user.setPhone(signupRequestDTO.getPhone());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
		user.setRole(UserRole.CLIENT);
		
		return userRepositry.save(user).getDTO();
	}
	
	public Boolean presentByEmail(String email) {
		return userRepositry.findFirstByEmail(email)!=null;
	}
	
	public UserDto signupCompany(SignupReuestDTO signupRequestDTO) {
		
		Users user= new Users();
		user.setName(signupRequestDTO.getName());
		user.setEmail(signupRequestDTO.getEmail());
		user.setPhone(signupRequestDTO.getPhone());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
		user.setRole(UserRole.COMPANY);
		
		return userRepositry.save(user).getDTO();
	}

	
}
