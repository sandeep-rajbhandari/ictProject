/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ictproject;

/**
 *
 * @author Sandeep
 */
import UncodeToPreetiConverter.Preeti;
import UncodeToPreetiConverter.file;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfPCell;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public  class JDBCConnection {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/ICTProject";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
   public static Connection conn=null;
   
   public  void getConnection() {
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
//   }finally{
//      //finally block used to close resources
//      try{
//         if(stmt!=null)
//            stmt.close();
//      }catch(SQLException se2){
//      }// nothing we can do
//      try{
//         if(conn!=null)
//            conn.close();
//      }catch(SQLException se){
//         se.printStackTrace();
//      }//end finally try
   }//end try
}//end main
      public static void populate(){
        try{
           Statement stmt = conn.createStatement();
      String sql;
      String name="sa";
      sql = "SELECT * from bidamanpanikosrot where name='"+name+"'";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
          System.out.println("name"+rs.getString("name"));
      }

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }
    }
      
      public static PdfPCell getSum(String name,String field,String tableName){
      try{
           Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT sum("+field+") from  "+tableName+" where name='"+name+"'";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      if(rs.next()){
          if(rs.getString(1)!=null)
            return numberConverterToUnicode(rs.getString(1));
      }

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }  
       return numberConverterToUnicode("0"); 
    }
    
    public static Chunk getDistinctResult(String name,String field,String tableName){
      try{
           Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT distinct("+field+") from  "+tableName+" where name='"+name+"'";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      if(rs.next()){
          if(rs.getString(1)!=null)
            return numberConverterToUnicodeInChunk(rs.getString(1));
      }

        }  
        catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
        }  
       return numberConverterToUnicodeInChunk("0"); 
    }
    
    
    public static String converter(String unicodeString){
        if(unicodeString.equals("")){
            return "";
        }
        file.temp=new StringBuffer(unicodeString);
        return new Preeti().converter();
    }
    public static PdfPCell numberConverterToUnicode(String actualData){
   
   Map<String,String> mapper=new HashMap<String, String>();
   mapper.put("1","!");
   mapper.put("2","@");
   mapper.put("3","#");
   mapper.put("4","$");
   mapper.put("5","%");
   mapper.put("6","^");
   mapper.put("7","&");
   mapper.put("8","*");
   mapper.put("9","(");
   mapper.put("0",")");
   for(String key:mapper.keySet()){
       actualData=actualData.replace(key, mapper.get(key));
   }
    return new ReportGenerator().getNepaliPhrase(actualData);
    }
    
    public static Chunk numberConverterToUnicodeInChunk(String actualData){
   
   Map<String,String> mapper=new HashMap<String, String>();
   mapper.put("1","!");
   mapper.put("2","@");
   mapper.put("3","#");
   mapper.put("4","$");
   mapper.put("5","%");
   mapper.put("6","^");
   mapper.put("7","&");
   mapper.put("8","*");
   mapper.put("9","(");
   mapper.put("0",")");
   for(String key:mapper.keySet()){
       actualData=actualData.replace(key, mapper.get(key));
   }
    return new ReportGenerator().getNepaliPhraseInChunk(actualData);
    }


}