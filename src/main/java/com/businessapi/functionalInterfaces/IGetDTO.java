package com.businessapi.functionalInterfaces;

import com.businessapi.dto.UserDTO;
import com.businessapi.entity.User;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface IGetDTO {

    Optional<UserDTO> getUserDTO(List<User> resultlist);
}
