package client.socket.tim;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener{
	private JPanel mainPanel = new JPanel();
	private static final long serialVersionUID = 417655854103163769L;
	private JTextField nF;
	private JPasswordField pF;
	private Connection con = null;
	public Login(){
		if(con==null) { 
			con = new Connection();
		}
		this.add(mainPanel, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2,2,2,2);
		c.gridx = 0;
		c.gridy = 0;
		JLabel nL = new JLabel("Name"), pL = new JLabel("Password");
		nF = new JTextField(10);
		pF = new JPasswordField(10);
		JButton submit = new JButton("Login"), quit = new JButton("Quit");
		submit.addActionListener(this);
		quit.addActionListener(this);
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.add(nL, c);
		c.gridx = 1;
		mainPanel.add(nF, c);
		c.gridx = 0;
		c.gridy = 1;
		mainPanel.add(pL, c);
		c.gridx = 1;
		mainPanel.add(pF, c);
		c.gridx = 2;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(quit, c);
		c.gridy = 1;
		mainPanel.add(submit, c);
		pack();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch(action){
		case "Login":
			this.login();
			break;
		case "Quit":
			System.exit(0);
		}
	}
	public void login(){
		boolean loggedin = false;

			loggedin = con.login(nF.getText(), new String(pF.getPassword()));

		if(loggedin){
			System.out.println("Logged in");
		}else{
			System.out.println("Fail");
		}
	}
}
