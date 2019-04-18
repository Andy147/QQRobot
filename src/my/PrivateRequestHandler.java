package my;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class PrivateRequestHandler
{
	List<QQRobotData> cfgItem = new ArrayList<QQRobotData>();
	String defa = null;
	public PrivateRequestHandler() throws DocumentException, IOException
	{
		//先加载配置文件
		System.out.println("===============加载配置文件================");
		load();
		System.out.println("===============配置文件加载成功==============");
		
	}
	public String Handler(String reqeust)
	{
		for(int i = 0 ; i < cfgItem.size() ; i++)
		{
			QQRobotData it = cfgItem.get(i);
			if(reqeust.indexOf(it.match) >= 0)
			{
				return it.response;
			}
		}
		return defa;
		
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
		for(Element e : list)
		{
			String match = e.elementText("match");
			String response = e.elementText("response");			
			if(match != null && response != null)
			{
				QQRobotData data = new QQRobotData();
				data.match = match.trim();
				data.response = response.trim();
				cfgItem.add(data);	
			}
			if(e.getName().equals("default"))
			{
				defa = e.getTextTrim();
			}
		
		}
	}

}
