/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ictproject;

import com.itextpdf.text.BaseColor;
 
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import static ictproject.JDBCConnection.conn;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javaapplication1.Preeti;
import javaapplication1.file;

/**
 *
 * @author Sandeep
 */
public class ReportGenerator {
    String encoding = "Identity-H";
//    Font fontNormal = FontFactory.getFont(("C:/Users/Sandeep/Downloads/arialuni.ttf"), encoding,BaseFont.EMBEDDED, 16, Font.NORMAL);
   // Font fontNormal = FontFactory.getFont(("C:/Users/Sandeep/Downloads/mangal.ttf"), encoding,BaseFont.EMBEDDED, 16, Font.NORMAL);
    Font fontNormal = FontFactory.getFont(("C:/Users/Sandeep/Downloads/Preeti_0.ttf"), encoding,BaseFont.EMBEDDED, 16, Font.NORMAL);
            
    public void writer(String selected){
       Document document=new Document(PageSize.A4); 
         try{
         com.itextpdf.text.pdf.PdfWriter.getInstance(document,new FileOutputStream("E:/hello.pdf")); 
         document.open(); 
         
         document.add(getParagraph(selected,"janajatiAnusar"));
         document.add(janajatiAnusar(selected));
 
         document.newPage();

         document.add(getParagraph(selected,"pani ko sroth"));
         document.add(getPaniKoSroth(selected));
         
          document.newPage();
          document.add(getParagraph(selected,"actual budget"));
          document.add(actualBudget(selected));
          document.add( Chunk.NEWLINE );
          document.add( Chunk.NEWLINE );
          document.add(getParagraph(selected,"expected budget"));
          document.add(actualBudget(selected));
          
         document.setPageSize(PageSize.A4.rotate());
         document.newPage();
         document.add(getParagraph(selected,"sauchalayKoAwasta"));
         document.add(sauchalayKoAwasta(selected));
         
         
          document.newPage();
          document.add(getParagraph(selected,"pani janya rog ko bibaran"));
          document.add(rogKoBibaran(selected));
          
                  
         document.close();
         }catch(Exception e){
             System.out.println("error"+e.getMessage());
         } 
    }
    
