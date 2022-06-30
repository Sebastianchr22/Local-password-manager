/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ote.passmanager;

import Backend.BackendAccess;
import Backend.Encryption;
import Backend.UserHandler;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author sebastian
 */
public class RegisterController implements Initializable {


    @FXML
    private Text IncorrectInputText;
    @FXML
    private TextField UsernameField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private PasswordField RepeatPasswordField;
    @FXML
    private Text ShortInputText;
    @FXML
    private Text UsernameTakenText;
    
    private BackendAccess backend;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        IncorrectInputText.setVisible(false);
        ShortInputText.setVisible(false);
        UsernameTakenText.setVisible(false);
        
        PasswordField.setText("");
        RepeatPasswordField.setText("");
        UsernameField.setText("");
        
        backend = BackendAccess.getInstance();
    }    
    
    private boolean checkUsernameTaken(){
        if(backend.usernameIsTaken(UsernameField.getText())){
            UsernameTakenText.setVisible(true);
            return true;
        }else{
            UsernameTakenText.setVisible(false);
            return false;
        }
    }
    
    private boolean checkPasswordNotMatch(){
        if(PasswordField.getText().equals(RepeatPasswordField.getText())){
            IncorrectInputText.setVisible(false);
            return false;
        }else{
            IncorrectInputText.setVisible(true);
            return true;
        }
    }
    
    private boolean inputTooShort(){
        if(PasswordField.getText().length() < 5 || UsernameField.getText().length() < 3){
            ShortInputText.setVisible(true);
            return true;
        }else{
            ShortInputText.setVisible(false);
            return false;
        }
    }
    
    @FXML
    private void CreateUserPressed(ActionEvent event) {
        if(checkUsernameTaken()) return;
        else{
            if(checkPasswordNotMatch()) return;
            else{
                if(inputTooShort()) return;
                else{
                    //Success!
                    backend.register(UsernameField.getText(), PasswordField.getText());
                    try {
                        App.setRoot("LoginPage");
                    } catch (IOException ex) {
                        System.out.println("Could not go to LoginPage");
                    }
                }
            }
        }
    }

    @FXML
    private void BackButtonPressed(ActionEvent event) {
        try {
            PasswordField.setText("");
            RepeatPasswordField.setText("");
            UsernameField.setText("");
            App.setRoot("LoginPage");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
