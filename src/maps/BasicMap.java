package maps;

import java.util.LinkedList;

import javafx.scene.shape.Polygon;
import util.CustomPanel;
import util.MyPolygon;
import util.Point;
import util.Segment;

public class BasicMap {

	
	public int mapSizeW = 3200;
	public int mapSizeH = 2500;
	
	public BasicMap () {	
	}
	
	public void createMap(LinkedList<Polygon> l_obs, CustomPanel pan, LinkedList<LinkedList<Double>> l_l_triangles, LinkedList<Double> l_pts) {
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
		
	}
	
}
