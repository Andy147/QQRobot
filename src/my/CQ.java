package my;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;


public class CQ 
{
	//接收消息类型
	public static final int CQ_GROUP_MSG = 2;//群消息
	public static final int CQ_DISCUSS_MSG = 4;//讨论组消息
	public static final int CQ_PRIVATE_MSG = 21;//私人发的消息
	
	
	//发消息的类型
	public static final int SEND_GROUP_MSG = 10002;//群消息
	public static final int SEND_DISCUSS_MSG = 10004;//讨论组消息
	public static final int SEND_PRIVATE_MSG = 10021;//私人发的消息
	DatagramSocket cmdSock;
	int cmdPort = 6542;
	public CQ()
	{
		try
		{
			cmdSock = new DatagramSocket();
		} catch (SocketException e)
		{
			e.printStackTrace();
		}
	}
	//发送消息
	public void senGroupMsg(long toGroup , long toQQ , String strMsg) throws IOException 
	{
		ByteBuffer bbuf = ByteBuffer.allocate(4000);
		bbuf.putInt(0); // reserved
		bbuf.putInt(SEND_GROUP_MSG); // cmd
		bbuf.putLong( toGroup); // fromGroup
		bbuf.putLong( toQQ ); // fromQQ
		
		byte[] strData = strMsg.getBytes("GBK");
		int len = strData.length;
		bbuf.putShort( (short) strData.length );
		bbuf.put(strData );
		
		System.out.printf("↑ Group %d , QQ: %d : %s \n" , toGroup,toQQ, strMsg );
		SocketAddress peerAddr = new InetSocketAddress("127.0.0.1", cmdPort);
		DatagramPacket sendPkt = new DatagramPacket(bbuf.array(), bbuf.position(),peerAddr);
		cmdSock.send( sendPkt );
	}

}
