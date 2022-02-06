package utilities;

public interface Bridge<S extends Node> {
	
	
	int getBridgesNb();

	S getXNode();
	
	S getYNode();
	
	void setBridgesNb(int bridgesNb);
	
	void setXNode(S xNode);
	
	void setYNode(S yNode);
	
}