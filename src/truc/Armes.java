package truc;

import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Polygon;

public abstract class Armes {
	int munition;
	CustomPanel pan;
	LinkedList<Polygon> l_obs;
	LinkedList<LinkedList<Double>> l_tri;
	double mapSizeW, mapSizeH;
	
	public void tire(){}
	public void tire(double depX, double depY, double finX, double finY){}
}

class Gun extends Armes{
	
	double vitesse;
	public double cdArme, cd;
	
	public Gun(double cdArme, int munition, double vitesse, LinkedList<LinkedList<Double>> l_tri, LinkedList<Polygon> l_obs, CustomPanel pan, double mapSizeW, double mapSizeH){
		this.mapSizeW = mapSizeW;
		this.mapSizeH = mapSizeH;
		this.munition = munition;
		this.vitesse = vitesse;
		this.cdArme = cdArme;
		this.l_obs = l_obs;
		this.pan = pan;
		this.l_tri = l_tri;
		this.cd = 0; 
	}
	
	public void tire(double depX, double depY, double finX, double finY){
		if (cd <= 0){
			new Bullet(depX, depY, finX, finY, vitesse, l_tri, l_obs, pan, mapSizeW, mapSizeH).start();
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