package grass.data;

import grass.gui.RenderPanel;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import djikstra.Path;

public class NPCMover implements Runnable{
	private NPC npc;
	private final double speed = 5.00;
	private RenderPanel renderPanel;
	private ArrayList<Tile> path;
	private Grid grid;
	public NPCMover(ArrayList<Tile> path, NPC npc, RenderPanel renderPanel, Grid grid){
		this.grid = grid;
		this.renderPanel = renderPanel;
		this.npc = npc;
		this.path = path;
	}
	@Override
	public void run() {
		for(Tile t: path){
			System.out.println("moving to "+t.getX()+":"+t.getY());
			moveTo(t);
		}
	}
	private void moveTo(Tile t){
		double moved = 0;
		Point2D a = new Point2D.Double(npc.getGridx(),npc.getGridy());
		
		Point2D b = new Point2D.Double((t.getX()*32+16),(t.getY()*32+16));
		double max = a.distance(b);
		double x = Math.abs(npc.getGridx()-(t.getX()*32+16));
		double y = Math.abs(npc.getGridy()-(t.getY()*32+16));
		double newx,newy;
		double angle = Math.atan2(y,x);
		while(moved<max){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.err.println("Player couldnt sleep");
			}
			System.out.println(moved+":"+max);
			moved=moved+speed;
			newy = moved*Math.sin(angle)+a.getY();
			newx = moved*Math.cos(angle)+a.getX();
			npc.setGridx(newx);
			npc.setGridy(newy);
			renderPanel.repaint();
			if(moved>max){
				moved = max;
			}
		}
	}

}
