package util;


public class Cell {
	
	int point;	
	double distance;

	public Cell(int p, double d){
		this.distance=d;
		this.point=p;
	}
	
	public int getPoint(){
		return this.point;
	}
	public double getDistance(){
		return this.distance;
	}
}