package com.java.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
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
		System.out.println("Enter the Test case you need to run \n 1.upload Test Case and result\n 2.Update Test status\n 3.vic regression\n 4.Exit");
		int ch = s.nextInt();
		switch(ch){
		case 1:
			System.out.println("Hi i am upload test case");
			System.out.println("Enter the folder id for testcase to be uploaded");
			s.nextLine();
			String oscompattfid = s.nextLine();
			System.out.println("tfid is"+oscompattfid);
			System.out.println("Enter the folder id to upload testcase for result");
			String oscompatrfid = s.nextLine();
			ex.getnumberofSheet(oscompattfid,oscompatrfid);
			break;
		case 2:
			System.out.println("Hi i am upload result");
			UpdateXml xm = new UpdateXml();
			xm.startParser();
			post.postXMLToUrl("http://tims.cisco.com/xml/Tst531p/update.svc", "C:/Users/pyogaraj/Desktop/parse_updated.xml");
			break;
		case 4:
			System.out.println("Thank you");
			System.exit(0);
		case 3:
			System.out.println("vic regression");
			System.out.println("Enter the folder id for testcase to be uploaded");
			s.nextLine();
			String tfid = s.nextLine();
			System.out.println("tfid is"+tfid);
			System.out.println("Enter the folder id to upload testcase for result");
			String rfid = s.nextLine();
			ex.vicRegressionExcel(tfid,rfid);
		
			
			
		}
		
	} 
	}

	public static void getnumberofSheet(String osctfid,String oscrfid) throws Exception{
		UpdateXml xm = new UpdateXml();
		String filename = "C:/Users/pyogaraj/Desktop/HBMR2.xlsx";
		FileOutputStream out = new FileOutputStream("C:/Users/pyogaraj/Desktop/HBMR2_updated.xls");
		
		List<String> sheetname = new ArrayList<String>();
		FileInputStream file = new FileInputStream(new File(filename));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		int sheetnum = wb.getNumberOfSheets();
		XSSFSheet sheet1 = wb.createSheet();
		wb.setSheetName(sheetnum,"Result ID");
		
		for (int i=0;i<wb.getNumberOfSheets();i++){
//			System.out.println("sheet name is"+wb.getSheetName(i));
			sheetname.add(wb.getSheetName(i));
		}
		
		for (int a=3;a<sheetname.size()-1;a++){
			System.out.println(sheetname.get(a));
			String servername = sheetname.get(a);
			String folder_name = sheetname.get(a);
//			xm.createFolder(folder_name,"Tst11752358f");
			xm.createFolder(folder_name,osctfid);
			String testcasefolderid = xm.returnID();
			System.out.println(testcasefolderid);
//			xm.createResFolder(folder_name,"Tst11752357f");
			xm.createResFolder(folder_name,oscrfid);
			String resfolid = xm.returnID();
			System.out.println("resid is"+resfolid);
			readExcel(servername,testcasefolderid,resfolid,sheet1);
//			readExcel(servername,testcasefolderid,resfolid);
		}
		wb.write(out);
		out.close();
//		System.out.println("Name of the sheet is"+sheetname);
	}
	public static void readExcel(String servername,String testcasefolderid,String resfolderid,XSSFSheet sheet1) throws Exception {
//		String filename = "C:/Users/pyogaraj/Desktop/GPMR2_Plumas.xlsx";//
		UpdateXml xm = new UpdateXml();
		XMLPoster post = new XMLPoster();
		String filename = "C:/Users/pyogaraj/Desktop/HBMR2.xlsx";
		ArrayList<String>resultid = new ArrayList<String>();
		ArrayList<String>testcase = new ArrayList<String>();
		
		FileInputStream file = new FileInputStream(new File(filename));
		XSSFWorkbook wb = new XSSFWorkbook(file);
//		Sheet sheet = wb.getSheetAt(3);
//		Sheet sheet = wb.getSheet(servername);
		Sheet sheet = wb.getSheet("S3260M5");
		
		
		
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
						//cell = row.getCell(i)
						// System.out.println(cell.getStringCellValue());						
						excelval.add(cell.getStringCellValue());						
//						parse.put(counter, excelval);
						
						if((cell.getCellComment()==null)&&(counter!=0)){
							if(cell.getStringCellValue()!=""){
							heading += cell.getStringCellValue()+"_";
//							System.out.println("heading is"+heading);
							}
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
//					    hparse.put(finalresid, combination);
						resultid.add(finalresid);
						testcase.add(combination);
//						break;
						
						}
						
					} catch (NullPointerException e) {
						
						continue;
					}
				}
				counter=counter+1;
//			}			
		}
		
		Cell cell1 = null;
		row = sheet1.createRow(sheet1.getLastRowNum());
		cell = row.createCell(0);
		cell.setCellValue(servername);
		
	
		for(int i=0;i<resultid.size();i++){
			int rowid =sheet1.getLastRowNum()+1;
			row = sheet1.createRow(rowid++);
			int cellid = 0;
			int cellid1 = 1;
			cell = row.createCell(cellid++);
			cell.setCellValue(resultid.get(i));
			cell1 = row.createCell(cellid1++);
			cell1.setCellValue(testcase.get(i));
		}
	
