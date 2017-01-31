package util;

import java.util.ArrayList;
import java.util.List;

//polygon structure
public class MyPolygon{
	public List<Point> vertices;

	// null constructor
	public MyPolygon(){
		vertices = new ArrayList<Point>();
	}
	
	// add a vertex
	public void addVertex(double _x, double _y){
		Point p = new Point(_x, _y);
		vertices.add(p);
		
	}
	
	// number of vertices
	public int size() {
		return vertices.size();
	}

	// index of vertice
	public int IndexOf(Point p)
	{
		for (int i=0; i< size(); i++)
			if (vertices.get(i).Equals(p))
				return i;
		return -1;
	}
	
	// check if a point is inside this polygon or not
	public boolean Inside(Point point)  
 {  
     int j = vertices.size() - 1;  
     boolean oddNodes = false;  

     for (int i = 0; i < vertices.size(); i++)  
     {  
         if (vertices.get(i).y < point.y && vertices.get(j).y >= point.y ||  
             vertices.get(j).y < point.y && vertices.get(i).y >= point.y)  
         {  
             if (vertices.get(i).x +  
                 (point.y - vertices.get(i).y)/(vertices.get(j).y - vertices.get(i).y)*(vertices.get(j).x - vertices.get(i).x) < point.x)  
             {  
                 oddNodes = !oddNodes;  
             }  
         }  
         j = i;  
     }

     return oddNodes;  
 }
	
	// check if a part of the segment, of which 2 end points are the polygon's vertices, is inside this or not
	public boolean Intersect(Segment s) {
		// a triangle does not Intersect any segment of which end points are the triangle's vertices
		//if (size() == 3)
		//	return false;
		// polygon has more than 3 vertices		
		// split the big segment into parts
		Point split_point = null;
		for (int i=0; i< size(); i++)
		{
			Point p1 = vertices.get(i);
			Point p2 = vertices.get((i+1) % size());
			Segment edge = new Segment(p1,p2);
			split_point = s.InterSectionExceptThisEnds(edge);
			if (split_point != null)
				break;
		}
		// if we can split
		if (split_point != null) // then check each part
		{
			boolean first_part = Intersect(new Segment(s.p1,split_point));
			if (first_part == true) // a part intersects means whole segment intersects
				return first_part;
			// if first part doesn't intersect
			// it depends on second one
			boolean second_part = Intersect(new Segment(split_point,s.p2));
			return second_part;
		}
		// cannot split this segment
		else
		{
			boolean result = Cover (s);
			return result;
		}
	}
	
	public boolean IsFullInside(Segment s) {
		// a triangle does not Intersect any segment of which end points are the triangle's vertices
		//if (size() == 3)
		//	return false;
		// polygon has more than 3 vertices		
		// split the big segment into parts
		Point split_point = null;
		for (int i=0; i< size(); i++)
		{
			Point p1 = vertices.get(i);
			Point p2 = vertices.get((i+1) % size());
			Segment edge = new Segment(p1,p2);
			split_point = s.InterSectionExceptThisEnds(edge);
			if (split_point != null)
				break;
		}
		// if we can split
		if (split_point != null) // then check each part
		{
			boolean first_part = Intersect(new Segment(s.p1,split_point));
			if (first_part == false) // a part intersects means whole segment intersects
				return first_part;
			// if first part doesn't intersect
			// it depends on second one
			boolean second_part = Intersect(new Segment(split_point,s.p2));
			return second_part;
		}
		// cannot split this segment
		else
		{
			return true;
		}
	}
	
	public boolean Cover(Segment s)
	{
		// if segment is a edge of this polygon
		int p1_pos = this.IndexOf(s.p1);
		int p2_pos = this.IndexOf(s.p2);
		if (p1_pos != -1 && p2_pos != -1)
		{
			int pos_distance = Math.abs(p1_pos - p2_pos);
			if (pos_distance == 1 || pos_distance == size()-1) // adjcent vertices
				return false;
		}
		// segment is unseparatable
		// so,if the mid point is inside polygon, whole segment will inside
		Point mid = s.MidPoint();
		if (this.Inside(mid))
			return true;
		else
			return false;
	}

	// check if a point is one of this polygon vertices
	public boolean isVertex(Point p) {
		for (int i=0; i< vertices.size(); i++)
			if (vertices.get(i).Equals(p))
				return true;
		return false;
	}
}