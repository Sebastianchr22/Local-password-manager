/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author sebastian
 */
public class Passwords {
    
    private ArrayList<SiteKey> siteKeys;
    private String passFilePostfix = "passwords.pwds";

    protected ArrayList<SiteKey> getSiteKeys() {
        return siteKeys;
    }
    
    protected void createSiteKeysFromFileContent(String fileContent){
        this.siteKeys  = new ArrayList();
        int lineNum = 0;
        for(String line : fileContent.split("\n")){
            if(lineNum > 0 && line.split(",").length > 1){
                String[] values = line.split(",");
                siteKeys.add(new SiteKey(values[0], values[1], values[2]));
            } 
            lineNum++;
        }
    }
    
    protected String createFileContentFromSiteKeys(){
        StringBuilder contents = new StringBuilder();
        for(SiteKey sk : siteKeys){
            contents.append(
                    sk.getSite() + "," +
                    sk.getUsername() + "," +
                    sk.getPassword() + "\n"
            );
        }
        return contents.toString();
    }
    
    protected void createPasswordFile(String username){
        try {
            FileWriter writer = new FileWriter(username + "-" + this.passFilePostfix);
            writer.close();
        } catch (IOException ex1) {
            System.out.println("Could not create file");
        }
    }
    
    protected void addSiteKey(String site, String password, String username){
        siteKeys.add(new SiteKey(site, username, password));
    }
    
    protected SiteKey findSiteKey(String site){
        for(SiteKey sk : siteKeys){
            if(sk.getSite().equals(site)) return sk;
        }
        return null;
    }
    
    protected void removeSiteKey(SiteKey siteKey){
        if(this.siteKeys.contains(siteKey)) this.siteKeys.remove(siteKey);
    }
    
    protected void removeSiteKey(String site){
        for(SiteKey siteKey : siteKeys){
            if(siteKey.getSite().equals(site)) siteKeys.remove(siteKey);
        }
    }
    
    protected void removeSiteKey(int index){
        siteKeys.remove(index);
    }
    
    protected void updateSiteKey(SiteKey siteKey, SiteKey newSiteKey){
        if(siteKeys.contains(siteKey)){
            siteKeys.remove(siteKey);
            siteKeys.add(newSiteKey);
        }
    }
    
    protected String generatePassword(int len, boolean specialChars, boolean numbers){
        StringBuilder chars = new StringBuilder(
                "abcdefghijklmnopqrstuvwxyz" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if(specialChars) chars.append("-_<>@£$%&()?!");
        if(numbers) chars.append("1234567890");
        StringBuilder pass = new StringBuilder("");
        Random rng = new Random();
        for(int i = 0; i < len; i++){
           pass.append(chars.charAt(rng.nextInt(chars.length() - 1)));
        }
        return pass.toString();
    }
    
    protected void saveEncryptedKeyStore(String encKeyStoreContent, String username) {
        try {
            FileWriter fr = new FileWriter(username + "-" + this.passFilePostfix);
            fr.write(encKeyStoreContent);
            fr.close();
        } catch (IOException ex) {
            System.out.println("Could not write encrypted content to file");
        }
        
    }
}
