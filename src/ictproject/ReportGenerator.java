/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ictproject;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.HeaderFooter;
import static ictproject.JDBCConnection.conn;
import static ictproject.JDBCConnection.converter;
import static ictproject.JDBCConnection.getDistinctResult;
import static ictproject.JDBCConnection.getSum;
import static ictproject.JDBCConnection.numberConverterToUnicode;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Sandeep
 */
public class ReportGenerator {
    File folderForPDF = new File("C:/ICTProject");
	
    String encoding = "Identity-H";
//    Font fontNormal = FontFactory.getFont(("C:/Users/Sandeep/Downloads/arialuni.ttf"), encoding,BaseFont.EMBEDDED, 16, Font.NORMAL);
   // Font fontNormal = FontFactory.getFont(("C:/Users/Sandeep/Downloads/mangal.ttf"), encoding,BaseFont.EMBEDDED, 16, Font.NORMAL);
//    File f=new File("Preeti_0.ttf");
    String fontPath="C:/Windows/Fonts/Preeti_0.ttf";
    Path path=Paths.get(fontPath);
    Font fontNormal = FontFactory.getFont(fontPath, encoding,BaseFont.EMBEDDED, 16, Font.NORMAL);
        
    public void writer(String selected){
//        if (!folderForPDF.exists()) {
//		if (folderForPDF.mkdir()) {
//			System.out.println("Directory is created!");
//		} else {
//			System.out.println("Failed to create directory!");
//		}
//	}
        
        
       Document document=new Document(PageSize.A4); 
         try{
         String path="E:/"+selected+".pdf";
         com.itextpdf.text.pdf.PdfWriter.getInstance(document,new FileOutputStream(path)); 
         document.open(); 
         Paragraph para=getParagraph(selected,"gulmi");
         document.add(para);
         document.add( Chunk.NEWLINE );
         document.add( Chunk.NEWLINE );
         document.add(getParagraph(selected,"janajatiAnusar"));
         document.add(janajatiAnusar(selected));
         document.add(getParagraph(selected,"pani ko sroth"));
         document.add(getPaniKoSroth(selected));
            
          document.newPage();
          document.add(getParagraph(selected, "name"));

          document.add(getParagraph(selected,"actual budget"));
          document.add(actualBudget(selected,"actualBudget"));
          document.add( Chunk.NEWLINE );
          document.add( Chunk.NEWLINE );
          document.add(getParagraph(selected,"expected budget"));
          document.add(actualBudget(selected,"expectedBudget"));
          
         document.setPageSize(PageSize.A4.rotate());
         document.newPage();
         document.add(getParagraph(selected, "name"));
         document.add(getParagraph(selected,"sauchalayKoAwasta"));
         document.add(sauchalayKoAwasta(selected));
         
         
          document.newPage();
          document.add(getParagraph(selected, "name"));
          document.add(getParagraph(selected,"pani janya rog ko bibaran"));
          document.add(rogKoBibaran(selected));
          
                  
         document.close();
         }catch(Exception e){
             e.printStackTrace();
             System.out.println("error"+e.getMessage());
             ErrorPopup errorPopup=new ErrorPopup(e.getMessage());
             errorPopup.setVisible(true);
             errorPopup.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
          table.addCell(numberConverterToUnicode(rs.getString("serialNo")));
          table.addCell(numberConverterToUnicode(rs.getString("wardNo")));
          table.addCell(numberConverterToUnicode(rs.getString("paniKoSrotCount")));
          table.addCell(numberConverterToUnicode(rs.getString("pokharicount")));
          table.addCell(getNepaliPhrase(converter(rs.getString("remarks"))));
 
      }
      table.addCell(getNepaliPhrase("hDdf"));
      table.addCell("");
      table.addCell("");
      table.addCell(getSum(name, "pokharicount","bidamanpanikosrot"));
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
            table.addCell(getNepaliPhrase("s}lkmot"));
            table.setHeaderRows(1);
            table.getDefaultCell().setBackgroundColor(null);

            try{
           Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT * from  panijanyarogkabibaran where name='"+name+"'";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
          table.addCell(numberConverterToUnicode(rs.getString("SNo")));
          table.addCell(getNepaliPhrase(converter(rs.getString("diseaseName"))));
          table.addCell(numberConverterToUnicode(rs.getString("year6970")));
          table.addCell(numberConverterToUnicode(rs.getString("year7071")));
          table.addCell(numberConverterToUnicode(rs.getString("year7172")));
          table.addCell(getNepaliPhrase(converter(rs.getString("remarks"))));
 
      }

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }
            return table;
}
    private  PdfPTable actualBudget(String name,String tableName){
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
      sql = "SELECT * from  "+tableName+" where name='"+name+"'";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
          table.addCell(getNepaliPhrase(rs.getString("arthikBarsa")));
          table.addCell(numberConverterToUnicode(rs.getString("khanePani")));
          table.addCell(numberConverterToUnicode(rs.getString("sarSafai")));
          table.addCell(numberConverterToUnicode(rs.getString("total")));
          table.addCell(getNepaliPhrase(converter(rs.getString("remarks"))));
 
      }
      table.addCell(getNepaliPhrase("hDdf"));//jamma
      table.addCell(getSum(name, "khanePani", tableName));
      table.addCell(getSum(name, "sarSafai", tableName));
      table.addCell(getSum(name, "total", tableName));
      
      table.addCell("");

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
        table.addCell(getNepaliPhrase("blnt"));//दिलत"));
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
            table.addCell(numberConverterToUnicode(rs.getString("sno")));
            table.addCell(numberConverterToUnicode(rs.getString("wardNo")));
            table.addCell(numberConverterToUnicode(rs.getString("dalit")));
            table.addCell(numberConverterToUnicode(rs.getString("adiwsi")));
            table.addCell(numberConverterToUnicode(rs.getString("muslim")));
            table.addCell(numberConverterToUnicode(rs.getString("anya")));
            table.addCell(numberConverterToUnicode(rs.getString("jamma")));
            table.addCell(getNepaliPhrase(converter(rs.getString("remarks"))));
 
      }

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }
            table.addCell(getNepaliPhrase("hDdf"));//jamma
            table.addCell("");
            table.addCell(getSum(name,"dalit","janajatianusarkogharduri"));
            table.addCell(getSum(name,"adiwsi","janajatianusarkogharduri"));
            table.addCell(getSum(name,"muslim","janajatianusarkogharduri"));
            table.addCell(getSum(name,"anya","janajatianusarkogharduri"));
            table.addCell(getSum(name,"jamma","janajatianusarkogharduri"));
            table.addCell("");
        
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
            table.addCell(numberConverterToUnicode(rs.getString("sno")));
            table.addCell(numberConverterToUnicode(rs.getString("wardNo")));
            table.addCell(numberConverterToUnicode(rs.getString("temporaryToilet")));
            table.addCell(numberConverterToUnicode(rs.getString("permanentToilet")));
            table.addCell(numberConverterToUnicode(rs.getString("noToilet")));
            table.addCell(getNepaliPhrase(converter(rs.getString("bhakonaBhako"))));
            table.addCell(getNepaliPhrase(converter(rs.getString("bhakonaDate").replace("-", "÷")).replace("247", "÷")));
            table.addCell(numberConverterToUnicode(rs.getString("urineSeperation")));
            table.addCell(numberConverterToUnicode(rs.getString("urineManure")));
            table.addCell(numberConverterToUnicode(rs.getString("bioGasUse")));
            table.addCell(numberConverterToUnicode(rs.getString("noSmokeGas")));
            table.addCell(numberConverterToUnicode(rs.getString("noSmokeWard")));
            table.addCell(getNepaliPhrase(converter(rs.getString("remarks"))));
      }

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }
            table.addCell(getNepaliPhrase("hDdf"));
            table.addCell("");
            table.addCell(getSum(name,"temporaryToilet","sauchalaykoawasta"));
            table.addCell(getSum(name,"permanentToilet","sauchalaykoawasta"));
            table.addCell(getSum(name,"noToilet","sauchalaykoawasta"));
            table.addCell("");
            table.addCell("");
            table.addCell(getSum(name,"urineSeperation","sauchalaykoawasta"));
            table.addCell(getSum(name,"urineManure","sauchalaykoawasta"));
            table.addCell(getSum(name,"bioGasUse","sauchalaykoawasta"));
            table.addCell(getSum(name,"noSmokeGas","sauchalaykoawasta"));
            table.addCell(getSum(name,"noSmokeWard","sauchalaykoawasta"));
            
            table.addCell("");
        
       return table;
    }
    private Paragraph getParagraph(String name,String type){
        Paragraph paragraph=new Paragraph();
        paragraph.setSpacingAfter(10);

        switch(type){
            case "pani ko sroth":{
                Paragraph para1=new Paragraph("@= ljBfdfg kfgLsf >f]tx?sf] cj:yfM",fontNormal);
                para1.setAlignment(Element.ALIGN_CENTER);

                paragraph.add(para1);
                break;
            }
            case "pani janya rog ko bibaran":{
                Paragraph para1=new Paragraph("%= kfgLhGo /f]ux?sf] ljj/0f M",fontNormal);
                para1.setAlignment(Element.ALIGN_CENTER);
                Paragraph para3=new Paragraph("uflj;:t/Lo kfgLhGo /f]ux?sf] ljj/0f :jf:Yo rf}sLaf6 jf dlxnf ;jo+;]ljsf af6 lng]",fontNormal);
                paragraph.add(para1);
                break;
            }
            case "actual budget":{
              Paragraph para1=new Paragraph("# -s_ ut tLg jif{sf nflu uflj;n] vfg]kfgL tyf ;/;kmfO If]qdf 5'6fPsf] jh]6 ljj/0fM",fontNormal);
//              Paragraph para3=new Paragraph("uflj;sf] hDdf ;Defljt nufgL ? ",fontNormal);
//              Paragraph para4=new Paragraph(getDistinctResult(name, "gabisaKoLagani", "actualBudget"));
              para1.setAlignment(Element.ALIGN_CENTER);
              Paragraph para5=new Paragraph();
             para5.add(new Chunk("uflj;sf] hDdf ;Defljt nufgL ? ",fontNormal));
             para5.add(getDistinctResult(name, "gabisaKoLagani", "actualBudget"));
                paragraph.add(para1);
                paragraph.add(para5);
                break;  
            }
            case "expected budget":{
                Paragraph para1=new Paragraph("# -v_ cfufdL # jif{sf nflu uflj;sf] vfg]kfgL tyf ;/;kmfO If]qdf x'g ;Sg] ;Defljt ah]6M",fontNormal);
                para1.setAlignment(Element.ALIGN_CENTER);
                Paragraph para5=new Paragraph();
                para5.add(new Chunk("uflj;sf] hDdf ;Defljt nufgL ? ",fontNormal));
                para5.add(getDistinctResult(name, "gabisaKoLagani", "expectedBudget"));
                paragraph.add(para1);
                paragraph.add(para5);
                break;
            }
            case "janajatiAnusar":{
                Paragraph para2=new Paragraph("uflj;sf] gfdMM"+converter(name),fontNormal);
                para2.setIndentationLeft(50);
                Paragraph para1=new Paragraph("!= hfthftL cg';f/sf] 3/w'/L ljj/0fM",fontNormal);
                para1.setAlignment(Element.ALIGN_CENTER);
                paragraph.add(para2);
                paragraph.add(Chunk.NEWLINE);
                paragraph.add(Chunk.NEWLINE);
                paragraph.add(para1);
                break;
            }
            case "sauchalayKoAwasta":{
                Paragraph para1=new Paragraph("$= zf}rfnosf] cj:yf M",fontNormal);//४. शौचालयको अवस्था ",fontNormal);
                para1.setAlignment(Element.ALIGN_CENTER);
                paragraph.add(para1);
                break;
            }
            case "gulmi":{
                Paragraph para=new Paragraph();
                Paragraph para1=new Paragraph("lhNnf ljsf; ;ldltsf] sfof{no",fontNormal);
                para1.setAlignment(Element.ALIGN_CENTER);
                
                Paragraph para3=new Paragraph("u'NdL",fontNormal);
                para3.setAlignment(Element.ALIGN_CENTER);
                Paragraph para4=new Paragraph("vfg]kfgL tyf ;/;kmfO OsfO{",fontNormal);
                para4.setAlignment(Element.ALIGN_CENTER);
                para.add(para1);
                para.add(para3);
                para.add(para4);
                return para;
            }
            case "name":{
                Paragraph para=new Paragraph(converter(name),fontNormal);
                para.setAlignment(Element.ALIGN_CENTER);
                return para;
            }
            
        }
        return paragraph ;
    }
    public PdfPCell getNepaliPhrase(String word){
      PdfPCell cell=new PdfPCell(new Phrase(new Chunk(word,fontNormal)));
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      return cell;
    }
    
    public Chunk getNepaliPhraseInChunk(String word){
      return new Chunk(word,fontNormal);
    }
}
