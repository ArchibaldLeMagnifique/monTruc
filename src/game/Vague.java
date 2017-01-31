package game;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import tyrandules.Suiveur;
import util.Point;

public class Vague {

	Game game;
	int id;
	int nbEnnemis, nbEnnemisCourant;
	
	double timer = 0;
	
	public Vague (int id, Game game) {
		this.game = game;
		this.nbEnnemis = 25;
		this.nbEnnemisCourant = 0;	
		start();

	}

	public void start() {
		Timeline t = new Timeline(
				new KeyFrame (Duration.seconds(1), event -> {
					Polygon p = new Polygon();
					double x = 0, y = 0;
					while (!(p == null)) {
						x = Math.random() * ( game.mapSizeW - 50 ) + 50;
						y = Math.random() * ( game.mapSizeH - 50 ) + 50;
						p = new Point(x,y).ptsDans(game.l_l_triangles, game.l_obs, game.pan);
					}
					new Suiveur(x, y, game, 2, 7, 4);
					timer = 0;
					nbEnnemisCourant++;
					if (nbEnnemisCourant >= nbEnnemis){
						p = new Polygon();
						x = 0;
						y = 0;
						while (!(p == null)) {
							x = Math.random() * ( game.mapSizeW - 50 ) + 50;
							y = Math.random() * ( game.mapSizeH - 50 ) + 50;
							p = new Point(x,y).ptsDans(game.l_l_triangles, game.l_obs, game.pan);
						}
						new Suiveur(x, y, game, 2, 20, 20);
					}
				})
				);
		t.setCycleCount(nbEnnemis);
		t.play();
	}




	/*
	public void start () {
		AnimationTimer anim = new AnimationTimer() {
			public void handle(long arg0) {
				Polygon p = new Polygon();
				double x = 0, y = 0;
				while (!(p == null)) {
					x = Math.random() * ( game.mapSizeW - 50 ) + 50;
					y = Math.random() * ( game.mapSizeH - 50 ) + 50;
					p = new Point(x,y).ptsDans(game.l_l_triangles, game.l_obs, game.pan);
				}
				new Suiveur(x, y, game, 2, 7, 4);
				timer = 0;
				nbEnnemisCourant++;
				if (nbEnnemisCourant >= nbEnnemis){
					p = new Polygon();
					x = 0;
					y = 0;
					while (!(p == null)) {
						x = Math.random() * ( game.mapSizeW - 50 ) + 50;
						y = Math.random() * ( game.mapSizeH - 50 ) + 50;
						p = new Point(x,y).ptsDans(game.l_l_triangles, game.l_obs, game.pan);
					}
					new Suiveur(x, y, game, 2, 20, 20);
					this.stop();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {e.printStackTrace(); }

			}
		};
		game.aPauser.add(anim);
		anim.start();
	}*/
}
