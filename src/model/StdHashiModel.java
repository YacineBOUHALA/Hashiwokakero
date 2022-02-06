package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;


import utilities.StdCoord;
import utilities.StdBridge;
import utilities.StdNode;

@SuppressWarnings("deprecation")
public class StdHashiModel extends Observable implements HashiModel {

	ArrayList<StdBridge> bridgeList;

	private int spaceGameSize;
	private int width;
	private int height;
	
	private boolean isSolvedGraphe = false;;
	
	private List<StdCoord> coordList;
	private List<Integer> degreeList;
	private List<StdNode> orderedNodeList;
	private Map<StdNode, List<StdNode>> neighborList;
	private Set<StdBridge> bridgesPossibilities;
	
	public StdHashiModel(int spaceGameSize, int width, int height) {

		coordList = new ArrayList<StdCoord>();
		degreeList = new ArrayList<Integer>();
		orderedNodeList = new ArrayList<StdNode>();
		neighborList =  new HashMap<StdNode, List<StdNode>>();
		bridgesPossibilities = new HashSet<StdBridge>();
		this.bridgeList = new ArrayList<StdBridge>();
		
		this.spaceGameSize = spaceGameSize;
		this.width = width;
		this.height = height;
		
		this.isSolvedGraphe = false;	
	}

	@Override
	public int getSize() {
		return this.spaceGameSize;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}


	@Override
	public boolean isValidPositionCoord(StdCoord c) {
		return c.getColumn() <= spaceGameSize && c.getRow() <= spaceGameSize;
	}

	@Override
	public boolean hasNodeAtCoord(StdCoord c) {
		
		return coordList.contains(c);
	}
	
	@Override
	public void placeNodeAtCoord(StdCoord c, int degree) {
		coordList.add(c);
		degreeList.add(degree);
		notifyObservers();
		setChanged();
	}
	
	@Override
	public void removeNodeAtCoord(StdCoord c) {
		degreeList.remove(coordList.indexOf(c));
		coordList.remove(c);
		notifyObservers();
		setChanged();
	}

	@Override
	public StdNode getNodeAtCoord(StdCoord c) {
		
		if (coordList.contains(c)) {
			int index = coordList.indexOf(c);
			
			return new StdNode(index, degreeList.get(index), coordList.get(index));
		}
		
		return null;
	}
	
	
	public List<StdNode> getNodes() {
		
		List<StdNode> currentNodes = new ArrayList<StdNode>();
		
		
		for (int i = 0, size = coordList.size(); i < size; i++) {
			currentNodes.add(i, new StdNode(i, degreeList.get(i), coordList.get(i)));
		}
		
		return currentNodes;
	}
	
	public  ArrayList<StdBridge> getEdgeList() {
		return bridgeList;
	}
	
	
	public void orderNodes() {
		
		orderedNodeList = getNodes();
		
		orderedNodeList.sort(new Comparator<StdNode>() {
			@Override
			public int compare(StdNode o1, StdNode o2) {
				if (o1.getNodeDegree() > o2.getNodeDegree())
					return -1;
				if (o1.getNodeDegree() < o2.getNodeDegree())
					return 1;
				return 0;
			}
		});
	}
	
	public boolean isLinkedable(StdNode node) {
		
		
		int possiblesBridgesNb = 0;
		
		for (StdNode neighbor : neighborList.get(node)) {
			
			possiblesBridgesNb += neighbor.getNodeDegree();
			
		}
		
		return possiblesBridgesNb - node.getNodeDegree() > 0 ;
		
	}
	
	public void findNeighborsNode() {
		
		
		for (StdNode node : orderedNodeList) {
			
			List<StdNode> neighbors = new ArrayList<StdNode>();
			
			int row = node.getCoord().getRow();
			int column = node.getCoord().getColumn();
			
			
			for (int rNbColumn = column+1; rNbColumn < getWidth(); rNbColumn++) {
				
				if (hasNodeAtCoord(new StdCoord(row, rNbColumn))) {

					neighbors.add(getNodeAtCoord(new StdCoord(row, rNbColumn)));
					break;
				}
			}
			
			for (int lNbColumn = column-1; lNbColumn >= 0; lNbColumn--) {
				
				if (hasNodeAtCoord(new StdCoord(row, lNbColumn))) {

					neighbors.add(getNodeAtCoord(new StdCoord(row, lNbColumn)));
					break;
				}
			}
			
			for (int upNbRow = row-1; upNbRow >= 0; upNbRow--) {
				
				if (hasNodeAtCoord(new StdCoord(upNbRow, column))) {
					
					neighbors.add(getNodeAtCoord(new StdCoord(upNbRow, column)));
					break;
				}
			}
			
			for (int dwNbRow = row+1; dwNbRow < getHeight(); dwNbRow++) {
				
				if (hasNodeAtCoord(new StdCoord(dwNbRow, column))) {
					
					neighbors.add(getNodeAtCoord(new StdCoord(dwNbRow, column)));
					break;
				}
			}
			
			neighborList.put(node, neighbors);	
		}
		notifyObservers();
		setChanged();
	}
	
	/*************************************************************************
    				initialise la liste de tout les ponts possible
	 *************************************************************************/

