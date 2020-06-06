package com.businessapi.service.serviceImpl;

import com.businessapi.customException.CouldNotCreateRecordException;
import com.businessapi.customException.CouldNotDeleteRecordException;
import com.businessapi.customException.CouldNotUpdateRecordException;
import com.businessapi.customException.NoRecordFoundException;
import com.businessapi.dao.MYSQLDAO;
import com.businessapi.dao.DAO;
import com.businessapi.dto.UserDTO;
import com.businessapi.entity.User;
import com.businessapi.functionalInterfaces.IGetDTO;
import com.businessapi.functionalInterfaces.IGetUserByUserName;
import com.businessapi.model.response.ErrorMessages;
import com.businessapi.service.UserService;
import com.businessapi.utils.UserProfileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;


public class UserServiceImpl implements UserService {

    private DAO database;

    public UserServiceImpl() {
        database = new MYSQLDAO();
    }

    public UserDTO createUser(UserDTO user) {
        UserProfileUtils userProfileUtils = new UserProfileUtils();
        userProfileUtils.validateRequiredFields(user);
        Optional<UserDTO> existinguser = this.IGetUserByUserName2.getUserbyUsername(user.getEmail());

        if (existinguser.isPresent()) {
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXIST.name());
        } else {
            userProfileUtils.initilizeUserValues(user);
            Transaction transaction = database.getActiveTransaction();
            try {
                UserDTO returnValue = database.saveUser(user);
                transaction.commit();
                return returnValue;
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                return null;
            }
        }
    }


    public UserDTO convertToResponse(UserDTO userDTO) {
        return new UserProfileUtils().convertToResponse(userDTO);
    }

    public UserDTO getUser(String id) {
        Transaction transaction = database.getActiveTransaction();
        try {
            return this.database.getUser(id);

        } catch (Exception ex) {
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND_EXCEPTION.getErrorMessage());
        } finally {
            transaction.commit();
        }
    }

    IGetUserByUserName IGetUserByUserName2 = username -> {
        if (StringUtils.isNotBlank(username)) {
            return this.searchUser.getUserbyUsername(username);
        } else {
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND_EXCEPTION.getErrorMessage());
        }
    };


    IGetUserByUserName searchUser = username -> {
        Transaction transaction = database.getActiveTransaction();
        try {
            return this.getUserbyUsername(username);

        } catch (Exception ex) {
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND_EXCEPTION.getErrorMessage());
        } finally {
            transaction.commit();
        }

    };

    public Optional<UserDTO> getUserbyUsername(String username)  {
        Query<User> query = database.getSession().createQuery(database.getUserCriteriaQuery(username, "email"));
        Optional<List<User>> resultlist = Optional.ofNullable(query.getResultList());
        if (resultlist.isPresent() && !resultlist.get().isEmpty()) {
            return this.IGetDTOImp.getUserDTO(resultlist.get());
        } else {
            return Optional.empty();
        }
    }

    IGetDTO IGetDTOImp = resultlist -> {
        Optional<UserDTO> userDTO = Optional.ofNullable(new UserDTO());
        User user = resultlist.get(0);
        BeanUtils.copyProperties(user, userDTO.get());
        userDTO.get().setU_Id(user.getUId());
        user.getBondList().stream().forEach(userDTO.get()::addBond);

        return userDTO;
    };


    public List<UserDTO> getUsers(int start, int limit) {
        Transaction transaction = database.getActiveTransaction();
        try {
            return this.database.getUsers(start, limit);
        } catch (Exception ex) {
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND_EXCEPTION.getErrorMessage());
        } finally {
            transaction.commit();
        }
    }


    public void updateUserDetails(UserDTO userDetails) {
        Transaction transaction = this.database.getActiveTransaction();
        try {
            this.database.updateUser(userDetails);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new CouldNotUpdateRecordException(ex.getMessage());
        }
    }

    public void deleteUser(UserDTO userDetails) {
        Transaction transaction = this.database.getActiveTransaction();
        try {
            this.database.deleteUser(userDetails);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new CouldNotDeleteRecordException(ex.getMessage());
        }
    }
}
