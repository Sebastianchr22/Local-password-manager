/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ote.passmanager;

import Backend.BackendAccess;
import Backend.SiteKey;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author sebastian
 */
public class MainController implements Initializable {


    @FXML
    private AnchorPane VaultPane;
    @FXML
    private GridPane VaultSiteKeyTemplate;
    @FXML
    private PasswordField SiteKeyPassword;
    @FXML
    private TextField SiteKeySite;
    @FXML
    private TextField VaultSiteKeySearchBar;
    @FXML
    private ImageView VaultSiteKeyIcon;
    @FXML
    private Text VaultSiteKeyName;
    @FXML
    private TextField SiteKeyUsername;
    @FXML
    private Button CopyButton;
    @FXML
    private CheckBox IncludeNumbers;
    @FXML
    private CheckBox IncludeSpecialChars;
    @FXML
    private Slider PasswordLengthSlider;
    @FXML
    private Text PasswordLengthText;
    @FXML
    private Text GeneratedPassword;
    @FXML
    private TabPane MainPageTabs;
    @FXML
    private Text CopiedText;
    @FXML
    private VBox VaultSiteKeyContainer;
    @FXML
    private GridPane SiteKeyDetailsContainer;
    @FXML
    private TextField NewSiteKeyUsername;
    @FXML
    private TextField NewSiteKeyPassword;
    @FXML
    private TextField NewSiteKeySite;
    @FXML
    private Text NewSiteKeySavedText;
    @FXML
    private Text NewSiteKeyNotSaved;
    @FXML
    private Button UpdateSiteKeyButton;
    @FXML
    private Text NoPasswordSelected;
    
    
    
