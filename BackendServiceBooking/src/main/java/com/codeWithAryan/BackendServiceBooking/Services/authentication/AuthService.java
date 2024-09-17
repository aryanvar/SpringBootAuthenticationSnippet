package com.codeWithAryan.BackendServiceBooking.Services.authentication;

import com.codeWithAryan.BackendServiceBooking.dto.SignupReuestDTO;
import com.codeWithAryan.BackendServiceBooking.dto.UserDto;

public interface AuthService {
	
	UserDto signupClient(SignupReuestDTO signupRequestDTO);
	Boolean presentByEmail(String email);
	UserDto signupCompany(SignupReuestDTO signupRequestDTO);
}
