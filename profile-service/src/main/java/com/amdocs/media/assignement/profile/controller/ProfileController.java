/*
 * package com.amdocs.media.assignement.profile.controller;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.amdocs.media.assignement.profile.entity.Profile; import
 * com.amdocs.media.assignement.profile.service.ProfileService;
 * 
 * @RestController
 * 
 * @RequestMapping("profile") public class ProfileController {
 * 
 * @Autowired private ProfileService profileService;
 * 
 * @PostMapping public ResponseEntity<Void> createProfile(@RequestBody Profile
 * profile) { profileService.save(profile); return
 * ResponseEntity.status(HttpStatus.CREATED).build(); }
 * 
 * }
 */