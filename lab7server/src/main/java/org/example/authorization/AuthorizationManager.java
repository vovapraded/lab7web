package org.example.authorization;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.example.dao.UserDao;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

public class AuthorizationManager {
    private static UserDao userDao =new UserDao();
    public static ImmutablePair<Boolean,Boolean> checkLoginAndPassword(String login,String password){
        var loginCorrect = false;
        var passwordCorrect = false;

        var hashAndSalt=userDao.getPasswordAndSaltByLogin(login);
        if (hashAndSalt == null ) return new ImmutablePair<>(loginCorrect,passwordCorrect);
        loginCorrect = true;
        var hash = hashAndSalt.getLeft();
        var salt = hashAndSalt.getRight();
        BigInteger currentHash = null;
        try {
            currentHash = PasswordManager.getHash(password,salt);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {
        }
        if (currentHash.equals(hash)) passwordCorrect = true;
        return new ImmutablePair<>(loginCorrect,passwordCorrect);
    }
    public static void register(String login,String password)  {
        if (!loginIsUnique(login))
            throw new AuthorizationException("Пользователь с таким логином уже существует");
        saveNewPassword(login,password);
    }
    private static void saveNewPassword(String login,String password)  {
        ImmutablePair<BigInteger, String> hashAndSalt = null;
        try {
            hashAndSalt = PasswordManager.getHash(password);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {

        }
        var hash = hashAndSalt.getLeft();
        var salt = hashAndSalt.getRight();
        userDao.insertUser(login,hash,salt);
    }
    private static boolean loginIsUnique(String login) {
        var hashAndSalt=userDao.getPasswordAndSaltByLogin(login);
        if (hashAndSalt == null)
            return  true;
        else
            return false;
    }
}
