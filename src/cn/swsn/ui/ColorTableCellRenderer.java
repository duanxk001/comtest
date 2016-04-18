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

class ColorTableCellRenderer extends DefaultTableCellRenderer  
{  
	/**
	 * 
	 */
	private static final long serialVersionUID = -1951734876493518876L;
	private boolean flag = false;
    DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();  
    public Component getTableCellRendererComponent(JTable table, Object value,  
        boolean isSelected, boolean hasFocus, int row, int column) {  
        if(column == 3 && value != null && !"null".equals(value.toString()) && Integer.parseInt(value.toString()) == 1){  
            //调用基类方法  
            flag = true;
        	return super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);  
        }else if(column == 3 && value != null && !"null".equals(value.toString()) && Integer.parseInt(value.toString()) == 0){
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