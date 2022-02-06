import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import model.StdHashiModel;
import utilities.Coord;
import utilities.Direction;
import utilities.StdCoord;
import utilities.StdBridge;
import utilities.StdNode;

public class IslandsSpace extends JComponent {

	
	public static final int ISLAND_SPACE_SQUARE_EDGE_SIZE = 80;
	public static final int ISLAND_SPACE_MARGIN = 3;
	public static final int ISLAND_SPACE_CIRCLE_MARGIN = 20;
	public static final int ISLAND_SPACE_CENTER_MARGIN = 2;
	public static final int BRIDGE_SPACING = ISLAND_SPACE_SQUARE_EDGE_SIZE / 7;
 	private StdHashiModel model;
	
	public IslandsSpace(StdHashiModel model) {
		
		this.model = model;
		Dimension d = new Dimension(ISLAND_SPACE_SQUARE_EDGE_SIZE * model.getWidth(), ISLAND_SPACE_SQUARE_EDGE_SIZE * model.getHeight());
		setPreferredSize(d);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		drawIlandSpaceGame(g);
		drawIsland(g, Color.BLACK);
		drawEdges(g, Color.GREEN);
		
	}
	
	private void drawIlandSpaceGame(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.fillRect(ISLAND_SPACE_MARGIN, ISLAND_SPACE_MARGIN, getWidth() - ISLAND_SPACE_MARGIN *2, getHeight() - ISLAND_SPACE_MARGIN *2);
		
		g.setColor(Color.WHITE);
		
		for (int i = 0; i < model.getWidth()+ 2; i++) {
			g.drawLine(
					ISLAND_SPACE_MARGIN + i * ISLAND_SPACE_SQUARE_EDGE_SIZE, 
					ISLAND_SPACE_MARGIN, 
					ISLAND_SPACE_MARGIN + i * ISLAND_SPACE_SQUARE_EDGE_SIZE, 
					getHeight() - ISLAND_SPACE_MARGIN);

		}
		
		for (int i = 0; i < model.getHeight()+2; i++) {
			
			g.drawLine(
					ISLAND_SPACE_MARGIN, 
					ISLAND_SPACE_MARGIN + i * ISLAND_SPACE_SQUARE_EDGE_SIZE, 
					getWidth() - ISLAND_SPACE_MARGIN,
			ISLAND_SPACE_MARGIN + i * ISLAND_SPACE_SQUARE_EDGE_SIZE); 
		}
	}
	
	private void drawIsland(Graphics g, Color c) {
		FontMetrics fm = g.getFontMetrics();
		
		g.setColor(c);
		
		for (StdNode node : model.getNodes()) {
				
			int x =   node.getCoord().getColumn() *ISLAND_SPACE_SQUARE_EDGE_SIZE + ISLAND_SPACE_SQUARE_EDGE_SIZE/4  +ISLAND_SPACE_MARGIN;
			int y =   node.getCoord().getRow() *ISLAND_SPACE_SQUARE_EDGE_SIZE + ISLAND_SPACE_SQUARE_EDGE_SIZE/4;
			
			g.drawOval(x ,y , ISLAND_SPACE_SQUARE_EDGE_SIZE/2, ISLAND_SPACE_SQUARE_EDGE_SIZE/2);
			
			Font myFont = new Font ("Courier New", 1, 17);
			g.setFont (myFont);
			
			String s = String.valueOf(node.getNodeDegree());
			int sWidth = fm.stringWidth(s);

			g.drawString(s, x + ISLAND_SPACE_SQUARE_EDGE_SIZE/4 - sWidth/2, y + ISLAND_SPACE_SQUARE_EDGE_SIZE/4 + sWidth/2);
		}
	}
	
	private void drawEdges(Graphics g, Color c) {
		
		g.setColor(c);
		
		for (StdBridge edge : model.getEdgeList()) {
		
			
			int x1 = ISLAND_SPACE_MARGIN  + edge.getXNode().getCoord().getColumn() * ISLAND_SPACE_SQUARE_EDGE_SIZE
					+ ((ISLAND_SPACE_SQUARE_EDGE_SIZE)/2);
			
			int y1 = ISLAND_SPACE_MARGIN + edge.getXNode().getCoord().getRow() * ISLAND_SPACE_SQUARE_EDGE_SIZE
					+ ((ISLAND_SPACE_SQUARE_EDGE_SIZE)/2);
			
			
			int x2 = ISLAND_SPACE_MARGIN  + edge.getYNode().getCoord().getColumn() * ISLAND_SPACE_SQUARE_EDGE_SIZE
					+ ((ISLAND_SPACE_SQUARE_EDGE_SIZE)/2);
			int y2 = ISLAND_SPACE_MARGIN + edge.getYNode().getCoord().getRow() * ISLAND_SPACE_SQUARE_EDGE_SIZE
					+ ((ISLAND_SPACE_SQUARE_EDGE_SIZE)/2);
			
			
			switch (edge.getDirection()) {
			case UP: 
				y2 += ISLAND_SPACE_SQUARE_EDGE_SIZE /4 -ISLAND_SPACE_MARGIN;
				y1 -= ISLAND_SPACE_SQUARE_EDGE_SIZE /4;
				break;
			case DOWN:
				y1 += ISLAND_SPACE_SQUARE_EDGE_SIZE /4 -ISLAND_SPACE_MARGIN;
				y2 -= ISLAND_SPACE_SQUARE_EDGE_SIZE /4 +ISLAND_SPACE_MARGIN;
				break;
			case LEFT:
				x2 += ISLAND_SPACE_SQUARE_EDGE_SIZE /4;
				x1 -= ISLAND_SPACE_SQUARE_EDGE_SIZE /4;
				break;
			case RIGHT:
				x1 += ISLAND_SPACE_SQUARE_EDGE_SIZE /4;
				x2 -= ISLAND_SPACE_SQUARE_EDGE_SIZE /4;
				break;			
			}
			
			int edgeNb = edge.getBridgesNb();
			
			if (edgeNb == 1) {
				g.drawLine(x1, y1, x2, y2);
				
			} else if (edgeNb == 2) {
				if (edge.isVertical()) {
					
					g.drawLine(x1-BRIDGE_SPACING/2, y1, x2 -BRIDGE_SPACING/2, y2);
					g.drawLine(x1+BRIDGE_SPACING/2, y1, x2 +BRIDGE_SPACING/2, y2);
					
				} else if (edge.isHorizontal()) {
					
					g.drawLine(x1, y1 -BRIDGE_SPACING/2, x2, y2 -BRIDGE_SPACING/2);
					g.drawLine(x1, y1 +BRIDGE_SPACING/2, x2, y2 +BRIDGE_SPACING/2);
					
				}
			}
			
		}
	}

}
