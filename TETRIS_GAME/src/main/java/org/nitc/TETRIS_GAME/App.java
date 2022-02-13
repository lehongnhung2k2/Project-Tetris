package org.nitc.TETRIS_GAME;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
//    private static Scene scene1;

    @Override
    public void start(Stage stage) throws IOException {
    	stage.setTitle("TETRIS");
    	InputStream iconStream = App.class.getResourceAsStream("../image/icon.png");
    	Image image = new Image(iconStream);
    	stage.getIcons().add(image);
    	
    	try {
    		Parent root = FXMLLoader.load(App.class.getResource("screen.fxml"));
        	scene = new Scene (root);
        	stage.setScene(scene);
        	stage.show();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}