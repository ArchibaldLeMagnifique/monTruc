package game;

import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public abstract class Armes {
	int munition;
	Game game;
	Image img;
	ImageView imgView;
	double reductionVitesse;
	double degats, range;
	
	Circle cercleRange;
	
	Timeline t = new Timeline(
			new KeyFrame (Duration.seconds(0.015), event -> {
				cercleRange.setCenterX(game.j.x);
				cercleRange.setCenterY(game.j.y);
			})
			);
	
	public void tire(){}
	public void tire(double depX, double depY, double finX, double finY){}
}

class Gun extends Armes{
	
	double vitesse;
	public double cdArme, cd;
		
	public Gun(double cdArme, int munition, double vitesse, double degats, Game game){
		this.game = game;
		this.munition = munition;
		this.vitesse = vitesse;
		this.degats = degats;
		this.cdArme = cdArme;
		this.cd = 0;
		this.range = 500;
		this.img = new Image("file:/" + (new File("").getAbsolutePath().toString()).replaceAll("\\\\", "/") + "/assets/gun.png");
		this.imgView = new ImageView(img);

		this.reductionVitesse = 0.8;
		
	}
	
	public void tire(double depX, double depY, double finX, double finY){
		if (cd <= 0){
			new Bullet(depX, depY, finX, finY, vitesse, degats, range, game).start();
			cd = cdArme;
			decCd.start();
		}
	}
	
	AnimationTimer decCd = new AnimationTimer() {
		public void handle(long arg0) {
			cd-=1.0/60;
			if (cd<0) this.stop();
		}
	};
}




class MiniGun extends Armes{
	
	double vitesse;
	public double cdArme, cd;
		
	public MiniGun(double cdArme, int munition, double vitesse, double degats, Game game){
		this.game = game;
		this.munition = munition;
		this.vitesse = vitesse;
		this.degats = degats;
		this.cdArme = cdArme;
		this.cd = 0;
		this.range = 300;
		this.img = new Image("file:/" + (new File("").getAbsolutePath().toString()).replaceAll("\\\\", "/") + "/assets/miniGun.png");
		this.imgView = new ImageView(img);
		
		this.reductionVitesse = 0.3;
		
	}
	
	public void tire(double depX, double depY, double finX, double finY){
		if (cd <= 0){
			new Bullet(depX, depY, finX, finY, vitesse, degats, range, game).start();
			cd = cdArme;
			decCd.start();
		}
	}
	
	AnimationTimer decCd = new AnimationTimer() {
		public void handle(long arg0) {
			cd-=1.0/60;
			if (cd<0) this.stop();
		}
	};
}