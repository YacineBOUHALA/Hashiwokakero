package model;

import utilities.StdCoord;
import utilities.StdNode;

public interface HashiModel {
	
	int getSize();
	
	boolean isValidPositionCoord(StdCoord c);
	    
	boolean hasNodeAtCoord(StdCoord c);
	
	StdNode getNodeAtCoord(StdCoord c);
	  
	void placeNodeAtCoord(StdCoord c, int degree);

	void removeNodeAtCoord(StdCoord c);
	    
	boolean isCompleteLinkedGraphe();
	  
	boolean solve();
}