	public void findPossibleBridges() {
		
		for (StdNode node : getNodes()) {
			
			neighborList.get(node);
			
			for (StdNode neighbor : neighborList.get(node)) {
				
				int maxBridgNb = Math.min(node.getNodeDegree(), neighbor.getNodeDegree());
				
				if (maxBridgNb > 2) {
					bridgesPossibilities.add(new StdBridge(node, neighbor, 2));
				} else {
					bridgesPossibilities.add(new StdBridge(node, neighbor));
				}
				
			}
		}
		
		
	}

	


	public boolean canAddBridge(StdBridge bridge) {
	
		
		if (!bridgesPossibilities.contains(bridge)) {
			return false;
		}
		
		if (isFullNode(bridge.getXNode()) || isFullNode(bridge.getYNode())) {
			 return false;
		}
		
		 if (isMakingCrossBridge(bridge)) {
			 return false;
		 }
		 
		 for (int i = 0; i < bridgeList.size(); i++) {
			if(bridgeList.get(i).equals(bridge)) {
				if (bridgeList.get(i).getBridgesNb() == 2) {
					return false;
				}
			}
		}
		 return true;

	}
	

	public boolean isFullNode(StdNode node) {
		
		int bridgeNb = 0;
		
		for (int i = 0; i < bridgeList.size(); i++) {
			
			if (bridgeList.get(i).containsNode(node)) {
				bridgeNb += bridgeList.get(i).getBridgesNb();
			}
		}
		boolean resultat = bridgeNb >= node.getNodeDegree();

		return resultat;
	}
	
	

	@Override
	public boolean isCompleteLinkedGraphe() {
		return coordList.size() == spaceGameSize;
	}


	@Override
	public boolean solve() {
		
		for(StdNode node : neighborList.keySet()) {
			if (neighborList.get(node).size() < 1) {
				return false;
			}
		}
		
		long begin = System.currentTimeMillis();
		long finish = begin + 100*1000;

		//solve
		int n = 1;
		while(n <= (getSize() - 1) && !isSolvedGraphe && System.currentTimeMillis() < finish){
			if(!findSolutionToGame(getNodes().get(0), getNodes().get(n))){
				n++;
			}else{
				isSolvedGraphe = true;
			}
		}		
		setChanged();
		notifyObservers();
		

		return isSolvedGraphe;
	}
	
	

		private boolean isMakingCrossBridge(StdBridge edge) {
		
		for (StdBridge stdBridge : bridgeList) {
			if (edge.isCrossBridge(stdBridge)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean checkIfSuucessGame(){
		
		for (StdNode node : getNodes()) {
			if (!isFullNode(node)) {
				return false;
			}
		}
		
		
		return true;
	}
	

	public boolean checkIfPossibleMovesAddBridge() {
		
		for (StdBridge possibleBridge : bridgesPossibilities) {
			if (canAddBridge(possibleBridge)) {
			
				return true;
			}
		}

		return false;
		
	}
	
	
	public void removeBridge(StdBridge bridge) {
		
		
		for (int i = 0; i < bridgeList.size(); i++) {
				
			if (bridgeList.get(i).equals(bridge)) {
				
				if (bridgeList.get(i).getBridgesNb() == 2) {		
					bridgeList.get(i).decBridgesNb();
				} else {
					bridgeList.remove(i);
				}
				
			}
			
		}
	}
	
	
	boolean areNeigbhorsNodes(StdNode node1, StdNode node2) {
		
		
		for (StdNode neighbor : neighborList.get(node1)) {
			
			if (neighbor.equals(node2)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean findSolutionToGame(StdNode node1, StdNode node2){
		if(node1.equals(node2)){
			return false;
		}
		 
		if (canAddBridge(new StdBridge(node1, node2))) {
			
			if (bridgeList.contains(new StdBridge(node1, node2))) {
				
				for (StdBridge presentBridge : bridgeList) {
					if (presentBridge.equals(new StdBridge(node1, node2))) {
						presentBridge.addBridgeNb();
						
					}
				}
			} else {
				bridgeList.add(new StdBridge(node1, node2));
				
			}
			
		} else {
			// impossible add retourn faux
			return false;
		}
		
		
		if(checkIfSuucessGame()){
			//if won, return true!
			return true;
		}
		else if(!checkIfPossibleMovesAddBridge()){
			
			removeBridge(new StdBridge(node1, node2));
			
			return false;
		}
		//ici on fait la  RECURSION
		else{
			// get pnode 1 and 2  ids de n1 et n2
			int pnode1 = 0;
			int pnode2 = 0;
			for(int i = 0; i < spaceGameSize -1; i++){
				if(node1 == getNodes().get(i))
					pnode1 = i;
				if(node2 == getNodes().get(i))
					pnode2 = i;
			}
			
			do{
				//if n2 is the last node go to next n1
				if(getNodes().get(pnode1).getNodeDegree() == 0 || pnode2 == spaceGameSize - 1){
					do{
						pnode1++;
						pnode2 = pnode1;
					}while(isFullNode(getNodes().get(pnode1)) && pnode1 < spaceGameSize-1);
				}
				
				else{
					pnode2++;
				}
				// recursion on the new node
				if(areNeigbhorsNodes(getNodes().get(pnode1), getNodes().get(pnode2))){
					if(findSolutionToGame(getNodes().get(pnode1), getNodes().get(pnode2))){
						return true;
					}
				}
			}while(!(pnode2 >= (spaceGameSize - 2) && pnode1 >= (spaceGameSize -1)));

			if(!findSolutionToGame(node1, node2)){
				removeBridge(new StdBridge(node1, node2));
				return false;
			}
		}
		return true;
	}

}
