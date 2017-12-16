package com.java.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ParseExcel {
	public static void main(String[] args) throws IOException {
		ParseExcel ex = new ParseExcel();
		ex.readExcel();

	}

	public void readExcel() throws IOException {
		String filename = "C:/Users/pyogaraj/Desktop/GPMR2_Plumas.xlsx";
		HashMap<Integer, List<String>> parse = new HashMap<Integer, List<String>>();
		HashMap<String,String> hparse =  new HashMap<String,String>();
		HashMap<Integer,HashMap<String,String>> hparse1 =  new HashMap<Integer,HashMap<String,String>>();
		FileInputStream file = new FileInputStream(new File(filename));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		Sheet sheet = wb.getSheetAt(2);
		
		Row row = null;
		Cell cell = null;
		row = sheet.getRow(0);
		System.out.println("check is" + row.getPhysicalNumberOfCells());
		int numofcell = row.getPhysicalNumberOfCells();
		int counter = 0;
		// System.out.println("active cell is"+rownum);

		for(int i=0;i < numofcell;i++) {			
//			if (counter < row.getPhysicalNumberOfCells()) {
			System.out.println("counter is"+counter);
				String heading="";
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
						String combination =heading+""+cell.getStringCellValue()+"_"+row.getCell(0).getStringCellValue()+"_"+cell.getCellComment().getString();
						
						System.out.println("combination is"+combination);
//						System.out.println(row.getCell(0).getStringCellValue());
//						System.out.println("comment is"+cell.getCellComment().getString());
//						hparse.put(cell.getStringCellValue(), cell.getCellComment().getString());
//						hparse.put(p+"-"+counter, combination);
						}
						
//						hparse.put(cell.getStringCellValue(), cell.getCellComment().getString());
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
//			System.out.println("key is"+ent.getKey()+ " , val is"+ent.getValue());
		}

	}
}
