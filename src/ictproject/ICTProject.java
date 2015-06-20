/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ictproject;

import java.io.FileOutputStream;
import java.io.IOException;
 
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.lang.reflect.Method;
import javaapplication1.Preeti;


/**
 *
 * @author Sandeep
 */
public class ICTProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         try {
                        
            Document document = new Document();
            String encoding = "Identity-H";
            Font fontNormal = FontFactory.getFont(("C:/Users/Sandeep/Downloads/Preeti_0.ttf"), encoding,BaseFont.EMBEDDED, 8, Font.NORMAL);
            PdfWriter.getInstance(document, new FileOutputStream("test.pdf"));
            
            document.open();
 
            Chunk chunkEnglish = new Chunk("Hello World " + "\n", fontNormal);
             Preeti preeti=new Preeti();
            Chunk chunkBrazil = new Chunk("नेपाल" + "\n", fontNormal);
 
            PdfPTable table = new PdfPTable(2);
            table.addCell("Locale");
            table.addCell("Translated Text");
 
            PdfPCell cellEnglish = new PdfPCell(new Phrase(chunkEnglish));
            table.addCell(new PdfPCell(new Phrase(new Chunk(new String("नेपालि".getBytes(),"UTF-8")))));
            table.addCell(cellEnglish);
 
            PdfPCell cellBrazil = new PdfPCell(new Phrase(chunkBrazil));
             PdfObject line=new PdfString("नेपालि", PdfObject.TEXT_UNICODE);;
             System.out.println("line"+line);
            table.addCell(new PdfPCell(new Phrase(PdfObject.TEXT_UNICODE)));
            table.addCell(cellBrazil);
 
            document.add(table);
            document.close();
 
            System.out.println("PDF generation complete....");
     
          } catch (Exception e) {
            System.out.println("Error occurred while generating PDF" + e.getMessage());
            e.printStackTrace();
          }
  JDBCConnection connection=new JDBCConnection();
    connection.getConnection();
        JDBCConnection.populate();
        MainSelectionPage mainSelectionPage=new MainSelectionPage();
       mainSelectionPage.setVisible(true);
    }
    
}
    
    

