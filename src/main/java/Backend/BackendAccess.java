/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author sebastian
 */
public class BackendAccess {
    private Encryption enc;
    private Passwords pass;
    private UserHandler users;
    private ConfigHandler config;
    private String[] activeUser = new String[2];
    
    private BackendAccess(){
        this.enc = new Encryption();
        this.pass = new Passwords();
        this.users = new UserHandler();
        this.config = new ConfigHandler();
    }
    
    private static BackendAccess instance = null;
    public static BackendAccess getInstance(){
        if(instance == null) instance = new BackendAccess();
        return instance;
    }
    
    public boolean getHasStoredUsername(){
        return this.config.hasStoredUsername();
    }
    
    public String getStoredUsername(){
        return this.config.getStoredUsername();
    }
    
    public boolean login(String username, String password, boolean rememberUsername){
        if(rememberUsername){
            config.storeUsername(username);
        }
        else{
            config.removeStoredUsername();
        }
        if(users.readUser(username)){
            try {
                if(users.login(username, enc.hash(password))){
                    this.activeUser[0] = username;
                    this.activeUser[1] = password;
                    this.pass.createSiteKeysFromFileContent(decryptStoredSiteKeys(password));
                    return true;
                }
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            } catch (InvalidKeySpecException ex) {
                ex.printStackTrace();
            }
        }else System.out.println("Could not read user file");
        return false;
    }
    
    public boolean hasActiveUser(){
        return this.activeUser != null;
    }
    
    /* Main page: */
    public String generatePassword(int len, boolean numbers, boolean special){
        return this.pass.generatePassword(len, special, numbers);
    }
    
    public boolean saveNewSiteKey(String site, String username, String password){
        pass.addSiteKey(site, password, username);
        return saveSiteKeys();
    }
    
    public void updateSiteKey(SiteKey siteKey, String site, String username, String password){
        pass.updateSiteKey(siteKey, new SiteKey(site, username, password));
        saveSiteKeys();
    }
    
    private String decryptStoredSiteKeys(String password){
        try {
            String storedSiteKeys = Files.readString(new File(this.activeUser[0] + "-passwords.pwds").toPath());
            return enc.passwordDecrypt(this.activeUser[0], this.activeUser[1], storedSiteKeys);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        } catch (InvalidAlgorithmParameterException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (NoSuchPaddingException ex) {
            ex.printStackTrace();
        } catch (IllegalBlockSizeException ex) {
            ex.printStackTrace();
        } catch (BadPaddingException ex) {
            ex.printStackTrace();
        }
        return "";
    }
    
    public ArrayList<SiteKey> getSiteKeys(){
        return this.pass.getSiteKeys();
    }
    
    public void removeSiteKey(SiteKey siteKey){
        this.pass.removeSiteKey(siteKey);
    }
    
    
    /* Maintenance: */
    public boolean saveSiteKeys(){
        System.out.println("Saving site keys to file");
        String passwordStoreContents = pass.createFileContentFromSiteKeys();
        try {
            String encryptedKeyStore = enc.passwordEncrypt(this.activeUser[0], this.activeUser[1], "Password Manager Storage:\n" + passwordStoreContents);
            pass.saveEncryptedKeyStore(encryptedKeyStore, this.activeUser[0]);
            return true;
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (NoSuchPaddingException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        } catch (InvalidAlgorithmParameterException ex) {
            ex.printStackTrace();
        } catch (IllegalBlockSizeException ex) {
            ex.printStackTrace();
        } catch (BadPaddingException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    
    /* Registration: */
    public boolean usernameIsTaken(String username){
        return this.users.usernameTaken(username);
    }
    
    public boolean register(String username, String password){
        try {
            users.createUser(username, enc.hash(password));
            pass.createPasswordFile(username);
            return true;
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Could not create user due to NoSuchAlgorithm");
            return false;
        } catch (InvalidKeySpecException ex) {
            System.out.println("Could not create user due to InvalidKeySpec");
            return false;
        }
    }
    
    public ArrayList<SiteKey> getSiteKeysWith(String query){
        ArrayList<SiteKey> results = new ArrayList();
        
        Pattern pattern = Pattern.compile(query);
        String regex = "(.*)"+query.toUpperCase()+"(.*)?";
        for(SiteKey key : this.pass.getSiteKeys()){
            if(pattern.matches(regex, key.getSite().toUpperCase()) && !results.contains(key)) results.add(key);
            else if(pattern.matches(regex, key.getUsername().toUpperCase()) && !results.contains(key)) results.add(key);
            else results.remove(key);
        }
        
        return results;
    }
}
