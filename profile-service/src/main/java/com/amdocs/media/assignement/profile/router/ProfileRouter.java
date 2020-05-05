package com.amdocs.media.assignement.profile.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.amdocs.media.assignement.profile.handler.ProfileHandler;

@Configuration
public class ProfileRouter {

	@Bean
	public RouterFunction<ServerResponse> routeProfile(ProfileHandler profileHandler) {
		return route(POST("/profile").and(accept(MediaType.APPLICATION_JSON)), profileHandler::saveProfile);
	}
}
