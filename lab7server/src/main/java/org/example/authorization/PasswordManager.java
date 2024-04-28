package org.example.authorization;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordManager {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 20;
    public static String getHash(String password,String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        var hash = new String(md.digest(
                (password  + salt).getBytes("UTF-8")));
        return hash;
    }
    public static ImmutablePair<String,String> getHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        var salt = getRandomString(LENGTH);
        var hash = new String(md.digest(
                (password  + salt).getBytes("UTF-8")));
        return new ImmutablePair<>(hash,salt);
    }

        private static String getRandomString(int length) {
            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int randomIndex = random.nextInt(CHARACTERS.length());
                char randomChar = CHARACTERS.charAt(randomIndex);
                sb.append(randomChar);
            }
            return sb.toString();
        }

}
