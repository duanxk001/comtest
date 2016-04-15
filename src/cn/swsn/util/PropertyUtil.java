package cn.swsn.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyUtil {
	static Properties prop = new Properties();  
	static InputStream in = null;
    static {
		try {
			in = new FileInputStream("C:/conf.properties");
			if(in == null){
	    		in = Object.class.getResourceAsStream("/conf.properties");
	    	}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			in = Object.class.getResourceAsStream("/conf.properties");
			e.printStackTrace();
		}
    }

	public static String getProperty(String str){
        String result = null;
        try {   
            prop.load(in);   
            result = new String(prop.getProperty(str).trim().getBytes("ISO-8859-1"),"UTF8");   
            //System.out.println(getEncoding(result));
        } catch (IOException e) {   
            e.printStackTrace();   
        } 
        return result;
	}
	public static void setProperty(String key,String value){
		try {   
			prop.load(in);
			OutputStream out = new FileOutputStream("C:/conf.properties");
            prop.setProperty(key, new String(value.getBytes("UTF8"),"ISO-8859-1"));
            prop.store(out, "update");
            out.close();
        } catch (IOException e) {   
            e.printStackTrace();   
        } 
	}
	
	/*
	 * 判断字符串编码
	 */
	public static String getEncoding(String str) {      
	       String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s = encode;      
	              return s;      
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   } 
	
	public static void main(String[] args) {
//		System.out.println(getProperty("db_name"));
//		System.out.println(getProperty("db_passwd"));
		//setProperty("ansName","bitch");
	}
}
