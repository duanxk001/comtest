package cn.swsn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import cn.swsn.ui.MainFrame;

public class DbUtil {

	public static Connection conn = null;
	public static Statement smt = null;
	
	public static MainFrame mf = null;
	public static Connection getConnect(){
		
		String acount = "?user=" + PropertyUtil.getProperty("db_name") + "&password=" + PropertyUtil.getProperty("db_passwd") + "&useUnicode=true&characterEncoding=UTF8";
		String url = "jdbc:mysql://" + PropertyUtil.getProperty("db_url") + ":3306/comtest";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动程序");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url + acount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String urlNew = "jdbc:mysql://localhost:3306/mysql";
			try {
				conn = DriverManager.getConnection(urlNew + acount);
				Statement smt = conn.createStatement();
				smt.execute("create database comtest Character Set UTF8");
				smt.close();
				getConnect();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				if(mf !=null )
					JOptionPane.showMessageDialog(mf, "数据库连接失败！");
			}
		}
		try {
			Statement smt1 = conn.createStatement();
			smt1.execute("create table if not exists t_record (id int key NOT NULL auto_increment,perName varchar(20) not null,portName varchar(20),ansname varchar(20),status varchar(20))");
			smt1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void save(String sql){
		if(conn == null){
			conn = getConnect();
		}
		if(smt == null){
			try {
				smt = conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			smt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<Map> read(String sql){
		if(conn == null){
			conn = getConnect();
		}
		if(smt == null){
			try {
				smt = conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<Map> resultList = new ArrayList<Map>();
		try {
			ResultSet rs = smt.executeQuery(sql);
			while(rs.next()){
				Map map = new HashMap();
				map.put("id",rs.getObject(1));
				map.put("protName",rs.getObject(3));
				map.put("ansName",rs.getObject(4));
				map.put("status",rs.getObject(5));
				resultList.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}
	
	public static void closeDB() throws SQLException{
		if(smt != null){
			smt.close();
		}
		if(conn != null){
			conn.close();
		}
	}
	public static void main(String[] args) {
		getConnect();
	}
}
