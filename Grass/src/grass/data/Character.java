package grass.data;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public abstract class Character {
	protected double gridx, gridy;
	protected CharacterDirection direction = CharacterDirection.UP;
	private final int tD = 32;
	protected BufferedImage image = null;
	protected String fileName, name;
	public Character(String n, String img, int x, int y) throws IOException{
		gridx = x;
		gridy = y;
		name = n;
		fileName = img;
		System.out.println("adding tile "+fileName);
		InputStream is = Tile.class.getResourceAsStream("/grass/tiles/"+fileName);
		BufferedInputStream bis = new BufferedInputStream(is);
		image = ImageIO.read(bis);
	}
	public double getX() {
		return gridx;
	}
	public void setX(double gridx) {
		this.gridx = gridx;
	}
	public double getY() {
		return gridy;
	}
	public void setY(double gridy) {
		this.gridy = gridy;
	}
	
	public void setDirection(CharacterDirection d){
		direction = d;
	}
	public void paint(Graphics2D g2){
		double rot = Math.PI/2.00*(double)(direction.ordinal());
		g2.translate(gridx, gridy);
		g2.rotate(rot);
		g2.drawImage(image,-tD/2,-tD/2, null);
		g2.rotate(-rot);
		g2.translate(-gridx,-gridy);
	}
	public String toString(){
		return name;
	}
}
