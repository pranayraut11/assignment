/*
 * package com.amdocs.media.assignement.authorization.router;
 * 
 * import static
 * org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
 * import static
 * org.springframework.web.reactive.function.server.RequestPredicates.GET;
 * import static
 * org.springframework.web.reactive.function.server.RequestPredicates.accept;
 * import static
 * org.springframework.web.reactive.function.server.RouterFunctions.route;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.http.MediaType; import
 * org.springframework.web.reactive.function.server.RouterFunction; import
 * org.springframework.web.reactive.function.server.ServerResponse;
 * 
 * import com.amdocs.media.assignement.authorization.handler.ProfileHandler;
 * 
 * @Configuration public class ProfileRouter {
 * 
 * @Bean public RouterFunction<ServerResponse> routeProfile(ProfileHandler
 * profileHandler) { return
 * route(GET("/profile").and(accept(MediaType.APPLICATION_JSON)),
 * profileHandler::deleteProfile); } }
 */