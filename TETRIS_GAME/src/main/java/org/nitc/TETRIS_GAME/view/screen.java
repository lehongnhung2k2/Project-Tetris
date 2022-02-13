package org.nitc.TETRIS_GAME.view;
import java.io.IOException;


import java.net.URL;
import java.util.ResourceBundle;

import org.nitc.TETRIS_GAME.App;
import org.nitc.TETRIS_GAME.controller.GameController;
import org.nitc.TETRIS_GAME.controller.screenGame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class screen {
	
	private Stage stage;
	private Scene scene;
//	private Parent root;

    @FXML
    private Button clickHowToPlay;

    @FXML
    private Button clickPlay;

    @FXML
    private ImageView imageHowToPlay;

    @FXML
    private ImageView imagePlay;

    @FXML
    private ImageView imageView;
    
    @FXML
    private ImageView imageTutorial;
    
    @FXML
    private Button clickDone;
    
    @FXML
    private ImageView imageDone;
    
    public void initialize() {
    	imageDone.setVisible(false);
    	clickDone.setVisible(false);
    	imageTutorial.setVisible(false);
    }
    
    @FXML
    void goToGame(ActionEvent event) throws IOException{
//    	Parent root = FXMLLoader.load(App.class.getResource("screenGame.fxml"));
//    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//    	scene = new Scene(root);
//    	stage.setScene(scene);
//    	stage.show();
    	
//    	new GameController(new screenGame());
    	
    	URL location = App.class.getResource("screenGame.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        screenGame g = fxmlLoader.getController();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        new GameController(g);
		
    }

    @FXML
    void goToTutorial(ActionEvent event) throws IOException {
    	clickHowToPlay.setVisible(false);
    	clickPlay.setVisible(false);
    	imageHowToPlay.setVisible(false);
    	imagePlay.setVisible(false);
    	
    	imageDone.setVisible(true);
    	clickDone.setVisible(true);
    	imageTutorial.setVisible(true);
    }
    
    @FXML
    void back(ActionEvent event) throws IOException {
    	clickHowToPlay.setVisible(true);
    	clickPlay.setVisible(true);
    	imageHowToPlay.setVisible(true);
    	imagePlay.setVisible(true);
    	
    	imageDone.setVisible(false);
    	clickDone.setVisible(false);
    	imageTutorial.setVisible(false);
    }

}


