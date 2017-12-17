package com.java.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ParseExcel {
	
	public static void main(String[] args) throws Exception {
		XMLPoster post = new XMLPoster();
		ParseExcel ex = new ParseExcel();		
//		ex.getnumberofSheet();
//		post.postXMLToUrl("http://tims.cisco.com/xml/Tst531p/entity.svc", "C:/Users/pyogaraj/Desktop/parse_updated.xml");
//		ex.readExcel("Plumas 1", "", "");
	while(true){
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the Test case you need to run \n 1.upload Test Case and result\n 2.Update Test status\n 3.Exit");
		int ch = s.nextInt();
		switch(ch){
		case 1:
			System.out.println("Hi i am upload test case");
			ex.getnumberofSheet();
			break;
		case 2:
			System.out.println("Hi i am upload result");
			UpdateXml xm = new UpdateXml();
			xm.startParser();
			post.postXMLToUrl("http://tims.cisco.com/xml/Tst531p/update.svc", "C:/Users/pyogaraj/Desktop/parse_updated.xml");
			break;
		case 3:
			System.out.println("Thank you");
			System.exit(0);
			
		}
		
	} 
	}

	public static void getnumberofSheet() throws Exception{
		UpdateXml xm = new UpdateXml();
		String filename = "C:/Users/pyogaraj/Desktop/GPMR2_latest.xlsx";
		List<String> sheetname = new ArrayList<String>();
		FileInputStream file = new FileInputStream(new File(filename));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		for (int i=0;i<wb.getNumberOfSheets();i++){
//			System.out.println("sheet name is"+wb.getSheetName(i));
			sheetname.add(wb.getSheetName(i));
		}
		for (int a=0;a<1;a++){
			System.out.println(sheetname.get(a));
			String servername = sheetname.get(a);
			String folder_name = sheetname.get(a);
			xm.createFolder(folder_name,"Tst11752000f");
			String testcasefolderid = xm.returnID();
			System.out.println(testcasefolderid);
			xm.createResFolder(folder_name,"Tst11751999f");
			String resfolid = xm.returnID();
			System.out.println("resid is"+resfolid);
			readExcel(servername,testcasefolderid,resfolid);
		}
//		System.out.println("Name of the sheet is"+sheetname);
	}
	public static void readExcel(String servername,String testcasefolderid,String resfolderid) throws Exception {
//		String filename = "C:/Users/pyogaraj/Desktop/GPMR2_Plumas.xlsx";//
		UpdateXml xm = new UpdateXml();
		XMLPoster post = new XMLPoster();
		String filename = "C:/Users/pyogaraj/Desktop/GPMR2_latest.xlsx";
		HashMap<Integer, List<String>> parse = new HashMap<Integer, List<String>>();
		HashMap<String,String> hparse =  new HashMap<String,String>();
		FileInputStream file = new FileInputStream(new File(filename));
		XSSFWorkbook wb = new XSSFWorkbook(file);
//		Sheet sheet = wb.getSheetAt(3);
		Sheet sheet = wb.getSheet(servername);
		
		
		Row row = null;
		Cell cell = null;
		Comment comment = null;		
		row = sheet.getRow(0);
		System.out.println("check is" + row.getPhysicalNumberOfCells());
		int numofcell = row.getPhysicalNumberOfCells();
		int counter = 0;
		// System.out.println("active cell is"+rownum);

		for(int i=0;i < numofcell;i++) {			
//			if (counter < row.getPhysicalNumberOfCells()) {
			System.out.println("counter is"+counter);
			    String heading="";
			    int id =0;
				ArrayList<String> excelval = new ArrayList<String>();
				for (int p = 0; p <= sheet.getLastRowNum(); p++) {	
						
					try {					
						row = sheet.getRow(p);
						cell = row.getCell(counter);
						// System.out.println(cell.getStringCellValue());						
						excelval.add(cell.getStringCellValue());						
						parse.put(counter, excelval);
						
						if((cell.getCellComment()==null)&&(counter!=0)){
							heading += "_"+cell.getStringCellValue();
//							System.out.println("heading is"+heading);
						}
						if(cell.getCellComment().getString()!=null){
//						System.out.println("heading is"+heading);
//						System.out.println("os is"+cell.getStringCellValue());				
						
						String combination =heading+"_"+cell.getStringCellValue()+"_"+row.getCell(0).getStringCellValue()+"_"+cell.getCellComment().getString();		
						
						System.out.println("combination is"+combination);
						xm.uploadTestcase(testcasefolderid,combination);
						post.postXMLToUrl("http://tims.cisco.com/xml/Tst531p/entity.svc", "C:/Users/pyogaraj/Desktop/parse_updated.xml");
						String testresid = xm.returnID();
						xm.uploadResult(resfolderid, combination, testresid);
						post.postXMLToUrl("http://tims.cisco.com/xml/Tst531p/entity.svc", "C:/Users/pyogaraj/Desktop/parse_updated.xml");
						String finalresid = xm.returnID();
//						System.exit(0);
					    hparse.put(finalresid, combination);
						break;
						
						}
						
					} catch (NullPointerException e) {
						
						continue;
					}
				}
				counter=counter+1;
//			}			
		}
	
		for (Map.Entry entry : parse.entrySet()) {
//			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
		
		for(Map.Entry ent : hparse.entrySet()){
			System.out.println("key is"+ent.getKey()+ " , val is"+ent.getValue());
		}

	}
}
