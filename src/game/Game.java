package game;

import java.io.File;
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
import menu.Main;

public class Game {

	LinkedList<Polygon> l_obs = new LinkedList<Polygon>();
	LinkedList<Double> l_pts = new LinkedList<Double>();

	double depCamX, depCamY, depCamX2, depCamY2;
	
	Graphe graphe;
	Joueur j;
	boolean clicDroit = false;
	boolean spaceKey = false;
	double mouseX, mouseY;
	
	boolean tirer = false;
	int choixArme = 1;
	int nbArmes = 3;
	Armes[] listeArmes = new Armes[nbArmes];
	
	LinkedList<Double> flagX = new LinkedList<Double>();
	LinkedList<Double> flagY = new LinkedList<Double>();
	
	public int screenWidth = 1920;
	public int screenHeight = 1080;
	public int mapSizeW = 3200;
	public int mapSizeH = 2500;
	Main monPapa;
	CustomPanel pan;
	LinkedList<LinkedList<Double>> l_l_triangles;
	LinkedList<Ennemis> l_ennemis = new LinkedList<Ennemis>();;

	public Game(int W, int H, Stage primaryStage, Main main){
		this.screenWidth = W;
		this.screenHeight = H;
		this.monPapa = main;
		this.start(primaryStage);
	}
	
