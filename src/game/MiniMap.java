package truc;

import java.util.LinkedList;

import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class MiniMap extends Pane{

	double dimension = 1;
	double screenSizeW, screenSizeH;
	double mapSizeW, mapSizeH;
	double coef=0;
	
	Rectangle rekt;
	Rectangle cam;
	public double camX=0, camY=0;
	Circle joueur;
	LinkedList<Polygon> l_obs;
	LinkedList<LinkedList<Double>> l_l_triangles;
	
	boolean clicGauche = false, clicDroit = false;
	
	public MiniMap(int w, int h, int mapSizeW, int mapSizeH, Main main, LinkedList<LinkedList<Double>> l_l_triangles, CustomPanel pan, Joueur joueur) {
		super();
		this.screenSizeW = w;
		this.screenSizeH = h;
		this.mapSizeW = mapSizeW;
		this.mapSizeH = mapSizeH;
		this.setPrefWidth(350 * dimension);
		this.setPrefHeight(300 * dimension);
		this.setStyle("-fx-background-color: #101010; -fx-border-width: 6; -fx-border-color: #5e4526; -fx-border-radius: 5; -fx-background-radius: 5");
		this.setTranslateX(w-this.getPrefWidth()-5);
		this.setTranslateY(h-this.getPrefHeight()-5);
		
		rekt = new Rectangle();
		this.coef = Math.min((this.getPrefWidth()-12)/this.mapSizeW, (this.getPrefHeight()-12)/this.mapSizeH);
		rekt.setHeight(this.coef * this.mapSizeH * dimension);
		rekt.setWidth(this.coef * this.mapSizeW * dimension);
		rekt.setTranslateX(this.getPrefWidth()/2-rekt.getWidth()/2);
		rekt.setTranslateY(this.getPrefHeight()/2-rekt.getHeight()/2);
		rekt.getStyleClass().add("back1");
		this.getChildren().add(rekt);
		
		for(int i=0; i<pan.getChildren().size(); i++){
			if (pan.getChildren().get(i).getClass().getName() == "javafx.scene.shape.Polygon"){
				Polygon poly = (Polygon) pan.getChildren().get(i);
				Polygon poly2 = new Polygon();
				l_obs = new LinkedList<Polygon> ();
				for(int j=0; j<poly.getPoints().size(); j++){
					if (j%2==0){
						poly2.getPoints().add(poly.getPoints().get(j) * coef * dimension + rekt.getTranslateX());
					}else{
						poly2.getPoints().add(poly.getPoints().get(j) * coef * dimension + rekt.getTranslateY());
					}
				}
				poly2.getStyleClass().add("obstacle1");
				l_obs.add(poly2);
				this.getChildren().add(poly2);
			}
		}
		
		this.cam = new Rectangle();
		this.cam.setFill(new Color(0,0,0,0));
		this.cam.setStroke(Color.web("#5e4526"));
		this.cam.setStrokeWidth(2);
		updateCam();
		translateCamX(0);
		translateCamY(0);
		this.cam.setWidth(this.screenSizeW * coef * dimension);
		this.cam.setHeight(this.screenSizeH * coef * dimension);
		this.getChildren().add(this.cam);
		
		this.joueur = new Circle(5);
		this.joueur.getStyleClass().add("joueur1");
		updateJoueur(joueur);
		this.getChildren().add(this.joueur);
		
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<InputEvent>() {
			public void handle(InputEvent m) {
				if (((MouseEvent) m).getButton().toString() == "PRIMARY") {
					clicGauche = true;
					pan.x = -(((MouseEvent) m).getX() - rekt.getTranslateX() - cam.getWidth()/2)/ coef / dimension;
					pan.translateX(0);
					pan.y = -(((MouseEvent) m).getY() - rekt.getTranslateY() - cam.getHeight()/2)/ coef / dimension;
					pan.translateY(0);
					camX = (((MouseEvent) m).getX() - rekt.getTranslateX() - cam.getWidth()/2)/ coef / dimension;
					translateCamX(0);
					camY = (((MouseEvent) m).getY() - rekt.getTranslateY() - cam.getHeight()/2)/ coef / dimension;
					translateCamY(0);
				}
				if (((MouseEvent) m).getButton().toString() == "SECONDARY") {
					clicDroit = true;
					double clicX = (((MouseEvent) m).getX()-rekt.getTranslateX())/coef/dimension;
					double clicY = (((MouseEvent) m).getY()-rekt.getTranslateY())/coef/dimension;
					if (clicX > 50 & clicX < mapSizeW-50 & clicY > 50 & clicY < mapSizeH-50)
						main.findPath(clicX, clicY, l_l_triangles, pan);
				}
			}
		});
		
		this.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<InputEvent>() {
			public void handle(InputEvent m) {
				if (((MouseEvent) m).getButton().toString() == "PRIMARY") clicGauche = false;
				if (((MouseEvent) m).getButton().toString() == "SECONDARY") clicDroit = false;
			}
		});
		
		this.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<InputEvent>() {
			public void handle(InputEvent m) {
				if (clicGauche == true){
					pan.x = -(((MouseEvent) m).getX() - rekt.getTranslateX() - cam.getWidth()/2)/ coef / dimension;
					pan.translateX(0);
					pan.y = -(((MouseEvent) m).getY() - rekt.getTranslateY() - cam.getHeight()/2)/ coef / dimension;
					pan.translateY(0);
					camX = (((MouseEvent) m).getX() - rekt.getTranslateX() - cam.getWidth()/2)/ coef / dimension;
					translateCamX(0);
					camY = (((MouseEvent) m).getY() - rekt.getTranslateY() - cam.getHeight()/2)/ coef / dimension;
					translateCamY(0);
				}
				if (clicDroit == true){
					double clicX = (((MouseEvent) m).getX()-rekt.getTranslateX())/coef/dimension;
					double clicY = (((MouseEvent) m).getY()-rekt.getTranslateY())/coef/dimension;
					if (clicX > 50 & clicX < mapSizeW-50 & clicY > 50 & clicY < mapSizeH-50)
						main.findPath(clicX, clicY, l_l_triangles, pan);
				}
			}
		});
	}

	
	public void translateCamX(double dep){
		this.camX+=dep;
		if(this.camX<0) this.camX=0;
		if(this.camX>this.mapSizeW-this.screenSizeW) this.camX=this.mapSizeW-this.screenSizeW;
		this.cam.setTranslateX(this.camX * coef * dimension + rekt.getTranslateX());
	}
	public void translateCamY(double dep){
		this.camY+=dep;
		if(this.camY<0) this.camY=0;
		if(this.camY>this.mapSizeH-this.screenSizeH) this.camY=this.mapSizeH-this.screenSizeH;
		this.cam.setTranslateY(this.camY * coef * dimension + rekt.getTranslateY());
	}
	
	
	private void updateCam(){
		this.cam.setWidth(this.screenSizeW * coef * dimension);
		this.cam.setHeight(this.screenSizeH * coef * dimension);
	}
	
	public void updateJoueur(Joueur joueur){
		this.joueur.setCenterX(joueur.getX() * coef * dimension + rekt.getTranslateX());
		this.joueur.setCenterY(joueur.getY() * coef * dimension + rekt.getTranslateY());
	}
	
	
}
