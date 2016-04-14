package cn.swsn.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainFrame extends SuperFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JLabel jt = new JLabel();
	public JButton jb1 = new JButton("答题");
	public JButton jb2 = new JButton("查看");
	public JButton jb3 = new JButton("设置");

	public MainFrame() {
		jt.setText("请点击下面按钮开始测试：");
		jt.setBounds(60, 40, 250, 50);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jb1.addMouseListener(this);
		jb2.addMouseListener(this);
		jb3.addMouseListener(this);
		jb1.setBounds(130, 120, 120, 34);
		jb2.setBounds(130, 220, 120, 34);
		jb3.setBounds(130, 320, 120, 34);
		this.jp.add(jt);
		this.jp.add(jb1);
		this.jp.add(jb2);
		this.jp.add(jb3);
		this.setVisible(true);
		this.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(jb1)) {
			new ActionFrame();
		} else if (e.getSource().equals(jb2)) {
			new ViewFrame();
		} else {
			new SettingFrame();
		}

	}

}

class ActionFrame extends SuperFrame implements Runnable {

	private static final long serialVersionUID = -5045490789445946268L;
	private int time;
	private JLabel jl_name = new JLabel("姓名:");
	private JTextField jt_name = new JTextField(10);
	private JLabel jl_no = new JLabel("学号:");
	private JTextField jt_no = new JTextField(10);
	private JButton jb1 = new JButton("开始");
	private JButton jb2 = new JButton("保存");
	private JLabel jl_info = new JLabel("答题信息：无");
	private JTextArea jta = new JTextArea(20, 30);
	Object[][] playerInfo = new Object[250][4];
	String[] Names = { "序号", "端子", "名称", "状态" };

