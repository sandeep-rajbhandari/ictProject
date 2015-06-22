/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ictproject;

import java.io.FileOutputStream;
 
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



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
            Font fontNormal = FontFactory.getFont(("C:/Users/Sandeep/Downloads/arialuni.ttf"), encoding,BaseFont.EMBEDDED, 8, Font.NORMAL);
            PdfWriter.getInstance(document, new FileOutputStream("E:/test.pdf"));
            
            document.open();
 
            Chunk chunkEnglish = new Chunk("Hello World " + "\n", fontNormal);
//            file.temp=new StringBuffer("नेपाल मेरो देश");
            
//            Chunk chunkBrazil = new Chunk(new Preeti().converter() + "\n", fontNormal);
 
            PdfPTable table = new PdfPTable(2);
            table.addCell("Locale");
            table.addCell("Translated Text");
 
            PdfPCell cellEnglish = new PdfPCell(new Phrase(chunkEnglish));
            table.addCell(new PdfPCell(new Phrase(new Chunk("नेपाल मेरो देश",fontNormal))));
            table.addCell(cellEnglish);
 
//            PdfPCell cellBrazil = new PdfPCell(new Phrase(chunkBrazil));
            table.addCell(new Phrase(new Chunk("h'dnf",fontNormal)));
//            table.addCell(cellBrazil);
 
            document.add(table);
            document.close();
 
            System.out.println("PDF generation complete....");
     
          } catch (Exception e) {
            System.out.println("Error occurred while generating PDF" + e);
            e.printStackTrace();
          }
        JDBCConnection connection=new JDBCConnection();
        connection.getConnection();
        JDBCConnection.populate();
        MainSelectionPage mainSelectionPage=new MainSelectionPage();
        mainSelectionPage.setVisible(true);
   }
  
}
    
    

