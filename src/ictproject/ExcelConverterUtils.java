/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ictproject;

import static ictproject.JDBCConnection.conn;
import static ictproject.JDBCConnection.converter;
import static ictproject.JDBCConnection.numberConverterToUnicode;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 *
 * @author Sandeep
 */
public class ExcelConverterUtils {

    public static final List<String> paniKaShrot = Arrays.asList("serialNo", "wardNo", "paniKoSrotCount", "", "", "", "pokharicount", "remarks");
    public static final List<String> janajatiAnusar = Arrays.asList(
            "sno", "wardNo", "dalit", "adiwsi", "muslim", "anya", "jamma", "remarks");

    public static void mergeCells(WritableSheet excelSheet, int page) throws IOException, WriteException {
        if (page == 1) {
            excelSheet.mergeCells(0, 1, 7, 1);
            excelSheet.mergeCells(0, 2, 7, 2);
            excelSheet.mergeCells(0, 3, 7, 3);
            excelSheet.mergeCells(0, 5, 7, 5);
            excelSheet.mergeCells(0, 7, 7, 7);
            //जातजाती अनुसारको घरधुरी विवरणः
            excelSheet.mergeCells(0, 9, 0, 10);
            excelSheet.mergeCells(1, 9, 1, 10);
            excelSheet.mergeCells(2, 9, 6, 9);
            excelSheet.mergeCells(7, 9, 7, 10);
            excelSheet.mergeCells(0, 20, 1, 20);
            //२. विद्यामान पानीका श्रोतहरुको अवस्था
            excelSheet.mergeCells(0, 22, 7, 22);
            excelSheet.mergeCells(2, 24, 5, 24);
            excelSheet.mergeCells(2, 25, 5, 25);
            excelSheet.mergeCells(2, 26, 5, 26);
            excelSheet.mergeCells(2, 27, 5, 27);
            excelSheet.mergeCells(2, 28, 5, 28);
            excelSheet.mergeCells(2, 29, 5, 29);
            excelSheet.mergeCells(2, 30, 5, 30);
            excelSheet.mergeCells(2, 31, 5, 31);
            excelSheet.mergeCells(2, 32, 5, 32);
            excelSheet.mergeCells(2, 33, 5, 33);
            excelSheet.mergeCells(2, 34, 5, 34);
        } else if (page == 2) {
            //ACTUAL BUDGET
            excelSheet.mergeCells(0, 0, 7, 0);
            excelSheet.mergeCells(0, 1, 7, 1);
            for (int i = 3; i <= 7; i++) {
                excelSheet.mergeCells(0, i, 1, i);
                excelSheet.mergeCells(2, i, 3, i);
                excelSheet.mergeCells(4, i, 5, i);

            }

            //EXPECTED BUDGET
            excelSheet.mergeCells(0, 9, 7, 9);
            excelSheet.mergeCells(0, 10, 7, 10);
            for (int i = 12; i <= 16; i++) {
                excelSheet.mergeCells(0, i, 1, i);
                excelSheet.mergeCells(2, i, 3, i);
                excelSheet.mergeCells(4, i, 5, i);

            }
            //४. पानीजन्य रोगहरुको विवरण ः
            excelSheet.mergeCells(0, 18, 7, 18);
            excelSheet.mergeCells(0, 19, 7, 19);
            for (int i = 21; i <= 30; i++) {
                excelSheet.mergeCells(1, i, 3, i);

            }

        }

    }

