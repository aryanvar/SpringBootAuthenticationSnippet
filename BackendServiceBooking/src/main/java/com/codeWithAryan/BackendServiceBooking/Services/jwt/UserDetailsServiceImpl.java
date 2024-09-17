package com.codeWithAryan.BackendServiceBooking.Services.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codeWithAryan.BackendServiceBooking.entity.Users;
import com.codeWithAryan.BackendServiceBooking.repositry.UserRepositry;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepositry userRepositry;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users users= userRepositry.findFirstByEmail(email);
		if (users ==null) throw new UsernameNotFoundException("Username not Found",null);
		return new User(users.getEmail(),users.getPassword(),new ArrayList<>());
	}

}
