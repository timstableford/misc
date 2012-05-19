import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
	private DrawPanel mainPanel;
	private static final long serialVersionUID = -3500972136249603648L;
	public MainFrame(){
		mainPanel = new DrawPanel();
		this.add(mainPanel,BorderLayout.CENTER);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}
}
