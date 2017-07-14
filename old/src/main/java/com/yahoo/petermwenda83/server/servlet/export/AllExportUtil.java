package com.yahoo.petermwenda83.server.servlet.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;



public class AllExportUtil {
	// The headings to be contained in the first row of the Excel sheet
		final static String[] TITLES = {"Student FirstName", "Student LastName", 
			"Student Surname", "Student House", "Father", "Father Phone","Mother ","Mother Phone","Primary School","KCPE Index","KCPE Year","KCPE Mark"};
		
		private static Logger logger = Logger.getLogger(AllExportUtil.class);
				
				
		/**
		 * Creates a Microsoft Excel Workbook containing activity provided in 
		 * a CSV text file. The format of the created file will be Office Open XML 
		 * (OOXML).
		 * <p>
		 * It expects the CSV to have the following columns from left to right:<br />
		 * topup.uuid, topup.msisdn, topup.amount, network.name, topupStatus.status, topup.topupTime
		 * <p>
		 * This method has been created to allow for large Excel files to be 
		 * created without overwhelming memory. 
		 * 
		 * 
		 * @param topupCSVFile a valid CSV text file. It should contain the full 
		 * 		  path and name of the file e.g. "/tmp/export/topups.csv"
		 * @param delimiter the delimiter used in the CSV file
		 * @param excelFile the Microsoft Excel file to be created. It should contain the full 
		 * 		  path and name of the file e.g. "/tmp/export/topups.xlsx"
		 * @return whether the creation of the Excel file was successful or not
		 */
		public static boolean createExcelExport(final String topupCSVFile, final String delimiter, 
				final String excelFile) {
			boolean success = true;
			
			int rowCount = 0;	// To keep track of the row that we are on
			
			Row row;
			Map<String, CellStyle> styles;
			
	    	SXSSFWorkbook wb = new SXSSFWorkbook(5000); 
					
			Sheet sheet = wb.createSheet(" ");
			styles = createStyles(wb);
			
			PrintSetup printSetupTopup = sheet.getPrintSetup();
	        printSetupTopup.setLandscape(true);
	        sheet.setFitToPage(true);
	        
			// Set up the heading to be seen in the Excel sheet
	        row = sheet.createRow(rowCount);

	        Cell titleCell;

	        row.setHeightInPoints(45);
	        titleCell = row.createCell(0);
	        titleCell.setCellValue("Students Informations");
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$L$1"));
	        titleCell.setCellStyle(styles.get("title"));

	        rowCount++;
	        row = sheet.createRow(rowCount);
	        row.setHeightInPoints(12.75f);

	        for (int i = 0; i <TITLES.length; i++) {
	            Cell cell = row.createCell(i);
	            cell.setCellValue(TITLES[i]);
	            cell.setCellStyle(styles.get("header"));
	        }
	        
	        rowCount++;
	        		
			FileUtils.deleteQuietly(new File(excelFile));		
			FileOutputStream out;
			
			try {
				FileUtils.touch(new File(excelFile)); 
				
				// Read the CSV file and populate the Excel sheet with it
		        LineIterator lineIter = FileUtils.lineIterator(new File(topupCSVFile));
		        String line;
		        String[] lineTokens;
		        int size;
		        
		        while(lineIter.hasNext()) {	        
		        	row = sheet.createRow(rowCount);
		        	line = lineIter.next();
		        	lineTokens =  StringUtils.split(line, delimiter);
		        	size = lineTokens.length;
		        	
		        	for(int cellnum = 0; cellnum < size; cellnum++) {
				        Cell cell = row.createCell(cellnum);			        
				        cell.setCellValue(lineTokens[cellnum]);
				    }
		        	
		        	rowCount++;
		        }
		        	        
				out = new FileOutputStream(excelFile);
				wb.write(out);
				out.close();
				
			} catch (FileNotFoundException e) {
				logger.error("FileNotFoundException while trying to create Excel file '" + excelFile +
						"' from CSV file '" + topupCSVFile + "'.");
	            logger.error(ExceptionUtils.getStackTrace(e));
	            success = false;
	            
			} catch (IOException e) {
				logger.error("IOException while trying to create Excel file '" + excelFile +
						"' from CSV file '" + topupCSVFile + "'.");
	            logger.error(ExceptionUtils.getStackTrace(e));
	            success = false;
			}
							
			wb.dispose(); // dispose of temporary files backup of this workbook on disk
	         
			return success;
		}
		
		
		/**
	     * Cell styles used for formatting the sheets
	     *
	     * @param wb
	     * @return Map<String, {@link CellStyle}>
	     */
	    public static Map<String, CellStyle> createStyles(Workbook wb) {
	        Map<String, CellStyle> styles = new HashMap<>();

	        CellStyle style;
	        Font headerFont = wb.createFont();
	        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        style = createBorderedStyle(wb);
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setFont(headerFont);
	        styles.put("header", style);

	        Font titleFont = wb.createFont();
	        titleFont.setFontHeightInPoints((short) 48);
	        titleFont.setColor(IndexedColors.DARK_BLUE.getIndex());
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFont(titleFont);
	        styles.put("title", style);

	        return styles;
	    }
	    
	    
	    /**
	    *
	    * @param wb
	    * @return CellStyle
	    */
	   public static CellStyle createBorderedStyle(Workbook wb) {
	       CellStyle style = wb.createCellStyle();
	       style.setBorderRight(CellStyle.BORDER_THIN);
	       style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	       style.setBorderBottom(CellStyle.BORDER_THIN);
	       style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	       style.setBorderLeft(CellStyle.BORDER_THIN);
	       style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	       style.setBorderTop(CellStyle.BORDER_THIN);
	       style.setTopBorderColor(IndexedColors.BLACK.getIndex());

	       return style;
	   }

}
