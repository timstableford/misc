package grass.data;

import java.awt.geom.Point2D;



public class Tile {
	private String fileName;
	private int rotation,x,y;
	private boolean passable = true;
	public Tile(int x, int y, String img, int rot, boolean b){
		setPassable(b);
		fileName = img;
		rotation = rot;
		this.x = x;
		this.y = y;
	}
	public Tile(String img, int rot){
		fileName = img;
		rotation = rot;
	}
	public String toString(){
		return fileName;
	}
	public int getRotation(){
		return rotation;
	}
	public boolean isPassable() {
		return passable;
	}
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
	public int getGridX() {
		return x;
	}
	public void setGridX(int x) {
		this.x = x;
	}
	public int getX(){
		return x*32+16;
	}
	public int getY(){
		return y*32+16;
	}

	public int getGridY() {
		return y;
	}
	public void setGridY(int y) {
		this.y = y;
	}
	public double getDistance(Tile n){
		if(n==null){
			return 99999999;
		}
		Point2D a = new Point2D.Double(x,y);
		Point2D b = new Point2D.Double(n.getGridX(),n.getGridY());
		return a.distance(b);
	}
}
