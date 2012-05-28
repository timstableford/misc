package grass.data;

import grass.gui.RenderPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import djikstra.Path;

public class Listener implements KeyListener{
	private Grid grid;
	private RenderPanel renderPanel;
	private char pressed = 0;
	private PlayerRunnable runner;
	public Listener(Grid g, RenderPanel p){
		grid = g;
		renderPanel = p;
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char code = e.getKeyChar();
		if(pressed!=0){ return; }
		pressed = code;
		switch(code){
		case 'w':
			pressed = code;
			grid.getPlayer();
			runner = new PlayerRunnable(grid.getPlayer(),CharacterDirection.UP,grid.getPlayerSpeed(),renderPanel);	
			Thread t = new Thread(runner);
			t.start();
			break;
		case 's':
			pressed = code;
			grid.getPlayer();
			runner = new PlayerRunnable(grid.getPlayer(),CharacterDirection.DOWN,grid.getPlayerSpeed(),renderPanel);	
			Thread z = new Thread(runner);
			z.start();
			break;
		case 'a':
			pressed = code;
			grid.getPlayer();
			runner = new PlayerRunnable(grid.getPlayer(),CharacterDirection.LEFT,grid.getPlayerSpeed(),renderPanel);	
			Thread x = new Thread(runner);
			x.start();
			break;
		case 'd':
			pressed = code;
			grid.getPlayer();
			runner = new PlayerRunnable(grid.getPlayer(),CharacterDirection.RIGHT,grid.getPlayerSpeed(),renderPanel);	
			Thread c = new Thread(runner);
			c.start();
			break;
		case 'u':
			Tile r = grid.charInTile(grid.getCharacters().get(1));
			Path p = new Path(r.getX(),r.getY(),r.getX()+5,r.getY(),grid.tileList());
			NPCMover m = new NPCMover(p.getPath(),(NPC)grid.getCharacters().get(1),renderPanel,grid);
			Thread u = new Thread(m);
			u.start();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char code = e.getKeyChar();
		if(code==pressed&&runner!=null){
			pressed = 0;
			runner.stop();
			runner = null;
		}
	}
}
