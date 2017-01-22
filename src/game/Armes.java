package game;

import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Armes {
	int munition;
	Game game;
	Image img;
	ImageView imgView;
	double reductionVitesse;
	double degats;
	
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
		this.img = new Image("file:/" + (new File("").getAbsolutePath().toString()).replaceAll("\\\\", "/") + "/assets/gun.png");
		this.imgView = new ImageView(img);
		this.reductionVitesse = 0.8;
	}
	
	public void tire(double depX, double depY, double finX, double finY){
		if (cd <= 0){
			new Bullet(depX, depY, finX, finY, vitesse, degats, game).start();
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
		this.img = new Image("file:/" + (new File("").getAbsolutePath().toString()).replaceAll("\\\\", "/") + "/assets/miniGun.png");
		this.imgView = new ImageView(img);
		this.reductionVitesse = 0.3;
	}
	
	public void tire(double depX, double depY, double finX, double finY){
		if (cd <= 0){
			new Bullet(depX, depY, finX, finY, vitesse, degats, game).start();
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