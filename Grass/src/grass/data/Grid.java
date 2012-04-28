package grass.data;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Grid {
	private Tile gameTiles[][];
	private final int gridx = 20;
	private final int gridy = 15;
	private final double playerSpeed = 5;
	public double getPlayerSpeed() {
		return playerSpeed;
	}
	private final int tileDiameter = 32;
	private ArrayList<TileImage> tileImages;
	private ArrayList<Character> characters;
	private Player player;
	public int getTileDiameter() {
		return tileDiameter;
	}
	public Grid(){
		gameTiles = new Tile[gridx][gridy];
		tileImages = new ArrayList<TileImage>();
		characters = new ArrayList<Character>();
	}
	/////////load image tiles//////////////////
	public void loadImages(String file) throws IOException{
		InputStreamReader is = new InputStreamReader(Tile.class.getResourceAsStream("/grass/tiles/"+file));
		BufferedReader br = new BufferedReader(is);
		String strLine;
		while ((strLine = br.readLine()) != null)   {
			processTileImage(strLine);
		}
		is.close();
	}
	private void processTileImage(String t) throws IOException{
		tileImages.add(new TileImage(t));
	}
	/**
	 * @param name of image to find
	 * @return found image
	 */
	public BufferedImage getImage(String name){
		for(TileImage t: tileImages){
			if(t.getName().equals(name)){
				return t.getImage();
			}
		}
		return null;
	}
	//////////////load tile data////////////////7
	public void loadTiles(String file) throws IOException{
		InputStreamReader is = new InputStreamReader(Tile.class.getResourceAsStream("/grass/tiles/"+file));
		BufferedReader br = new BufferedReader(is);
		String strLine;
		while ((strLine = br.readLine()) != null)   {
			processTile(strLine);
		}
		is.close();
	}
	private void processTile(String t) throws IOException{
		if(!t.contains("-")){ return; }
		String[] data = t.split("-");
		int x = Integer.parseInt(data[0]);
		int y = Integer.parseInt(data[1]);
		String image = data[2];
		int rotation = Integer.parseInt(data[3]);
		if(getImage(image)!=null){
			gameTiles[x][y] = new Tile(image,rotation);
		}
	}
	////////////////////////////////////////////////7
	public void printTiles(){
		for(int i=0;i<gridx;i++){
			for(int j=0;j<gridy;j++){
				if(gameTiles[i][j]!=null){
					System.out.println("x="+i+" y="+j+" image="+gameTiles[i][j].toString()+" rot="+gameTiles[i][j].getRotation());
				}
			}
		}
	}
	public Tile[][] getTiles(){
		return gameTiles;
	}
	public int getGridx() {
		return gridx;
	}
	public int getGridy() {
		return gridy;
	}
	public ArrayList<Character> getCharacters(){
		return characters;
	}
	public void addCharacter(Character c){
		characters.add(c);
	}
	public void setPlayer(Player p){
		characters.add(p);
		player = p;
	}
	public Player getPlayer(){
		return player;
	}
}
