package my;

import java.io.UnsupportedEncodingException;

public class Test
{

	public static void main(String[] args) throws UnsupportedEncodingException
	{
		String str = "iiii";
		byte[] buf = str.getBytes("GBK");
		int len = buf.length;
		System.out.println("000");

	}

}
