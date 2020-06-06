package com.businessapi.service;

import com.businessapi.customException.AuthenticationException;
import com.businessapi.dto.UserDTO;

public interface AuthenticationService {
    UserDTO authenticate(String userName, String password) throws AuthenticationException;

    String issueAccessToken(UserDTO userProfile) throws AuthenticationException;

    void resetSecuritiyCridentials(String password, UserDTO userrProfile);
}
