package br.ifsul.finwise.service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionService {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding"; // Modo CBC com padding
    private static final int KEY_SIZE = 256; // Tamanho da chave de 256 bits
    private static final int IV_SIZE = 16; // Tamanho do IV (Initialization Vector)

    // Método para gerar uma chave AES
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES"); // Usar apenas "AES" para gerar chave
        keyGenerator.init(KEY_SIZE);
        return keyGenerator.generateKey();
    }
    
    // Método para gerar um IV (Initialization Vector) aleatório
    public static byte[] generateIV() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    // Método para criptografar dados com IV aleatório
    public static String encrypt(String data, SecretKey key) throws Exception {
        // Gerar IV aleatório para cada criptografia
        byte[] iv = generateIV();
        
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        
        byte[] encryptedData = cipher.doFinal(data.getBytes("UTF-8"));
        
        // Combinar IV + dados criptografados
        byte[] combined = new byte[IV_SIZE + encryptedData.length];
        System.arraycopy(iv, 0, combined, 0, IV_SIZE);
        System.arraycopy(encryptedData, 0, combined, IV_SIZE, encryptedData.length);
        
        return Base64.getEncoder().encodeToString(combined);
    }

    // Método para descriptografar dados com IV
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedData);
        
        // Extrair IV dos primeiros 16 bytes
        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(combined, 0, iv, 0, IV_SIZE);
        
        // Extrair dados criptografados
        byte[] encrypted = new byte[combined.length - IV_SIZE];
        System.arraycopy(combined, IV_SIZE, encrypted, 0, encrypted.length);
        
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        
        byte[] decryptedData = cipher.doFinal(encrypted);
        return new String(decryptedData, "UTF-8");
    }

    // Converter String para SecretKey
    public static SecretKey getKeyFromString(String key) {
        return new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
    }
}


