package my;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import org.dom4j.DocumentException;

public class QQRobot {

	public static void main(String[] args) throws DocumentException 
	{
		try {
			DatagramSocket sock = new DatagramSocket(6543);
			byte[] recvBuf = new byte[1024*64];
			CQ cq = new CQ();
			
			while(true)
			{
				DatagramPacket recvPkt = new DatagramPacket(recvBuf, recvBuf.length);
				sock.receive(recvPkt);
				ByteBuffer bbuf = ByteBuffer.wrap(recvPkt.getData(), 0, recvPkt.getLength());
				//开始对数据进行解析
				int reserved = bbuf.getInt();//预留字段，未使用
			    int type = bbuf.getInt();//消息类型
			    if(type == CQ.CQ_GROUP_MSG)
			    {
			    	int subType = bbuf.getInt();
			    	int sendTime = bbuf.getInt();
			    	long fromGroup = bbuf.getLong();
			    	long fromQQ = bbuf.getLong();
			    	short strN = bbuf.getShort();
			    	
			    	byte[] msgData = new byte[4000];
			    	bbuf.get(msgData, 0, strN);
			    	String msg = new String(msgData , 0 , strN , "GBK");
			    	String str = PrivateRequestHandler.i.Handler(msg);
			    	if(msg.indexOf("CQ:at") >= 0)
			    	{
			    		String ss = "[CQ:at,qq=" + String.valueOf(fromQQ) + "]";
			    		ss += str;
			    		str = ss;
			    	}
			    	System.out.println(">>>>>>接收到的数据" + msg );
			    	if(str != null)
			    	{
			    		cq.senGroupMsg(fromGroup, fromQQ, str);
			    	}
			    	
			    }
				
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
