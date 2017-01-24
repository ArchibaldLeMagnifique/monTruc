package pause;


import java.io.File;

import game.Game;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class Pause {

	public int screenWidth, screenHeight;
	Game monPapa;
	
	Rectangle fadingRec;
	double coef;
	
	Stage primaryStage;
	Font font;
	Group root;
	
	public Pause(int screenWidth, int screenHeight, Stage primaryStage, Game game){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.monPapa = game;
		this.primaryStage = primaryStage;
		try {
			this.createScene();
		} catch (AWTException e) {e.printStackTrace(); }
	}
	
	
	public void createScene() throws AWTException{
		//---   Create a screen capture   ---
		Robot robot;
		robot = new Robot();
		java.awt.Rectangle area = new java.awt.Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage bufferedImage = robot.createScreenCapture(area);
		Image image = SwingFXUtils.toFXImage(bufferedImage, null);
		ImageView capture = new ImageView(image);

		font = Font.loadFont("file:/" + (new File("").getAbsolutePath().toString()).replaceAll("\\\\", "/") + "/assets/GUNPLAY.ttf", 70);
		
		root = new Group();
		Scene scenePause = new Scene(root, screenWidth, screenHeight);
		scenePause.getStylesheets().add("GameStage.css");
		
		Image curseur = new Image("file:/" + (new File("").getAbsolutePath().toString()).replaceAll("\\\\", "/") + "/assets/normal.png");
		scenePause.setCursor(new ImageCursor(curseur));
		
		primaryStage.setScene(scenePause);
		
		Pane pan = new Pane();
		pan.setPrefWidth(screenWidth);
		pan.setPrefHeight(screenHeight);
		pan.setStyle("-fx-background-color: #222428");
		root.getChildren().add(pan);
		
		root.getChildren().add(capture);

		fadingRec = new Rectangle();
		fadingRec.setWidth(screenWidth);
		fadingRec.setHeight(screenHeight);
		fadingRec.setFill(Color.rgb(0, 0, 0, 0));
		coef = 0; fade.start();
		root.getChildren().add(fadingRec);
		
		scenePause.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent g) {
				switch(g.getCode().toString()) {
				case "ESCAPE":
					monPapa.unPause();
					break;
				default:
					break;
				}
			}
		});
	}
	
	AnimationTimer fade = new AnimationTimer() {
		public void handle(long currentNanoTime) {
			fadingRec.setFill(Color.rgb(0, 0, 0, coef));
			coef += 1.0/10;
			if (coef>0.8) {
				this.stop();
				createScene2();
			}
		}
	};
	
	public void createScene2() {
		Text paused = new Text("PAUSE");
		paused.setFill(Color.DARKORANGE);
		paused.setFont(font);
		paused.setScaleX(2);
		paused.setScaleY(2);
		paused.setX(screenWidth/2-paused.getLayoutBounds().getWidth()/2);
		paused.setY(320);
		root.getChildren().add(paused);
		
		Text resume = new Text("RESUME");
		resume.setFill(Color.WHITE);
		resume.setFont(font);
		resume.setX(screenWidth/2-resume.getLayoutBounds().getWidth()/2);
		resume.setY(450);
		resume.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				resume.setScaleX(1.1);
				resume.setScaleY(1.1);
			}
		});
		resume.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				resume.setScaleX(1);
				resume.setScaleY(1);
			}
		});
		resume.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				resume.setScaleX(1);
				resume.setScaleY(1);
			}
		});
		resume.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				resume.setScaleX(1.1);
				resume.setScaleY(1.1);
			}
		});
		resume.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				monPapa.unPause();
			}
		});
		root.getChildren().add(resume);
		
		Text options = new Text("OPTIONS");
		options.setFill(Color.WHITE);
		options.setFont(font);
		options.setX(screenWidth/2-options.getLayoutBounds().getWidth()/2);
		options.setY(550);
		options.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				options.setScaleX(1.1);
				options.setScaleY(1.1);
			}
		});
		options.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				options.setScaleX(1);
				options.setScaleY(1);
			}
		});
		options.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				options.setScaleX(1);
				options.setScaleY(1);
			}
		});
		options.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				options.setScaleX(1.1);
				options.setScaleY(1.1);
			}
		});
		options.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				
			}
		});
		root.getChildren().add(options);
		
		Text mainMenu = new Text("RETURN TO MENU");
		mainMenu.setFill(Color.WHITE);
		mainMenu.setFont(font);
		mainMenu.setX(screenWidth/2-mainMenu.getLayoutBounds().getWidth()/2);
		mainMenu.setY(650);
		mainMenu.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				mainMenu.setScaleX(1.1);
				mainMenu.setScaleY(1.1);
			}
		});
		mainMenu.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				mainMenu.setScaleX(1);
				mainMenu.setScaleY(1);
			}
		});
		mainMenu.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				mainMenu.setScaleX(1);
				mainMenu.setScaleY(1);
			}
		});
		mainMenu.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				mainMenu.setScaleX(1.1);
				mainMenu.setScaleY(1.1);
			}
		});
		mainMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				monPapa.retourMenu(primaryStage);
			}
		});
		root.getChildren().add(mainMenu);
		
		Text exit = new Text("EXIT");
		exit.setFill(Color.WHITE);
		exit.setFont(font);
		exit.setX(screenWidth/2-exit.getLayoutBounds().getWidth()/2);
		exit.setY(750);
		exit.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				exit.setScaleX(1.1);
				exit.setScaleY(1.1);
			}
		});
		exit.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				exit.setScaleX(1);
				exit.setScaleY(1);
			}
		});
		exit.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				exit.setScaleX(1);
				exit.setScaleY(1);
			}
		});
		exit.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				exit.setScaleX(1.1);
				exit.setScaleY(1.1);
			}
		});
		exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				System.exit(0);
			}
		});
		root.getChildren().add(exit);
		
	}

}
