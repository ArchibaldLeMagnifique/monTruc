package game;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class WeaponBar extends Pane {

	double screenSizeW, screenSizeH;
	int nbArmes;
	int selection = 1;
	HBox box;
	Rectangle rekt;
	double coordX = 0;
	
	public WeaponBar(int w, int h, int nbArmes){
		super();
		box = new HBox();
		this.getChildren().add(box);
		this.screenSizeW = w;
		this.screenSizeH = h;
		this.nbArmes = nbArmes;
		this.setPrefWidth(90 * nbArmes);
		this.setPrefHeight(90);
		box.setPrefWidth(90 * nbArmes);
		box.setPrefHeight(90);
		
		for(int i=0; i<nbArmes; i++){
			box.getChildren().add(new WeaponPane());
		}
		this.setTranslateX(w/2-this.getPrefWidth()/2);
		this.setTranslateY(h-this.getPrefHeight()-5);
		
		rekt = new Rectangle();
		rekt.setHeight(90);
		rekt.setWidth(90);
		rekt.setStyle("-fx-stroke: #42f4e8; -fx-fill: rgb(0,0,0,0);");
		this.getChildren().add(rekt);
		
	}
	
	

	public void start() {
		AnimationTimer slide = new AnimationTimer() {
			public void handle(long arg0) {
				
				if (rekt.getX()<(selection-1)*90){
					coordX += Math.sqrt(Math.pow(rekt.getX() - (selection-1)*90,2))/4;
				}else{
					coordX -= Math.sqrt(Math.pow(rekt.getX() - (selection-1)*90,2))/4;
				}
				rekt.setX(coordX);
				if(Math.sqrt(Math.pow(rekt.getX() - (selection-1)*90,2)) < 2) {
					rekt.setX((selection-1)*90);
					this.stop();
				}
			}
		};slide.start();
	}
	
	public int switchWeapon(Armes arme, int indice){
		((Pane) box.getChildren().get(indice)).getChildren().remove(0, ((Pane) box.getChildren().get(indice)).getChildren().size());;
		if (arme == null) return 0;
		ImageView img = arme.imgView;
		switch (arme.getClass().toString()) {
		case "class game.Gun":
			img.setScaleX(0.7);
			img.setScaleY(0.7);
			img.setX(-7);
			img.setY(-10);
			break;
		case "class game.MiniGun":
			img.setScaleX(0.7);
			img.setScaleY(0.7);
			img.setX(-7);
			img.setY(-10);
			break;
		}
		
		
		
		((Pane) box.getChildren().get(indice)).getChildren().add(img);
		return(1);
	}
	
}




class WeaponPane extends Pane{
	
	Armes arme;
	int munitions;
	
	public WeaponPane(){
		this.setPrefSize(90, 90);
		this.setStyle("-fx-BackGround-Color: radial-gradient(focus-distance 0% , center 50% 50% , radius 70% , rgb(22,24,28,0.85), rgb(22,24,28,0.85), rgb(42,44,48,1)); -fx-border-color: #5e4526");
	}
	
}
