package tyrandules;

import game.Game;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Suiveur extends Ennemis{

	public Suiveur (double x, double y, Game game, double vitesse, int taille, int vie) {
		super(x,y,game,taille);
		this.vitesse = vitesse;
		game.l_ennemis.add((Ennemis)this);
		this.game = game;
		this.size = taille;
		this.x = x;
		this.y = y;
		
		this.vie = vie;
		this.vieTotale = vie;
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
					Ennemis e = null;
					for(int i=0; i<game.l_ennemis.size(); i++){
						if (game.distance(cercle.getCenterX() + dx, cercle.getCenterY() + dy, game.l_ennemis.get(i).cercle.getCenterX(), game.l_ennemis.get(i).cercle.getCenterY()) < game.l_ennemis.get(i).size + size & game.l_ennemis.get(i)!=me) {
							deplacement = false;
							e = game.l_ennemis.get(i);
						}
					}
					if (deplacement){
						deplace(dx,dy);
					}else{
						double midX = cercle.getCenterX() + dx;
						double midY = cercle.getCenterY() + dy;
						double vectX = midX - e.cercle.getCenterX();
						double vectY = midY - e.cercle.getCenterY();
						double norme = Math.sqrt(Math.pow(vectX, 2) + Math.pow(vectY, 2));
						if (norme == 0){
							e.deplace(dx/2, dy/2);
						}else{
							vectX /= norme;
							vectY /= norme;
							double dist = game.distance(cercle.getCenterX() + dx, cercle.getCenterY() + dy, e.cercle.getCenterX(), e.cercle.getCenterY());
							double ecart = cercle.getRadius() + e.cercle.getRadius() - dist;
							e.deplace(-vectX * ecart/2, -vectY * ecart/2);
							deplace(vectX * ecart/2, vectY * ecart/2);
						}
					}
				}
			}
		};
		game.aPauser.add(anim);
		anim.start();
	}
}
