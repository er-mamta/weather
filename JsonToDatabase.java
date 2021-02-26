package com.weatherApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class JsonToDatabase {
	
	
	
   public static Connection ConnectToDB() throws Exception {
      //Registering the Driver
	   Class.forName("oracle.jdbc.driver.OracleDriver");  

  //    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
      //Getting the connection
       
      String mysqlUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
      Connection con = DriverManager.getConnection(mysqlUrl, "hr", "oracle");
      System.out.println("Connection established......");
      return con;
   }
   public static void JsonToDB(String result) {
      //Creating a JSONParser object
	   int city_id = 0,temp,temp_min,temp_max;
       String city_name = null;
       String country = null;
	   
	   
	   
	   JSONParser jsonParser = new JSONParser();
      
	   try {
         //Parsing the contents of the JSON file
         JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
         //Retrieving the array
   //      JSONArray jsonArray = (JSONArray) jsonObject.get("list.main");
     //    JSONArray jsonArray_1 = (JSONArray) jsonObject.get("city");
         Connection con = ConnectToDB();
         //Insert a row into the  table
         
         while(jsonObject != null) {
        	 
              JSONObject obj = (JSONObject) jsonObject.get("list.main");
              JSONObject obj_city = (JSONObject) jsonObject.get("city");

              temp = Integer.parseInt((String) obj.get("temp"));
              temp_max = Integer.parseInt((String) obj.get("temp_max"));
             
              temp_min = Integer.parseInt((String) obj.get("temp_min"));


         	  city_id = Integer.parseInt((String) obj_city.get("id"));
              city_name = (String) obj_city.get("name");
              country = (String) obj_city.get("country");
             

              PreparedStatement pstmt = con.prepareStatement("INSERT INTO WEATHER_DATA values (?, ?, ?, ?, ?, ? )");
              pstmt.setInt(1, city_id);
              pstmt.setString(2, city_name);
              pstmt.setString(3, country);
              
              pstmt.setInt(4,temp_max );
              pstmt.setInt(5, temp_min);
              pstmt.setInt(6, temp);

              pstmt.executeUpdate();
           }
         
           System.out.println("Records inserted.....");
        } catch (Exception e) {
           e.printStackTrace();
        }
      }
}
        
         
         
    /*     for(Object obj: jsonArray_1) {
             JSONObject record = (JSONObject) obj;

        	 city_id = Integer.parseInt((String) record.get("id"));
             city_name = (String) record.get("name");
             country = (String) record.get("country");

         }*/
       
            
            
           
         
 