package br.ifsul.finwise.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Serviço responsável por criptografar e descriptografar dados sensíveis
 * usando AES 256 bits (CBC + PKCS5Padding) com chave definida no application.properties.
 */
@Service
public class EncryptionService {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_SIZE = 16; // 128 bits
    private static SecretKey secretKey;

    @Value("${aes.key}")
    private String aesKeyBase64;

    @PostConstruct
    public void init() {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(aesKeyBase64);
            EncryptionService.secretKey = new SecretKeySpec(decodedKey, "AES");
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao inicializar a chave AES", e);
        }
    }

    /**
     * Criptografa um texto simples usando AES/CBC.
     *
     * @param plainText texto original
     * @return texto criptografado em Base64 (incluindo o IV)
     */
    public static String encrypt(String plainText) {
        try {
            byte[] iv = generateIV();
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            // Concatena IV + dados criptografados
            byte[] combined = new byte[IV_SIZE + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, IV_SIZE);
            System.arraycopy(encrypted, 0, combined, IV_SIZE, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new EncryptionException("Erro ao criptografar o texto", e);
        }
    }

    /**
     * Descriptografa um texto criptografado pelo método encrypt().
     *
     * @param encryptedBase64 texto em Base64 (IV + dados)
     * @return texto original (plaintext)
     */
    public static String decrypt(String encryptedBase64) {
        try {
            byte[] combined = Base64.getDecoder().decode(encryptedBase64);

            // Separa IV e dados
            byte[] iv = new byte[IV_SIZE];
            System.arraycopy(combined, 0, iv, 0, IV_SIZE);

            byte[] encrypted = new byte[combined.length - IV_SIZE];
            System.arraycopy(combined, IV_SIZE, encrypted, 0, encrypted.length);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new EncryptionException("Erro ao descriptografar o texto", e);
        }
    }

    private static byte[] generateIV() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    /**
     * Exceção personalizada para erros de criptografia.
     */
    public static class EncryptionException extends RuntimeException {
        public EncryptionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
