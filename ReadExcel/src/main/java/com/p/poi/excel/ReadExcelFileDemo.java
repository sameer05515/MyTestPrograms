package com.p.poi.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringJoiner;

public class ReadExcelFileDemo {
    public static void main(String args[]) throws IOException {
        //File myFile = new File("C:\\Users\\kumar.premendra\\Desktop\\Expertise.xlsx");
        //File myFile = new File("Expertise.xlsx");

        String fileName = "Expertise.xlsx";
        ClassLoader classLoader = ReadExcelFileDemo.class.getClassLoader();

        File myFile = new File(classLoader.getResource(fileName).getFile());

        FileInputStream fis = new FileInputStream(myFile);
        // Finds the workbook instance for XLSX file
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();
        // Traversing over each row of XLSX file
        int rows = 0;
        String[] keys = {"topic", "usedInLast2Years", "demandedInMarket", "totalRelevantExperience",
                "projectsWhereUsed", "howWhyUsed", "remarks", "label"};
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (rows++ == 0) {
                continue;
            }
            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            int cols = 0;
            StringJoiner sjNode = new StringJoiner(",\n", "{\n", "\n}");
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = getCellValue(cell);
                //System.out.print(keys[cols]+":"+cellValue);

                sjNode.add("\"" + keys[cols] + "\" : \"" + cellValue + "\"");
                cols++;
            }
            System.out.println(sjNode.toString() + ",");

        }
    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                //System.out.print(cell.getStringCellValue() + "\t||\t");
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
//			System.out.print(cell.getNumericCellValue() + "\t||\t");
                cellValue = cell.getNumericCellValue() + "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
//			System.out.print(cell.getBooleanCellValue() + "\t||");
                cellValue = cell.getBooleanCellValue() + "";
                break;
            default:
        }

        return cellValue;
    }

//    @Deprecated
//    private static void scrapCode() throws IOException {
//
//        // obtaining input bytes from a file
//        FileInputStream fis = new FileInputStream(new File("C:\\Users\\kumar.premendra\\Desktop\\Expertise.xlsx"));
//        // creating workbook instance that refers to .xls file
//        HSSFWorkbook wb = new HSSFWorkbook(fis);
//        // creating a Sheet object to retrieve the object
//        HSSFSheet sheet = wb.getSheetAt(0);
//        // evaluating cell type
//        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
//        for (Row row : sheet) // iteration over row using for each loop
//        {
//            for (Cell cell : row) // iteration over cell using for each loop
//            {
//                switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
//                    case Cell.CELL_TYPE_NUMERIC: // field that represents numeric cell type
//                        // getting the value of the cell as a number
//                        System.out.print(cell.getNumericCellValue() + "\t\t||");
//                        break;
//                    case Cell.CELL_TYPE_STRING: // field that represents string cell type
//                        // getting the value of the cell as a string
//                        System.out.print(cell.getStringCellValue() + "\t\t||");
//                        break;
//                }
//            }
//            System.out.println();
//        }
//        System.out.println("======================");
//
//    }
}