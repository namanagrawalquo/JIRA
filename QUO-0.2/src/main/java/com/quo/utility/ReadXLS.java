package com.quo.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadXLS {

	Logger logger = Logger.getLogger("devpinoyLogger");

	ResourceBundle resourceBundle = ResourceBundle.getBundle("Config");
	
	public void writeSprintReportData(String filePath, String sheetName, int rowNumber, int columnNumber, String data)
	{
		try
		{			
			FileInputStream file = new FileInputStream(new File(filePath));

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            Cell cell = null;

            //Retrieve the row and check for null
            XSSFRow sheetrow = sheet.getRow(rowNumber);
            if(sheetrow == null){
                sheetrow = sheet.createRow(rowNumber);
            }
            //Update the value of cell
            cell = sheetrow.getCell(columnNumber);
            if(cell == null){
                cell = sheetrow.createCell(columnNumber);
            }
            cell.setCellValue((String)data);

            file.close();

            FileOutputStream outFile =new FileOutputStream(new File(filePath));
            workbook.write(outFile);
            outFile.close();
		}
		catch (Exception ex) 
		{
			logger.info("Exception Occurred: "+ex);
		}
	}
	
	public String readConfigData(String filePath, int sheet, int row, int cell) 
	{
		String sData  = "";
		try 
		{
			XSSFCell data;
			int cellValue;
			InputStream ExcelFileToRead = new FileInputStream(filePath);
	        XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
	        data =  wb.getSheetAt(sheet).getRow(row).getCell(cell);
	        switch (data.getCellTypeEnum()) {
		        case STRING:
		        	sData =  data.toString();
		        	break;
		        case NUMERIC:
		        	cellValue = (int)data.getNumericCellValue();
		        	sData = Integer.toString(cellValue);
		        	break;
	        }
			return sData;
		} 
		catch (Exception e) 
		{
			logger.info("Exception Occurred: " + e.getMessage());
			return sData = "Exception";
		}
	}
	
	public String createWorkbook(String filePath, boolean completedIssues,	boolean issuesNotCompleted, boolean issueOutside, boolean issueRemoved)
	{
		String workbookFileName = "";
		try
		{
	        // Creating Workbook instances 
			XSSFWorkbook wb = new XSSFWorkbook(); 
	        
	        DateFormat dateFormat = new SimpleDateFormat(resourceBundle.getString("dateFormatForScreenshot"));
	        // An output stream accepts output bytes and sends them to sink. 
	        workbookFileName = filePath + "SprintReport-"+dateFormat.format(new Date())+".xlsx";
	        OutputStream fileOut = new FileOutputStream(workbookFileName); 
	          
	        // Creating Sheets using sheet object 
	        if (completedIssues)
	        {
	        	wb.createSheet("Completed Issues"); 
	        	logger.info("Completed Issues sheet added");
	        }
	        if (issuesNotCompleted)
	        {
	        	wb.createSheet("Issues Not Completed");
	        	logger.info("Issues Not Completed sheet added");
	        }
	        if (issueOutside)
	        {
	        	wb.createSheet("Issues completed outside");
	        	logger.info("Issues completed outside sheet added");
	        }
	        if (issueRemoved)
	        {
	        	wb.createSheet("Issues Removed From Sprint");
	        	logger.info("Issues Removed From Sprint sheet added");
	        }
	        wb.write(fileOut); 
	        return workbookFileName;
		} 
		catch (Exception e) 
		{
			logger.info("Exception Occurred: " + e.getMessage());
			workbookFileName = "Not Found";
			return workbookFileName;
		}
	}
	
	public int getNumberOfRows(String filePath)
	{
		int rows = 0;
		try
		{
			InputStream ExcelFileToRead = new FileInputStream(filePath);
	        XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
			XSSFSheet sheet = wb.getSheetAt(1);
			rows = sheet.getPhysicalNumberOfRows();
			return rows;
		} 
		catch (Exception e) 
		{
			logger.info("Exception Occurred: " + e.getMessage());
			rows = -1;
			return rows;
		}
	}
}
