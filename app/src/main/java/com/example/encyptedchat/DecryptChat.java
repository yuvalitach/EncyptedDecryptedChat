package com.example.encyptedchat;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DecryptChat {
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue =
            new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
                    'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };

    public static List<String> decrypt(List<String> encryptedValues) {Log.d("decryptedValuesTAG", "encryptedValues: "+encryptedValues);
        List<String> decryptedValues = new ArrayList<>();
        try {
            SecretKeySpec key = new SecretKeySpec(keyValue, ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);

            for (String value : encryptedValues) {
                byte[] decodedValue = Base64.getDecoder().decode(value);
                Log.d("decryptedValuesTAG", "decodedValue: "+decodedValue);
                byte[] decValue = c.doFinal(decodedValue);
                decryptedValues.add(new String(decValue, StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedValues;
    }

    public static String encrypt(String value) {
        String encryptedValue = null;
        try {
            SecretKeySpec key = new SecretKeySpec(keyValue, ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);

            byte[] encValue = c.doFinal(value.getBytes());
            byte[] encodedValue = Base64.getEncoder().encode(encValue);
            encryptedValue = new String(encodedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedValue;
    }

}
