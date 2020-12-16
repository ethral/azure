package com.tefloncode.azure.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tefloncode.azure.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUserName(String userName);

}



