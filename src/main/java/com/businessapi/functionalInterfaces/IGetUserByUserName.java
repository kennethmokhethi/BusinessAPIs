package com.businessapi.functionalInterfaces;

import com.businessapi.dto.UserDTO;

import java.util.Optional;

@FunctionalInterface
public interface IGetUserByUserName {

    Optional<UserDTO> getUserbyUsername(String username);
}
