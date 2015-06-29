/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ictproject;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import static ictproject.ExcelConverterUtils.*;

/**
 *
 * @author Sandeep
 */
public class ExcelTest {

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private String inputFile;

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void write(String name) throws IOException, WriteException {
        File file = new File(inputFile);
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Part1", 0);
        WritableSheet excelSheet1 = workbook.getSheet(0);
        mergeCells(excelSheet1,1);
        createLabels(excelSheet1, name,1);
        addContent("SELECT * from  janajatianusarkogharduri where name='" + name + "'", excelSheet1, 11, janajatiAnusar);
        addContent("SELECT * from  bidamanpanikosrot where name='" + name + "'", excelSheet1, 25, paniKaShrot);
        workbook.createSheet("Part2", 1);
        WritableSheet excelSheet2 = workbook.getSheet(1);
        mergeCells(excelSheet2,2);
        createLabels(excelSheet2, name, 2);

        workbook.write();
        workbook.close();
    }

    private void createLabel(WritableSheet sheet, String content, int x, int y)
            throws WriteException {
        // Write a few headers
        addCaption(sheet, x, y, content);
//    addCaption(sheet, 1, 0, "This is another header");

    }

    private void createContent(WritableSheet sheet) throws WriteException,
            RowsExceededException {
        // Write a few number
        for (int i = 1; i < 10; i++) {
            // First column
            addNumber(sheet, 0, i, i + 10);
            // Second column
            addNumber(sheet, 1, i, i * i);
        }
        // Lets calculate the sum of it
        StringBuffer buf = new StringBuffer();
        buf.append("SUM(A2:A10)");
        Formula f = new Formula(0, 10, buf.toString());
        sheet.addCell(f);
        buf = new StringBuffer();
        buf.append("SUM(B2:B10)");
        f = new Formula(1, 10, buf.toString());
        sheet.addCell(f);

        // now a bit of text
        for (int i = 12; i < 20; i++) {
            // First column
            addLabel(sheet, 0, i, "Boring text " + i);
            // Second column
            addLabel(sheet, 1, i, "Another text");
        }
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        WritableFont cellFont = new WritableFont(WritableFont.TIMES, 20);

        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFormat.setAlignment(Alignment.CENTRE);
        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        cellFormat.setWrap(true);
        Label label;
        label = new Label(column, row, s, cellFormat);
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int column, int row,
            Integer integer) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, integer, times);
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

    public static void main(String[] args) throws WriteException, IOException {
        ExcelTest test = new ExcelTest();
        test.setOutputFile("c:/temp/lars.xls");
        JDBCConnection connection = new JDBCConnection();
        connection.getConnection();
        test.write("बिरगुनज");
        System.out
                .println("Please check the result file under c:/temp/lars.xls ");
    }

}
