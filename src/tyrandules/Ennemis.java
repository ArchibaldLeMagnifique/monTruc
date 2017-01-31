package tyrandules;

import java.util.LinkedList;

import game.Game;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class Ennemis {
	Game game;
	double vie;
	double vieTotale;
	public double size;
	public double x;
	public double y;
	Color color;
	public Circle cercle;
	public Circle cercleMap;
	
	Rectangle rektVie;
	Rectangle fontVie;
	
	public LinkedList<Double> flagX = new LinkedList<Double>();
	public LinkedList<Double> flagY = new LinkedList<Double>();
	
	double vitesse;
	Ennemis me;
	double timer;
	
	
	public Ennemis(double x, double y, Game game, int size){
		this.me = this;
		this.game = game;
		this.size = size;
		fontVie = new Rectangle();
		fontVie.setHeight(size/2);
		fontVie.setWidth(size*2.2);
		fontVie.setX(x-fontVie.getWidth()/2);
		fontVie.setY(y-size*1.7);
		
		rektVie = new Rectangle();
		rektVie.setFill(Color.LAWNGREEN);
		rektVie.setHeight(size/2);
		rektVie.setWidth(size*2.2);
		rektVie.setX(x-fontVie.getWidth()/2);
		rektVie.setY(y-size*1.7);
		
		this.cercle = new Circle(this.size);
		this.cercle.setCenterX(x);
		this.cercle.setCenterY(y);
		this.color = Color.RED;
		this.cercle.setFill(color);
		this.cercle.setOpacity(0.8);
		this.game.pan.getChildren().add(cercle);
		
		this.cercleMap = new Circle(this.size/5);
		this.cercleMap.setCenterX(cercle.getCenterX() * game.ui.miniMap.coef * game.ui.miniMap.dimension + game.ui.miniMap.rekt.getTranslateX());
		this.cercleMap.setCenterY(cercle.getCenterY() * game.ui.miniMap.coef * game.ui.miniMap.dimension + game.ui.miniMap.rekt.getTranslateY());
		this.color = Color.RED;
		this.cercleMap.setFill(color);
		this.cercleMap.setOpacity(0.8);
		this.game.ui.miniMap.getChildren().add(cercleMap);
	}
	
	public void deplace(double dx, double dy){
		cercle.setCenterX(cercle.getCenterX() + dx);
		cercle.setCenterY(cercle.getCenterY() + dy);
		cercleMap.setCenterX(cercle.getCenterX() * game.ui.miniMap.coef * game.ui.miniMap.dimension + game.ui.miniMap.rekt.getTranslateX());
		cercleMap.setCenterY(cercle.getCenterY() * game.ui.miniMap.coef * game.ui.miniMap.dimension + game.ui.miniMap.rekt.getTranslateY());
		fontVie.setX(cercle.getCenterX()-fontVie.getWidth()/2);
		fontVie.setY(cercle.getCenterY()-size*1.7);
		rektVie.setX(cercle.getCenterX()-fontVie.getWidth()/2);
		rektVie.setY(cercle.getCenterY()-size*1.7);
	}
	
	
	public void getDamaged(double damages){
		if (vie == vieTotale){
			game.pan.getChildren().add(fontVie);
			game.pan.getChildren().add(rektVie);
		}
		vie -= damages;
		rektVie.setWidth(size*2.2 * vie/vieTotale);
		if (vie <= 0){
			for(int i=0; i<4; i++){
				new pops(cercle.getCenterX(), cercle.getCenterY(),size , game.pan);
			}
			game.l_ennemis.remove((Ennemis)this);
			game.pan.getChildren().remove(cercle);
			game.ui.miniMap.getChildren().remove(cercleMap);
			game.pan.getChildren().remove(rektVie);
			game.pan.getChildren().remove(fontVie);
			
		}
		cercle.setFill(Color.WHITE);
		hit();
	}
	public void hit() {
		AnimationTimer anim = new AnimationTimer() {
			public void handle(long arg0) {
				int red = Integer.parseInt(cercle.getFill().toString().substring(2,4), 16);
				int green = Integer.parseInt(cercle.getFill().toString().substring(4,6), 16)-18;
				int blue = Integer.parseInt(cercle.getFill().toString().substring(6,8), 16)-18;
				String res = "#" + Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue);
				if (red <= 15 | green <= 15 | blue <= 15){
					cercle.setFill(color);
					this.stop();
				}else
				cercle.setFill(Color.web(res));
			}
		};
		anim.start();
	}
	
}