    private  PdfPTable getPaniKoSroth(String name)throws Exception{
        PdfPTable table = new PdfPTable(5); // 3 columns.
             table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
            table.setWidths(new int[]{ 1, 1, 2, 1, 1});
            table.addCell(getNepaliPhrase("l;=g+="));//"िस.नं."));
            table.addCell(getNepaliPhrase("j8f g+="));//"वडा नं."));
            table.addCell(getNepaliPhrase("kfgL >f]t ;'Sb} uPsf] cj:yf-clt w]/}–%,w]/}–$,l7s}–#,sd–@,5}g–!_"));//पानी श्रोत सुक्दै गएको अवस्था(अित धेरै–५,धेरै–४,िठकै–३,कम–२,छैन–१)"));
            table.addCell(getNepaliPhrase("kf]v/Lsf] ;+Vof"));//पोखरीको संख्या"));
            table.addCell(getNepaliPhrase("s}lkmot"));//कैफियत"));
            table.setHeaderRows(2);
            table.getDefaultCell().setBackgroundColor(null);

            try{
           Statement stmt = conn.createStatement();
      String sql;
      String selected=name;
      
      sql = "SELECT * from bidamanpanikosrot where name='"+name+"'";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
          table.addCell(rs.getString("serialNo"));
          table.addCell(rs.getString("wardNo"));
          table.addCell(rs.getString("paniKoSrotCount"));
          table.addCell(rs.getString("pokharicount"));
          table.addCell(rs.getString("Remarks"));
 
      }
      table.addCell(getNepaliPhrase("जम्मा"));
      table.addCell("");
      table.addCell("");
      table.addCell((String.valueOf(getSum(name, "pokharicount","bidamanpanikosrot"))));
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
          
            table.addCell(getNepaliPhrase("l;=g+="));//िस.नं.");
            table.addCell(getNepaliPhrase("l;=g+="));//;"रोगको नाम");
            table.addCell(getNepaliPhrase("aif{-@)^(÷&)_"));//"बर्ष""));//२०६९/७०)");
            table.addCell(getNepaliPhrase("aif{-@)&)÷&!_"));
            table.addCell(getNepaliPhrase("aif{-@)&)÷&!_"));
            table.addCell(getNepaliPhrase("aif{-@)&)÷&!_"));
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
          
            table.addCell(getNepaliPhrase("cfly{s jif{"));//artik barsa
            table.addCell(getNepaliPhrase("vfg]kfgL-?k}of_"));//khanepani
            table.addCell(getNepaliPhrase(";/;kmfO-?k}of_"));//sarsafai
            table.addCell(getNepaliPhrase("hDdf"));//jamma
            table.addCell(getNepaliPhrase("s}lkmot"));//kaifiyat
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
        cell = new PdfPCell(getNepaliPhrase("l;=g+="));//s.no
        cell.setRowspan(2);
        table.addCell(cell);

        cell = new PdfPCell(getNepaliPhrase("j8f g+="));//ward no
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(getNepaliPhrase("hfthftL cg';f/sf] 3/w'/Lsf] ljj/0f ;+Vof"));//जातजाती अनुसारको घरधुरीको िववरण संख्या"));
        cell.setColspan(5);
        table.addCell(cell);
        cell = new PdfPCell(getNepaliPhrase("s}lkmot"));//कैिफयत"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell(getNepaliPhrase("s}lkmot"));//दिलत"));
        table.addCell(getNepaliPhrase("cflbjf;L÷hghftL"));//आिदवासी/जनजाती"));
        table.addCell(getNepaliPhrase("d'lZnd"));//muslim
        table.addCell(getNepaliPhrase("cGo"));//अन्य"));
        table.addCell(getNepaliPhrase("hDdf"));//जम्मा"));
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

        cell = new PdfPCell(getNepaliPhrase("hDdf"));//;िस.नं."));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(getNepaliPhrase("hDdf"));//वडा नं."));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(getNepaliPhrase("zf}rfnosf ljj/0f"));//शौचालयका िववरण"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(getNepaliPhrase("v'Nnf lb;fd\"St 3f]if0f"));//खुल्ला िदसामूक्त घोषण"));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(getNepaliPhrase("lk;fj cnu ug]{ u/]sf 3/w'/L ;+Vof"));//िपसाव अलग गर्ने गरेका घरधुरी संख्या"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(getNepaliPhrase("lk;fj dn k|of]u ug]{ 3/w'/L ;+Vof"));//िपसाव मल प्रयोग गर्ने घरधुरी संख्या"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(getNepaliPhrase("afof] Uof; rkL{ k|of]u ug]{ 3/w'/L ;+Vof"));//बायो ग्यास चर्पी प्रयोग गर्ने घरधुरी संख्या"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(getNepaliPhrase("w'Fjf/lxt r'Nxf] ePsf] 3/ ;+Vof"));//धुँवारिहत चुल्हो भएको घर संख्या"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(getNepaliPhrase("w'Fjf/lxt j8f 3f]if0f  ePsf]÷gePsf]"));//धुँवारहित वडा घोषण  भएको/नभएको"));
        cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(getNepaliPhrase("s}lkmot"));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell(getNepaliPhrase("c:yfoL rkL{ ;+Vof"));//अस्थायी चर्पी संख्या"));
        table.addCell(getNepaliPhrase(":yfoL rkL{ ;+Vof"));//स्थायी चर्पी संख्या"));
        table.addCell(getNepaliPhrase("rkL{ gePsf] ;+Vof"));//चर्पी नभएको संख्या"));
        table.addCell(getNepaliPhrase("ePsf]÷gePsf"));//भएको/नभएको"));
        table.addCell(getNepaliPhrase("ePsf] eP ldlt"));//bhayeko bhaye miti
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
            table.addCell(new Phrase(new Chunk(rs.getString("remarks"),fontNormal)));
 
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
        paragraph.setSpacingAfter(10);

        switch(type){
            case "pani ko sroth":{
                Paragraph para1=new Paragraph("@= ljBfdfg kfgLsf >f]tx?sf] cj:yfM",fontNormal);
                paragraph.add(para1);
                break;
            }
            case "pani janya rog ko bibaran":{
                Paragraph para1=new Paragraph("%= kfgLhGo /f]ux?sf] ljj/0f M",fontNormal);
                Paragraph para3=new Paragraph("uflj;:t/Lo kfgLhGo /f]ux?sf] ljj/0f :jf:Yo rf}sLaf6 jf dlxnf ;jo+;]ljsf af6 lng]",fontNormal);
                paragraph.add(para1);
                break;
            }
            case "actual budget":{
              Paragraph para1=new Paragraph("# -s_ ut tLg jif{sf nflu uflj;n] vfg]kfgL tyf ;/;kmfO If]qdf 5'6fPsf] jh]6 ljj/0fM",fontNormal);
              Paragraph para3=new Paragraph("uflj;sf] hDdf ;Defljt nufgL ? ",fontNormal);
                paragraph.add(para1);
                paragraph.add(para3);
                break;  
            }
            case "expected budget":{
                Paragraph para1=new Paragraph("# -v_ cfufdL # jif{sf nflu uflj;sf] vfg]kfgL tyf ;/;kmfO If]qdf x'g ;Sg] ;Defljt ah]6M",fontNormal);
                Paragraph para3=new Paragraph("uflj;sf] hDdf ;Defljt nufgL ? ",fontNormal);
                paragraph.add(para1);
                paragraph.add(para3);
                break;
            }
            case "janajatiAnusar":{
                Paragraph para2=new Paragraph("uflj;sf] gfd MM"+converter(name),fontNormal);
                Paragraph para1=new Paragraph("!= hfthftL cg';f/sf] 3/w'/L ljj/0fM",fontNormal);
                
                paragraph.add(para2);
                paragraph.add(para1);
                break;
            }
            case "sauchalayKoAwasta":{
                Paragraph para1=new Paragraph("$= zf}rfnosf] cj:yf M",fontNormal);//४. शौचालयको अवस्था ",fontNormal);
                paragraph.add(para1);
                break;
            }
            
        }
        return paragraph ;
    }
    private int getSum(String name,String field,String tableName){
      try{
           Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT sum("+field+") from  "+tableName+" where name='"+name+"'";
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
    private PdfPCell getNepaliPhrase(String word){
        
        return new PdfPCell(new Phrase(new Chunk(word,fontNormal)));
    }
    
    private String converter(String unicodeString){
        file.temp=new StringBuffer(unicodeString);
        return new Preeti().converter();
    }
}
