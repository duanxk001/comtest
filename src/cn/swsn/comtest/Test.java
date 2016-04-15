package cn.swsn.comtest;

import java.io.File;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;

public class Test {

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.library.path"));
		
		Enumeration<?> en = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier portId;
		while (en.hasMoreElements()) {
			portId = (CommPortIdentifier) en.nextElement();
			// 如果端口类型是串口，则打印出其端口信息
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println(portId.getName());
			}
		}
	}
}
