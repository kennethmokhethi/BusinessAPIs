package com.businessapi.service.serviceImpl;

import com.businessapi.customException.AuthenticationException;
import com.businessapi.customException.CouldNotUpdateRecordException;
import com.businessapi.dao.MYSQLDAO;
import com.businessapi.dao.DAO;
import com.businessapi.dto.UserDTO;
import com.businessapi.model.response.ErrorMessages;
import com.businessapi.service.AuthenticationService;
import com.businessapi.utils.UserProfileUtils;
import org.hibernate.Transaction;

import java.util.Base64;
import java.util.Optional;

public class AuthenticationServiceImpl implements AuthenticationService {
    DAO database;

    public UserDTO authenticate(String userName, String password) throws AuthenticationException {
        Optional<UserDTO> storedUserOptional = new UserServiceImpl().getUserbyUsername(userName);

        if (storedUserOptional == null || !new UserProfileUtils().doesPasswordMatch(password, storedUserOptional.get())) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        } else {
            return storedUserOptional.get();
        }
    }

    public String issueAccessToken(UserDTO userProfile) throws AuthenticationException {
        byte[] encyptedAccessToken = new UserProfileUtils().generateEncytedAccessToken(userProfile);
        String encryptedAccesstokenBase64Encoded = Base64.getEncoder().encodeToString(encyptedAccessToken);
        String tokentoSaveToDB = encryptedAccesstokenBase64Encoded.substring(0, encryptedAccesstokenBase64Encoded.length() / 2);
        String returnValue = encryptedAccesstokenBase64Encoded.substring(encryptedAccesstokenBase64Encoded.length() / 2);
        userProfile.setToken(tokentoSaveToDB);
        updateUserProfile(userProfile);
        return returnValue;
    }


    public void resetSecuritiyCridentials(String password, UserDTO userProfile) {
        UserProfileUtils userProfileUtils = new UserProfileUtils();
        String salt = userProfileUtils.getSalt(30);
        userProfile.setSalt(salt);
        String securePassword = userProfileUtils.generateSecurePassword(password, salt);
        userProfile.setEncrytedPassword(securePassword);
        updateUserProfile(userProfile);
    }

    private void updateUserProfile(UserDTO userProfile) {
        this.database = new MYSQLDAO();
        Transaction transaction = this.database.getActiveTransaction();
        try {
            this.database.updateUser(userProfile);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new CouldNotUpdateRecordException(ex.getMessage());
        }
    }
}
