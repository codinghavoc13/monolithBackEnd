package com.codinghavoc.monolith.schoolmanager.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class PasswordHashUtil {
    public static String[] hashPW(String pwClear) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        String[] result = new String[2];
        byte[] salt = generateSalt();
        result[0] = convertByteArrToString(salt);
        md.update(generateSalt());
        byte[] byteArr = md.digest(pwClear.getBytes(StandardCharsets.UTF_8));
        result[1] = convertByteArrToString(byteArr);
        return result;
    }

    public static String convertByteArrToString(byte[] arr){
        StringBuilder sb = new StringBuilder();
        for(byte b : arr){
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }

    public static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static boolean validateLogin(String pwClear, String salt, String pwHash) throws NoSuchAlgorithmException{
        System.out.println("A");
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes());
        // byte[] byteArr = md.digest(pwClear.getBytes(StandardCharsets.UTF_8));
        // String temp = convertByteArrToString(byteArr);
        String temp = convertByteArrToString(md.digest(pwClear.getBytes(StandardCharsets.UTF_8)));
        System.out.println("temp: "+ temp);
        System.out.println("pwHash: "+ pwHash);
        return pwHash.equals(temp);
    }
}
