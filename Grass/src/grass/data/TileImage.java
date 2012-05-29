package grass.data;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class TileImage {
	private String fileName;
	private BufferedImage image;
	public TileImage(String img) throws IOException{
		fileName = img;
		System.out.println("adding tile "+fileName);
		InputStream is = Tile.class.getResourceAsStream("/grass/tiles/"+fileName);
		BufferedInputStream bis = new BufferedInputStream(is);
		image = ImageIO.read(bis);
	}
	public BufferedImage getImage(){
		return image;
	}
	public String getName(){
		return fileName;
	}
}