//		for (Map.Entry entry : parse.entrySet()) {
////			System.out.println(entry.getKey() + ", " + entry.getValue());
//		}
		
//		for(Map.Entry ent : hparse.entrySet()){
//			System.out.println("key is"+ent.getKey()+ " , val is"+ent.getValue());
//		}

	}
	
	public void vicRegressionExcel(String testreleasefolderid,String resultreleasefolderid) throws Exception{
		UpdateXml xm = new UpdateXml();
		XMLPoster post = new XMLPoster();
		
		String filename = "C:/Users/pyogaraj/Desktop/vic.xlsx";
		FileOutputStream out = new FileOutputStream("C:/Users/pyogaraj/Desktop/vic_updated.xls");
		
		List<String> sheetname = new ArrayList<String>();
		HashMap<Integer, List<String>> parse = new HashMap<Integer, List<String>>();
		
		FileInputStream file = new FileInputStream(new File(filename));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		int sheetnum = wb.getNumberOfSheets();
		
		XSSFSheet sheet1 = wb.createSheet();
		wb.setSheetName(sheetnum,"Result ID");
		
		for (int i=0;i<wb.getNumberOfSheets();i++){
			System.out.println("sheet name is"+wb.getSheetName(i));
			sheetname.add(wb.getSheetName(i));
		}
		System.out.println("size is"+sheetname.size());
		
		
		for(int a=0;a<sheetname.size()-1;a++){
			String folder_name = sheetname.get(a);
			
//			xm.createFolder(folder_name,"Tst11752358f");
			xm.createFolder(folder_name,testreleasefolderid);
			String testcasefolderid = xm.returnID();
			System.out.println(testcasefolderid);
//			xm.createResFolder(folder_name,"Tst11752357f");
			xm.createResFolder(folder_name,resultreleasefolderid);
			String resfolid = xm.returnID();
//			System.out.println("resid is"+resfolid);
			readvicexcel(folder_name,testcasefolderid,resfolid,sheet1);
			
		}
		wb.write(out);
		out.close();
		
	}
	
	public void  readvicexcel(String servername,String tstfolderid,String resfolderid,XSSFSheet sheet1) throws Exception{
		UpdateXml xm = new UpdateXml();
		int rowid;
		XMLPoster post = new XMLPoster();
		String filename = "C:/Users/pyogaraj/Desktop/vic.xlsx";
		FileOutputStream out = new FileOutputStream("C:/Users/pyogaraj/Desktop/vic_updated.xls");
		
		ArrayList<String>resultid = new ArrayList<String>();
		ArrayList<String>testcase = new ArrayList<String>();
		
		FileInputStream file = new FileInputStream(new File(filename));
		XSSFWorkbook wb = new XSSFWorkbook(file);
//		Sheet sheet = wb.getSheetAt(3);
		Sheet sheet = wb.getSheet(servername);
						
		Row row = null;
		Cell cell = null;
		Comment comment = null;	
			
		
//	    CreationHelper factory = wb.getCreationHelper();
//		ClientAnchor anchor = factory.createClientAnchor();
//		Drawing drawing = sheet.createDrawingPatriarch();
		
//		comment = drawing.createCellComment(anchor);
		
		row = sheet.getRow(0);
		System.out.println("check is" + row.getPhysicalNumberOfCells());
		int numofcell = row.getPhysicalNumberOfCells();
		System.out.println("sheetname is"+servername);
		for (int i=1;i<=sheet.getLastRowNum();i++){
			
			String combination = "";
			row = sheet.getRow(i);
			for(int j=0;j<7;j++){
				try{
					cell = row.getCell(j);
//					System.out.println(cell.getStringCellValue());
					combination += cell.getStringCellValue()+"_";
					
				}catch(Exception e){
					continue;
				}
				
			}
			
		
			System.out.println("combination is"+combination);		
			xm.uploadTestcase(tstfolderid,combination);
			post.postXMLToUrl("http://tims.cisco.com/xml/Tst531p/entity.svc", "C:/Users/pyogaraj/Desktop/parse_updated.xml");
			String testresid = xm.returnID();
			xm.uploadResult(resfolderid, combination, testresid);
			post.postXMLToUrl("http://tims.cisco.com/xml/Tst531p/entity.svc", "C:/Users/pyogaraj/Desktop/parse_updated.xml");
			String finalresid = xm.returnID();
//			hparse.put(finalresid, combination);
						
//			XSSFRichTextString st = new XSSFRichTextString("Hello"+i);
//			String st1 = "Hello"+servername;
			resultid.add(finalresid);
			testcase.add(combination);
//			comment.setString(st);
//			cell.setCellComment(comment);
		
		}
		
		Cell cell1 = null;
		row = sheet1.createRow(sheet1.getLastRowNum());
		cell = row.createCell(0);
		cell.setCellValue(servername);
		
	
		for(int i=0;i<resultid.size();i++){
			rowid =sheet1.getLastRowNum()+1;
			row = sheet1.createRow(rowid++);
			int cellid = 0;
			int cellid1 = 1;
			System.out.println(resultid.get(i));
			cell = row.createCell(cellid++);
			cell.setCellValue(resultid.get(i));
			cell1 = row.createCell(cellid1++);
			cell1.setCellValue(testcase.get(i));
		}		
	
//		wb.write(out);
//		out.close();

	}

}
