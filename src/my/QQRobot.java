package my;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class QQRobot {

	public static void main(String[] args) 
	{
		try {
			DatagramSocket sock = new DatagramSocket(6543);
			byte[] recvBuf = new byte[1024*64];
			while(true)
			{
				DatagramPacket recvPkt = new DatagramPacket(recvBuf, recvBuf.length);
				sock.receive(recvPkt);
				System.out.println("接收到的数据" + recvPkt.getLength() + "byte...");
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
