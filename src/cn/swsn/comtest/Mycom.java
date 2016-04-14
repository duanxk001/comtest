package cn.swsn.comtest;

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

public class Mycom implements SerialPortEventListener{

	protected int sendCount, reciveCount;
	protected InputStream inputStream = null;
	protected OutputStream outputStream = null;
	protected List<String> portList; 
	
	protected CommPortIdentifier portId;
	protected SerialPort serialPort;
	
	public List<String> results = new ArrayList<String>();
	
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
            byte[] readBuffer = new byte[50];  

        try {  
            while (inputStream.available() > 0) {  
                inputStream.read(readBuffer);  
            }  
            StringBuilder receivedMsg = new StringBuilder("/-- ");  
            receivedMsg.append(new String(readBuffer).trim()).append(" --/\n"); 
            results.add(new String(readBuffer).trim());
            System.out.println(receivedMsg.toString());  
            reciveCount++;  
            System.out.println("  ����: "+sendCount+"                                      ����: "+reciveCount);  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
    } 
	}
	
	//�򿪶˿�
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
            System.out.println("δ�ҵ����õĴ��ж˿ں�,�����޷�����!");  
            System.exit(0);  
        }
	}
	
	/*
	 * �򿪴��ڲ�����
	 */
	public void openSerialPort() {   
        // ��ȡҪ�򿪵Ķ˿�  
		String portname = "COM1";
        try {  
            portId = CommPortIdentifier.getPortIdentifier(portname);  
        } catch (NoSuchPortException e) {  
            System.out.println("��Ǹ,û���ҵ�"+portname+"���ж˿ں�!");  
            return ;  
        }  
        // �򿪶˿�  
        try {  
            serialPort = (SerialPort) portId.open("Mycom", 2000);  
            System.out.println(portname+"�����Ѿ���!");  
        } catch (PortInUseException e) {  
        	System.out.println(portname+"�˿��ѱ�ռ��,����!");  
            return ;  
        }  
          
        // ���ö˿ڲ���  
        try {  
            int rate = 9600;  
            int data = 8;  
            int stop = 1;  
            int parity = 0;  
            serialPort.setSerialPortParams(rate,data,stop,parity);  
        } catch (UnsupportedCommOperationException e) {  
        	System.out.println(e.getMessage());  
        }  
  
        // �򿪶˿ڵ�IO���ܵ�   
        try {   
            outputStream = serialPort.getOutputStream();   
            inputStream = serialPort.getInputStream();   
        } catch (IOException e) {  
        	System.out.println(e.getMessage());  
        }   
  
        // ���˿���Ӽ�����  
        try {   
            serialPort.addEventListener(this);   
        } catch (TooManyListenersException e) {  
        	System.out.println(e.getMessage());  
        }   
  
        serialPort.notifyOnDataAvailable(true);   
    }
	
    /** 
     * �����ж˿ڷ������� 
     */  
    public void sendDataToSeriaPort(String mesg) {   
        try {   
            sendCount++;  
            outputStream.write(mesg.getBytes());   
            outputStream.flush();   
  
        } catch (IOException e) {   
            System.out.println(e.getMessage());  
        }   
    }
	
	public static void main(String[] args) {
		  Mycom mc = new Mycom();
		  mc.scanPorts();
		  mc.openSerialPort();
		  mc.sendDataToSeriaPort("���ǹ���");
	}

}
