package utilities;

import java.util.ArrayList;
import java.util.List;


public class StdBridge {

	private StdNode xNode;
	private StdNode yNode;
	private int bridgesNb;
	private Direction direction;
	
	
	public StdBridge(StdNode xNode, StdNode yNode, int bridgesNb) {
		
		this.xNode  = xNode;
		this.yNode  = yNode;
		this.bridgesNb = (bridgesNb > 2 ) ? 2 : bridgesNb;
		this.initDirection();
	}
	
	public StdBridge(StdNode xNode, StdNode yNode) {
	
		this.xNode  = xNode;
		this.yNode  = yNode;
		this.bridgesNb = 1;
		this.initDirection();
	}
	
	private void initDirection() {
		
		if (xNode.getCoord().getColumn() == yNode.getCoord().getColumn()) {
			if (xNode.getCoord().getRow() < yNode.getCoord().getRow()) {
				this.direction = Direction.DOWN;
			} else {
				this.direction = Direction.UP;
			}
		}else {
			if (xNode.getCoord().getColumn() < yNode.getCoord().getColumn()) {
				this.direction = Direction.RIGHT;
			} else {
				this.direction = Direction.LEFT;
			}
		}
		
	}

	public StdNode getXNode() {
		return this.xNode;
	}

	public StdNode getYNode() {
		return this.yNode;
	}
	
	public int getBridgesNb() {
		return this.bridgesNb;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public boolean containsNode(StdNode node) {
		
		return (node.equals(getXNode()) || node.equals(getYNode()));
		
	}
	
	
	
	public void addBridgeNb() {
		if (bridgesNb == 1) this.bridgesNb = 2;
	}
	
	public void decBridgesNb() {
		if (bridgesNb == 2) this.bridgesNb = 1;
	}

	public void setXNode(StdNode xNode) {
		//Contract.checkCondition(xNode != null, "Sommet invalide ("+ xNode +") !");
	
		this.xNode = xNode;
	}

	public void setYNode(StdNode yNode) {
		//Contract.checkCondition(yNode != null, "Sommet invalide ("+ yNode +") !");
		
		this.yNode = yNode;
	}
	
	public boolean isLinked(int x, int y) {
		
		boolean haveX = (getXNode().getId() == x || getYNode().getId() == x);
		boolean haveY = (getXNode().getId() == y || getYNode().getId() == y);
		
		return (haveX && haveY);
	}
	
	public boolean isVertical() {
		return direction.vertical();
	}
	
	public boolean isHorizontal() {
		return direction.horizontal();
	}
	
	public boolean isCrossBridge(StdBridge bridge) {
		if (! hasCommonNodeWithLast(bridge)) {
			if (isHorizontal() && bridge.isVertical()) {
				
				if (getXNode().getCoord().getRow() >= Math.min(bridge.getXNode().getCoord().getRow(), bridge.getYNode().getCoord().getRow())
						&& getXNode().getCoord().getRow() <= Math.max(bridge.getXNode().getCoord().getRow(), bridge.getYNode().getCoord().getRow())) {
					return (
							bridge.getXNode().getCoord().getColumn() >= Math.min(xNode.getCoord().getColumn(), yNode.getCoord().getColumn())
							
							&& bridge.getXNode().getCoord().getColumn() <= Math.max(xNode.getCoord().getColumn(), yNode.getCoord().getColumn())); 

				}
				return false;
								
			} else if (isVertical() && bridge.isHorizontal()) {
				
				if (getXNode().getCoord().getColumn() >= Math.min(bridge.getXNode().getCoord().getColumn(), bridge.getYNode().getCoord().getColumn())
						&& getXNode().getCoord().getColumn() <= Math.max(bridge.getXNode().getCoord().getColumn(), bridge.getYNode().getCoord().getColumn())) {
					return (
							bridge.getXNode().getCoord().getRow() >= Math.min(xNode.getCoord().getRow(), yNode.getCoord().getRow())
							
							&& bridge.getXNode().getCoord().getRow() <= Math.max(xNode.getCoord().getRow(), yNode.getCoord().getRow())); 

				}
				return false;
				
			} 
		} 
		return false;
	}
	
	
public boolean equals(Object otherOne) {
		
		if (this == otherOne) {
            return true;
        }
        if (otherOne == null || getClass() != otherOne.getClass()) {
            return false;
        }

        StdBridge e = (StdBridge) otherOne;
		return this.xNode.equals(e.getXNode()) && this.yNode.equals(e.getYNode()) 
					|| this.xNode.equals(e.getYNode()) && this.yNode.equals(e.getXNode());
	}
	
	@Override
	public int hashCode() {
		
		int x = xNode.hashCode();
		int y = yNode.hashCode();
		int tmp = ( x +  ((y+1)/2));
        return x +  ( tmp * tmp);
	}

	
	public boolean hasCommonNodeWithLast(StdBridge bridge) {
		return (xNode.equals(bridge.getXNode()) || xNode.equals(bridge.getYNode()) || yNode.equals(bridge.getXNode()) || yNode.equals(bridge.getYNode()));
	}
	
	public String toString() {
		return "["+getXNode()+"  "+getYNode()+","+getBridgesNb()+","+getDirection()+"]";
	}
	
	
	public List<StdNode> getNodes() {
		
		List<StdNode> bridgeNodes = new ArrayList<StdNode>();
		
		bridgeNodes.add(xNode);
		bridgeNodes.add(yNode);
		
		return bridgeNodes;
		
	}
	
	
}
