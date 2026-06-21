/**
 * RSA加密和解密
 */
package com.ai.gis.util;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {
    // 生成RSA密钥对
    public static Key generateKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // 密钥大小为2048位
        return keyPairGenerator.generateKeyPair().getPublic();
    }
    //解析公钥
    private static PublicKey parsePublicKey(String pem) throws Exception {
        String publicKeyPEM = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", ""); // 去除所有空白字符

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    //加密
    public static String encrypt(String plainText, Key publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    //解密
    public static String decrypt(String encryptedText, Key privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    //加密
    public static String encrypt(String plainText, String publicKeyStr) throws Exception {
        // 将Base64编码的公钥字符串转换为PublicKey对象
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);

        // 使用公钥加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    //解密
    public static String decrypt(String encryptedText, String privateKeyStr) throws Exception {
        // 将Base64编码的私钥字符串转换为PrivateKey对象
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        // 使用私钥解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}
