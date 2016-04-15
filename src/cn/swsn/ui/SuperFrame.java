package cn.swsn.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SuperFrame extends JFrame implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String title = "通讯测试软件";
	public JPanel jp = new MyJPanel();

	public SuperFrame(){
		this.setTitle(title);
		this.setResizable(false);
		this.setSize(400, 500);
		int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int x = (width - this.getWidth())/2;
		int y = (height - this.getHeight())/2;
		this.setLocation(x, y);
		this.setLayout(null);
		jp.setLayout(null);
		jp.setBounds(0, 0, 400,500);
		//jp.setBackground(Color.red);
		this.add(jp);
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

class MyJPanel extends JPanel{
    Image i;
    public MyJPanel(){
        URL url = getClass().getResource("bg1.jpg");
		i=Toolkit.getDefaultToolkit().getImage(url);
    }
    public void paint(Graphics g){
        g.drawImage(i, -2, -10,this);
        super.paintComponents(g);
    }
}