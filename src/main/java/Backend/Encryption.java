/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author sebastian
 */
public class Encryption {
    private String algorithm = "AES/CBC/PKCS5Padding";
    
    protected String passwordEncrypt(String masterUsername, String masterPassword, String fileContent) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        SecretKey key = produceKeyFromPass(masterPassword, masterUsername);
        IvParameterSpec iv = generateIv();
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.ENCRYPT_MODE, key, iv);
        return Base64.getEncoder().encodeToString(c.doFinal(fileContent.getBytes()));
    }
    
    protected String passwordDecrypt(String masterUsername, String masterPassword, String fileContent) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        SecretKey key = produceKeyFromPass(masterPassword, masterUsername);
        IvParameterSpec iv = generateIv();
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.DECRYPT_MODE, key, iv);
        return new String(c.doFinal(Base64.getDecoder().decode(fileContent)));
    }
    
    protected String hash(String content) throws NoSuchAlgorithmException, InvalidKeySpecException{
        PBEKeySpec spec = new PBEKeySpec(content.toCharArray(), "hashmypass".getBytes(), 10000, 256);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        Arrays.fill(content.toCharArray(), Character.MIN_VALUE);
        return new String(skf.generateSecret(spec).getEncoded());
    }
    
    /*
    IV: a pseudo-random value and has the same size as the block that is encrypted.
    */
    private IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
    
    private SecretKey produceKeyFromPass(String pass, String usr){
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(pass.toCharArray(), usr.getBytes(), 65536, 256);
            SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            return secretKey;
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("No such algorithm");
        } catch (InvalidKeySpecException ex) {
            System.out.println("Could not generate private key");
        }
        return null;
    }
    
    private static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }
}
