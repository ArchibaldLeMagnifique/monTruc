package game;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import util.Point;

class Bullet {

	Circle cercle;
	double vectX, vectY, norme, vitesse;
	Game game;
	Point dep;
	double degats;
	
	public Bullet(double depX, double depY, double finX, double finY, double vitesse, double degats, Game game){
		this.game = game;
		this.dep = new Point(depX, depY);
		this.vitesse = vitesse;
		this.degats = degats;
		this.cercle = new Circle(3);
		this.cercle.setFill(Color.WHITE);
		this.cercle.setCenterX(depX);
		this.cercle.setCenterY(depY);
		game.pan.getChildren().add(cercle);
		vectX = finX-depX; vectY = finY-depY;
		norme = Math.sqrt(Math.pow(vectX, 2) + Math.pow(vectY, 2));
		vectX /= norme; vectY /= norme;
	}
	
	public void start() {
		AnimationTimer anim = new AnimationTimer() {
			public void handle(long arg0) {
				cercle.setCenterX(cercle.getCenterX() + vectX*vitesse);
				cercle.setCenterY(cercle.getCenterY() + vectY*vitesse);
				
				for(int i=0; i<(game.l_ennemis.size()); i++){
					if (Math.sqrt(Math.pow(cercle.getCenterX() - game.l_ennemis.get(i).cercle.getCenterX(), 2) + Math.pow(cercle.getCenterY() - game.l_ennemis.get(i).cercle.getCenterY(), 2)) < 3+game.l_ennemis.get(i).size){
						game.l_ennemis.get(i).getDamaged(degats);
						game.pan.getChildren().remove(cercle);
						this.stop();
					}
				}
				
				
				if ((new Point(cercle.getCenterX(), cercle.getCenterY()).ptsDans(game.l_l_triangles, game.l_obs, game.pan) != null) | 
						cercle.getCenterX() < 50 | cercle.getCenterX() > game.mapSizeW-50 | cercle.getCenterY() < 50 | cercle.getCenterY() > game.mapSizeH-50 |
						dep.distance(new Point(cercle.getCenterX(), cercle.getCenterY()))  >  500) {
					game.pan.getChildren().remove(cercle);
					this.stop();
				}
			}
		};
		game.aPauser.add(anim);
		anim.start();
	}


}
