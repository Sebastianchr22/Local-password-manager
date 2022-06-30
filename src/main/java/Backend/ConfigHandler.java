/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author sebastian
 */
public class ConfigHandler {
    private String path = ".conf.pwds";
    private boolean storeUsernameOption = false;
    private String usernameToRemember;
    
    protected ConfigHandler(){
        try {
            FileReader fr = new FileReader(path);
            createCofig(Files.readString(new File(path).toPath()));
        } catch (FileNotFoundException ex) {
            try {
                FileWriter writer = new FileWriter(path);
                writer.write("");
                writer.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void createCofig(String content){
        for(String line : content.split("\n")){
            if(line.split(",")[0].equals("StoredUsername")) this.usernameToRemember = line.split(",")[1];
        }
    }
    
    protected void storeUsername(String username){
        this.usernameToRemember = username;
        this.storeUsernameOption = true;
        writeConfig();
    }
    
    protected void removeStoredUsername(){
        this.storeUsernameOption = false;
        writeConfig();
    }
    
    protected void writeConfig(){
        try {
            FileWriter fr = new FileWriter(path);
            if(this.storeUsernameOption) fr.write("StoredUsername" + "," + this.usernameToRemember);
            fr.close();
        } catch (IOException ex) {
            System.out.println("Could not write to config file");
        }
    }
    
    protected String getStoredUsername(){
        return this.usernameToRemember;
    }
    
    protected boolean hasStoredUsername(){
        return this.usernameToRemember != null;
    }
}
