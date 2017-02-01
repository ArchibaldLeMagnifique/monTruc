package game;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import maps.BasicMap;
import menu.Main;
import pause.Pause;
import tyrandules.*;
import util.Graphe;
import util.MyPolygon;
import util.Point;
import util.Segment;
import util.Cell;
import util.CustomPanel;

public class Game {

	Stage primaryStage;
	Scene sceneGame;
	LinkedList<Polygon> l_obs = new LinkedList<Polygon>();
	LinkedList<Double> l_pts = new LinkedList<Double>();
	public LinkedList<LinkedList<Double>> l_l_triangles = new LinkedList<LinkedList<Double>>();;

	double depCamX, depCamY, depCamX2, depCamY2;
	
	Graphe graphe;
	public Joueur j;
	boolean clicDroit = false;
	boolean spaceKey = false;
	double mouseX, mouseY;
	
	boolean tirer = false;
	int choixArme = 1;
	int nbArmes = 3;
	Armes[] listeArmes = new Armes[nbArmes];
	
	LinkedList<Double> flagX = new LinkedList<Double>();
	LinkedList<Double> flagY = new LinkedList<Double>();
	
	public int screenWidth, screenHeight, mapSizeW, mapSizeH;

	Main monPapa; Game me;
	public CustomPanel pan;
	public LinkedList<Ennemis> l_ennemis = new LinkedList<Ennemis>();
	public ArrayList<AnimationTimer> aPauser;
	AnimationTimer jeu;
	public UI ui;
	int vague = 1;
	
	long crystals = 0L;
	long researchs = 0L;

	public Game(int W, int H, Stage primaryStage, Main main){
		this.aPauser = new ArrayList<AnimationTimer> ();
		this.screenWidth = W;
		this.screenHeight = H;
		this.monPapa = main;
		this.start(primaryStage);
		this.me = this;
	}
	
	public void retourMenu(Stage primaryStage) {monPapa.createScene(primaryStage); }
	
	public void pauseMenu(Stage primaryStage) {
		for(int i=0; i<aPauser.size(); i++){
			aPauser.get(i).stop();
		}
		clicDroit = false;
		spaceKey = false;
		tirer = false;
		new Pause(screenWidth, screenHeight, primaryStage, this); 
	}
	
	public void unPause(){
		for(int i=0; i<aPauser.size(); i++){
			aPauser.get(i).start();
		}
		primaryStage.setScene(sceneGame);
	}
	
	
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		Group rootGame = new Group();
		sceneGame = new Scene(rootGame, screenWidth, screenHeight);
		sceneGame.getStylesheets().add("GameStage.css");
		
		Image curseur = new Image("file:/" + (new File("").getAbsolutePath().toString()).replaceAll("\\\\", "/") + "/assets/normal.png");
		sceneGame.setCursor(new ImageCursor(curseur));

		primaryStage.setScene(sceneGame);

		
		BasicMap map = new BasicMap();
		this.mapSizeH = map.mapSizeH;
		this.mapSizeW = map.mapSizeW;
		
		pan = new CustomPanel(map.mapSizeW, map.mapSizeH);
		pan.setPrefWidth(screenWidth);
		pan.setPrefHeight(screenHeight);
		rootGame.getChildren().add(pan);

		pan.translateX(map.mapSizeW/2-screenWidth/2);
		pan.translateY(map.mapSizeH/2-screenHeight/2+40);
		
		Rectangle leFont = new Rectangle();
		leFont.setWidth(map.mapSizeW);
		leFont.setHeight(map.mapSizeH);
		leFont.getStyleClass().add("back1");
		pan.getChildren().add(leFont);

		
		map.createMap(l_obs, pan, l_l_triangles, l_pts);
		
		graphe = new Graphe(l_obs, pan);
		
