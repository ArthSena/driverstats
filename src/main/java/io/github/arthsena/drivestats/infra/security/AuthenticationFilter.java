package io.github.arthsena.drivestats.infra.security;

import io.github.arthsena.drivestats.domain.providers.JwtTokenProvider;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject JwtTokenProvider tokenProvider;

    @Override
	public void filter(ContainerRequestContext context) {
	    String header = context.getHeaderString(HttpHeaders.AUTHORIZATION);
        String AUTH_HEADER_PREFIX = "Token ";
        if (header != null && header.startsWith(AUTH_HEADER_PREFIX)) {
	        String token = header.replace(AUTH_HEADER_PREFIX, "");
			context.setSecurityContext(new AppContext(tokenProvider.verify(token)));
		} else {
	    	context.setSecurityContext(new EmptySecurityContext());
	    }
	}
	
}