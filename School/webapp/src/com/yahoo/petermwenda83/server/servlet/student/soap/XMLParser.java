
package com.yahoo.petermwenda83.server.servlet.student.soap;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
/**
 * 
 * @author peter
 *
 */
public class XMLParser {
	
	/**
	 * @param xmlStr String to convert to Document 
	 * @return Document
	 */
	private static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param xmlStrings XML to convert to MAP
	 * @return hashmap
	 */
	public HashMap<String, String> xmlToMap(String xmlStrings){
		HashMap<String, String> values = new HashMap<String, String>();
		Document xml = convertStringToDocument(xmlStrings);
		if(xml != null){
			Node user = xml.getFirstChild();
			NodeList childs = user.getChildNodes();
			Node child;
			for (int i = 0; i < childs.getLength(); i++) {
				child = childs.item(i);
				values.put(child.getNodeName(), child.getTextContent());
			}
		}
		return values;
	}

	/**
	 * @param responseMap MAP to converet to XML
	 * @return XML document 
	 */
	public String mapToXml(HashMap<String, String> responseMap){
		String strXML = "";
		String NewLine = "";
		strXML = "<?xml version= '1.0'   encoding= 'utf-8'?>" + NewLine;
		strXML += "<message>" + NewLine;
		try {
			String key = "";
			String value = "";
			for (Map.Entry<String, String> entry : responseMap.entrySet()) {
				key = entry.getKey();
				value = entry.getValue() + "";
				if (!"".equals(value)) {
					strXML += "<field" + key + ">" + entry.getValue() + "</field" + key + ">" + NewLine;
				}
			}
			strXML += "</message>" + NewLine;
		} catch (Exception ex) {
			ex.printStackTrace();

			return null;
		}

		return strXML;
	}

	
	/**
	 * @param args
	 */
	public static void main(String args[]){
		XMLParser xmlParser = new XMLParser();
		StringBuilder xml = new StringBuilder();
		       xml.append("<?xml version= '1.0' encoding= 'utf-8'?>"); 
		       xml.append("<message>  <sourceid>STP_INCOMING</sourceid>");
		       xml.append("<password>2f594b736d1b46238c761ea3f34dc3ac876456dfa7cd9e5d028519952e54af0b</password>");
		       xml.append("<field0>0200</field0>");
		       xml.append("<field3>120000</field3>");
		       xml.append("<field4>00</field4>");
		       xml.append( "<field32>ID123456</field32>");
		       xml.append("<field37>008445919885</field37>");
		       xml.append("<field47>0</field47>");
		       xml.append("<field88>Naration</field88>");
		       xml.append( "</message>");
		       
		HashMap<String,String> xmlmap = xmlParser.xmlToMap(xml.toString());  

		if(!xmlmap.isEmpty()){
			//System.out.println("xml: " + xmlmap.toString());
			//System.out.println("mat to xml : "+ xmlParser.mapToXml(xmlmap));
			//System.out.println("xml to map : "+ xmlParser.xmlToMap(xml.toString())); 
			Map<String,String> myxml = null; 
			myxml = xmlParser.xmlToMap(xml.toString());
			System.out.println("myxml : "+ myxml.get("sourceid"));  
			
		}
		else{
			System.out.println("INVALID XML FORMAT");
		}

	}

}