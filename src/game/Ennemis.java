package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ennemis {
	Game game;
	double vie;
	double size;
	double x;
	double y;
	public void getDamaged(double damages){}
}


class EnnemiPassif extends Ennemis {
	
	Circle cercle;
	
	public EnnemiPassif(double x, double y, Game game) {
		game.l_ennemis.add((Ennemis)this);
		this.game = game;
		this.size = 7;
		this.x = x;
		this.y = y;
		this.cercle = new Circle(this.size);
		this.cercle.setCenterX(x);
		this.cercle.setCenterY(y);
		this.cercle.setFill(Color.RED);
		this.cercle.setOpacity(0.8);
		this.game.pan.getChildren().add(cercle);
		
		this.vie = 4;
		
	}
	public void getDamaged(double damages){
		this.vie -= damages;
		if (vie <= 0){
			game.pan.getChildren().remove(cercle);
		}
	}
	
}