package org.ngi.xml;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



public class XMLParser {

	public XMLParser() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xmlStr = "apoorv is trying";
		try {
			Document doc = getDocumentFromString(xmlStr);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public static Document getDocument(String apfFileName){
		try {
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new FileReader(apfFileName));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(true);
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			Document document = docBuilder.parse(inputSource);
			return document;
		} catch (ParserConfigurationException ex) {
			//Logger.getLogger(CalculateAgreement.class.getName()).log(Level.SEVERE, null, ex);
			System.err.println("Problem in file: "+apfFileName);
		} catch (SAXException saxException) {
			saxException.printStackTrace();
			System.err.println("Problem in file: "+apfFileName);
		} catch (IOException ioException) {
			ioException.printStackTrace();
			System.err.println("Problem in file: "+apfFileName);
		}
		return null;
	}

	/**
	 * This file basically parses an XML document as String into a DOM Document
	 * @param xmlFileContents: Contents of XML file as a string
	 * @return document
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws NullPointerException
	 * @throws SAXException 
	 */
	public static Document getDocumentFromString(String xmlFileContents) throws ParserConfigurationException, IOException, NullPointerException, SAXException{
		if(xmlFileContents==null){
			throw new NullPointerException("Xml file to be parsed is null");
		}
		InputSource inputSource = new InputSource(new StringReader(xmlFileContents));
		if(inputSource==null){
			throw new IOException("cannot read Xml file contents");
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
		if(docBuilder==null){
			throw new ParserConfigurationException("cannot create a new document builder -- reason unknown");
		}
		Document document = null;
		try {
			document = docBuilder.parse(inputSource);
		} catch (SAXException e) {
			e.printStackTrace();
			throw new SAXException("cannot parse input xml file -- badly formed XML");	
		}
		return document;
	}

	public static String getAttributeFromNode(Node n, String attributeName){
		if(n==null){
			return null;
		}
		String str = null;
		Element e = (Element)n;
		str = e.getAttribute(attributeName); 
		return str;

	}

	public static String getStringFromDocument(Document domDocument){
		TransformerFactory tFactory =
			TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DOMSource source = new DOMSource(domDocument);
		StringWriter stringWriter = new StringWriter();
		//Result str = null;
		StreamResult result = new StreamResult(stringWriter);
		try {
			transformer.transform(source, result);
			return stringWriter.getBuffer().toString();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return null;
	}
	public static NodeList getNodeListNamed(Node n, String nodeListName){
		NodeList nl = null;
		Element e = (Element)n;
		nl = e.getElementsByTagName(nodeListName);
		return nl;
	}

	public static String printNodeList(NodeList nl){
		String str = "";
		for(int i=0;i<nl.getLength();i++){
			Node n = nl.item(i);
			str += getStringFromNode(n);
		}
		return str;
	}

	public static String getStringFromNode(Node n){
		TransformerFactory tFactory =
			TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DOMSource source = new DOMSource(n);
		StringWriter stringWriter = new StringWriter();
		//Result str = null;
		StreamResult result = new StreamResult(stringWriter);
		try {
			transformer.transform(source, result);
			return stringWriter.getBuffer().toString();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return null;
	}


}
