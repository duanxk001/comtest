package cn.swsn.comtest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import cn.swsn.util.PropertyUtil;

public class Mycom implements SerialPortEventListener{

	private static Mycom mc = null;
	protected int sendCount, reciveCount;
	protected InputStream inputStream = null;
	protected OutputStream outputStream = null;
	protected List<String> portList; 
	
	protected CommPortIdentifier portId;
	protected SerialPort serialPort;
	
	public List<String> results = new ArrayList<String>();
	
	public static Mycom getMC(){
		if(mc == null){
			mc = new Mycom();
		}
		return mc;
	}
	
	@Override
	public void serialEvent(SerialPortEvent event) {
		// TODO Auto-generated method stub
		switch (event.getEventType()) {  
        case SerialPortEvent.BI:  
        case SerialPortEvent.OE:  
        case SerialPortEvent.FE:  
        case SerialPortEvent.PE:  
        case SerialPortEvent.CD:  
        case SerialPortEvent.CTS:  
        case SerialPortEvent.DSR:  
        case SerialPortEvent.RI:  
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:  
            break;  
        case SerialPortEvent.DATA_AVAILABLE:  
            byte[] readBuffer = new byte[256];  
            dealAsByte(readBuffer);
  		    break;
  		default:
  		    mc.sendDataToSeriaPort(false);
        	
    }
	}
	
	public void dealAsByte(byte[] readBuffer){
		try {  
            while (inputStream.available() > 0) {  
                inputStream.read(readBuffer);  
            }
            
            if("e1".equals(Integer.toHexString((int)readBuffer[0] & 0xFF))
            	&& "d2".equals(Integer.toHexString((int)readBuffer[1] & 0xFF))){
            		int c = ((int)readBuffer[2])/8;
            		for(int i = 0; i < c; i++){
            			int l = readBuffer[3 + i];
            			String str2=Integer.toBinaryString(l);
            			String[] bs = str2.split("");
            			for(int j = 0; j < 8; j++){
            				if(j < (9 - bs.length)){
            					results.add("0");
            				}else{
            					results.add(bs[j - (8 - bs.length)]);
            				}
            			}
                     	System.out.println("int:" + (int)readBuffer[3 + i] + " HexString:" + Integer.toHexString((int)readBuffer[3 + i] & 0xFF));
                     }
            		mc.sendDataToSeriaPort(true);
            }else{
      		    mc.sendDataToSeriaPort(false);
            }
            
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        } 
    } 
	
	public void dealAsString(byte[] readBuffer){
		try {  
            while (inputStream.available() > 0) {  
                inputStream.read(readBuffer);  
            }  
            StringBuilder receivedMsg = new StringBuilder("/-- ");  
            receivedMsg.append(new String(readBuffer).trim()).append(" --/\n"); 
            results.add(new String(readBuffer).trim());
            System.out.println(receivedMsg.toString());  
            reciveCount++;  
            System.out.println("  发送: "+sendCount+"                                      接收: "+reciveCount);  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        } 
    } 
	
	//打开端口
	public void scanPorts(){
		portList = new ArrayList<String>();  
        Enumeration<?> en = CommPortIdentifier.getPortIdentifiers();  
        CommPortIdentifier portId;  
        while(en.hasMoreElements()){  
            portId = (CommPortIdentifier) en.nextElement();  
            if(portId.getPortType() == CommPortIdentifier.PORT_SERIAL){  
                String name = portId.getName();  
                if(!portList.contains(name)) {  
                    portList.add(name);  
                }  
            }  
        }  
        if(null == portList   
                || portList.isEmpty()) {  
            System.out.println("未找到可用的串行端口号,程序无法启动!");  
            System.exit(0);  
        }
	}
	
	/*
	 * 打开串口并监听
	 */
	public void openSerialPort() {   
        // 获取要打开的端口  
		String portname = PropertyUtil.getProperty("portName");
		if(portId == null){
	        try {  
	            portId = CommPortIdentifier.getPortIdentifier(portname);  
	        } catch (NoSuchPortException e) {  
	            System.out.println("抱歉,没有找到"+portname+"串行端口号!");  
	            return ;  
	        } 
		}
 
        // 打开端口  
        try {  
        	if(serialPort == null){
        		serialPort = (SerialPort) portId.open("Mycom", 2000); 
        	}
            System.out.println(portname+"串口已经打开!");  
        } catch (PortInUseException e) {  
        	System.out.println(portname+"端口已被占用,请检查!");  
            return ;  
        }  
          
        // 设置端口参数  
        try {  
            int rate = 9600;  
            int data = 8;  
            int stop = 1;  
            int parity = 0;  
            serialPort.setSerialPortParams(rate,data,stop,parity);  
        } catch (UnsupportedCommOperationException e) {  
        	System.out.println(e.getMessage());  
        }  
  
        // 打开端口的IO流管道   
        try {   
            outputStream = serialPort.getOutputStream();   
            inputStream = serialPort.getInputStream();   
        } catch (IOException e) {  
        	System.out.println(e.getMessage());  
        }   
  
    }
	
	public void listenData(){
		if(serialPort == null){
			openSerialPort();
		}
		 // 给端口添加监听器  
        try {   
            serialPort.addEventListener(this);   
        } catch (TooManyListenersException e) {  
        	System.out.println(e.getMessage());  
        }   
  
        serialPort.notifyOnDataAvailable(true); 
	}
	
	public void stopListenData(){
		if(serialPort != null){
			serialPort.removeEventListener();   
		    serialPort.notifyOnDataAvailable(false);
		    results = new ArrayList<String>();
		}
	}
	
    /** 
     * 给串行端口发送数据 
     */  
    public void sendDataToSeriaPort(boolean t) { 
    	
    	byte[] res = null;
    	if(t){
    		res = new byte[]{(byte)0xa1,(byte)0xb2,(byte)0xc3,(byte)0xd4};
    	}else{
    		res = new byte[]{(byte)0x51,(byte)0x62,(byte)0x73,(byte)0x84};
    	}
        try {   
            sendCount++;  
            outputStream.write(res);   
            outputStream.flush();   
  
        } catch (IOException e) {   
            System.out.println(e.getMessage());  
        }   
    }
    
    /*
     * 关闭串口
     */
	public static void main(String[] args) {
		 /* Mycom mc = new Mycom();
		  mc.scanPorts();
		  mc.openSerialPort();
		  byte[] bs = new byte[]{(byte)0xa1,(byte)0xb2,(byte)0xc3,(byte)0xd4};*/
		  //mc.sendDataToSeriaPort(bs);
		System.out.println(System.getProperty("java.home") + 
			      File.separator + 
			      "lib" + 
			      File.separator + 
			      "javax.comm.properties");		
	/*	String str="1f";
		int i=Integer.parseInt(str,16);
		String str2=Integer.toBinaryString(i);
		System.out.println(str2);
		byte[] bs = new Integer(16).toString().getBytes();
		for(byte b : bs){
			System.out.println(b);
		}*/
	}

}
