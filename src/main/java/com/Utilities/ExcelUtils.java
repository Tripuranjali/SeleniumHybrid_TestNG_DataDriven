package com.Utilities;

import java.io.FileInputStream;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ExcelUtils {
	
	public static List<Map<String, String>> getExcelData(String excelFilePath, String sheetIdentifier) throws IOException {
	   
		List<Map<String, String>> dataList = new ArrayList<>();
		FileInputStream file = new FileInputStream(excelFilePath);
	    XSSFWorkbook workbook = new XSSFWorkbook(file);
	    Sheet sheet;

	    // Determine whether sheetIdentifier is a name or index
	    if (sheetIdentifier.matches("\\d+")) {
	        int sheetIndex = Integer.parseInt(sheetIdentifier);
	        sheet = workbook.getSheetAt(sheetIndex);
	    } else {
	        sheet = workbook.getSheet(sheetIdentifier);
	    }

	    if (sheet == null) {
	        workbook.close();
	        file.close();
	        throw new IllegalArgumentException("Invalid Sheet Name or Index: " + sheetIdentifier);
	    }

	    
	    
	    Row headerRow = sheet.getRow(0);
	    int colCount = headerRow.getPhysicalNumberOfCells();
	    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	        Row row = sheet.getRow(i);
	        if (row == null) continue;
	        Map<String, String> rowData = new HashMap<>();
	        
	        for (int j = 0; j < colCount; j++) {
	            Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	            String header = headerRow.getCell(j).getStringCellValue();

	            switch (cell.getCellType()) {
	                case STRING:
	                    rowData.put(header, cell.getStringCellValue());
	                    break;
	                case NUMERIC:
	                    // Convert NUMERIC values to STRING to avoid errors
	                    rowData.put(header, String.valueOf((long) cell.getNumericCellValue()));
	                    break;
	                case BOOLEAN:
	                    rowData.put(header, String.valueOf(cell.getBooleanCellValue()));
	                    break;
	                default:
	                    rowData.put(header, ""); // Handle empty or unsupported cells
	            }
	        }
	        dataList.add(rowData);
	    }

	    workbook.close();
	    file.close();
	    return dataList; // Return list of HashMaps
	}
	

	
	public static Map<String, List<String>> getAllColumnsData(String excelPath, String sheetIdentifier) throws IOException {
	    Map<String, List<String>> dataMap = new LinkedHashMap<>();
		FileInputStream file = new FileInputStream(excelPath);
	    XSSFWorkbook workbook = new XSSFWorkbook(file);
	    Sheet sheet;

	    // Determine whether sheetIdentifier is a name or index
	    if (sheetIdentifier.matches("\\d+")) {
	        int sheetIndex = Integer.parseInt(sheetIdentifier);
	        sheet = workbook.getSheetAt(sheetIndex);
	    } else {
	        sheet = workbook.getSheet(sheetIdentifier);
	    }

	    if (sheet == null) {
	        workbook.close();
	        file.close();
	        throw new IllegalArgumentException("Invalid Sheet Name or Index: " + sheetIdentifier);
	    }

	    Row headerRow = sheet.getRow(0);
	    int colCount = headerRow.getPhysicalNumberOfCells();
	    
	    // Initialize lists for each column
	    for (int col = 0; col < colCount; col++) {
	        String columnName = headerRow.getCell(col).getStringCellValue().trim();
	        dataMap.put(columnName, new ArrayList<>());
	    }

	    // Read all row data dynamically
	    for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skipping header
	        Row row = sheet.getRow(i);
	        if (row == null) continue; {
	            for (int j = 0; j < colCount; j++) {
	                Cell cell = row.getCell(j);
	                String header = headerRow.getCell(j).getStringCellValue().trim();
	                if(cell!=null) {
	                	dataMap.get(header).add(cell.getStringCellValue().trim());
	                }		
					
	               
				}
	        }
	}
	    workbook.close();
	    file.close();
	    return dataMap;
	}
	
	

	
	//ExcelData to Json

	public static void convertExcelToJson(String excelFilePath, String jsonFilePath) throws IOException {
		FileInputStream file = new FileInputStream(
				"C:/AutomationProjects/FE_OIPA_Automation/src/main/java/falcorp/TestData/TestDataMyClient.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0); // Read first sheet

		List<Map<String, String>> jsonList = new ArrayList<>();
		Row headerRow = sheet.getRow(0);
		int colCount = headerRow.getPhysicalNumberOfCells();

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null)
				continue;

			Map<String, String> rowData = new HashMap<>();
			for (int j = 0; j < colCount; j++) {
				Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				rowData.put(headerRow.getCell(j).getStringCellValue(), cell.toString());
			}
			jsonList.add(rowData);
		}

		workbook.close();
		file.close();

		// Convert to JSON and write to file
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter(
				"C:/AutomationProjects/FE_OIPA_Automation/src/test/java/falcorp/TestData/CreatePolicyConverted.json");
		gson.toJson(jsonList, writer);
		writer.flush();
		writer.close();

		System.out.println("JSON file created successfully: " + jsonFilePath);
	}

	

  

}
