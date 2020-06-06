package com.businessapi.utils;

import com.businessapi.customException.AuthenticationException;
import com.businessapi.customException.MissingRequiredFieldException;
import com.businessapi.dto.UserDTO;
import com.businessapi.entity.Bond;
import com.businessapi.model.response.BondResponse;
import com.businessapi.model.response.ErrorMessages;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;


public class UserProfileUtils {

    private final int ITERATIONS = 10000;
    private final int KEY_LENGTH = 256;
    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPGRSTUVWSYZabcdefghijklmnopqrstuvwxyz";

    public void validateRequiredFields(UserDTO userDTO) throws MissingRequiredFieldException {
        if (StringUtils.isBlank(userDTO.getFirstname()) || StringUtils.isBlank(userDTO.getLastname())) {
            throw new MissingRequiredFieldException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
    }

    private String generateRandomStr(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    public UserDTO convertToResponse(UserDTO userDTO) {
        for (Bond bond : userDTO.getBondList()) {
            BondResponse bondResponse = new BondResponse();
            BeanUtils.copyProperties(bond, bondResponse);
            userDTO.getBondBondArrayResponseList().add(bondResponse);
        }
        return userDTO;
    }

    public String generateUserId(int length) {
        return generateRandomStr(length);
    }

    public String getSalt(int length) {
        return generateRandomStr(length);
    }

    public String generateSecurePassword(String password, String salt) {
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        String returnValue = Base64.getEncoder().encodeToString(securePassword);
        return returnValue;
    }

    public boolean doesPasswordMatch(String userPassword, UserDTO userDTO) {
        if (StringUtils.isNotBlank(userPassword)) {
            String securePassword = this.generateSecurePassword(userPassword, userDTO.getSalt());
            return StringUtils.isNotBlank(securePassword) && securePassword.equalsIgnoreCase(userDTO.getEncrytedPassword());
        } else {
            return Boolean.FALSE;
        }
    }

    public byte[] generateEncytedAccessToken(UserDTO userProfile) {
        try {
            String accessTokenMaterial = userProfile.getUserId() + userProfile.getSalt();
            return this.encrypt(userProfile.getEncrytedPassword(), accessTokenMaterial);
        } catch (InvalidKeySpecException ex) {
            throw new AuthenticationException("Failed to issue access token");
        }
    }

    public byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WIThHmacSHA1");
            return secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new AssertionError("Error while hashing password " + ex.getMessage(), ex);
        } catch (InvalidKeySpecException ex2) {
            throw new AssertionError("Error while hashing password " + ex2.getMessage(), ex2);
        } finally {
            spec.clearPassword();
        }
    }

    public void initilizeUserValues(UserDTO user) {
        user.setUserId(this.generateUserId(30));
        String salt = this.getSalt(30);
        String encyptedPassword = this.generateSecurePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setEncrytedPassword(encyptedPassword);
    }

    public byte[] encrypt(String securePassword, String accessTokenMat) throws InvalidKeySpecException {
        return hash(securePassword.toCharArray(), accessTokenMat.getBytes());
    }
}
