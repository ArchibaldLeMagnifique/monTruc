package tyrandules;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import util.CustomPanel;

class pops {
	Circle cercle;
	CustomPanel pan;
	double vitesse;
	double angle;
	
	public pops(double x, double y, double size, CustomPanel pan){
		this.cercle = new Circle(size/2);
		this.cercle.setCenterX(x);
		this.cercle.setCenterY(y);
		this.cercle.setFill(Color.WHITE);
		this.pan=pan;
		this.pan.getChildren().add(this.cercle);
		this.vitesse = size;
		this.angle = Math.random()*2*Math.PI;
		start();
	}
	
	
	public void start () {
		AnimationTimer anim = new AnimationTimer() {
			public void handle(long arg0) {
				if (vitesse < 0.5) {
					pan.getChildren().remove(cercle);
					this.stop();
				}
				cercle.setCenterX(cercle.getCenterX() + Math.sin(angle) * vitesse);
				cercle.setCenterY(cercle.getCenterY() + Math.cos(angle) * vitesse);
				cercle.setOpacity(vitesse/5);
				vitesse /= 1.1;
			}
		};
		anim.start();
	}
}