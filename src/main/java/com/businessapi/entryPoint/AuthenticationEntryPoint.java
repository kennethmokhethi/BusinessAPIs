package com.businessapi.entryPoint;

import com.clientwebapp.dto.UserDTO;
import com.clientwebapp.model.response.AuthenticationDetails;
import com.clientwebapp.model.request.LoginCredentials;

import com.clientwebapp.servicepackage.AuthenticationService;
import com.clientwebapp.servicepackage.serviceImpl.AuthenticationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/authentication")
@Api("/Authentication")
@SwaggerDefinition(tags={@Tag(name="Authentication",description="Rest endpoint for authentication")})
public class AuthenticationEntryPoint {

    @ApiOperation("Sign in")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public AuthenticationDetails userLoginDetails(LoginCredentials loginCredentials)
    {
        AuthenticationDetails returnValue = new AuthenticationDetails();
        AuthenticationService authenticationService = new AuthenticationServiceImpl();
        UserDTO authenticatedUser = authenticationService.authenticate(loginCredentials.getUsername(),loginCredentials.getUserpassword());
        authenticationService.resetSecuritiyCridentials(loginCredentials.getUserpassword(),authenticatedUser);
        String accessToken = authenticationService.issueAccessToken(authenticatedUser);
        returnValue.setId(authenticatedUser.getUserId());
        returnValue.setToken(accessToken);

      return returnValue;
    }

}
