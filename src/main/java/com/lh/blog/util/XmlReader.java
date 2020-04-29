package com.lh.blog.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XmlReader {

	private static Logger log = Logger.getLogger(XmlReader.class);
	
	/**
	   * 装载xml文件
	   * @param filePath
	   * @return
	   * @throws Exception
	   */
	  public static Element GetXmlDoc(String filePath)throws Exception
	  {	
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = null;    
	    db = dbf.newDocumentBuilder();    
	    Document doc = null;    
	    doc = db.parse(filePath);    
	    return doc.getDocumentElement();
	  }
	  /**
	   * 装载xml文件
	   * @param is
	   * @return
	   * @throws Exception
	   */
	  public static Element GetXmlDoc(InputStream is)throws Exception
	  {
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = null;    
	    db = dbf.newDocumentBuilder();    
	    Document doc = null;    
	    doc = db.parse(is);    
	    return doc.getDocumentElement();
	  }
	  /**
	   * 获取单个节点
	   * @param express
	   * @param source
	   * @return
	   * @throws XPathExpressionException
	   */
	  public static Node selectSingleNode(String express, Object source) throws XPathExpressionException {
	    Node result = null;
	    XPathFactory xpathFactory = XPathFactory.newInstance();
	    XPath xpath = xpathFactory.newXPath();    
	    result = (Node)xpath.evaluate(express, source, XPathConstants.NODE);    
	    return result;
	  }
	  /**
	   * 获取一组节点
	   * @param express
	   * @param source
	   * @return
	   * @throws XPathExpressionException
	   */
	  public static NodeList selectNodes(String express, Object source) throws XPathExpressionException {
	    NodeList result = null;
	    XPathFactory xpathFactory = XPathFactory.newInstance();
	    XPath xpath = xpathFactory.newXPath();    
	    result = (NodeList)xpath.evaluate(express, source, XPathConstants.NODESET);    
	    return result;
	  }
	  /**
	   * 获取xml的属性节点的值
	   * @param node
	   * @param key
	   * @return
	   */
	  public static String getNodeAttributeValue(Node node,String key){
		  if(node==null)
			  return null;
		  if(key==null)
			  return null;
		  NamedNodeMap nm = node.getAttributes(); 
		  if(nm==null)
			  return null;
		  Node nameNode = nm.getNamedItem(key);
		  if(nameNode==null)
			  return null;
		  return nameNode.getNodeValue();
	  }
	  
	  public static void modifyNodeAttributeValue(String express, String key, String value, String path){
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db = null;    
		  try {
			db = dbf.newDocumentBuilder();
		  } catch (ParserConfigurationException e) {
			log.error("XmlReader.modifyNodeAttributeValue ParserConfigurationException" , e);
		  }    
		  Document doc = null;    
		  try {
			  if(db != null){
				  doc = db.parse(path);
			  }
		  } catch (SAXException e) {
			log.error("XmlReader.modifyNodeAttributeValue SAXException" ,e);
		  } catch (IOException e) {
			log.error("XmlReader.modifyNodeAttributeValue" , e);
		  }
		  Node node = null;
		try {
			if(doc != null){
				node = XmlReader.selectSingleNode("timerTasks", doc.getDocumentElement());
			}
			
		} catch (XPathExpressionException e1) {
			log.error("XmlReader.modifyNodeAttributeValue XPathExpressionException",e1);
		}
		  if(node==null)
			  return;
		  if(key==null)
			  return;;
		  NamedNodeMap nm = node.getAttributes(); 
		  if(nm==null)
			  return;;
		  Node nameNode = nm.getNamedItem(key);
		  if(nameNode==null)
			  return;
		  nameNode.setNodeValue(value);
	      //保存xml文件
	      TransformerFactory transformerFactory=TransformerFactory.newInstance();
	      Transformer transformer = null;
	      try {
			  transformer = transformerFactory.newTransformer();
	      } catch (TransformerConfigurationException e) {
			  log.error("XmlReader.modifyNodeAttributeValue TransformerConfigurationException" , e);
		  }
	      DOMSource domSource=new DOMSource(doc);
	      //设置编码类型
	      if(transformer != null){
	          transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
	      }
	      StreamResult result = null;
	      try {
			result = new StreamResult(new FileOutputStream(path));
	      } catch (FileNotFoundException e) {
			log.error("XmlReader.modifyNodeAttributeValue ", e);
	      }
	      //把DOM树转换为xml文件
	      try {
	    	  if(transformer != null){
	    		  transformer.transform(domSource, result);
	    	  }
		} catch (TransformerException e) {
			log.error("XmlReader.modifyNodeAttributeValue" , e);
		}
	  }
	
}
