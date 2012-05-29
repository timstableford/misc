package grass.data;

import grass.gui.RenderPanel;

public class Follower implements Runnable{
	private NPC npc;
	private RenderPanel renderPanel;
	private Grid grid;
	private boolean run = true;
	private NPCMover mover= null;
	public Follower(NPC npc, RenderPanel renderPanel, Grid grid){
		this.npc = npc;
		this.renderPanel = renderPanel;
		this.grid = grid;
	}
	@Override
	public void run() {
		while(run){
			mover = new NPCMover(npc,renderPanel,grid);
			if(mover!=null){ mover.stop(); mover=null; }
			mover = new NPCMover(npc,renderPanel,grid);
			Thread t = new Thread(mover);
			t.start();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void stop(){
		run = false;
	}
}
