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
import java.util.HashMap;

/**
 *
 * @author sebastian
 */
public class UserHandler {
    private String usersAndStore;
    private String userRegistryPrefix = "PasswordManagerUser";
    private String filePostfix = ".pwds";
    private HashMap<String, String> userMap = new HashMap();
    protected UserHandler(){
        this.usersAndStore = ".registeredUsers.pwd";
        try {
            readUserAndStores();
        } catch (IOException ex) {
        }
    }
    
    protected void createUser(String username, String passwordHash) {
        this.userMap.put(username, userRegistryPrefix + "-" + username + filePostfix);
        try {
            FileWriter fr = new FileWriter(userMap.get(username));
            fr.write(username + "," + passwordHash);
            fr.close();
        } catch (IOException ex) {
            System.out.println("Could not write to user file");
        }
        persistRegisteredUsers();
    }
    
    private String[] fromFile;
    protected boolean readUser(String username){
        if(!this.userMap.containsKey(username)) return false;
        try {
            FileReader fr = new FileReader(this.userMap.get(username));
        } catch (FileNotFoundException ex) {
            return false;
        }
        try {
            fromFile = Files.readString(new File(this.userMap.get(username)).toPath()).split(",");
            return true;
        } catch (IOException ex) {
            System.out.println("Could not read user data file");
        }
        return false;
    }
    
    protected boolean login(String username, String password){
        if(username.equals(fromFile[0]) && password.equals(fromFile[1])) return true;
        return false;
    }
    
    protected boolean usersAndStoreFileExists(){
        try {
            FileReader fr = new FileReader(this.usersAndStore);
            return true;
        } catch (FileNotFoundException ex) {
            return false;
        }
    }
    
    protected void readUserAndStores() throws IOException{
        if(usersAndStoreFileExists()){
           String content = Files.readString(new File(this.usersAndStore).toPath());
            for(String userAndStore : content.split("\n")){
                if(userAndStore.length() > 1){
                    String[] details = userAndStore.split(",");
                    this.userMap.put(details[0], details[1]);
                }
            } 
        }else{
            createNewRegisteredUsersFile();
        }
    }
    
    protected void createNewRegisteredUsersFile(){
        try {
            FileWriter fr = new FileWriter(this.usersAndStore);
            fr.write("");
            fr.close();
        } catch (IOException ex1) {
            System.out.println("Could not write new registered users file");
        }
    }
    
    protected String getStoreNameOfUser(String username){
        return this.userMap.get(username);
    }
    
    protected boolean usernameTaken(String username){
        return this.userMap.containsKey(username);
    }
    
    protected String getPasswordOfUser(String username){
        try {
            String stored = Files.readString(new File(userRegistryPrefix + "-" + username).toPath());
            return stored.split(",")[1];
        } catch (IOException ex) {
            System.out.println("Could not find stored user file");
            return null;
        }
    }
    
    protected void persistRegisteredUsers(){
        FileWriter fr;
        try {
            fr = new FileWriter(this.usersAndStore);
            for(String user : this.userMap.keySet()){
                fr.write(user + "," + this.userMap.get(user) + "\n");
            }
            fr.close();
        } catch (IOException ex) {
            System.out.println("Could not persist registered users");
        }
        
    }
}
