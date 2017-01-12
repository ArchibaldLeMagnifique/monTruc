package game;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Joueur {

	double x, y;
	
	Circle c= new Circle(8);
	
	public Joueur(double x, double y, Pane p){
		this.x=x;this.y=y;
		c.setCenterX(x);
		c.setCenterY(y);
		c.getStyleClass().add("joueur1");
		p.getChildren().add(c);
		
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public void translate(double x, double y){
		this.x+=x;this.y+=y;
		c.setCenterX(this.x);
		c.setCenterY(this.y);
		
	}
	
}
