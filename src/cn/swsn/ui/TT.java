package cn.swsn.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import cn.swsn.util.DbUtil;

class TT extends SuperFrame{

	private static final long serialVersionUID = -6158314552223562499L;
	private JLabel jl_name = new JLabel("姓名");
	private JTextField jt_name = new JTextField(10);
	private JButton jb1 = new JButton("搜索");
	private JLabel jl_info = new JLabel("答题信息：无");
	private JTextArea jta = new JTextArea(20, 30);
	Object[][] playerInfo = new Object[250][4];
	String[] Names = { "序号", "端子", "名称", "状态" };

	public TT() {
		this.setTitle("查看答题信息");
		this.remove(jp);
		this.jp = new JPanel();
		this.jp.setLayout(null);
		this.jp.setBounds(0, 0, 400,500);
		this.add(jp);
		JTable table = new JTable(playerInfo, Names);
		table.setPreferredScrollableViewportSize(new Dimension(550, 30));
		ColorTableCellRenderer cc = new ColorTableCellRenderer();
		table.setDefaultRenderer(Object.class, cc);
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
		//List<Map> lists = DbUtil.read("select * from t_record where perName = '" + jt_name.getText().trim() + "'");
		for (int i = 0; i < 18; i++) {
			if(i%2 == 0){
				Object[] obj = { 
						1,
						2,
						3,
						1};
				playerInfo[i] = obj;
			}else{
				Object[] obj = { 
						1,
						2,
						3,
						0};
				playerInfo[i] = obj;
			}
			
			this.repaint();
		}
	}
	
	
	
	public static void main(String[] args) {
		new TT();
	}


}
class ColorTableCellRenderer extends DefaultTableCellRenderer  
{  
	private boolean flag = false;
    DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();  
    public Component getTableCellRendererComponent(JTable table, Object value,  
        boolean isSelected, boolean hasFocus, int row, int column) {  
        if(column == 3 && value != null && Integer.parseInt(value.toString()) == 1){  
            //调用基类方法  
            flag = true;
        	return super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);  
        }else if(column == 3 && value != null && Integer.parseInt(value.toString()) == 0){
        	flag = false;
        	return super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);
        }  
        else{  
            return renderer.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);  
        }  
    }  
    //该类继承与JLabel，Graphics用于绘制单元格,绘制红线  
    public void paintComponent(Graphics g){  
        super.paintComponent(g);  
        Graphics2D g2=(Graphics2D)g;  
        final BasicStroke stroke=new BasicStroke(2.0f);  
        g2.setStroke(stroke);  
        if(flag){
        	g2.setColor(Color.GREEN); 
        	g2.fillOval(getWidth()/2 - 5,getHeight()/2 - 5, 10, 10);
        }else{
        	g2.setColor(Color.RED); 
        	g2.drawLine(getWidth()/2 - 5,getHeight()/2,getWidth()/2 + 5,getHeight()/2);
        }
          
    }  
}  