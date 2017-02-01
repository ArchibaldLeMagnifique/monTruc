package game;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ScorePan extends Pane {

	public double dimension = 1;
	double screenSizeW, screenSizeH;
	Game game;
	
	Text crystals;
	Text researchs;
	
	public ScorePan (Game game) {
		super();
		this.screenSizeW = game.screenWidth;
		this.screenSizeH = game.screenHeight;
		this.game = game;
		
		this.setPrefWidth(350 * dimension);
		this.setPrefHeight(130 * dimension);
		this.setStyle("-fx-background-color: #101010FA; -fx-border-width: 2; -fx-border-color: #5e4526; -fx-border-radius: 5; -fx-background-radius: 5");
		this.setTranslateX(this.screenSizeW-this.getPrefWidth()-5);
		this.setTranslateY(5);
		
		crystals = new Text(toFicelle(game.crystals));
		crystals.setFill(Color.WHITE);
		crystals.setTranslateX(70);
		crystals.setTranslateY(52);
		crystals.setFont(Font.font("Times New Roman", FontWeight.BOLD, 38));
		this.getChildren().add(crystals);
		
		Image crystalsImg = new Image("file:/" + (new File("").getAbsolutePath().toString()).replaceAll("\\\\", "/") + "/assets/CrystalsMini.png");
		ImageView crystalsImgView = new ImageView(crystalsImg);
		crystalsImgView.setScaleX(0.5);
		crystalsImgView.setScaleY(0.5);
		crystalsImgView.setTranslateX(15);
		crystalsImgView.setTranslateY(2);
		this.getChildren().add(crystalsImgView);
		
		researchs = new Text(toFicelle(game.researchs));
		researchs.setFill(Color.WHITE);
		researchs.setTranslateX(70);
		researchs.setTranslateY(103);
		researchs.setFont(Font.font("Times New Roman", FontWeight.BOLD, 38));
		this.getChildren().add(researchs);
		
		
		Image researchsImg = new Image("file:/" + (new File("").getAbsolutePath().toString()).replaceAll("\\\\", "/") + "/assets/ResearchsMini.png");
		ImageView researchsImgView = new ImageView(researchsImg);
		researchsImgView.setScaleX(0.6);
		researchsImgView.setScaleY(0.6);
		researchsImgView.setTranslateX(15);
		researchsImgView.setTranslateY(57);
		this.getChildren().add(researchsImgView);
	}
	
	public void refresh() {
		crystals.setText(toFicelle(game.crystals));
		researchs.setText(toFicelle(game.researchs));
	}
	
	public String toFicelle (long x) {
		long cur = x;
		String res = "";
		
		while (cur/1000 > 0) {
			if  (cur%1000 < 10) {
				res = "00" + String.valueOf(cur%1000) +" "+ res;
			}else if (cur%1000 < 100) {
				res = "0" + String.valueOf(cur%1000) +" "+ res;
			}else{
				res = String.valueOf(cur%1000) +" "+ res;
			}
			cur /= 1000;
		}
		res = String.valueOf(cur%1000) +" "+ res;
		return res;
	}
	
}
