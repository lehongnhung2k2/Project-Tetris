package org.nitc.TETRIS_GAME.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.nitc.TETRIS_GAME.App;
import org.nitc.TETRIS_GAME.model.DownData;
import org.nitc.TETRIS_GAME.model.EventSource;
import org.nitc.TETRIS_GAME.model.EventType;
import org.nitc.TETRIS_GAME.model.MoveEvent;
import org.nitc.TETRIS_GAME.model.ViewData;
import org.nitc.TETRIS_GAME.view.InputEvent;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class screenGame implements Initializable{
	
	private Stage stage;
	private Scene scene;
	
	private static final int BRICK_SIZE = 20;
	Timeline timeline;
	private InputEvent inputEvent;
	private Rectangle[][] displayMatrix;
	private Rectangle[][] rectangles;
	private boolean pause = false;
	
	private File directory;
    private File[] files;
    private ArrayList<File> songs;
    private int songNumber;
    private Media media;
    private MediaPlayer mediaPlayer;
    
    @FXML
    private BorderPane borderSongName;
    
    @FXML
    private Button previousIButton;

    @FXML
    private ImageView previousImage;
    
    @FXML
    private Label songName;
    
    @FXML
    private Button nextButton;

    @FXML
    private ImageView nextImage;
	
	@FXML
    private Button pauseMusicButton;

    @FXML
    private ImageView pauseMusicImage;

    @FXML
    private Button playMusicButton;

    @FXML
    private ImageView playMusicImage;

	@FXML
    private Group Notification;
	
	@FXML
	private ImageView home;
	
	@FXML
    private ImageView gameOver;
	
	@FXML
	private Button clickHome;

	
	@FXML
    private Button clickContinute;
	
	@FXML
    private ImageView imageContinue;
	
	@FXML
    private BorderPane boardScore;
	
	@FXML
	private Group groupNotification;
	
	@FXML
    private GridPane nextBrick;
	
	@FXML
	private Text score;
	
	@FXML
    private BorderPane borderNextBrick;
	
	@FXML
    private Text textNextBrick;

    @FXML
    private Button clickNewGame;
    
    @FXML
    private Text textScore;

    @FXML
    private BorderPane gameBoard;
    

    @FXML
    private ImageView imageNewGame;
    
    @FXML
    private Button clickPause;
    
    @FXML
    private ImageView imagePause;
    
    
    @FXML
    void goHome(ActionEvent event) throws IOException{
    	mediaPlayer.stop();
    	Parent root = FXMLLoader.load(App.class.getResource("screen.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene (root);
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    void goToContinue(ActionEvent event) {
    	pause = false;
    	timeline.play();
    }
    
    @FXML
    void goToPause(ActionEvent event) {
    	pause = true;
    	timeline.pause();
    }

    @FXML
    void goToGame(ActionEvent event) throws IOException{
    	mediaPlayer.stop();
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
    private GridPane gamePanel;
    
    @FXML
    private GridPane brickPanel;
    
//    @FXML
//    public void initialize() {
//    	System.out.println("hello");
//    	initGameView(null, null);
//    }
    
    public void initGameView(int[][] boardMatrix, ViewData viewData) {
    	displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
    	for (int i=2; i<boardMatrix.length; i++) {
    		for (int j=0; j<boardMatrix[i].length; j++) {
    			Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
    			rectangle.setFill(Color.TRANSPARENT);
    			displayMatrix[i][j] = rectangle;
    			gamePanel.add(rectangle, j, i-2);
    		}
    	}
    	
    	rectangles = new Rectangle[viewData.getBrickData().length][viewData.getBrickData()[0].length];
    	
//    	int[][] currentShape = brick.getBrickMatrix().get(0);
    	for(int i=0; i<viewData.getBrickData().length; i++) {
    		for (int j=0; j<viewData.getBrickData()[i].length; j++) {
    			Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
    			rectangle.setFill(getFillColor(viewData.getBrickData()[i][j]));
    			rectangles[i][j] = rectangle;
    			brickPanel.add(rectangle, j, i);
    		}
    	}
    	
    	brickPanel.setLayoutX(gamePanel.getLayoutX()+viewData.getX()*(BRICK_SIZE+1.25));
    	brickPanel.setLayoutY(-42+gamePanel.getLayoutY()+(viewData.getY()*BRICK_SIZE)+viewData.getY());
    	
    	showNexBrick(viewData.getBrickData());
    	
    	timeline = new Timeline(new KeyFrame(Duration.millis(300), ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))));
    	timeline.setCycleCount(Timeline.INDEFINITE);
    	timeline.play();
    }
    
    private void showNexBrick(int [][] nextBrickData) {
    	nextBrick.getChildren().clear();
    	for (int i=0; i<nextBrickData.length; i++) {
    		for (int j=0; j<nextBrickData[i].length; j++) {
    			Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
    			setRectangleData(nextBrickData[i][j], rectangle);
    			if (nextBrickData[i][j]!=0) {
    				nextBrick.add(rectangle, j, i);
    			}
    		}
    	}
    }
    
    public void refreshGameBackground (int[][] board) {
    	for (int i=2; i<board.length; i++) {
    		for (int j=0; j<board[i].length; j++) {
    			setRectangleData(board[i][j], displayMatrix[i][j]);
    		}
    	}
    }
    
    private void setRectangleData(int color, Rectangle rectangle) {
    	rectangle.setFill(getFillColor(color));
//    	rectangle.setArcHeight(9);
//    	rectangle.setArcWidth(9);
	}

	public void bindScore(IntegerProperty integerProperty) {
    	score.textProperty().bind(integerProperty.asString());
    }
    
    private void moveDown(MoveEvent event) {
    	DownData downData = inputEvent.onDownEvent(event);
    	
    	refreshBrick(downData.getViewData());
    }
    
    private void refreshBrick(ViewData viewData) {
    	brickPanel.setLayoutX(gamePanel.getLayoutX()+viewData.getX()*(BRICK_SIZE+1.25));
    	brickPanel.setLayoutY(-42+gamePanel.getLayoutY()+(viewData.getY()*BRICK_SIZE)+viewData.getY());
    	
    	for (int i=0; i<viewData.getBrickData().length; i++) {
    		for (int j=0; j<viewData.getBrickData()[i].length; j++) {
    			setRectangleData(viewData.getBrickData()[i][j], rectangles[i][j]);
    		}
    	}
    	
    	showNexBrick(viewData.getNextBrickData());
	}

	public void setInputEvent(InputEvent inputEvent) {
		this.inputEvent = inputEvent;
	}

	public Paint getFillColor(int i) {
    	Paint returnPaint;
    	switch (i) {
		case 0:
			returnPaint = Color.TRANSPARENT;
			break;
		case 1:
			returnPaint = Color.web("#EF919B");
			break;
		case 2:
			returnPaint = Color.web("#F8B392");
			break;
		case 3:
			returnPaint = Color.web("#F6F7B0");
			break;
		case 4:
			returnPaint = Color.web("#A0CFA2");
			break;
		case 5: 
			returnPaint = Color.web("#71BEE7");
			break;
		case 6:
			returnPaint = Color.web("#8380B3");
			break;
		default:
			returnPaint = Color.WHITE;
			break;
		}
    	return returnPaint;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		songs = new ArrayList<File>();
		directory = new File("music");
		files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				songs.add(file);
			}
		}
		
		media = new Media(songs.get(songNumber).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		songName.setText(songs.get(songNumber).getName());
		
		gameOver.setVisible(false);
		gamePanel.setFocusTraversable(true);
		gamePanel.requestFocus();
		gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (pause == false) {
					if (event.getCode() == KeyCode.UP) {
						refreshBrick(inputEvent.onRotateEvent());
						event.consume();
					}
					if (event.getCode() == KeyCode.DOWN) {
						moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
						event.consume();
					}
					if (event.getCode() == KeyCode.LEFT) {
						refreshBrick(inputEvent.onLeftEvent());
						event.consume();
					}
					if (event.getCode() == KeyCode.RIGHT) {
						refreshBrick(inputEvent.onRightEvent());
						event.consume();
					}
				}
				
			}
		});
	
	}
	
	public void gameOver() {
		timeline.stop();
		pause = true;
		gameOver.setVisible(true);
	}
	
	@FXML
    void pauseMusic(ActionEvent event) {
		mediaPlayer.pause();
    }

    @FXML
    void playMusic(ActionEvent event) {
    	mediaPlayer.play();
    }
    
    @FXML
    void nextMedia(ActionEvent event) {
    	if (songNumber < songs.size() - 1) {
    		++songNumber;
    		mediaPlayer.stop();
    		media = new Media(songs.get(songNumber).toURI().toString());
    		mediaPlayer = new MediaPlayer(media);
    		mediaPlayer.play();
    		songName.setText(songs.get(songNumber).getName());
    	}
    	else {
    		songNumber = 0;
    		mediaPlayer.stop();
    		media = new Media(songs.get(songNumber).toURI().toString());
    		mediaPlayer = new MediaPlayer(media);
    		mediaPlayer.play();
    		songName.setText(songs.get(songNumber).getName());
    	}

    }
    
    @FXML
    void previousIMedia(ActionEvent event) {
    	if (songNumber > 0) {
    		--songNumber;
    		mediaPlayer.stop();
    		media = new Media(songs.get(songNumber).toURI().toString());
    		mediaPlayer = new MediaPlayer(media);
    		mediaPlayer.play();
    		songName.setText(songs.get(songNumber).getName());
    	}
    	else {
    		songNumber = songs.size()-1;
    		mediaPlayer.stop();
    		media = new Media(songs.get(songNumber).toURI().toString());
    		mediaPlayer = new MediaPlayer(media);
    		mediaPlayer.play();
    		songName.setText(songs.get(songNumber).getName());
    	}

    }




}
