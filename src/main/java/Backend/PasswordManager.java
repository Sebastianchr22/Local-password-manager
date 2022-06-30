/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class PasswordManager {
    private String loginFile = ".login.pwd";
    private String username = "myUser";
    private String password = "myPass";
    
   /* public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        PasswordManager pm = new PasswordManager();
        Encryption enc = new Encryption();
        UserHandler user = new UserHandler(pm.loginFile);
        
        String passwordHash = enc.hash(pm.password);
        try {
            if(user.login(pm.username, enc.hash(pm.password))){
                System.out.println("Logged in successfully!");
                Passwords pwds = new Passwords();
                pm.openApp(enc, pwds);
                
                //System.out.println("------ Writing ------");
                //pm.createNewSiteKey("Youtube.com", pm.generatePassword(16, false, false), "youtubeMan");
                //pm.createNewSiteKey("Microsoft.com", pm.generatePassword(28, true, false), "AnotherUser");
                //pm.createNewSiteKey("Google.Drive.com", pm.generatePassword(64, true, true), "googleBoy");
                
                
                pm.closeApp(enc, pwds);
            }
            else System.out.println("Login attempt failed");
            //pm.createUser(pm.username, passwordHash);
        } catch (IOException ex) {
            System.out.println("No user registered");
        }
    }
    
    private void openApp(Encryption enc, Passwords pwds) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        try {
            FileReader fr = new FileReader(pwds.getPasswordsFile());
            File passwords = new File(pwds.getPasswordsFile());
            String encryptedPasswordStore = Files.readString(passwords.toPath());
            if(encryptedPasswordStore.split("\n").length > 1){
                pwds.createSiteKeysFromFileContent(enc.passwordDecrypt(username, password, encryptedPasswordStore.split("\n")[1].toString()));
            }else{
                pwds.createSiteKeysFromFileContent(enc.passwordDecrypt(username, password, encryptedPasswordStore));
            }
            
        } catch (FileNotFoundException ex) {
            pwds.createPasswordFile();
        } catch (IOException ex) {
            System.out.println("Could not fetch password file");
        }
    }
    
    private void closeApp(Encryption enc, Passwords pwds) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        String passwordStoreContents = pwds.createFileContentFromSiteKeys();
        String encryptedKeyStore = enc.passwordEncrypt(username, password, "Password Manager Storage:\n" + passwordStoreContents);
        pwds.saveEncryptedKeyStore(encryptedKeyStore);
    }
*/
}