	public void retourMenu(Stage primaryStage) {monPapa.createScene(primaryStage); }
	
	
	public void start(Stage primaryStage) {
		Group rootGame = new Group();
		Scene sceneGame = new Scene(rootGame, screenWidth, screenHeight);
		sceneGame.getStylesheets().add("GameStage.css");
		
		Image curseur = new Image("file:/" + (new File("").getAbsolutePath().toString()).replaceAll("\\\\", "/") + "/assets/normal.png");
		sceneGame.setCursor(new ImageCursor(curseur));
		
		primaryStage.setScene(sceneGame);

		pan = new CustomPanel(mapSizeW, mapSizeH);
		pan.setPrefWidth(screenWidth);
		pan.setPrefHeight(screenHeight);
		rootGame.getChildren().add(pan);
		
		Rectangle leFont = new Rectangle();
		leFont.setWidth(mapSizeW);
		leFont.setHeight(mapSizeH);
		leFont.getStyleClass().add("back1");
		pan.getChildren().add(leFont);

		Polygon poly = new Polygon();
		poly.getPoints().addAll(new Double[] { 330.0, 270.0, 270.0, 330.0, 300.0, 500.0, 330.0, 520.0, 360.0, 500.0,
				400.0, 380.0, 650.0, 360.0, 720.0, 330.0, 700.0, 300.0, 500.0, 270.0 });
		poly.getStyleClass().add("obstacle1");
		l_obs.add(poly);
		pan.getChildren().add(poly);

		poly = new Polygon();
		poly.getPoints().addAll(new Double[] { 600.0, 600.0, 500.0, 800.0, 570.0, 870.0, 645.0, 790.0, 800.0, 730.0, 953.0, 780.0, 1200.0, 980.0, 1300.0, 930.0, 1330.0, 700.0, 1400.0, 450.0, 1300.0, 250.0, 1170.0, 260.0, 1120.0, 400.0, 1050.0, 500.0, 830.0, 530.0});
		poly.getStyleClass().add("obstacle1");
		l_obs.add(poly);
		pan.getChildren().add(poly);
		
		int fin = l_obs.size();
		for(int i=0; i<fin; i++){
			poly = new Polygon();
			for(int j=0; j<l_obs.get(i).getPoints().size(); j++){
				if (j%2==0){
					poly.getPoints().add(mapSizeW - l_obs.get(i).getPoints().get(j));
				}else{
					poly.getPoints().add(mapSizeH - l_obs.get(i).getPoints().get(j));
				}
			}
			poly.getStyleClass().add("obstacle1");
			l_obs.add(poly);
			pan.getChildren().add(poly);
			poly = new Polygon();
			for(int j=0; j<l_obs.get(i).getPoints().size(); j++){
				if (j%2==0){
					poly.getPoints().add(l_obs.get(i).getPoints().get(j));
				}else{
					poly.getPoints().add(mapSizeH - l_obs.get(i).getPoints().get(j));
				}
			}
			poly.getStyleClass().add("obstacle1");
			l_obs.add(poly);
			pan.getChildren().add(poly);
			poly = new Polygon();
			for(int j=0; j<l_obs.get(i).getPoints().size(); j++){
				if (j%2==0){
					poly.getPoints().add(mapSizeW - l_obs.get(i).getPoints().get(j));
				}else{
					poly.getPoints().add(l_obs.get(i).getPoints().get(j));
				}
			}
			poly.getStyleClass().add("obstacle1");
			l_obs.add(poly);
			pan.getChildren().add(poly);
		}
		
		//---   TRIANGULATION   ---
		l_l_triangles = new LinkedList<LinkedList<Double>>();
		for(int k=0; k<l_obs.size(); k++){
			poly = l_obs.get(k);
			int i;
			LinkedList<Double> l_triangles = new LinkedList<Double>();
			Polygon poly2 = new Polygon();
			for (i = 0; i < poly.getPoints().size(); i++) {poly2.getPoints().add(poly.getPoints().get(i));}
			i=0;
			double dd = 0;
			while (poly2.getPoints().size()>=6 & i<40) {
				double Ax = (double) poly2.getPoints().toArray()[(2 * i) % poly2.getPoints().size()];
				double Ay = (double) poly2.getPoints().toArray()[(2 * i + 1) % poly2.getPoints().size()];
				double Bx = (double) poly2.getPoints().toArray()[(2 * i + 2) % poly2.getPoints().size()];
				double By = (double) poly2.getPoints().toArray()[(2 * i + 3) % poly2.getPoints().size()];
				double Cx = (double) poly2.getPoints().toArray()[(2 * i + 4) % poly2.getPoints().size()];
				double Cy = (double) poly2.getPoints().toArray()[(2 * i + 5) % poly2.getPoints().size()];
				double Dx = Bx - Ax; double Dy = By - Ay;
				double Tx = Cx - Ax; double Ty = Cy - Ay;
				double d = Dx * Ty - Dy * Tx;
				Segment s = new Segment(new Point(Ax,Ay), new Point(Cx,Cy));
				MyPolygon p = new MyPolygon();
				for(int n=0; n<poly2.getPoints().toArray().length/2;n++){
					p.addVertex(((Double)poly2.getPoints().toArray()[2*n]).intValue(),  ((Double)poly2.getPoints().toArray()[2*n+1]).intValue());
				}
				if (dd == 0){
					dd = d;
				}else{
					if (d*dd >= 0 & p.IsFullInside(s)) {
						l_triangles.add(Ax); l_triangles.add(Ay);
						l_triangles.add(Bx); l_triangles.add(By);
						l_triangles.add(Cx); l_triangles.add(Cy);
						poly2.getPoints().remove((2 * i + 2) % poly2.getPoints().size());
						poly2.getPoints().remove((2 * i + 2) % (poly2.getPoints().size()+1));
						i--;
					}i++;
				}
			}
			l_l_triangles.add(l_triangles);
		}

		for(int i=0; i<l_obs.size(); i++){
			for(int j=0;j<l_obs.get(i).getPoints().size(); j++){
				l_pts.add(l_obs.get(i).getPoints().get(j));
			}
		}
		
		// ---   Creation du graphe   ---
		graphe = new Graphe(l_obs, pan);
		
		// ---   Bords   ---
		poly = new Polygon();
		poly.getPoints().addAll(new Double[] { 0.0, 0.0, 0.0, 50.0, (double)mapSizeW, 50.0, (double)mapSizeW, 0.0 });
		poly.getStyleClass().add("obstacle1");
		pan.getChildren().add(poly);
		poly = new Polygon();
		poly.getPoints().addAll(new Double[] { 0.0, 0.0, 0.0, (double)mapSizeH, 50.0, (double)mapSizeH, 50.0, 0.0 });
		poly.getStyleClass().add("obstacle1");
		pan.getChildren().add(poly);
		poly = new Polygon();
		poly.getPoints().addAll(new Double[] { 0.0, (double)mapSizeH-50, 0.0, (double)mapSizeH, (double)mapSizeW, (double)mapSizeH, (double)mapSizeW, (double)mapSizeH-50 });
		poly.getStyleClass().add("obstacle1");
		pan.getChildren().add(poly);
		poly = new Polygon();
		poly.getPoints().addAll(new Double[] { (double)mapSizeW-50, 0.0, (double)mapSizeW-50, (double)mapSizeH, (double)mapSizeW, (double)mapSizeH, (double)mapSizeW, 0.0 });
		poly.getStyleClass().add("obstacle1");
		pan.getChildren().add(poly);

		j = new Joueur(mapSizeW/2, mapSizeH/2, pan);
		double vitesse = 4;
		
		new EnnemiPassif(mapSizeW/2, mapSizeH/2+400,this);
		new EnnemiPassif(mapSizeW/2+500, mapSizeH/2,this);
		new EnnemiPassif(mapSizeW/2-500, mapSizeH/2,this);
		new EnnemiPassif(mapSizeW/2, mapSizeH/2-400,this);
		
		MiniMap miniMap = new MiniMap(screenWidth, screenHeight, mapSizeW, mapSizeH, this, l_l_triangles, pan, j);
		rootGame.getChildren().add(miniMap);
		
		pan.translateX(mapSizeW/2-screenWidth/2);
		pan.translateY(mapSizeH/2-screenHeight/2+40);
		miniMap.translateCamX(mapSizeW/2-screenWidth/2);
		miniMap.translateCamY(mapSizeH/2-screenHeight/2+40);
		
		WeaponBar weaponBar = new WeaponBar(screenWidth, screenHeight, 3);
		listeArmes[0] = new Gun(0.5, -1, 8, 1, this);
		weaponBar.switchWeapon(listeArmes[0], 0);
		listeArmes[1] = new MiniGun(0.05, -1, 8, 1, this);
		weaponBar.switchWeapon(listeArmes[1], 1);
		
		rootGame.getChildren().add(weaponBar);
		
		AnimationTimer jeu = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				if (depCamX != 0 | depCamX2 != 0) {
					if (depCamX2 == 0) {
						pan.translateX(depCamX);
						miniMap.translateCamX(depCamX);
					} else {
						pan.translateX(depCamX2);
						miniMap.translateCamX(depCamX2);
					}
				}
				if (depCamY != 0 | depCamY2 != 0) {
					if (depCamY2 == 0) {
						pan.translateY(depCamY);
						miniMap.translateCamY(depCamY);
					} else {
						pan.translateY(depCamY2);
						miniMap.translateCamY(depCamY2);
					}
				}
				if (spaceKey){
					pan.x = -j.x + screenWidth/2;pan.translateX(0);
					pan.y = -j.y + screenHeight/2;pan.translateY(0);
					miniMap.camX = -pan.x;miniMap.translateCamX(0);
					miniMap.camY = -pan.y;miniMap.translateCamY(0);
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
					miniMap.updateJoueur(j);
				}
				if (tirer){
					if (!(j.x == mouseX & j.y == mouseY)){
						listeArmes[choixArme-1].tire(j.x, j.y, mouseX, mouseY);
					}
				}

			}
		};

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
					miniMap.camX = -pan.x;miniMap.translateCamX(0);
					miniMap.camY = -pan.y;miniMap.translateCamY(0);
					break;
				case "ESCAPE":
					System.exit(1);
					retourMenu(primaryStage);
				}
				if (g.getCode().toString().startsWith("DIGIT")){
					int arme = Integer.parseInt(g.getCode().toString().substring(5));
					if (arme == choixArme & listeArmes[choixArme-1]!=null){
						tirer = true;
					}else{
						if (arme <= nbArmes){
							choixArme = arme;
							tirer = false;
							weaponBar.selection = choixArme;
							weaponBar.start();
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
				mouseX = m.getX(); mouseY = m.getY();
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
				mouseX = m.getX(); mouseY = m.getY();
				if (m.getX() > 50 & m.getX() < mapSizeW-50 & m.getY() > 50 & m.getY() < mapSizeH-50) {
					findPath(m.getX(), m.getY(), l_l_triangles, pan);
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
				if (m.getButton().toString() == "SECONDARY" & m.getX() > 50 & m.getX() < mapSizeW-50 & m.getY() > 50 & m.getY() < mapSizeH-50) {
					clicDroit = true;
					sceneGame.setCursor(Cursor.NONE);
					findPath(m.getX(), m.getY(), l_l_triangles, pan);
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
		pan.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				mouseX = m.getX(); mouseY = m.getY();
			}
		});
		pan.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent m) {
				depCamX2 = 0;
				depCamY2 = 0;
			}
		});
		
		primaryStage.show();
		jeu.start();
	}

	
	
	
	public int findPath(double x, double y, LinkedList<LinkedList<Double>> l_l_triangles, CustomPanel pan) {
		double tab[] = repositionnePts(x, y, l_l_triangles, l_obs, pan);
		double destX = tab[0];
		double destY = tab[1];
		tab = repositionnePts(j.x, j.y, l_l_triangles, l_obs, pan);
		double depX = tab[0];
		double depY = tab[1];
		
		new curseurFx(destX,destY,8,pan).start();
		
		graphe.setDebut(l_obs, depX, depY);
		graphe.setFin(l_obs, destX, destY);
		
		if (graphe.getDebut().size()==0 | graphe.getFin().size()==0 | graphe.getDebut().size()==graphe.getGraphe().size() | graphe.getFin().size()==graphe.getGraphe().size()) return 0;
		if (distance(destX, destY, j.x, j.y)<3) return 1;

		
		int nb_sommets = graphe.getGraphe().size()+2;
		
		double[] d = new double[nb_sommets];
		int[] predecesseur = new int[nb_sommets];
		LinkedList<Integer> sommets = new LinkedList<Integer> ();
		LinkedList<LinkedList<cell>> graphe_complet = new LinkedList<LinkedList<cell>>();
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
			graphe_complet.get(graphe_complet.getLast().get(i).getPoint()).add(new cell(nb_sommets-1, graphe_complet.getLast().get(i).getDistance()));
		}
		for(int i=0; i<graphe_complet.getFirst().size(); i++){
			graphe_complet.get(graphe_complet.getFirst().get(i).getPoint()).addFirst(new cell(0, graphe_complet.getFirst().get(i).getDistance()));
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
			graphe_complet.get(0).addLast(new cell(nb_sommets-1, distance(depX, depY, destX, destY)));
			graphe_complet.get(nb_sommets-1).addFirst(new cell(0, distance(depX, depY, destX, destY)));
		}

		/*
		for(int i=0; i<graphe_complet.size(); i++){
			System.out.print(i+" : ");
			for(int j=0; j<graphe_complet.get(i).size(); j++){
				System.out.println("\t"+graphe_complet.get(i).get(j).getPoint()+" "+graphe_complet.get(i).get(j).getDistance());
			}System.out.println();
		}*/
		
		
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
			flagX = new LinkedList<Double>();
			flagY = new LinkedList<Double>();
			int s = nb_sommets-1;
			while(s!=0){
				if (s!=nb_sommets-1){
					flagX.addFirst(l_pts.get(2*(s-1)));
					flagY.addFirst(l_pts.get(2*(s-1)+1));
				}
				s=predecesseur[s];
			}
			flagX.addLast(destX);
			flagY.addLast(destY);
		}
		
		//---   retour a letat initial   ---
		if (coupe==false){
			graphe_complet.get(nb_sommets-1).removeFirstOccurrence(new cell(0, distance(depX, depY, destX, destY)));
			graphe_complet.get(0).removeFirstOccurrence(new cell(nb_sommets-1, distance(depX, depY, destX, destY)));
		}
		
		for(int i=0; i<graphe_complet.getLast().size(); i++){
			graphe_complet.get(graphe_complet.getLast().get(i).getPoint()).removeLast();
		}
		for(int i=0; i<graphe_complet.getFirst().size(); i++){
			graphe_complet.get(graphe_complet.getFirst().get(i).getPoint()).removeFirst();
		}

		return 1;
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