	ActionFrame() {
		time = 100;
		this.setTitle("答题测试");
		this.remove(jp);
		this.jp = new JPanel();
		this.jp.setLayout(null);
		this.jp.setBounds(0, 0, 400,500);
		this.add(jp);
		JTable table = new JTable(playerInfo, Names);
		table.setPreferredScrollableViewportSize(new Dimension(550, 30));
		JScrollPane scrollPane = new JScrollPane(table);
		jb1.addMouseListener(this);
		jb2.addMouseListener(this);
		jl_name.setBounds(10, 20, 40, 24);
		jt_name.setBounds(40, 20, 100, 24);
		jl_no.setBounds(170, 20, 40, 24);
		jt_no.setBounds(200, 20, 100, 24);
		jb1.setBounds(320, 20, 60, 24);
		jb2.setBounds(320, 50, 60, 24);
		scrollPane.setBounds(10, 80, 380, 400);
		jl_info.setBounds(10, 60, 380, 24);
		this.jp.prepareImage(null, this);
		this.jp.add(jl_name);
		this.jp.add(jt_name);
		this.jp.add(jl_no);
		this.jp.add(jt_no);
		this.jp.add(jb1);
		this.jp.add(jb2);
		this.jp.add(jl_info);
		this.jp.add(scrollPane);
		this.setVisible(true);
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// new ActionFrame();
		if (e.getSource().equals(jb1)) {
			// new Thread(this).start();
			for (int i = 0; i < 250; i++) {
				/*
				 * try { Thread.sleep(50); } catch (InterruptedException e1) {
				 * // TODO Auto-generated catch block e1.printStackTrace(); }
				 */
				// jta.append("BAT" + i + "  --> ECU1   灯光" + i + " --> 灯光3" +
				// "\n");
				Object[] obj = { "1", "2", "3", "4" };
				playerInfo[i] = obj;
				this.repaint();
			}
		} else if (e.getSource().equals(jb2)) {
			JOptionPane.showMessageDialog(this.getParent(), "保存答题信息成功！");
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (time > 0) {
			try {
				jl_info.setText(time-- + "");
				this.repaint();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}

class ViewFrame extends SuperFrame {

	private static final long serialVersionUID = -6158314552223562499L;
	private JLabel jl_name = new JLabel("姓名");
	private JTextField jt_name = new JTextField(10);
	private JButton jb1 = new JButton("搜索");
	private JLabel jl_info = new JLabel("答题信息：无");
	private JTextArea jta = new JTextArea(20, 30);
	Object[][] playerInfo = new Object[250][4];
	String[] Names = { "序号", "端子", "名称", "状态" };

	ViewFrame() {
		this.setTitle("查看答题信息");
		this.remove(jp);
		this.jp = new JPanel();
		this.jp.setLayout(null);
		this.jp.setBounds(0, 0, 400,500);
		this.add(jp);
		JTable table = new JTable(playerInfo, Names);
		table.setPreferredScrollableViewportSize(new Dimension(550, 30));
		JScrollPane scrollPane = new JScrollPane(table);
		jb1.addMouseListener(this);
		jl_name.setBounds(10, 20, 40, 24);
		jt_name.setBounds(40, 20, 100, 24);
		jb1.setBounds(220, 20, 60, 24);
		scrollPane.setBounds(10, 80, 380, 400);
		jl_info.setBounds(10, 60, 380, 24);
		this.jp.add(jl_name);
		this.jp.add(jt_name);
		this.jp.add(jb1);
		this.jp.add(jl_info);
		this.jp.add(scrollPane);
		this.setVisible(true);
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// new ActionFrame();
		for (int i = 0; i < 250; i++) {
			/*
			 * try { Thread.sleep(50); } catch (InterruptedException e1) { //
			 * TODO Auto-generated catch block e1.printStackTrace(); }
			 */
			// jta.append("BAT" + i + "  --> ECU1   灯光" + i + " --> 灯光3" +
			// "\n");
			Object[] obj = { "1", "2", "3", "4" };
			playerInfo[i] = obj;
			this.repaint();
		}
	}

}

class SettingFrame extends SuperFrame {

	private static final long serialVersionUID = 8778767035322845353L;
	private JLabel jl_com = new JLabel("端口:");
	public JTextField jt_com = new JTextField(10);
	private JLabel jl_name = new JLabel("名称:");
	public JTextField jt_name = new JTextField(10);
	private JLabel jl_time = new JLabel("答题时间:");
	public JTextField jt_time = new JTextField(20);
	private JLabel jl_size = new JLabel("显示题目数量:");
	public JTextField jt_size = new JTextField(20);
	public JButton jb = new JButton("保存");

	private JLabel jl_dburl = new JLabel("数据库地址:");
	public JTextField jt_dburl = new JTextField(50);
	private JLabel jl_username = new JLabel("用户名:");
	public JTextField jt_username = new JTextField(10);
	private JLabel jl_passwd = new JLabel("密码:");
	public JTextField jt_passwd = new JTextField(10);

	SettingFrame() {
		this.setTitle("程序设置");
		jl_com.setBounds(50, 50, 40, 24);
		jl_name.setBounds(200, 50, 40, 24);
		jt_com.setBounds(80, 50, 100, 24);
		jt_name.setBounds(230, 50, 100, 24);
		jl_time.setBounds(50, 100, 100, 24);
		jt_time.setBounds(140, 100, 150, 24);
		jl_size.setBounds(50, 150, 100, 24);
		jt_size.setBounds(140, 150, 150, 24);

		jl_dburl.setBounds(50, 250, 100, 24);
		jt_dburl.setBounds(130, 250, 150, 24);
		jl_username.setBounds(50, 290, 100, 24);
		jt_username.setBounds(130, 290, 100, 24);
		jl_passwd.setBounds(50, 330, 100, 24);
		jt_passwd.setBounds(130, 330, 100, 24);

		jb.addMouseListener(this);
		jb.setBounds(160, 380, 60, 24);

		this.jp.add(jl_com);
		this.jp.add(jt_com);
		this.jp.add(jl_name);
		this.jp.add(jt_name);
		this.jp.add(jl_time);
		this.jp.add(jt_time);
		this.jp.add(jl_size);
		this.jp.add(jt_size);

		this.jp.add(jl_dburl);
		this.jp.add(jt_dburl);
		this.jp.add(jl_username);
		this.jp.add(jt_username);
		this.jp.add(jl_passwd);
		this.jp.add(jt_passwd);

		this.jp.add(jb);
		this.setVisible(true);
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this.getParent(), "保存设置信息成功！");
	}

}