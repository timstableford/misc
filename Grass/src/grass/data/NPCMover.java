package grass.data;

import grass.gui.RenderPanel;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import djikstra.Path;

public class NPCMover implements Runnable{
	private NPC npc;
	private final double speed = 4;
	private RenderPanel renderPanel;
	private ArrayList<Tile> path;
	private Grid grid;
	private boolean run = true;
	public NPCMover(NPC npc, RenderPanel renderPanel, Grid grid){
		this.grid = grid;
		this.renderPanel = renderPanel;
		this.npc = npc;
		Tile r = grid.charInTile(npc);
		Tile s = grid.charInTile(grid.getPlayer());
		if(!s.isPassable()){
			Tile closest = null;
			for(Tile t: grid.tileList()){
				if(closest==null){ closest = t; }
				if(s.getDistance(t)<closest.getDistance(s)&&t.isPassable()){
					closest = t;
				}
			}
			s = closest;
		}
		Path p = new Path(r.getGridX(),r.getGridY(),s.getGridX(),s.getGridY(),grid.tileList());
		this.path = p.getPath();
	}
	@Override
	public void run() {
		grid.centerCharacter(npc);
		renderPanel.repaint();
		for(Tile t: path){
			if(run){ moveTo(t); }
		}
	}
	private void moveTo(Tile t){
		if(!run){ return; }
		Tile lastTile = grid.charInTile(npc);
		System.out.println("current tile "+lastTile.getGridX()+":"+lastTile.getGridY()+" next tile "+t.getGridX()+":"+t.getGridY());
		while(lastTile.getGridX()!=t.getGridX()||lastTile.getGridY()!=t.getGridY()){
			if(lastTile.getGridY()>t.getGridY()&&grid.tilePassable(lastTile.getGridX(),lastTile.getGridY()-1)){
				move(CharacterDirection.UP);
			}else if(lastTile.getGridY()<t.getGridY()&&grid.tilePassable(lastTile.getGridX(),lastTile.getGridY()+1)){
				move(CharacterDirection.DOWN);
			}else if(lastTile.getGridX()>t.getGridX()&&grid.tilePassable(lastTile.getGridX()-1,lastTile.getGridY())){
				move(CharacterDirection.LEFT);
			}else if(lastTile.getGridX()<t.getGridX()&&grid.tilePassable(lastTile.getGridX()+1,lastTile.getGridY())){
				move(CharacterDirection.RIGHT);
			}else{
				System.err.println("cannot move npc");
			}
			lastTile = grid.charInTile(npc);
		}
	}
	private void move(CharacterDirection c){
		if(!run){ return; }
		double dist = 0;
		npc.setDirection(c);
		while(dist<32){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				System.err.println("Player couldnt sleep");
			}
			switch(c){
			case UP:
				npc.setY(npc.getY()-speed);
				break;
			case DOWN:
				npc.setY(npc.getY()+speed);
				break;
			case LEFT:
				npc.setX(npc.getX()-speed);
				break;
			case RIGHT:
				npc.setX(npc.getX()+speed);
				break;
			}
			renderPanel.repaint();
			dist = dist + speed;
		}
	}
	public void stop(){
		run = false;
	}
}
