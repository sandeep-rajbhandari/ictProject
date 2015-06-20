/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ictproject;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import static ictproject.JDBCConnection.conn;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Sandeep
 */
public class ReportGenerator {
    public void writer(String selected){
       Document document=new Document(PageSize.A4); 
         try{
         com.itextpdf.text.pdf.PdfWriter.getInstance(document,new FileOutputStream("hello.pdf")); 
         document.open(); 
         
         document.add(getParagraph(selected,"pani ko sroth"));
         document.add(getPaniKoSroth(selected));
         
          document.newPage();
          document.add(getParagraph(selected,"pani janya rog ko bibaran"));
          document.add(rogKoBibaran(selected));
          
          document.newPage();
          document.add(getParagraph(selected,"actual budget"));
          document.add(actualBudget(selected));
          document.add( Chunk.NEWLINE );
          document.add( Chunk.NEWLINE );
          document.add(getParagraph(selected,"expected budget"));
          document.add(actualBudget(selected));
          
          document.newPage();
          document.add(getParagraph(selected,"janajatiAnusar"));
          document.add(janajatiAnusar(selected));
         
         document.setPageSize(PageSize.A4.rotate());
         document.newPage();
         document.add(getParagraph(selected,"sauchalayKoAwasta"));
         document.add(sauchalayKoAwasta(selected));
         document.close();
         }catch(Exception e){
             System.out.println("error"+e.getMessage());
         } 
    }
    
