package cn.swsn.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.swsn.comtest.Mycom;
import cn.swsn.util.DbUtil;
import cn.swsn.util.PropertyUtil;

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
		DbUtil.mf = this;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(jb1)) {
			new ActionFrame();
		} else if (e.getSource().equals(jb2)) {
			new ViewFrame();
		} else {
			String pwd = JOptionPane.showInputDialog(this,"请输入密码");
			if("123".equals(pwd)){
				new SettingFrame();
			}else{
				JOptionPane.showMessageDialog(this, "密码错误！");
			}
					
		}

	}

}

class ActionFrame extends SuperFrame implements Runnable,WindowListener{

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
	Mycom mc = Mycom.getMC();
	Thread th = null;

	ActionFrame() {
		time = Integer.parseInt(PropertyUtil.getProperty("time")) * 600;
		this.setTitle("答题测试");
		this.remove(jp);
		this.jp = new JPanel();
		this.jp.setLayout(null);
		this.jp.setBounds(0, 0, 400,500);
		this.add(jp);
		JTable table = new JTable(playerInfo, Names);
		table.setDefaultRenderer(Object.class, new ColorTableCellRenderer());
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
		jb2.setEnabled(false);
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
		this.addWindowListener(this);
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// new ActionFrame();
		if (e.getSource().equals(jb1)) {
			if(jb1.getText().equals("开始")){
				jb1.setText("结束");
				jb2.setEnabled(true);
				mc.listenData();
				time = Integer.parseInt(PropertyUtil.getProperty("time")) * 600;
				for (int i = 0; i < playerInfo.length; i++) {
					playerInfo[i] = new Object[4];
				}
				th = new Thread(this);
				th.start();
			}else{
				jb1.setText("开始");
				jb2.setEnabled(false);
				time = 0;
				//mc.sendDataToSeriaPort("ok");
				mc.stopListenData();
				th.stop();
			}
		} else if (e.getSource().equals(jb2)) {
			if("".equals(jt_name.getText().trim()) || jt_name.getText().trim() == null){
				JOptionPane.showMessageDialog(this.getParent(), "请输入姓名");
			}else{
				for(int i = 0; i < playerInfo.length; i++){
					if(playerInfo[i][3] != null && !"".equals(playerInfo[i][3])){
						String sql = "insert into t_record (perName,portName,ansName,status) values ('" + 
								 jt_name.getText().trim() + "','" +
								 playerInfo[i][1] + "','" +
								 playerInfo[i][2] + "','" +
								 playerInfo[i][3] + "')" ;
					System.out.println(sql);
					DbUtil.save(sql);
					}
				}
				jb2.setEnabled(false);
				JOptionPane.showMessageDialog(this.getParent(), "保存答题信息成功！");
			}
			
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<String> results = mc.results;
		String comm = PropertyUtil.getProperty("portName");
		String aname = PropertyUtil.getProperty("ansName");
		//int i = 0;
		int count = 0;
		while (time > 0) {
			try {
				int tem = time--;
				String ss = "答题数：" + count + ".  剩余时间:" + tem/600 + "分" + (tem/10)%60 + "秒";
				jl_info.setText(ss);
				if(results.size() > count){
					//for(int j = count; j < results.size(); j++){
						Object[] obj = { count, comm, aname, results.get(count) };
						playerInfo[count] = obj;
						count ++;
					//}
					//i = results.size() - 1;
				}
				this.repaint();
				Thread.sleep(100);
				if(time == 0){
					//mc.sendDataToSeriaPort("ok");
					mc.stopListenData();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		th.stop();
		System.out.println("线程关闭");
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		if(th != null){
			th.stop();
		}
		if(mc != null){
			mc.stopListenData();
		}
		System.out.println("线程关闭");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
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
		table.setDefaultRenderer(Object.class, new ColorTableCellRenderer());
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
		for (int i = 0; i < playerInfo.length; i++) {
			playerInfo[i] = new Object[4];
		}
		this.repaint();
		String sql = "select * from t_record where perName = '" + jt_name.getText().trim() + "'";
		System.out.println(sql);
		List<Map> lists = DbUtil.read(sql);
		for (int i = 0; i < lists.size(); i++) {
			Object[] obj = { 
					lists.get(i).get("id"),
					lists.get(i).get("portName"),
					lists.get(i).get("ansName"),
					lists.get(i).get("status")};
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
		this.loadProperties();
		this.setVisible(true);
		this.repaint();
	}

	private void loadProperties(){
		jt_com.setText(PropertyUtil.getProperty("portName"));
		jt_name.setText(PropertyUtil.getProperty("ansName"));
		jt_time.setText(PropertyUtil.getProperty("time"));
		jt_size.setText(PropertyUtil.getProperty("size"));
		jt_dburl.setText(PropertyUtil.getProperty("db_url"));
		jt_username.setText(PropertyUtil.getProperty("db_name"));
		jt_passwd.setText(PropertyUtil.getProperty("db_passwd"));
	}
	
	private void saveProperties(){
		PropertyUtil.setProperty("portName",jt_com.getText());
		PropertyUtil.setProperty("ansName",jt_name.getText());
		PropertyUtil.setProperty("time",jt_time.getText());
		PropertyUtil.setProperty("size",jt_size.getText());
		PropertyUtil.setProperty("db_url",jt_dburl.getText());
		PropertyUtil.setProperty("db_name",jt_username.getText());
		PropertyUtil.setProperty("db_passwd",jt_passwd.getText());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		saveProperties();
		JOptionPane.showMessageDialog(this.getParent(), "保存设置信息成功！");
	}

}