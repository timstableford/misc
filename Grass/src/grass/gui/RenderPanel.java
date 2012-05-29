package grass.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import grass.data.Grid;
import grass.data.Listener;
import grass.data.Tile;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderPanel extends JPanel{
	private static final long serialVersionUID = -530425533384822507L;
	private Grid grid;
	private Listener listener;
	public RenderPanel(Grid g){
		grid = g;
		this.setPreferredSize(new Dimension(grid.getGridx()*grid.getTileDiameter(),grid.getGridy()*grid.getTileDiameter()));
		listener = new Listener(grid, this);
		this.addKeyListener(listener);
		this.setFocusable(true);
	}
	public void paint(Graphics g){
		Tile[][] tiles = grid.getTiles();
		int tD = grid.getTileDiameter();
		Graphics2D g2 = (Graphics2D)g;
		g2.fillRect(0, 0, grid.getGridx()*grid.getTileDiameter(), grid.getGridy()*grid.getTileDiameter());
		/////draw the tiles//////
		for(int i=0; i<grid.getGridx();i++){
			for(int j=0; j<grid.getGridy();j++){
				if(tiles[i][j]!=null){
					double rot = Math.PI/2.00*(double)(tiles[i][j].getRotation());
					g2.rotate(rot, i*tD+tD/2, j*tD+tD/2);
					g2.drawImage(grid.getImage(tiles[i][j].toString()),tD*i,tD*j, null);
				}
			}
		}
		/////////////////////////
		for(grass.data.Character c: grid.getCharacters()){
			c.paint(g2);
		}
	}
}
