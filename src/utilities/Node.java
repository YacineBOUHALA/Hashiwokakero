package utilities;

public interface Node {
	
	public static int MAX_DEGREE = 8;
	
	int getId();
    
   int getNodeDegree();
	
	Coord getCoord();
	
	void setCoord(Coord coord);
	
	void setDegree(int degree);
	
}
