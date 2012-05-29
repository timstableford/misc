package grass.data;

import grass.gui.RenderPanel;

public class PlayerRunnable implements Runnable {
	private Player player;
	private CharacterDirection direction;
	private double speed;
	private RenderPanel renderPanel;
	private boolean move = true;
	public PlayerRunnable(Player p, CharacterDirection d, double s, RenderPanel r){
		player = p;
		direction = d;
		speed = s;
		renderPanel = r;
	}
	@Override
	public void run() {
		while(move){
			switch(direction){
			case UP:
				double c = player.getY()-speed;
				if(c>64){
					player.setDirection(CharacterDirection.UP);
					player.setY(c);
					renderPanel.repaint();
				}
				break;
			case DOWN:
				double d = player.getY()+speed;
				if(d-32<renderPanel.getHeight()){
					player.setDirection(CharacterDirection.DOWN);
					player.setY(d);
					renderPanel.repaint();
				}
				break;
			case LEFT:
				double e = player.getX()-speed;
				if(e-64<renderPanel.getWidth()){
					player.setDirection(CharacterDirection.LEFT);
					player.setX(e);
					renderPanel.repaint();
				}
				break;
			case RIGHT:
				double f = player.getX()+speed;
				if(f>96){
					player.setDirection(CharacterDirection.RIGHT);
					player.setX(f);
					renderPanel.repaint();
				}
				break;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.err.println("Player couldnt sleep");
			}
		}
	}
	public void stop(){
		move = false;
	}

}
