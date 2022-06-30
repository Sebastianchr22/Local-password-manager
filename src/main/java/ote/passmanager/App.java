package ote.passmanager;

import Backend.BackendAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private BackendAccess backend;
    
    @Override
    public void start(Stage stage) throws IOException {
        this.backend = BackendAccess.getInstance();
        //scene = new Scene(loadFXML("LoginPage"), 640, 480);
        scene = new Scene(loadFXML("LoginPage"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        if(this.backend.hasActiveUser()) this.backend.saveSiteKeys();
    }
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public BackendAccess getBackend(){
        return this.backend;
    }

}