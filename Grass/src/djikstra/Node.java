package djikstra;

import java.awt.geom.Point2D;

public class Node {
	private double distance = 999999;
	private int x,y;
	private Node parent = null;
	public Node(int x, int y){
		this.setX(x);
		this.setY(y);
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
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
	public String toString(){
		return distance+"";
	}
	public boolean isNeighbour(Node n){
		if(getDistance(n)<1.80){
			return true;
		}else{
			return false;
		}
	}
	public double getDistance(Node n){
		Point2D a = new Point2D.Double(x,y);
		Point2D b = new Point2D.Double(n.getX(),n.getY());
		return a.distance(b);
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
}
