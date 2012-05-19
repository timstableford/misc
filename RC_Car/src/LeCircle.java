import java.awt.Graphics;


public class LeCircle {
	private int x;
	private int y;
	public LeCircle(){
		x = 255;
		y = 255;
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
	public void paint(Graphics g){
		g.drawOval(x-5,y-5,10,10);
		g.drawLine(255,0,255,510);
		g.drawLine(0,255,510,255);
	}
}
