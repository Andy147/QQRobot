package my;

public class PrivateRequestHandler
{
	public String Handler(String reqeust)
	{
		if(reqeust.indexOf("哪里") >= 0)
		{
			return "我在上班";
		}
		else if(reqeust.indexOf("干什么") >= 0)
		{
			return "上幼儿园";
		}
		else
		{
			return "无法解答";
		}
		
	}

}
