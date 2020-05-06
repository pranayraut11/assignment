/*
 * package com.amdocs.media.assignement.authorization.handler;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.MediaType; import
 * org.springframework.stereotype.Component; import
 * org.springframework.web.reactive.function.server.ServerRequest; import
 * org.springframework.web.reactive.function.server.ServerResponse;
 * 
 * import com.amdocs.media.assignement.authorization.dto.ProfileDTO; import
 * com.amdocs.media.assignement.authorization.service.ProfileService;
 * 
 * import reactor.core.publisher.Mono;
 * 
 * @Component public class ProfileHandler {
 * 
 * @Autowired private ProfileService profileService;
 * 
 * public Mono<ServerResponse> deleteProfile(ServerRequest request) {
 * System.out.println("Inside Profile Handler : DELETE"); Mono<ProfileDTO>
 * profileMono = request.bodyToMono(ProfileDTO.class); return
 * profileMono.flatMap(profile -> ServerResponse.status(HttpStatus.CREATED)
 * .contentType(MediaType.APPLICATION_JSON).body(profileService.save(profile),
 * ProfileDTO.class));
 * 
 * }
 * 
 * }
 */