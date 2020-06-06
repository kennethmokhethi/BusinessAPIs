package com.businessapi.filters;

import com.businessapi.annotations.Secured;
import com.businessapi.dto.UserDTO;
import com.businessapi.functionalInterfaces.Generator;
import com.businessapi.service.serviceImpl.UserServiceImpl;
import com.businessapi.utils.UserProfileUtils;

import javax.annotation.Priority;
import javax.security.sasl.AuthenticationException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Optional;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    Generator generator = (String securePas, String accessToke) -> {
        try {
            return Optional.ofNullable(new UserProfileUtils().encrypt(securePas, accessToke));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    };

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            throw new AuthenticationException("Authorization header must be provided");
        }
        String token = authorizationHeader.substring("Bearer".length()).trim();
        String userid = containerRequestContext.getUriInfo().getPathParameters().getFirst("id");
        validateToken(token, userid);
    }

    private void validateToken(String token, String userId) throws AuthenticationException {
        UserDTO userProfile = new UserServiceImpl().getUser(userId);
        String completeToken = userProfile.getToken() + token;
        String securePassword = userProfile.getEncrytedPassword();
        String accesstokenMaterial = userId + userProfile.getSalt();

        Optional<byte[]> encryptedPassword = this.generator.generateEncryptedPassword(securePassword, accesstokenMaterial);

        if (encryptedPassword.isPresent()) {
            String encryptyedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedPassword.get());
            if (!encryptyedAccessTokenBase64Encoded.equalsIgnoreCase(completeToken)) {
                throw new AuthenticationException("Authorization token did not match");
            }
        } else {
            throw new AuthenticationException("Authorization token did not match");
        }
    }
}
