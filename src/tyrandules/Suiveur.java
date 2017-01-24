package tyrandules;

import game.Game;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Suiveur extends Ennemis{

	public Suiveur (double x, double y, Game game, double vitesse) {
		super(x,y,game,7);
		this.vitesse = vitesse;
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
		this.timer = 0;
		start();
		
	}
	
	public void start () {
		AnimationTimer anim = new AnimationTimer() {
			public void handle(long arg0) {
				if (timer >= 10){ timer = 0;
					game.findPath(cercle.getCenterX(), cercle.getCenterY(), game.j.getX(), game.j.getY(), game.l_l_triangles, game.pan, me);
				}else{
					timer++;
				}
				
				if (flagX.size() != 0 & flagY.size() != 0) {
					double dx = flagX.get(0) - cercle.getCenterX();
					double dy = flagY.get(0) - cercle.getCenterY();
					if (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) < 5) {
						flagX.removeFirst();
						flagY.removeFirst();
					}
					double mod = Math.max((double) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) , 10);
					dx = dx / mod * vitesse;
					dy = dy / mod * vitesse;
					boolean deplacement = true;
					for(int i=0; i<game.l_ennemis.size(); i++){
						if (game.distance(cercle.getCenterX() + dx, cercle.getCenterY() + dy, game.l_ennemis.get(i).cercle.getCenterX(), game.l_ennemis.get(i).cercle.getCenterY()) < game.l_ennemis.get(i).size + size & game.l_ennemis.get(i)!=me) deplacement = false;
					}
					if (deplacement){
						cercle.setCenterX(cercle.getCenterX() + dx);
						cercle.setCenterY(cercle.getCenterY() + dy);
						fontVie.setX(cercle.getCenterX()-fontVie.getWidth()/2);
						fontVie.setY(cercle.getCenterY()-size*1.7);
						rektVie.setX(cercle.getCenterX()-fontVie.getWidth()/2);
						rektVie.setY(cercle.getCenterY()-size*1.7);
					}		
				}
			}
		};
		anim.start();
	}
}
