package my;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class PrivateRequestHandler
{
	List<QQRobotData> cfgItem = new ArrayList<QQRobotData>();
	String defa = null;
	
	//单例设计模式
	public static PrivateRequestHandler i = new PrivateRequestHandler();
	private PrivateRequestHandler()
	{
		//先加载配置文件
		System.out.println("===============加载配置文件================");
		try
		{
			load();
		} catch (DocumentException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		System.out.println("===============配置文件加载成功==============");
		
	}
	public String Handler(String reqeust)
	{
		for(int i = 0 ; i < cfgItem.size() ; i++)
		{
			QQRobotData it = cfgItem.get(i);
			if(reqeust.indexOf(it.match) >= 0)
			{
				long now = System.currentTimeMillis();
				if(now - it.timestamp < 1000 * 60)
				{
					return null;
				}
				else
				{
					it.timestamp = now;
					return it.response;
				}
				
			}
			if((reqeust.indexOf("CQ:at") >= 0) && (reqeust.indexOf("http://") >= 0 || reqeust.indexOf("www") >= 0))
			{
				String url = "www.baidu.com?url=";
				//int index = reqeust.lastIndexOf("]");
				String str  = reqeust.substring(reqeust.lastIndexOf("]") + 1).trim();
				return url + str;
			}
		}
		return null;
		
	}
	/**
	 * 读取xml配置文件
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	private void load() throws DocumentException, IOException
	{
		FileInputStream inputStream = new FileInputStream("src/ad-QQRobot_cfg.xml");
		SAXReader xmlReader = new SAXReader();
		
		Document x_doc = xmlReader.read(inputStream);
		Element x_root = x_doc.getRootElement();
		inputStream.close();
		
		List<Element> list = x_root.elements();
		for(int i = 0 ; i < list.size() ; i++)
		{
			Element e = list.get(i);
			String match = e.elementText("match");
			String response = e.elementText("response");
			String priority = e.elementText("priority");
			if(match != null && response != null)
			{
				QQRobotData data = new QQRobotData();
				data.match = match.trim();
				data.response = response.trim();
				data.index = i;
				try {
					data.priority = Integer.valueOf(priority);//如果优先级不设置则默认为0
				}catch (Exception e1) {
					data.priority = 0;
				}
				
				cfgItem.add(data);	
			}
			if(e.getName().equals("default"))
			{
				defa = e.getTextTrim();
			}
			
		
		}
		//根据优先级进行排序
		cfgItem.sort(new Comparator<QQRobotData>()
		{

			@Override
			public int compare(QQRobotData o1, QQRobotData o2)
			{
				//按优先级来排
				if(o1.priority < o2.priority) return 1;
				if(o1.priority > o2.priority) return -1;
				
				if(o1.index < o2.index) return -1;
				if(o1.index > o2.index) return 1;
					
				return 0;
			}
		});
		
	}

}
