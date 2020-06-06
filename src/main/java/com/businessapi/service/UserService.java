package com.businessapi.service;

import com.businessapi.dto.UserDTO;
import java.util.List;


public interface UserService {
    UserDTO convertToResponse(UserDTO userDTO);

    UserDTO createUser(UserDTO user);

    UserDTO getUser(String id);

    List<UserDTO> getUsers(int start, int limit);

    void updateUserDetails(UserDTO userDetails);

    void deleteUser(UserDTO userDTO);

}
