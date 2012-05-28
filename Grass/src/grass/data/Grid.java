package grass.data;

import java.awt.geom.Point2D;
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
		for(int i=0;i<gridx;i++){
			for(int j=0;j<gridy;j++){
				gameTiles[i][j] = new Tile(i,j,"grass.bmp",0,true);
			}
		}
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
		boolean passable = true;
		if(data[4].equals("false")){
			passable = false;
		}
		if(getImage(image)!=null){
			gameTiles[x][y] = new Tile(x,y,image,rotation,passable);
		}
	}
	////////////////////////////////////////////////7
	public void printTiles(){
		for(int i=0;i<gridx;i++){
			for(int j=0;j<gridy;j++){
				if(gameTiles[i][j]!=null){
					Tile p = gameTiles[i][j];
					System.out.println("x="+p.getX()+" y="+p.getY()+" image="+gameTiles[i][j].toString()+" rot="+gameTiles[i][j].getRotation()+" passable "+gameTiles[i][j].isPassable());
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
	public ArrayList<Tile> tileList(){
		ArrayList<Tile> returns = new ArrayList<Tile>();
		for(int x=0;x<gridx;x++){
			for(int y=0;y<gridy;y++){
				returns.add(gameTiles[x][y]);
			}
		}
		return returns;
	}
	private boolean pointInRectangle(Point2D topLeft,Point2D bottomRight, Point2D point){
		if(point.getX()<topLeft.getX()||point.getX()>bottomRight.getX()||point.getY()<topLeft.getY()||point.getY()>bottomRight.getY()){
			return false;
		}else{
			return true;
		}
	}
	public Tile charInTile(Character c){
		Point2D point = new Point2D.Double(c.getGridx(),c.getGridy());
		Point2D topLeft,bottomRight;
		for(Tile t: tileList()){
			topLeft = new Point2D.Double(t.getX()*32,t.getY()*32);
			bottomRight = new Point2D.Double(t.getX()*32+32,t.getY()*32+32);
			if(pointInRectangle(topLeft,bottomRight,point)){
				return t;
			}
		}
		return null;
	}
}
