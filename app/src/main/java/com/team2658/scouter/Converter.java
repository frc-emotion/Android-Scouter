package com.team2658.scouter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by Gokul Swaminathan on 4/1/2018.
 *
 * This class converts files.
 */

public class Converter {

    private String csvFilePath;
    private String excelFilePath;
    private static final String CVS_SEPERATOR_CHAR = ",";

    public Converter(String csvFilePath, String excelFilePath) {
        this.csvFilePath = csvFilePath;
        this.excelFilePath = excelFilePath;
    }

    //method to convert .csv to .xls
    public void convertCsvToExcel() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(csvFilePath))));
        HSSFWorkbook myWorkBook = new HSSFWorkbook();
        FileOutputStream writer = new FileOutputStream(new File(excelFilePath));
        HSSFSheet mySheet = myWorkBook.createSheet();
        String line = "";
        int rowNo = 0;
        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(CVS_SEPERATOR_CHAR);
            HSSFRow myRow = mySheet.createRow(rowNo);
            for (int i = 0; i < columns.length; i++) {
                HSSFCell myCell = myRow.createCell(i);
                myCell.setCellValue(columns[i]);
            }
            rowNo++;
        }
        myWorkBook.write(writer);
        writer.close();
    }
}


