package com.java.test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UpdateXml {
	
	public static void main(String[] args) throws Exception{
		XMLPoster post = new XMLPoster();
//		startParser();
		Thread.sleep(1000);
		UpdateXml xm = new UpdateXml();
		xm.updateTestcase();
		
		post.postXMLToUrl("http://tims.cisco.com/xml/Tst531p/entity.svc", "C:/Users/pyogaraj/Desktop/parse_updated.xml");
		xm.returnID();
/*		while(true){
			Scanner s = new Scanner(System.in);
			System.out.println("Enter the Test case you need to run \n 1.upload Test Case\n 2.Upload Result\n 3.Exit");
			int ch = s.nextInt();
			switch(ch){
			case 1:
				System.out.println("Hi i am upload test case");
				break;
			case 2:
				System.out.println("Hi i am upload result");
				break;
			case 3:
				System.out.println("Thank you");
				System.exit(0);
				
			}
			
		} */
		
	
	}
	public static void updateTestcase(){
		String filePath2 = "C:/Users/pyogaraj/Desktop/testcase.xml";
		File xmlFile2 = new File(filePath2);
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	     DocumentBuilder dBuilder;
	     try{
	     dBuilder = dbFactory.newDocumentBuilder();	     
	     Document doc2 = dBuilder.parse(xmlFile2);	     
	     doc2.getDocumentElement().normalize();		 
		 testcaseupdate(doc2,"VIC 1385____CISCO 1.5 T_INT x710 DA4_INT x550__HGST SN200 6.4TB_SLES 12.2_LOCAL_CISCO 1.5 T + UEFI");		 
		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc2);
         StreamResult result = new StreamResult(new File("C:/Users/pyogaraj/Desktop/parse_updated.xml"));
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.transform(source, result);
         System.out.println("XML file parsed and updated successfully");
	     }catch(SAXException | ParserConfigurationException | IOException | TransformerException e1){
	    	 e1.printStackTrace();
	     }
		
	}
	
	public static void returnID(){
		String filePath1 = "C:/Users/pyogaraj/Desktop/file1.xml";				
		File xmlFile1 = new File(filePath1);
		
		
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	     DocumentBuilder dBuilder;
	     
	     try{
	     dBuilder = dbFactory.newDocumentBuilder();	     
	     Document doc1 = dBuilder.parse(xmlFile1);	      
	     doc1.getDocumentElement().normalize();	     
		 NodeList testid = doc1.getElementsByTagName("ID");
		 Element tstid = null;
		 
		 for(int a=0;a<testid.getLength();a++){
			 tstid = (Element) testid.item(a);
			 System.out.println(tstid.getTextContent());
		 }		 
	     }catch(Exception e1){
	    	 e1.printStackTrace();
	     }
	     
	}
	
	public static void startParser(){
        String filePath = "C:/Users/pyogaraj/Desktop/test1.xml";
        
        
        File xmlFile = new File(filePath);
        
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();            
            Document doc = dBuilder.parse(xmlFile);        
            
            doc.getDocumentElement().normalize();          
//            
            System.out.println("Enter the id please");
            Scanner s = new Scanner(System.in);
            String id = s.nextLine();	
            System.out.println("Enter the status");
            String status = s.nextLine();
           //update Id
            updateId(doc,id);
            //update value            
            updateValue(doc,id,status);
            
           
            //write the updated document to file or console
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:/Users/pyogaraj/Desktop/parse_updated.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("XML file updated successfully");
            
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
            e1.printStackTrace();
        }
    	
    }
	
	  public static void updateValue(Document doc,String timsid,String status){
	    	NodeList tagid = doc.getElementsByTagName("ID");
	    	NodeList logicalid = doc.getElementsByTagName("LogicalID");
	    	NodeList tagstat = doc.getElementsByTagName("Status");
	    	Element tag = null;
	    	
	    	for(int i=0;i<tagid.getLength();i++){
	    		tag = (Element) tagid.item(i);
	    		tag.setTextContent(timsid);
	    	}
	    	for(int j=0;j<logicalid.getLength();j++){
	    		tag = (Element) logicalid.item(j);
	    		tag.setTextContent(timsid);
	    	}  	
	    	for(int k=0;k<tagstat.getLength();k++){
	    		tag = (Element)tagstat.item(k);
	    		tag.setTextContent(status);
	    	}
	    	
	    }
	    public static void updateId(Document doc,String timsid){
	    	NodeList tagid = doc.getElementsByTagName("ID");
	    	NodeList tagresult = doc.getElementsByTagName("Result");
	    	Element em = null;
	    	
	    	for(int i=0;i<tagid.getLength();i++){
	    		em = (Element) tagid.item(i);
//	    		Node idtag = em.getElementsByTagName("ID").item(0);
	    		em.setAttribute("xlink:href", "");
	    		em.setAttribute("xlink:href", "http://tims.cisco.com/xml/"+timsid+"/entity.svc"+em.getAttribute("xlink:href"));
	    	}
	    	for(int j=0;j<tagresult.getLength();j++){
	    		em = (Element) tagresult.item(j);
	    		em.setAttribute("xlink:href", "");
	    		em.setAttribute("xlink:href", "http://tims.cisco.com/xml/"+timsid+"/entity.svc"+em.getAttribute("xlink:href"));
	    	}
	    	
	    }
	    
	    public static void testcaseupdate(Document doc2,String combination){
	    	System.out.println("insidde testcase update");
	    	NodeList titleid = doc2.getElementsByTagName("Title");
	    	Element title = null;
	    	for(int i=0;i<titleid.getLength();i++){
	    		title = (Element) titleid.item(i);
	    		title.setTextContent(combination);
	    		
	    	}
	    } 

}
