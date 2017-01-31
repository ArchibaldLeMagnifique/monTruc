package util;

import java.util.LinkedList;

import javafx.scene.shape.Polygon;


public class Graphe {

	private LinkedList<LinkedList<Cell>> graphe = new LinkedList<LinkedList<Cell>>();
	private LinkedList<Cell> debut, fin;
	
	public Graphe(LinkedList<Polygon> l_obs, CustomPanel pan) {
		for (int i = 0; i < l_obs.size(); i++) {
			Object[] point = l_obs.get(i).getPoints().toArray();
			for (int j = 0; j < point.length / 2; j++) {
				
				double Xa = (double) point[2 * j];
				double Ya = (double) point[2 * j + 1];
				graphe.add(updatePts(l_obs, Xa, Ya));
			}
		}
	}
	
	public LinkedList<Cell> updatePts(LinkedList<Polygon> l_obs, double Xa, double Ya){
		LinkedList<Cell> tmp_Cell = new LinkedList<Cell> ();
		int id = 1;
		for (int k = 0; k < l_obs.size(); k++) {
			Object[] point2 = l_obs.get(k).getPoints().toArray();
			for (int l = 0; l < point2.length / 2; l++) {
				
				boolean coupe = false;
				double Xb = (double) point2[2 * l];
				double Yb = (double) point2[2 * l + 1];	
				
				if (Xa!=Xb | Ya!=Yb){
					Segment s = new Segment(new Point(Xa,Ya), new Point(Xb,Yb));
					
					for (int m = 0; m < l_obs.size(); m++) {
						Object[] point3 = l_obs.get(m).getPoints().toArray();
						MyPolygon p = new MyPolygon();
						for(int n=0; n<point3.length/2;n++){
							p.addVertex(((Double)point3[2*n]).intValue(),  ((Double)point3[2*n+1]).intValue());
						}
						if (p.Intersect(s)) coupe =true;
						
					}

					if(coupe==false){
						tmp_Cell.add(new Cell(id, Math.sqrt(Math.pow(Xb-Xa, 2)+Math.pow(Yb-Ya, 2))));
					}
					
				}id++;
			}
		}
		return tmp_Cell;
	}
	
	public LinkedList<Cell> getDebut(){
		return this.debut;
	}
	
	public LinkedList<Cell> getFin(){
		return this.fin;
	}
	
	public void setDebut(LinkedList<Polygon> l_obs, double Xa, double Ya){
		this.debut = updatePts(l_obs,Xa,Ya);
	}
	
	public void setFin(LinkedList<Polygon> l_obs, double Xa, double Ya){
		this.fin = updatePts(l_obs,Xa,Ya);//  /!\ les distances entre fin et les autres ne sont connues que par fin lui meme
	}
	
	public LinkedList<LinkedList<Cell>> getGraphe(){
		return this.graphe;
	}
	
	
}
