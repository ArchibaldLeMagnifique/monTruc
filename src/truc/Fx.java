package truc;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class curseurFx{
	Circle cercle;
	double taille;
	CustomPanel pan;
	public curseurFx(double x, double y, int size, CustomPanel pan){
		this.cercle = new Circle(size);
		this.taille=1;
		this.cercle.setCenterX(x);
		this.cercle.setCenterY(y);
		this.cercle.setFill(Color.WHITE);
		this.pan=pan;
		this.pan.getChildren().add(this.cercle);
	}
	
	public void start() {
		AnimationTimer jeu = new AnimationTimer() {
			public void handle(long arg0) {
				cercle.setOpacity(taille);
				cercle.setScaleX(taille-=0.15);
				cercle.setScaleY(taille);
				if (cercle.isVisible()==false){
					pan.getChildren().remove(cercle);
					this.stop();
				}
				if (taille<0) {
					cercle.setScaleX(1);
					cercle.setScaleY(1);
					cercle.setVisible(false);
				}
			}
			
		};
		jeu.start();
	}

	
	

	
}



