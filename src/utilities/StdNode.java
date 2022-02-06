package utilities;


public class StdNode implements Node {
	
	private Coord coord;
	private int nodeDegree;
	private int id;
	
	
	public StdNode(int id, int noddeDegree, Coord coord) {
		
		this.id = id;
		this.nodeDegree = noddeDegree;
		this.coord = coord;
		
	}
	
	public int getId() {
		return this.id;
	}

	@Override
	public int getNodeDegree() {
		return this.nodeDegree;
	}

	@Override
	public Coord getCoord() {
		return this.coord;
	}

	@Override
	public void setCoord(Coord c) {
		
		this.coord = c;
	}

	@Override
	public void setDegree(int degree) {
		
		this.nodeDegree = degree;
	}
	
	@Override
	public boolean equals(Object other) {
		
		if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        StdNode n = (StdNode) other;
			return this.id == n.getId();		
	}
	

	@Override
	public int hashCode() {
	
        return getCoord().hashCode();
    
	}
	
	public boolean canJoin(StdNode node) {
		
		if (node == null) return false;
		
		return getCoord().getRow() == node.getCoord().getRow() || getCoord().getColumn() == node.getCoord().getColumn(); 
	}
	
	
	public String toString() {
		return "{"+getId()+"|"+getCoord()+","+getNodeDegree()+"}";
		
	}
}
