package com.cybrilla.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cybrilla.domain.User;

public interface UserDao extends CrudRepository<User, Long> {

	User findByUserName(String userName);
	User findByEmail(String email);
	List<User> findAll();
}
