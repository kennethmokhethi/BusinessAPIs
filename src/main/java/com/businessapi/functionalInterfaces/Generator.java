package com.businessapi.functionalInterfaces;

import java.util.Optional;

@FunctionalInterface
public interface Generator {

    Optional<byte[]> generateEncryptedPassword(String securePassword, String accessTokenMaterial);
}