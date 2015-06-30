/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ictproject;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import static ictproject.ExcelConverterUtils.*;
import java.sql.SQLException;

/**
 *
 * @author Sandeep
 */
public class ExcelTest {

    private WritableCellFormat times;
    private String inputFile;

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void write(String name) throws IOException, WriteException,SQLException {
        File file = new File(inputFile);
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Part1", 0);
        WritableSheet excelSheet1 = workbook.getSheet(0);
        mergeCells(excelSheet1, 1);
        createLabels(excelSheet1, name, 1);
        addContent("SELECT * from  janajatianusarkogharduri where name='" + name + "'", excelSheet1, 11, JANAJATIANUSAR);
        addContent("SELECT * from  bidamanpanikosrot where name='" + name + "'", excelSheet1, 25, PANIkASHROT);
        workbook.createSheet("Part2", 1);
        WritableSheet excelSheet2 = workbook.getSheet(1);
        mergeCells(excelSheet2, 2);
        createLabels(excelSheet2, name, 2);
        addContent("SELECT * from  actualbudget where name='" + name + "'", excelSheet2, 4, ACTUALBUDGET);
        addContent("SELECT * from  expectedbudget where name='" + name + "'", excelSheet2, 13, EXPECTEDBUDGET);
        addContent("SELECT * from  panijanyarogkabibaran where name='" + name + "'", excelSheet2, 22, PANIKOROG);

        workbook.createSheet("Part3", 2);
        WritableSheet excelSheet3 = workbook.getSheet(2);
        mergeCells(excelSheet3, 3);
        createLabels(excelSheet3, name, 3);
        addContent("SELECT * from  sauchalaykoawasta where name='" + name + "'", excelSheet3, 5, SAUCHALAYAKOAWASTA);

        workbook.write();
        workbook.close();
    }

    public static void main(String[] args) throws WriteException, IOException,SQLException {
        ExcelTest test = new ExcelTest();
        test.setOutputFile("c:/temp/lars.xls");
        JDBCConnection connection = new JDBCConnection();
        connection.getConnection();
        test.write("बिरगुनज");
        System.out
                .println("Please check the result file under c:/temp/lars.xls ");
    }

}