		// ---   Bords   ---
		Polygon poly = new Polygon();
		poly.getPoints().addAll(new Double[] { 0.0, 0.0, 0.0, 50.0, (double)map.mapSizeW, 50.0, (double)map.mapSizeW, 0.0 });
		poly.getStyleClass().add("obstacle1");
		pan.getChildren().add(poly);
		poly = new Polygon();
		poly.getPoints().addAll(new Double[] { 0.0, 0.0, 0.0, (double)map.mapSizeH, 50.0, (double)map.mapSizeH, 50.0, 0.0 });
		poly.getStyleClass().add("obstacle1");
		pan.getChildren().add(poly);
		poly = new Polygon();
		poly.getPoints().addAll(new Double[] { 0.0, (double)map.mapSizeH-50, 0.0, (double)map.mapSizeH, (double)map.mapSizeW, (double)map.mapSizeH, (double)map.mapSizeW, (double)map.mapSizeH-50 });
		poly.getStyleClass().add("obstacle1");
		pan.getChildren().add(poly);
		poly = new Polygon();
		poly.getPoints().addAll(new Double[] { (double)map.mapSizeW-50, 0.0, (double)map.mapSizeW-50, (double)map.mapSizeH, (double)map.mapSizeW, (double)map.mapSizeH, (double)map.mapSizeW, 0.0 });
		poly.getStyleClass().add("obstacle1");
		pan.getChildren().add(poly);

		j = new Joueur(map.mapSizeW/2, map.mapSizeH/2, pan);
		double vitesse = 4;
		
		new Vague (vague, this);
		
