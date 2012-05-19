import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JPanel;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 6506379635019982996L;
	private LeCircle circle = new LeCircle();
	private boolean allowDrag = false;
	private long oldTime = System.currentTimeMillis();
	private OutputStream output;
	private boolean isInWindow = true;
	public DrawPanel(){
		this.setPreferredSize(new Dimension(510,510));
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		LeSerial main = new LeSerial();
		main.initialize();
		output = main.getOutput();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		circle.paint(g);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		int mX = e.getX();
		int mY = e.getY();
		Point2D a = new Point2D.Float(mX,mY);
		Point2D b = new Point2D.Float(255,255);
		if(a.distance(b)<=5){
			allowDrag = true;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(allowDrag){
			circle.setX(255);
			circle.setY(255);
			repaint();
			allowDrag = false;
			writeString("sxne");
			writeString("syne");
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		isInWindow = true;
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		isInWindow = false;
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(allowDrag){
			circle.setX(e.getX());
			circle.setY(e.getY());
			repaint();
			//5
			if((System.currentTimeMillis()-oldTime)>10){
				sendUpdate(e.getX(),e.getY());
				oldTime = System.currentTimeMillis();
			}
		}
	}
	public void sendUpdate(int mX, int mY){
		if(isInWindow){
			byte[] leX = {'s','x',0,0};
			if(mX<190){
				leX[2] = 'l';
				leX[3] = map(mX);
				writeBytes(leX);
			}else if(mX>210){
				leX[2] = 'r';
				leX[3] = map(mX);
				writeBytes(leX);
			}else{
				writeString("sxne");
			}
			byte[] leY = {'s','y',0,0};
			if(mY<190){
				leY[2] = 'f';
				leY[3] = map(mY);
				writeBytes(leY);
			}else if(mY>210){
				leY[2] = 'r';
				leY[3] = map(mY);
				writeBytes(leY);
			}else{
				writeString("syne");
			}
		}
	}
	public byte map(int a){
		int b = Math.abs(a-255);
		if(b<0){
			b = 0;
		}
		if(b>255){
			b = 255;
		}
		return (byte) b;
	}
	public void writeString(String s){
		for(int i = 0; i<4; i++){
			try {
				output.write((byte)s.charAt(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void writeBytes(byte[] s){
		
		for(int i = 0; i<s.length; i++){
			try {
				output.write((byte)s[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
}
