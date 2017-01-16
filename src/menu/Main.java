package menu;

import game.Game;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
	
	public int screenWidth = 1920;
	public int screenHeight = 1080;
	
	Game game; Main me = this;
	Scene sceneMenu;
	Font mainFont;
	Font secFont;
	
	public void start(Stage primaryStage) throws Exception {
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
		try{
			mainFont = Font.loadFont(Main.class.getResource("/HACKED.TTF").toExternalForm(), 220);
			secFont = Font.loadFont(Main.class.getResource("/GUNPLAY.TTF").toExternalForm(), 70);
		}catch (Exception e) {System.out.println("error : Can't load font."); }
		createScene(primaryStage);
		game = new Game(screenWidth, screenHeight, primaryStage, me);
	}
	
	public void createScene(Stage primaryStage){
		Group root = new Group();
		sceneMenu = new Scene(root, screenWidth, screenHeight);
		
		primaryStage.setScene(sceneMenu);
		primaryStage.setY(0); primaryStage.setX(0);
		
		Pane pan = new Pane();
		pan.setPrefWidth(screenWidth);
		pan.setPrefHeight(screenHeight);
		pan.setStyle("-fx-background-color: #222428");
		root.getChildren().add(pan);
		
		Text titre = new Text("Fallen Base");
		titre.setX(100);
		titre.setY(220);
		titre.setFont(mainFont);
		titre.setRotate(-1);
		titre.setScaleX(1.1);
		titre.setStyle("-fx-fill: linear-gradient(from 0% -100% to 100% 300%, repeat, aqua 0%, red 100%);");
		pan.getChildren().add(titre);

		
		
		Pane bPane = new Pane();
		bPane.setTranslateX(180);
		bPane.setTranslateY(400);
		bPane.setRotate(-1);
		pan.getChildren().add(bPane);
		
		
		Text bContinue = new Text("CONTINUE");
		bContinue.setFill(Color.GRAY);
		bContinue.setFont(secFont);
		/*bContinue.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				bContinue.setScaleX(1.1);
				bContinue.setScaleY(1.1);
			}
		});
		bContinue.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				bContinue.setScaleX(1);
				bContinue.setScaleY(1);
			}
		});*/
		bPane.getChildren().add(bContinue);
		
		
		Text bNewGame = new Text("NEW GAME");
		bNewGame.setFill(Color.WHITE);
		bNewGame.setFont(secFont);
		bNewGame.setY(100);
		bNewGame.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				bNewGame.setScaleX(1.1);
				bNewGame.setScaleY(1.1);
			}
		});
		bNewGame.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				bNewGame.setScaleX(1);
				bNewGame.setScaleY(1);
			}
		});
		bNewGame.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				bNewGame.setScaleX(1);
				bNewGame.setScaleY(1);
			}
		});
		bNewGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				game = new Game(screenWidth, screenHeight, primaryStage, me);
			}
		});
		bPane.getChildren().add(bNewGame);
		

		Text bOptions = new Text("OPTIONS");
		bOptions.setFill(Color.GRAY);
		bOptions.setFont(secFont);
		bOptions.setY(200);
		/*bOptions.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				bOptions.setScaleX(1.1);
				bOptions.setScaleY(1.1);
			}
		});
		bOptions.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				bOptions.setScaleX(1);
				bOptions.setScaleY(1);
			}
		});*/
		bPane.getChildren().add(bOptions);
		
		
		Text bExit = new Text("EXIT");
		bExit.setFill(Color.WHITE);
		bExit.setFont(secFont);
		bExit.setY(300);
		bExit.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				bExit.setScaleX(1.1);
				bExit.setScaleY(1.1);
			}
		});
		bExit.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				bExit.setScaleX(1);
				bExit.setScaleY(1);
			}
		});
		bExit.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				bExit.setScaleX(1);
				bExit.setScaleY(1);
			}
		});
		bExit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {System.exit(0); }
		});

		bPane.getChildren().add(bExit);
	}

	
	public static void main(String[] args) {
		launch();
	}
}
