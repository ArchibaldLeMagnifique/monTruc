package util;

import java.util.LinkedList;

import javafx.scene.shape.Polygon;

//point structure with x,y are coordinates
public class Point{
	public double x, y;
	//parameterized constructor
	public Point(double _X, double _Y){
		x = _X;
		y = _Y;
	}
	//copy constructor
	public Point(Point p) {
		x = p.x;
		y = p.y;
	}
	//compare operation
	public boolean Equals(Point p) {
		if (Math.abs(this.x - p.x) > 0.00001)
			return false;
		if (Math.abs(this.y - p.y) > 0.00001)
			return false;
		return true;
	}
	//calculate distance to a point
	public double distance(Point p) {
		double dx = x - p.x;
		double dy = y - p.y;
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public String toString(){
		return this.x+" "+this.y;
	}
	
	
	public Polygon ptsDans(LinkedList<LinkedList<Double>> l_l_triangles, LinkedList<Polygon> l_obs, CustomPanel pan){
		boolean test; int i;
		Polygon res = null;
		for (int k=0; k<l_l_triangles.size(); k++){
			for (int j = 0; j < l_l_triangles.get(k).size() / 6; j++) {//TEST DE COLLISION
				LinkedList<Double> tri = new LinkedList<Double>();
				test = false;
				int valeur=0;
				for (i = 6 * j; i < 6 * j + 6; i++)
					tri.add(l_l_triangles.get(k).get(i));
				for (i = 0; i < 3; i++) {
					double Ax = tri.get((2 * i) % 6);
					double Ay = tri.get((2 * i + 1) % 6);
					double Bx = tri.get((2 * i + 2) % 6);
					double By = tri.get((2 * i + 3) % 6);
					double Dx = Bx - Ax;
					double Dy = By - Ay;
					double Tx = this.x - Ax;
					double Ty = this.y - Ay;
					double d = Dx * Ty - Dy * Tx;
					if (valeur==0){
						if (d > 0) valeur = 1;
						if (d < 0) valeur = -1;
					}else{
						if ((d > 0 & valeur==-1) | (d < 0 & valeur==1)) test = true;	
					}
				}
				if (test == false) {
					res=l_obs.get(k);
				}
			}
		}
		return res;
	}
		
		
	
}
