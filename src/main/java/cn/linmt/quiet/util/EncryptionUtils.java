package cn.linmt.quiet.util;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {

  private static final String ENCRYPTION_ALGORITHM = "AES";
  private static final String ENCRYPTION_TRANSFORMATION = "AES/GCM/NoPadding";
  private static final int GCM_TAG_LENGTH = 16;
  private static final int GCM_IV_LENGTH = 12;

  // 生成AES密钥
  public static SecretKey generateAESKey() throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
    keyGenerator.init(256); // 256位AES密钥
    return keyGenerator.generateKey();
  }

  // 将密钥以Base64编码保存
  public static String encodeKeyToString(SecretKey secretKey) {
    return Base64.getEncoder().encodeToString(secretKey.getEncoded());
  }

  // 从Base64解码恢复密钥
  public static SecretKey decodeKeyFromString(String keyStr) {
    byte[] decodedKey = Base64.getDecoder().decode(keyStr);
    return new SecretKeySpec(decodedKey, 0, decodedKey.length, ENCRYPTION_ALGORITHM);
  }

  // 加密
  public static String encrypt(String data, SecretKey secretKey, byte[] iv) throws Exception {
    Cipher cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION);
    GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
    byte[] encryptedData = cipher.doFinal(data.getBytes());
    return Base64.getEncoder().encodeToString(encryptedData);
  }

  // 解密
  public static String decrypt(String encryptedData, SecretKey secretKey, byte[] iv)
      throws Exception {
    Cipher cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION);
    GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
    cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
    byte[] decodedData = Base64.getDecoder().decode(encryptedData);
    byte[] decryptedData = cipher.doFinal(decodedData);
    return new String(decryptedData);
  }

  // 生成IV（初始化向量）
  public static byte[] generateIV() {
    byte[] iv = new byte[GCM_IV_LENGTH];
    SecureRandom random = new SecureRandom();
    random.nextBytes(iv);
    return iv;
  }
}
