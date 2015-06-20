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
import java.sql.*;
import javax.swing.table.TableModel;

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

}