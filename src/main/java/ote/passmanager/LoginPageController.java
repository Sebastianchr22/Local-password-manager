/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ote.passmanager;

import Backend.BackendAccess;
import Backend.UserHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sebastian
 */
public class LoginPageController implements Initializable {

    @FXML
    private PasswordField PasswordField;
    @FXML
    private Button LoginButton;
    @FXML
    private TextField UsernameField;
    @FXML
    private Text IncorrectInputText;
    @FXML
    private AnchorPane Login;
    @FXML
    private CheckBox RememberUsername;

    
    private BackendAccess backend;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.backend = BackendAccess.getInstance();
        if(this.backend.getHasStoredUsername()){
            RememberUsername.setSelected(true);
            UsernameField.setText(this.backend.getStoredUsername());
            UsernameField.setFocusTraversable(false);
            PasswordField.requestFocus();
        }
    }    
    private void login() throws IOException{
        if(this.backend.login(UsernameField.getText(), PasswordField.getText(), this.RememberUsername.isSelected())){
            IncorrectInputText.setVisible(false);
            App.setRoot("Main");
        }else{
            IncorrectInputText.setVisible(true);
        }
    }
    
    @FXML
    private void LoginPressed(ActionEvent event){
        try {
            login();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void RegisterButtonPressed(ActionEvent event) {
        try {
            App.setRoot("Register");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void KeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                login();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
