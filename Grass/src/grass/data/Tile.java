package grass.data;


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
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
