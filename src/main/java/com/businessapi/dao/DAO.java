package com.businessapi.dao;

import com.businessapi.dto.UserDTO;
import com.businessapi.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface DAO {
    Transaction getActiveTransaction();

    Session getSession();

    CriteriaQuery<User> getUserCriteriaQuery(String username, String email);


    UserDTO saveUser(UserDTO userDTO);

    UserDTO getUser(String id);

    void updateUser(UserDTO userProfile);

    List<UserDTO> getUsers(int start, int limit);

    void deleteUser(UserDTO userDetail);
}
