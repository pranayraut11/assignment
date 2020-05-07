package com.amdocs.media.assignement.authorization.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import com.amdocs.media.assignement.authorization.entity.User;
import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.amdocs.media.assignement.authorization.repository.UserRepository;
import com.amdocs.media.assignement.authorization.service.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private UserRepository userRepository;

	@Test
	public void loadUserByUsernameTest() {

		User user = new User();
		user.setId(1);
		user.setUsername("demo");
		user.setPassword("123");

		when(userRepository.findByUsername("demo")).thenReturn(Optional.of(user));

		UserDetails userDetaisl = userServiceImpl.loadUserByUsername("demo");

		assertEquals(user.getPassword(), userDetaisl.getPassword());

	}

	@Test(expected = NotFoundException.class)
	public void loadUserUserNotFoundExceptionTest() {
		userServiceImpl.loadUserByUsername("demo");
	}

}
