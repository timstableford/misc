package grass.data;

import java.io.IOException;

public class Tile {
	private String fileName;
	private int rotation;
	public Tile(String img, int rot) throws IOException{
		fileName = img;
		rotation = rot;
	}
	public String toString(){
		return fileName;
	}
	public int getRotation(){
		return rotation;
	}
}