    public static void createLabels(WritableSheet excelSheet, String name, int page) throws IOException, WriteException {
        if (page == 1) {
            createLabel(excelSheet, "जिल्ला विकास समितिको कार्यालय", 0, 1);
            createLabel(excelSheet, "गुल्मी", 0, 2);
            createLabel(excelSheet, "खानेपानी तथा सरसफाइ इकाई", 0, 3);
            createLabel(excelSheet, "गाविसको नाम::" + name, 0, 5);
            createLabel(excelSheet, "१. जातजाती अनुसारको घरधुरी विवरणः" + name, 0, 7);
            //जातजाती अनुसारको घरधुरी विवरणः
            createLabel(excelSheet, "सि.नं.", 0, 9);
            createLabel(excelSheet, "वडा नं.", 1, 9);
            createLabel(excelSheet, "जातजाती अनुसारको घरधुरीको विवरण संख्या", 2, 9);
            createLabel(excelSheet, "कैफियत", 7, 9);
            createLabel(excelSheet, "दलित", 2, 10);
            createLabel(excelSheet, "आदिवासी/जनजाती", 3, 10);
            createLabel(excelSheet, "मुश्लिम", 4, 10);
            createLabel(excelSheet, "अन्य", 5, 10);
            createLabel(excelSheet, "जम्मा", 6, 10);
            createLabel(excelSheet, "जम्मा", 0, 20);
            //२. विद्यामान पानीका श्रोतहरुको अवस्थाः
            createLabel(excelSheet, "२. विद्यामान पानीका श्रोतहरुको अवस्थाः", 0, 22);
            createLabel(excelSheet, "सि.नं.", 0, 24);
            createLabel(excelSheet, "वडा नं.", 1, 24);
            createLabel(excelSheet, "पानी श्रोत सुक्दै अवस्था(अति धेरै–५,धेरै–४,ठिकै–३,कम–२,छैन–१)", 2, 24);
            createLabel(excelSheet, "पोखरीको संख्या", 6, 24);
            createLabel(excelSheet, "कैफियत", 7, 24);
        } else if (page == 2) {
            //actual 
            createLabel(excelSheet, "३ (क) गत तीन वर्षका लागि गाविसले खानेपानी तथा सरसफाइ क्षेत्रमा छुटाएको वजेट विवरणः", 0, 0);
            createLabel(excelSheet, "गाविसको जम्मा लगानी रु ः", 0, 1);
            createLabel(excelSheet, "आर्थिक वर्ष", 0, 3);
            createLabel(excelSheet, "खानेपानी(रुपैया)", 2, 3);
            createLabel(excelSheet, "सरसफाइ(रुपैया)", 4, 3);
            createLabel(excelSheet, "जम्मा", 6, 3);
            createLabel(excelSheet, "कैफियत", 7, 3);
            //budget
            createLabel(excelSheet, "३ (ख) आगामी ३ वर्षका लागि गाविसको खानेपानी तथा सरसफाइ क्षेत्रमा हुन सक्ने सम्भावित बज", 0, 9);
            createLabel(excelSheet, "गाविसको जम्मा सम्भावित लगानी रुः", 0, 10);
            createLabel(excelSheet, "आर्थिक वर्ष", 0, 12);
            createLabel(excelSheet, "खानेपानी(रुपैया)", 2, 12);
            createLabel(excelSheet, "सरसफाइ(रुपैया)", 4, 12);
            createLabel(excelSheet, "जम्मा", 6, 12);
            createLabel(excelSheet, "कैफियत", 7, 12);
            
            //४. पानीजन्य रोगहरुको विवरण ः

            createLabel(excelSheet, "४. पानीजन्य रोगहरुको विवरण ः", 0, 18);
            createLabel(excelSheet, "गाविसस्तरीय पानीजन्य रोगहरुको विवरण स्वास्थ्य चौकीबाट वा महिला स्वयंसेविका बाट लिने", 0, 19);
            createLabel(excelSheet, "सि. नं.", 0, 21);
            createLabel(excelSheet, "रोगको नाम", 1, 21);
            createLabel(excelSheet, "बर्ष (२०६९/७०)", 4, 21);
            createLabel(excelSheet, "बर्ष (२०७०/७१)", 5, 21);
            createLabel(excelSheet, "बर्ष (२०७१/७२)", 6, 21);
            createLabel(excelSheet, "कैफियत", 7, 21);


        }
    }

    private static void createLabel(WritableSheet sheet, String content, int x, int y)
            throws WriteException {
        // Write a few headers
        addCaption(sheet, x, y, content);

    }

    private static void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        WritableFont cellFont = new WritableFont(WritableFont.TIMES, 11);

        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFormat.setAlignment(Alignment.CENTRE);
        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        cellFormat.setWrap(true);
        Label label;
        label = new Label(column, row, s, cellFormat);
        sheet.addCell(label);
    }

    public static void addContent(String sqlQuery, WritableSheet sheet, int row, List<String> fields) throws IOException, WriteException {
        WritableFont cellFont = new WritableFont(WritableFont.TIMES, 11);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        cellFormat.setAlignment(Alignment.CENTRE);
        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        cellFormat.setWrap(true);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                for (String field : fields) {
                    if (!field.isEmpty()) {
                        if (!field.equals("remarks")) {
                            sheet.addCell(new Number(fields.indexOf(field), row, Integer.parseInt(rs.getString(field)), cellFormat));
                        } else {
                            sheet.addCell(new Label(fields.indexOf(field), row, rs.getString(field), cellFormat));

                        }
                    }
                }
                row++;
            }

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }

    }

}