    private  PdfPTable getPaniKoSroth(String name){
        PdfPTable table = new PdfPTable(5); // 3 columns.
             table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
           
            table.addCell("Serial no");
            table.addCell("Ward no");
            table.addCell("Pani ko Sroth ko adhar");
            table.addCell("Pokhari Count");
            table.addCell("Remarks");
            table.setHeaderRows(2);
            table.getDefaultCell().setBackgroundColor(null);

            try{
           Statement stmt = conn.createStatement();
      String sql;
      String selected=name;
      sql = "SELECT * from bidamanpanikosrot where name='"+name+"'";
                System.out.println("sql>>>>>."+sql);
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
          System.out.println(">>>>>>>"+rs.getString("serialNo"));
          table.addCell(rs.getString("serialNo"));
          table.addCell(rs.getString("wardNo"));
          table.addCell(rs.getString("paniKoSrotCount"));
          table.addCell(rs.getString("pokharicount"));
          table.addCell(rs.getString("Remarks"));
 
      }
      table.addCell("");

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }
            return table;
}
    private  PdfPTable rogKoBibaran(String name){
        PdfPTable table = new PdfPTable(6); // 3 columns.
            table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
          
            table.addCell("Serial no");
            table.addCell("Rog ko naam");
            table.addCell("year");
            table.addCell("year");
            table.addCell("year");
            table.addCell("remarks");
            table.setHeaderRows(1);
            table.getDefaultCell().setBackgroundColor(null);

            try{
           Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT * from  panijanyarogkabibaran where name='"+name+"'";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
          table.addCell(rs.getString("SNo"));
          table.addCell(rs.getString("diseaseName"));
          table.addCell(rs.getString("year6970"));
          table.addCell(rs.getString("year7071"));
          table.addCell(rs.getString("year7172"));
          table.addCell(rs.getString("remarks"));
 
      }

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }
            return table;
}
    private  PdfPTable actualBudget(String name){
        PdfPTable table = new PdfPTable(5);
            table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
          
            table.addCell("arthikBarsa");
            table.addCell("khanePani");
            table.addCell("sarSafai");
            table.addCell("total");
            table.addCell("remarks");
            table.setHeaderRows(1);
            table.getDefaultCell().setBackgroundColor(null);

            try{
           Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT * from  actualBudget where name='"+name+"'";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
          table.addCell(rs.getString("arthikBarsa"));
          table.addCell(rs.getString("khanePani"));
          table.addCell(rs.getString("sarSafai"));
          table.addCell(rs.getString("total"));
          table.addCell(rs.getString("remarks"));
 
      }

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }
            return table;
}
    private  PdfPTable janajatiAnusar(String name) throws Exception{
       PdfPTable table = new PdfPTable(8);
        table.setWidths(new int[]{ 1, 1, 2, 2, 2, 2, 2, 2});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("S/N"));
        cell.setRowspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Ward no"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Janajati anusar ghar duri sankhya"));
        cell.setColspan(5);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Remarks"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("Dalit");
        table.addCell("Adibasi Janajati");
        table.addCell("Muslim");
        table.addCell("anya");
        table.addCell("total");
        try{
           Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT * from  janajatianusarkogharduri where name='"+name+"'";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
            table.addCell(rs.getString("sno"));
            table.addCell(rs.getString("wardNo"));
            table.addCell(rs.getString("dalit"));
            table.addCell(rs.getString("adiwsi"));
            table.addCell(rs.getString("muslim"));
            table.addCell(rs.getString("anya"));
            table.addCell(rs.getString("jamma"));
            table.addCell(rs.getString("remarks"));
 
      }

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }
        
       return table;
    }
    private  PdfPTable sauchalayKoAwasta(String name) throws Exception{
        PdfPTable table = new PdfPTable(13);
        table.setWidths(new int[]{ 1, 1, 1,1,1,1,1,1,1,1,1,1,1});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("S/N"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Ward no"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Sauchalay ko bibaran"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Kula disa mukta gosana"));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Pisab alag garne ko sankya"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Pisab maal garne ko sankya"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Bio gas prayog garne ko sankya"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("duwa rahit chulo prayog garne ko sankya"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("duwa rahit gosana bhako na bhako"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("remarks"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell("asthayi charpi sankhya");
        table.addCell("sthayi charpi sankya");
        table.addCell("charpi na bhako");
        table.addCell("bhayeko/nabhayeko");
        table.addCell("bhayeko bhaye miti");
        try{
           Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT * from   sauchalaykoawasta where name='"+name+"'";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
            table.addCell(rs.getString("sno"));
            table.addCell(rs.getString("wardNo"));
            table.addCell(rs.getString("temporaryToilet"));
            table.addCell(rs.getString("permanentToilet"));
            table.addCell(rs.getString("noToilet"));
            table.addCell(rs.getString("bhakonaBhako"));
            table.addCell(rs.getString("bhakonaDate"));
            table.addCell(rs.getString("urineSeperation"));
            table.addCell(rs.getString("urineManure"));
            table.addCell(rs.getString("bioGasUse"));
            table.addCell(rs.getString("noSmokeGas"));
            table.addCell(rs.getString("noSmokeWard"));
            table.addCell(rs.getString("remarks"));
 
      }

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }
        
       return table;
    }
    private Paragraph getParagraph(String name,String type){
        Paragraph paragraph=new Paragraph();
        Paragraph para2=new Paragraph("gabisako naam::"+name);
        System.out.println(">>>>>>>"+name);
        paragraph.setSpacingAfter(10);

        switch(type){
            case "pani ko sroth":{
                Paragraph para1=new Paragraph("pani ko sroth");
                paragraph.add(para1);
                break;
            }
            case "pani janya rog ko bibaran":{
                Paragraph para1=new Paragraph("pani janya rog ko bibaran");
                paragraph.add(para1);
                break;
            }
            case "actual budget":{
              Paragraph para1=new Paragraph("jamma lagani");
              Paragraph para3=new Paragraph("actual budget");
                paragraph.add(para1);
                paragraph.add(para3);
                break;  
            }
            case "expected budget":{
                Paragraph para1=new Paragraph("expected budget");
                Paragraph para3=new Paragraph("sambhabid budget");
                paragraph.add(para1);
                paragraph.add(para3);
                break;
            }
            case "janajatiAnusar":{
                Paragraph para1=new Paragraph("janajati anusar");
                paragraph.add(para1);
                break;
            }
            case "sauchalayKoAwasta":{
                Paragraph para1=new Paragraph("sauchalay ko awastha");
                paragraph.add(para1);
                break;
            }
            
        }
        paragraph.add(para2);
        return paragraph ;
    }
    private int getSum(String name,String field){
      try{
           Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT sum() from  "+field+" where name="+name;
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      if(rs.next()){
          if(rs.getString(1)!=null)
            return Integer.parseInt(rs.getString(1));
      }

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }  
       return 0; 
    }
}
