package grass.gui;

import java.awt.BorderLayout;

import grass.data.Grid;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 8639189778568615830L;
	private Grid grid;
	private RenderPanel renderPanel;
	public MainFrame(Grid g){
		grid = g;
		renderPanel = new RenderPanel(grid);
		this.add(renderPanel,BorderLayout.CENTER);
		this.pack();
		renderPanel.repaint();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
