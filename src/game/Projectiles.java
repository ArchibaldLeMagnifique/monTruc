package game;

import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

class Bullet {

	Circle cercle;
	double vectX, vectY, norme, vitesse;
	LinkedList<Polygon> l_obs;
	LinkedList<LinkedList<Double>> l_tri;
	CustomPanel pan;
	double mapSizeW, mapSizeH;
	Point dep;
	
	public Bullet(double depX, double depY, double finX, double finY, double vitesse, LinkedList<LinkedList<Double>> l_tri, LinkedList<Polygon> l_obs, CustomPanel pan, double mapSizeW, double mapSizeH){
		this.mapSizeW = mapSizeW;
		this.mapSizeH = mapSizeH;
		this.dep = new Point(depX, depY);
		this.pan = pan;
		this.l_obs = l_obs;
		this.l_tri = l_tri;
		this.vitesse = vitesse;
		this.cercle = new Circle(3);
		this.cercle.setFill(Color.WHITE);
		this.cercle.setCenterX(depX);
		this.cercle.setCenterY(depY);
		pan.getChildren().add(cercle);
		vectX = finX-depX; vectY = finY-depY;
		norme = Math.sqrt(Math.pow(vectX, 2) + Math.pow(vectY, 2));
		vectX /= norme; vectY /= norme;
	}
	public void start() {
		AnimationTimer jeu = new AnimationTimer() {
			public void handle(long arg0) {
				cercle.setCenterX(cercle.getCenterX() + vectX*vitesse);
				cercle.setCenterY(cercle.getCenterY() + vectY*vitesse);
				if ((new Point(cercle.getCenterX(), cercle.getCenterY()).ptsDans(l_tri, l_obs, pan) != null) | 
						cercle.getCenterX() < 50 | cercle.getCenterX() > mapSizeW-50 | cercle.getCenterY() < 50 | cercle.getCenterY() > mapSizeH-50 |
						dep.distance(new Point(cercle.getCenterX(), cercle.getCenterY()))  >  500) {
					pan.getChildren().remove(cercle);
					this.stop();
				}
			}
		};
		jeu.start();
	}


}
