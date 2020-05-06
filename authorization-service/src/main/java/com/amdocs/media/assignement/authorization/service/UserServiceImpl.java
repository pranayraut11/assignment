package com.amdocs.media.assignement.authorization.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.amdocs.media.assignement.authorization.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<com.amdocs.media.assignement.authorization.entity.User> userOpt = userRepository
				.findByUsername(username);
		com.amdocs.media.assignement.authorization.entity.User user = userOpt
				.orElseThrow(() -> new NotFoundException("User"));
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

}