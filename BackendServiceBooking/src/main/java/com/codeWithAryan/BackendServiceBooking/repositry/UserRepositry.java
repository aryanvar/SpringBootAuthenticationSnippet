package com.codeWithAryan.BackendServiceBooking.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeWithAryan.BackendServiceBooking.entity.Users;

@Repository
public interface UserRepositry extends JpaRepository<Users, Long> {
	
	Users findFirstByEmail(String email);
}