		ui = new UI(rootGame, this);
		
		
		jeu = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				if (depCamX != 0 | depCamX2 != 0) {
					if (depCamX2 == 0) {
						pan.translateX(depCamX);
						ui.miniMap.translateCamX(depCamX);
					} else {
						pan.translateX(depCamX2);
						ui.miniMap.translateCamX(depCamX2);
					}
				}
				if (depCamY != 0 | depCamY2 != 0) {
					if (depCamY2 == 0) {
						pan.translateY(depCamY);
						ui.miniMap.translateCamY(depCamY);
					} else {
						pan.translateY(depCamY2);
						ui.miniMap.translateCamY(depCamY2);
					}
				}
				if (spaceKey){
					pan.x = -j.x + screenWidth/2;pan.translateX(0);
					pan.y = -j.y + screenHeight/2;pan.translateY(0);
					ui.miniMap.camX = -pan.x; ui.miniMap.translateCamX(0);
					ui.miniMap.camY = -pan.y; ui.miniMap.translateCamY(0);
				}
				if (flagX.size() != 0 & flagY.size() != 0) {
					double dx = flagX.get(0) - j.getX();
					double dy = flagY.get(0) - j.getY();
					if (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) < 4) {
						j.x=flagX.get(0)+0.0021;
						j.y=flagY.get(0)+0.0012;
						flagX.removeFirst();
						flagY.removeFirst();
					}
					double mod = Math.max((double) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) , 10);
					if (tirer) mod/=listeArmes[choixArme-1].reductionVitesse;
					dx = dx / mod * vitesse;
					dy = dy / mod * vitesse;
					j.translate(dx, dy);
					ui.miniMap.updateJoueur(j);
				}
				if (tirer){
					if (!(j.x == mouseX & j.y == mouseY)){
						listeArmes[choixArme-1].tire(j.x, j.y, mouseX, mouseY);
					}
				}

			}
		}; aPauser.add(jeu);

		sceneGame.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent g) {
				switch(g.getCode().toString()) {
				case "UP":
					depCamY = -15;
					break;
				case "DOWN":
					depCamY = 15;
					break;
				case "LEFT":
					depCamX = -15;
					break;
				case "RIGHT":
					depCamX = 15;
					break;
				case "SPACE":
					spaceKey = true;
					pan.x = -j.x + screenWidth/2;pan.translateX(0);
					pan.y = -j.y + screenHeight/2;pan.translateY(0);
					ui.miniMap.camX = -pan.x; ui.miniMap.translateCamX(0);
					ui.miniMap.camY = -pan.y; ui.miniMap.translateCamY(0);
					break;
				case "ESCAPE":
					pauseMenu(primaryStage);
				}
				if (g.getCode().toString().startsWith("DIGIT")){
					int arme = Integer.parseInt(g.getCode().toString().substring(5));
					if (arme == choixArme & listeArmes[choixArme-1]!=null){
						tirer = true;
					}else{
						if (arme <= nbArmes){
							choixArme = arme;
							tirer = false;
							ui.weaponBar.selection = choixArme;
							ui.weaponBar.start();
						}
					}
					
				}
			}
		});

		sceneGame.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent g) {
				switch(g.getCode().toString()) {
				case "UP" : case "DOWN" :
					depCamY = 0; break;
				case "LEFT" : case "RIGHT" :
					depCamX = 0; break;
				case "SPACE" :
					spaceKey = false; break;

				}
				if (g.getCode().toString().startsWith("DIGIT")){
					tirer = false;
				}
			}

		});
		
		pan.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				mouseX = m.getScreenX()-pan.x; mouseY = m.getScreenY()-pan.y;
				depCamX2 = 0;
				depCamY2 = 0;
				if (m.getScreenX() < 10) {
					depCamX2 = -15;
				}
				if (m.getScreenX() > 1910) {
					depCamX2 = 15;
				}
				if (m.getScreenY() < 10) {
					depCamY2 = -15;
				}
				if (m.getScreenY() > 1070) {
					depCamY2 = 15;
				}
			}
		});
		
		pan.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				mouseX = m.getScreenX()-pan.x; mouseY = m.getScreenY()-pan.y;
				if (m.getX() > 50 & m.getX() < map.mapSizeW-50 & m.getY() > 50 & m.getY() < map.mapSizeH-50 & m.getButton().toString()=="SECONDARY") {
					findPath(j.x,j.y, m.getX(), m.getY(), l_l_triangles, pan, me);
				}
				depCamX2 = 0;
				depCamY2 = 0;
				if (m.getScreenX() < 10) {
					depCamX2 = -15;
				}
				if (m.getScreenX() > 1910) {
					depCamX2 = 15;
				}
				if (m.getScreenY() < 10) {
					depCamY2 = -15;
				}
				if (m.getScreenY() > 1070) {
					depCamY2 = 15;
				}
			}
		});
		

		pan.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				if (m.getButton().toString() == "PRIMARY") {

				}
				if (m.getButton().toString() == "SECONDARY" & m.getX() > 50 & m.getX() < map.mapSizeW-50 & m.getY() > 50 & m.getY() < map.mapSizeH-50) {
					clicDroit = true;
					sceneGame.setCursor(Cursor.NONE);
					findPath(j.x,j.y, m.getX(), m.getY(), l_l_triangles, pan, me);
				}
			}
		});
		pan.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				if (m.getButton().toString() == "PRIMARY") {

				}
				if (m.getButton().toString() == "SECONDARY") {
					clicDroit = false;
					sceneGame.setCursor(new ImageCursor(curseur));
				}
			}
		});
		
		primaryStage.show();
		jeu.start();
	}

	
	
	public int findPath(double debX, double debY, double x, double y, LinkedList<LinkedList<Double>> l_l_triangles, CustomPanel pan, Object obj) {
		double tab[] = repositionnePts(x, y, l_l_triangles, l_obs, pan);
		double destX = tab[0];
		double destY = tab[1];
		tab = repositionnePts(debX, debY, l_l_triangles, l_obs, pan);
		double depX = tab[0];
		double depY = tab[1];
		if (obj.getClass().toString().equals("class game.Game"))
			new curseurFx(destX,destY,8,pan).start();
		
		graphe.setDebut(l_obs, depX, depY);
		graphe.setFin(l_obs, destX, destY);
		
		if (graphe.getDebut().size()==0 | graphe.getFin().size()==0 | graphe.getDebut().size()==graphe.getGraphe().size() | graphe.getFin().size()==graphe.getGraphe().size()) return 0;

		
		int nb_sommets = graphe.getGraphe().size()+2;
		
		double[] d = new double[nb_sommets];
		int[] predecesseur = new int[nb_sommets];
		LinkedList<Integer> sommets = new LinkedList<Integer> ();
		LinkedList<LinkedList<Cell>> graphe_complet = new LinkedList<LinkedList<Cell>>();
		//---   Initialisation   ---
		graphe_complet.add(graphe.getDebut());
		for(int i=0; i<nb_sommets; i++){
			d[i]=Integer.MAX_VALUE;
			sommets.add(i);
			if (i<nb_sommets-2) graphe_complet.add(graphe.getGraphe().get(i));
		}
		graphe_complet.add(graphe.getFin());
		d[0]=0;
		for(int i=0; i<graphe_complet.getLast().size(); i++){
			graphe_complet.get(graphe_complet.getLast().get(i).getPoint()).add(new Cell(nb_sommets-1, graphe_complet.getLast().get(i).getDistance()));
		}
		for(int i=0; i<graphe_complet.getFirst().size(); i++){
			graphe_complet.get(graphe_complet.getFirst().get(i).getPoint()).addFirst(new Cell(0, graphe_complet.getFirst().get(i).getDistance()));
		}

		
		//---   de la redondance comme on les aime   ---
		Segment seg = new Segment(new Point(depX, depY), new Point(destX,destY));
		boolean coupe = false;
		for (int m = 0; m < l_obs.size(); m++) {
			Object[] point3 = l_obs.get(m).getPoints().toArray();
			MyPolygon p = new MyPolygon();
			for(int n=0; n<point3.length/2;n++){
				p.addVertex(((Double)point3[2*n]).intValue(),  ((Double)point3[2*n+1]).intValue());
			}
			if (p.Intersect(seg)) coupe =true;
			
		}
		if(coupe==false){
			graphe_complet.get(0).addLast(new Cell(nb_sommets-1, distance(depX, depY, destX, destY)));
			graphe_complet.get(nb_sommets-1).addFirst(new Cell(0, distance(depX, depY, destX, destY)));
		}

		//---   Dijkstra   ---
		int s1;
		while (sommets.isEmpty()==false){
			s1 = trouveMin(sommets, d);
			sommets.removeFirstOccurrence(s1);
			for(int i=0; i<graphe_complet.get(s1).size(); i++){
				if (d[graphe_complet.get(s1).get(i).getPoint()] > d[s1] + graphe_complet.get(s1).get(i).getDistance()){
					d[graphe_complet.get(s1).get(i).getPoint()] = d[s1] + graphe_complet.get(s1).get(i).getDistance();
					predecesseur[graphe_complet.get(s1).get(i).getPoint()] = s1;
				}
			}
		}
		
		//-- maj des drapeaux   ---
		synchronized(this){
			int s;
			switch (obj.getClass().toString()){
			case "class game.Game":
				((Game) obj).flagX = new LinkedList<Double>();
				((Game) obj).flagY = new LinkedList<Double>();
				s = nb_sommets-1;
				while(s!=0){
					if (s!=nb_sommets-1){
						((Game) obj).flagX.addFirst(l_pts.get(2*(s-1)));
						((Game) obj).flagY.addFirst(l_pts.get(2*(s-1)+1));
					}
					s=predecesseur[s];
				}
				((Game) obj).flagX.addLast(destX);
				((Game) obj).flagY.addLast(destY);
				break;
			case "class tyrandules.Suiveur":
				((tyrandules.Ennemis) obj).flagX = new LinkedList<Double>();
				((tyrandules.Ennemis) obj).flagY = new LinkedList<Double>();
				s = nb_sommets-1;
				while(s!=0){
					if (s!=nb_sommets-1){
						((tyrandules.Ennemis) obj).flagX.addFirst(l_pts.get(2*(s-1)));
						((tyrandules.Ennemis) obj).flagY.addFirst(l_pts.get(2*(s-1)+1));
					}
					s=predecesseur[s];
				}
				((tyrandules.Ennemis) obj).flagX.addLast(destX);
				((tyrandules.Ennemis) obj).flagY.addLast(destY);
				break;
			default:
				return 2;
			}
		}

		//---   retour a letat initial   ---
		if (coupe==false){
			graphe_complet.get(nb_sommets-1).removeFirstOccurrence(new Cell(0, distance(depX, depY, destX, destY)));
			graphe_complet.get(0).removeFirstOccurrence(new Cell(nb_sommets-1, distance(depX, depY, destX, destY)));
		}
		
		for(int i=0; i<graphe_complet.getLast().size(); i++){
			graphe_complet.get(graphe_complet.getLast().get(i).getPoint()).removeLast();
		}
		for(int i=0; i<graphe_complet.getFirst().size(); i++){
			graphe_complet.get(graphe_complet.getFirst().get(i).getPoint()).removeFirst();
		}

		return 0;
	}
	
	private int trouveMin(LinkedList<Integer> sommets, double[] d){//un truc de Dijkstra
		double mini = Integer.MAX_VALUE;
		int sommet_mini = -1;
		for(int i=0; i<sommets.size(); i++){
			if (d[sommets.get(i)] < mini){
				mini = d[sommets.get(i)];
				sommet_mini = sommets.get(i);
			}
		}
		return sommet_mini;
	}

	
	public double[] repositionnePts(double x, double y, LinkedList<LinkedList<Double>> l_l_triangles, LinkedList<Polygon> l_obs, CustomPanel pan){
		Polygon res = new Point(x,y).ptsDans(l_l_triangles, l_obs,pan);
		if (res==null) return new double[] {x, y};
		
		double d = Double.MAX_VALUE;
		double resX=0, resY=0;
		for(int i=0; i<res.getPoints().size()/2; i++){
			double Xa = res.getPoints().get(2*i);
			double Ya = res.getPoints().get(2*i+1);
			double Xb = res.getPoints().get((2*i+2) % res.getPoints().size());
			double Yb = res.getPoints().get((2*i+3) % res.getPoints().size());
			double AIx = x-Xa;
			double AIy = y-Ya;
			double ABx = Xb-Xa;
			double ABy = Yb-Ya;
			double norme = Math.sqrt(Math.pow(ABx, 2)+Math.pow(ABy, 2));
			ABx/=norme;
			ABy/=norme;
			double AH = AIx*ABx + AIy*ABy;
			double Xh = Xa + AH/Math.sqrt(Math.pow(ABx, 2)+Math.pow(ABy, 2)) * ABx;
			double Yh = Ya + AH/Math.sqrt(Math.pow(ABx, 2)+Math.pow(ABy, 2)) * ABy;
			
			double Xmid = (Xa+Xb)/2;
			double Ymid = (Ya+Yb)/2;
			
			if (!(Xh<=Math.max(Xa, Xb) & Xh>=Math.min(Xa, Xb) & Yh<=Math.max(Ya, Yb) & Yh>=Math.min(Ya, Yb))){
				if (Xh<Xmid) {Xh = Math.min(Xa, Xb);}
				else {Xh = Math.max(Xa, Xb);}
				if (Yh<Ymid) {Yh = Math.min(Ya, Yb);}
				else {Yh = Math.max(Ya, Yb);}
			}
			if (distance(x,y,Xh,Yh)<d){
				d = distance(x,y,Xh,Yh);
				double IHx = Xh-x;
				double IHy = Yh-y;
				norme = Math.sqrt(Math.pow(IHx, 2)+Math.pow(IHy, 2));
				resX=Xh + IHx/norme;
				resY=Yh + IHy/norme;
			}
		}
		return new double[] {resX, resY};
	}
	
	
	
	public double distance(double a, double b, double c, double d) {
		return Math.sqrt(Math.pow(c - a, 2) + Math.pow(d - b, 2));
	}

}