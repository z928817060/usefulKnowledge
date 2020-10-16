package com.study.usefulknowledge;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
/**@title 20��java���ô���
 *@desc ��дxml�ļ�(ʮһ)
 * @create 20130708
 * @author usefulknowledge
 * */
public class XmlReader {
	//������ԭ����dom������ �ǳ����鷳 
	//����ʹ��dom4j������
	//����Ȥ�Ŀ��Կ���http://java.chinaitlab.com/XMLBeans/717370_2.html
	private final static String  filepath="src/user.xml";
	public static void main(String[] args) {
		//writeXML();
		readXML();
		
	}

	private static void writeXML() {
		DocumentBuilderFactory builderFactory=	DocumentBuilderFactory.newInstance();
		try {
		DocumentBuilder builder=	builderFactory.newDocumentBuilder();
		Document document=builder.newDocument();
		Element root=document.createElement("users");//�������ڵ�
		document.appendChild(root);
		Element user=document.createElement("user");
		user.setAttribute("id", "1");
		Element name=document.createElement("name");
		Text value=document.createTextNode("usefulknowledge");
		name.appendChild(value);
		user.appendChild(name);
		root.appendChild(user);
		FileOutputStream fos=new FileOutputStream(filepath);
		OutputFormat format=new OutputFormat(document ); 
		XMLSerializer xmlSerilizer = new XMLSerializer(fos, format);
		xmlSerilizer.serialize(document); //���л��ĵ�
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		System.out.println("�Ҳ����ļ�");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void readXML() {
		DocumentBuilderFactory builderFactory=	DocumentBuilderFactory.newInstance();
		try {
		DocumentBuilder builder=	builderFactory.newDocumentBuilder();
		Document document =builder.parse(filepath);
		NodeList root= document.getElementsByTagName("user");//getDocumentElement
		
		for (int i = 0; i < root.getLength(); i++) {
		Element user=	(Element) root.item(i);
		System.out.println(user.getAttribute("id"));
		String name =user.getElementsByTagName("name").item(0).getTextContent();
		System.out.println(name);
		}
		/*System.out.println(user.getAttribute("id"));
		Node node= user.getFirstChild();
		System.out.println(node.getNodeValue());*/
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

