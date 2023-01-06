package com.mall.choisinsa.util;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.common.secret.ConstData;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class EncryptionUtil {

    public static String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(ConstData.ALG);
            SecretKeySpec keySpec = new SecretKeySpec(ConstData.IV.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(ConstData.IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (NoSuchAlgorithmException
                 | NoSuchPaddingException
                 | InvalidKeyException
                 | InvalidAlgorithmParameterException
                 | IllegalBlockSizeException
                 | UnsupportedEncodingException
                 | BadPaddingException e) {

            log.error("ERROR: ", e);
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
    }

    public class DecryptionUtil {
        public static String decrypt(String cipherText) {
            try {
                Cipher cipher = Cipher.getInstance(ConstData.ALG);
                SecretKeySpec keySpec = new SecretKeySpec(ConstData.IV.getBytes(), "AES");
                IvParameterSpec ivParamSpec = new IvParameterSpec(ConstData.IV.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

                byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
                byte[] decrypted = cipher.doFinal(decodedBytes);
                return new String(decrypted, "UTF-8");
            } catch (NoSuchPaddingException
                | IllegalBlockSizeException
                | NoSuchAlgorithmException
                | BadPaddingException
                | InvalidKeyException
                | InvalidAlgorithmParameterException
                | UnsupportedEncodingException e) {

                log.error("ERROR: ", e);
                throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
            }
        }
    }

}
