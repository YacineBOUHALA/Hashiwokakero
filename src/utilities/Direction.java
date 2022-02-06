package utilities;


public enum Direction {
	
	UP(0, 1),
	DOWN(0, -1),
	LEFT(-1, 0),
	RIGHT(1, 0);
	
	private int x;
	private int y;
	
	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	public boolean horizontal() {
		return x != 0;
	}
	
	public boolean vertical() {
		return y != 0;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name().toLowerCase();
	}
	
}
