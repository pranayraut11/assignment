package com.amdocs.media.assignement.authorization.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.media.assignement.authorization.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
