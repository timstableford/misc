package grass;

import java.io.IOException;

import grass.data.Grid;
import grass.data.NPC;
import grass.data.Player;
import grass.gui.MainFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Grid g = new Grid();
		
		try {
			g.loadImages("images.txt");
			g.loadTiles("tiles.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Player p = new Player("Link","player.png",300,150);
			g.setPlayer(p);
			NPC npc = new NPC("Bob","player.png",16,16);
			g.addCharacter(npc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.printTiles();
		MainFrame m = new MainFrame(g);
		m.setVisible(true);
	}

}
