package game;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ennemis {
	Game game;
	double vie;
	double vieTotale;
	double size;
	double x;
	double y;
	Color color;
	Circle cercle;
	
	Rectangle rektVie;
	Rectangle fontVie;
	
	public Ennemis(double x, double y, Game game, int size){
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
	}
	
	
	public void getDamaged(double damages){
		if (vie == vieTotale){
			game.pan.getChildren().add(fontVie);
			game.pan.getChildren().add(rektVie);
		}
		vie -= damages;
		rektVie.setWidth(size*2.2 * vie/vieTotale);
		if (vie <= 0){
			game.pan.getChildren().remove(cercle);
			game.pan.getChildren().remove(rektVie);
			game.pan.getChildren().remove(fontVie);
		}
		cercle.setFill(Color.WHITE);
		hit();
	}
	public void hit() {
		new AnimationTimer() {
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
		}.start();
	}
	
}



class EnnemiPassif extends Ennemis {
		
	public EnnemiPassif(double x, double y, Game game) {
		super(x,y,game,7);
		game.l_ennemis.add((Ennemis)this);
		this.game = game;
		this.size = 7;
		this.x = x;
		this.y = y;
		this.cercle = new Circle(this.size);
		this.cercle.setCenterX(x);
		this.cercle.setCenterY(y);
		this.color = Color.RED;
		this.cercle.setFill(color);
		this.cercle.setOpacity(0.8);
		this.game.pan.getChildren().add(cercle);
		
		this.vie = 4;
		this.vieTotale = 4;
		
	}
	
	
}