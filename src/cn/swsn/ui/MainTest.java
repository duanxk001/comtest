package cn.swsn.ui;

import java.awt.Font;

import javax.swing.UIManager;

public class MainTest {

	public static void main(String[] args) {
		Font font = new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,12);
        UIManager.put("Button.font", font); 
        UIManager.put("Label.font", font);
		new MainFrame();
	}
}
