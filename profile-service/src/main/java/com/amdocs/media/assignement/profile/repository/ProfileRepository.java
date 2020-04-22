package com.amdocs.media.assignement.profile.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.media.assignement.profile.entity.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {

	Optional<Profile> findByUserId(Integer userId);
}
