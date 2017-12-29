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
//		xm.reurnTestCaseFolderID();
//		xm.updateTestcase();
		
		post.postXMLToUrl("http://tims.cisco.com/xml/Tst531p/entity.svc", "C:/Users/pyogaraj/Desktop/parse_updated.xml","");
		xm.returnID("pyogaraj");
			
	}
	public static void createFolder(String foldername,String tstfolder,String cecid) throws Exception{
		XMLPoster post = new XMLPoster();
		String filePath2 = "C:/Users/"+cecid+"/Desktop/testfolder.xml";
		File xmlFile2 = new File(filePath2);
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	     DocumentBuilder dBuilder;
	     try{
	     dBuilder = dbFactory.newDocumentBuilder();	     
	     Document doc2 = dBuilder.parse(xmlFile2);	     
	     doc2.getDocumentElement().normalize();		
	     createtstfolder(doc2,foldername,tstfolder);
		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc2);
         StreamResult result = new StreamResult(new File("C:/Users/"+cecid+"/Desktop/parse_updated.xml"));
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.transform(source, result);
         System.out.println("XML file parsed and updated successfully");
	     }catch(SAXException | ParserConfigurationException | IOException | TransformerException e1){
	    	 e1.printStackTrace();
	     }
	     post.postXMLToUrl("http://tims.cisco.com/xml/Tst531p/entity.svc", "C:/Users/"+cecid+"/Desktop/parse_updated.xml",cecid);
	}
	public static void createResFolder(String foldername,String resfolder,String cecid) throws Exception{
		XMLPoster post = new XMLPoster();
		String filePath2 = "C:/Users/"+cecid+"/Desktop/testfolder.xml";
		File xmlFile2 = new File(filePath2);
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	     DocumentBuilder dBuilder;
	     try{
	     dBuilder = dbFactory.newDocumentBuilder();	     
	     Document doc2 = dBuilder.parse(xmlFile2);	     
	     doc2.getDocumentElement().normalize();		
	     createresultfolder(doc2,foldername,resfolder);
		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc2);
         StreamResult result = new StreamResult(new File("C:/Users/"+cecid+"/Desktop/parse_updated.xml"));
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.transform(source, result);
         System.out.println("XML file parsed and updated successfully");
	     }catch(SAXException | ParserConfigurationException | IOException | TransformerException e1){
	    	 e1.printStackTrace();
	     }
	     post.postXMLToUrl("http://tims.cisco.com/xml/Tst531p/entity.svc", "C:/Users/"+cecid+"/Desktop/parse_updated.xml",cecid);
	}
	public static void uploadTestcase(String folderID,String combination,String cecid){
		String filePath2 = "C:/Users/"+cecid+"/Desktop/testcase.xml";
		File xmlFile2 = new File(filePath2);
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	     DocumentBuilder dBuilder;
	     try{
	     dBuilder = dbFactory.newDocumentBuilder();	     
	     Document doc2 = dBuilder.parse(xmlFile2);	     
	     doc2.getDocumentElement().normalize();		 
		 testcaseupload(doc2,combination,folderID);		 
		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc2);
         StreamResult result = new StreamResult(new File("C:/Users/"+cecid+"/Desktop/parse_updated.xml"));
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.transform(source, result);
         System.out.println("XML file parsed and updated successfully");
	     }catch(SAXException | ParserConfigurationException | IOException | TransformerException e1){
	    	 e1.printStackTrace();
	     }
		
	}
	
	public static void uploadResult(String folderID,String combination,String logicalid,String cecid){
		String filePath2 = "C:/Users/"+cecid+"/Desktop/result.xml";
		File xmlFile2 = new File(filePath2);
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	     DocumentBuilder dBuilder;
	     try{
	     dBuilder = dbFactory.newDocumentBuilder();	     
	     Document doc2 = dBuilder.parse(xmlFile2);	     
	     doc2.getDocumentElement().normalize();		 
		 resupload(doc2,combination,folderID,logicalid);		 
		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc2);
         StreamResult result = new StreamResult(new File("C:/Users/"+cecid+"/Desktop/parse_updated.xml"));
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.transform(source, result);
         System.out.println("XML file parsed and updated successfully");
	     }catch(SAXException | ParserConfigurationException | IOException | TransformerException e1){
	    	 e1.printStackTrace();
	     }
		
	}
	
	public static String returnID(String cecid){
		String filePath1 = "C:/Users/"+cecid+"/Desktop/file1.xml";
		String foldid ="";
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
//			 System.out.println(tstid.getTextContent());
			 foldid = tstid.getTextContent();
		 }		 
	     }catch(Exception e1){
	    	 e1.printStackTrace();
	     }
		return foldid;
	     
	}
	
	public static void startParser(String cecid){
        String filePath = "C:/Users/"+cecid+"/Desktop/test1.xml";
        
        
        File xmlFile = new File(filePath);
        
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();            
            Document doc = dBuilder.parse(xmlFile);        
            
            doc.getDocumentElement().normalize();          
//            
            System.out.println("Enter the Test case id please");
            Scanner s = new Scanner(System.in);
            String id = s.nextLine();	
            System.out.println("Enter the status");
            String status = s.nextLine();
            System.out.println("Enter the title please");
            String title = s.nextLine();
           //update Id
            updateId(doc,id);
            //update value            
            updateValue(doc,id,status,title);
            
           
            //write the updated document to file or console
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:/Users/"+cecid+"/Desktop/parse_updated.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("XML file updated successfully");
            
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
            e1.printStackTrace();
        }
    	
    }
	
	  public static void updateValue(Document doc,String timsid,String status,String title){
	    	NodeList tagid = doc.getElementsByTagName("ID");
	    	NodeList logicalid = doc.getElementsByTagName("LogicalID");
	    	NodeList tagstat = doc.getElementsByTagName("Status");
	    	NodeList tagtitle = doc.getElementsByTagName("Title");
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
	    	
	    	for(int k=0;k<tagtitle.getLength();k++){
	    		tag = (Element) tagtitle.item(k);
	    		tag.setTextContent(title);
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
	    
	    public static void testcaseupload(Document doc2,String combination,String folid){
	    	System.out.println("insidde testcase update");
	    	NodeList titleid = doc2.getElementsByTagName("Title");
	    	NodeList folderid = doc2.getElementsByTagName("FolderID");
	    	Element title = null;
	    	Element fol = null;
	    	for(int i=0;i<titleid.getLength();i++){
	    		title = (Element) titleid.item(i);
	    		title.setTextContent(combination);
	    		
	    	}
	    	for(int i=0;i<folderid.getLength();i++){
	    		fol = (Element) folderid.item(i);
	    		fol.setTextContent(folid);
	    		
	    	}
	    }
	    
	  public static void createtstfolder(Document doc,String foldername,String fldid ){
		  NodeList fold_name = doc.getElementsByTagName("Title");
		  NodeList fold_id = doc.getElementsByTagName("FolderID");
		  Element folder= null;
		  Element folder_id= null;
		  for(int i=0;i<fold_name.getLength();i++){
			  folder = (Element)fold_name.item(i);
			  folder.setTextContent(foldername);
		  }
		  
		  for(int j=0;j<fold_id.getLength();j++){
			  folder_id = (Element)fold_id.item(j);
			  folder_id.setTextContent(fldid);
		  }
	  }  
	  public static void createresultfolder(Document doc,String foldername,String fldid ){
		  NodeList fold_name = doc.getElementsByTagName("Title");
		  NodeList fold_id = doc.getElementsByTagName("FolderID");
		  Element folder= null;
		  Element folder_id= null;
		  for(int i=0;i<fold_name.getLength();i++){
			  folder = (Element)fold_name.item(i);
			  folder.setTextContent(foldername);
		  }
		  
		  for(int j=0;j<fold_id.getLength();j++){
			  folder_id = (Element)fold_id.item(j);
			  folder_id.setTextContent(fldid);
		  }
	  }
	  
	  public static void resupload(Document doc,String combination,String fid,String logid){
		  NodeList restitle = doc.getElementsByTagName("Title");
		  Element title = null;
		  for(int i=0;i<restitle.getLength();i++){
			  title = (Element)restitle.item(i);
			  title.setTextContent(combination);
		  }
		  NodeList logicalid = doc.getElementsByTagName("LogicalID");
		  Element lid = null;
		  for(int j=0;j<logicalid.getLength();j++){
			  lid = (Element)logicalid.item(j);
			  lid.setTextContent(logid);
			  
		  }
		  NodeList caseid = doc.getElementsByTagName("CaseID");
		  Element cid = null;
		  for(int k=0;k<caseid.getLength();k++){
			  cid = (Element)caseid.item(k);
			  cid.setAttribute("xlink:href", "");
			  cid.setAttribute("xlink:href", "http://tims.cisco.com/xml/"+logid+"/entity.svc"+cid.getAttribute("xlink:href"));
			  cid.setTextContent(logid);
		  }
		  NodeList fold_id = doc.getElementsByTagName("FolderID");
		  Element folder= null;
		  for(int j=0;j<fold_id.getLength();j++){
			  folder = (Element)fold_id.item(j);
			  folder.setTextContent(fid);
		  }
	  }

}
