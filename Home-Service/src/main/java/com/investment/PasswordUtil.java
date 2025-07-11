package com.investment;

import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Component;
@Component
public class PasswordUtil {
	private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
public static String generatePassword(int length) {
	SecureRandom random = new SecureRandom();
    StringBuilder password = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
        int index = random.nextInt(ALL_CHARACTERS.length());
        password.append(ALL_CHARACTERS.charAt(index));
    }

    return password.toString();
}

}