    private BackendAccess backend;
    private GridPane siteKeyTemp;
    private ArrayList<SiteKey> siteKeys;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.backend = BackendAccess.getInstance();
        this.siteKeyTemp = VaultSiteKeyTemplate;
        VaultSiteKeyContainer.getChildren().remove(VaultSiteKeyTemplate);
        SiteKeyDetailsContainer.setVisible(false);
        UpdateSiteKeyButton.setVisible(false);
        displaySiteKeys(this.backend.getSiteKeys());
    } 

    
    
    /* SiteKey Functions: */
    private SiteKey activeSiteKey;
    private void VaultSiteKeyPressed(SiteKey siteKey, Button button) {
        NoPasswordSelected.setVisible(false);
        Node parent = button.getParent().getParent();
        for(Node n : parent.lookupAll("Button")){
            Button b = (Button) n;
            if(n == button){
                if(b.isCancelButton()){
                    System.out.println("Found cancel button");
                    closeSiteKeyDetails();
                    b.setCancelButton(false);
                    return;
                }
            }else{
                b.setCancelButton(false);
            }
        }
        button.setCancelButton(true);
        
        SiteKeyDetailsContainer.setVisible(true);
        SiteKeyUsername.setText(siteKey.getUsername());
        SiteKeyPassword.setText(siteKey.getPassword());
        SiteKeySite.setText(siteKey.getSite());
        this.activeSiteKey = siteKey;
    }

    @FXML
    private void CopyUsernamePressed(ActionEvent event) {
        copyToClipboard(this.activeSiteKey.getUsername());
    }

    @FXML
    private void CopyPasswordPressed(ActionEvent event) {
        copyToClipboard(this.activeSiteKey.getPassword());
    }

    @FXML
    private void DeleteSiteKeyPressed(ActionEvent event) {
        this.backend.removeSiteKey(this.activeSiteKey);
        this.activeSiteKey = null;
        SiteKeyDetailsContainer.setVisible(false);
        SiteKeyUsername.setText("");
        SiteKeyPassword.setText("");
        SiteKeySite.setText("");
        backend.saveSiteKeys();
        displaySiteKeys(this.backend.getSiteKeys());
    }
    
    @FXML
    private void SiteKeyAltered(KeyEvent event) {
        UpdateSiteKeyButton.setVisible(true);
    }
    
    @FXML
    private void UpdateSiteKeyButtonPressed(ActionEvent event) {
        UpdateSiteKeyButton.setVisible(false);
        this.backend.updateSiteKey(activeSiteKey, SiteKeySite.getText(), SiteKeyUsername.getText(), SiteKeyPassword.getText());
        displaySiteKeys(this.backend.getSiteKeys());
    }
    
    private void closeSiteKeyDetails(){
        SiteKeyDetailsContainer.setVisible(false);
        SiteKeyUsername.setText("");
        SiteKeyPassword.setText("");
        SiteKeySite.setText("");
        this.activeSiteKey = null;
        NoPasswordSelected.setVisible(true);
    }
    
    
    
    
    
    
    
    
    
    /* Display & General: */
    private void displaySiteKeys(ArrayList<SiteKey> siteKeys){
        VaultSiteKeyContainer.getChildren().removeAll(VaultSiteKeyContainer.getChildren());
        for(SiteKey siteKey : siteKeys){
            Button b = createSiteKeyContainer(siteKey);
            HBox spacing = new HBox();
            spacing.setPadding(new Insets(10,0,0,0));
            VaultSiteKeyContainer.getChildren().add(spacing);
            VaultSiteKeyContainer.getChildren().add(b);
        }
    }
    
    private Button createSiteKeyContainer(SiteKey siteKey){
        Button button = new Button();
        button.setOnAction(a->VaultSiteKeyPressed(siteKey, button));
        button.setVisible(true); //false
        button.setText(siteKey.getSite());
        button.setPrefSize(160, 35);
        button.setPadding(new Insets(10));
        button.setFocusTraversable(false);
        return button;
    }
    
    private void copyToClipboard(String content){
        ClipboardContent cc = new ClipboardContent();
        cc.putString(content);
        Clipboard.getSystemClipboard().setContent(cc);
    }
    
    
    
    
    
    /* Generator page:*/
    private String lastPass = null;
    private void displayGeneratedPassword(){
        int len = (int) PasswordLengthSlider.getValue();
        lastPass = backend.generatePassword(len, IncludeNumbers.isSelected(), IncludeSpecialChars.isSelected());
        GeneratedPassword.setText(lastPass);
        CopiedText.setVisible(false);
    }
    
    @FXML
    private void SelectedOtherPage(Event event) {
        if(!MainPageTabs.getTabs().get(0).isSelected()){
            //On generate page:
            if(lastPass == null){
                displayGeneratedPassword();
            }
        }else if(this.backend != null){
            this.displaySiteKeys(this.backend.getSiteKeys());
        }
    }

    @FXML
    private void LengthSliderDragged(MouseEvent event) {
        int len = (int) PasswordLengthSlider.getValue();
        PasswordLengthText.setText(String.valueOf(len));
    }

    @FXML
    private void IncludeNumbersPressed(ActionEvent event) {
        displayGeneratedPassword();
    }

    @FXML
    private void IncludeSpecialCharsPressed(ActionEvent event) {
        displayGeneratedPassword();
    }

    @FXML
    private void LengthSliderDragDone(MouseEvent event) {
        displayGeneratedPassword();
    }
    
    @FXML
    private void CopyGeneratedPassword(ActionEvent event) {
        copyToClipboard(lastPass);
        CopiedText.setVisible(true);
    }
    
    @FXML
    private void generateNewPassword(ActionEvent event) {
        displayGeneratedPassword();
    }

    
    
    /* New SiteKey Page: */
    @FXML
    private void SaveNewSiteKey(ActionEvent event) {
        if(NewSiteKeyUsername.getText().length() > 1 && NewSiteKeyPassword.getText().length() > 1){
            if(backend.saveNewSiteKey(NewSiteKeySite.getText(), NewSiteKeyUsername.getText(), NewSiteKeyPassword.getText())){
                NewSiteKeyNotSaved.setText("");
                NewSiteKeySavedText.setVisible(true);
                resetTextInputs();
            }else NewSiteKeyNotSaved.setText("Could not store passwords.");
            
        }else{
            NewSiteKeySavedText.setVisible(false);
            NewSiteKeyNotSaved.setText("Username or password cannot be that short.");
        }
    }

    @FXML
    private void CancelNewSiteKey(ActionEvent event) {
        resetTextInputs();
    }
    
    private void resetTextInputs(){
        NewSiteKeyUsername.setText("");
        NewSiteKeyPassword.setText("");
        NewSiteKeySite.setText("");
    }

    
    
    
    
    
    /* Search function: */
    @FXML
    private void SearchKeyPressed(KeyEvent event) {
        ArrayList<SiteKey> results = searchSiteKeys(VaultSiteKeySearchBar.getText());
        this.displaySiteKeys(results);
    }
    
    private ArrayList<SiteKey> searchSiteKeys(String query){
        return this.backend.getSiteKeysWith(query);
    }

    @FXML
    private void DeleteSearchQuery(ActionEvent event) {
        ArrayList<SiteKey> results = searchSiteKeys("");
        this.displaySiteKeys(results);
        VaultSiteKeySearchBar.setText("");
    }

    
    @FXML
    private void LockManager(Event event) throws IOException {
        Thread logout = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                try {
                    App.setRoot("LoginPage");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        logout.start();
    }

    
}
