package util;

import javafx.scene.layout.Pane;

public class CustomPanel extends Pane{

	public double x, y, w, h;
	
	public CustomPanel(double w, double h) {
		super();
		this.w=w;this.h=h;
	}

	public void translateX(double dx){
		x-=dx;
		if(x>0) x=0;
		if(x<-w+1920) x=-w+1920;
		this.setTranslateX(x);
	}
	public void translateY(double dy){
		y-=dy;
		if(y>0) y=0;
		if(y<-h+1080) y=-h+1080;
		this.setTranslateY(y);
	}
	
	
}
