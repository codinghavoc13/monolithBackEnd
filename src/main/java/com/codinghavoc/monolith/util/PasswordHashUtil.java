package com.codinghavoc.monolith.util;

import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHashUtil {
    public static String[] hashPWWPBKDF(String pwclear){
        String[] result = new String[2];
        try {
            int iterations = 1000;
            char[] chars = pwclear.toCharArray();
            byte[] salt = generateSalt();

            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64*8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            result[0] = toHex(salt);
            result[1] = toHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean validateWithPBKDF(String pwClear, String salt, String pwHash){
        int iterations = 1000;
        byte[] saltArr = fromHex(salt);
        byte[] hashArr = fromHex(pwHash);

        PBEKeySpec spec = new PBEKeySpec(pwClear.toCharArray(), saltArr, iterations, hashArr.length*8);
        SecretKeyFactory skf;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] temp = skf.generateSecret(spec).getEncoded();

            int diff = hashArr.length ^ temp.length;
            for(int i = 0; i < hashArr.length && i < temp.length; i++){
                diff |= hashArr[i]^temp[i];
            }
            return diff == 0;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String toHex(byte[] array){
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    private static byte[] fromHex(String hex){
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt;
    }
}
