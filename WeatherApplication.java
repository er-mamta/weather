package com.weatherApp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import org.apache.commons.io.IOUtils;




public class WeatherApplication{

	
    public static void main(String[] args) {
		
		try {
			
			
			
			
			Properties prop=new Properties();
			FileInputStream ip= new FileInputStream("/Users/mamtasinghal/Documents/JavaTraining/JavaTrainingSession/WeatherApplication/config.properties");
			prop.load(ip);
			
			String API_KEY = prop.getProperty("API_KEY");
			String city_name = prop.getProperty("city_name");
					
			String str_url = "http://api.openweathermap.org/data/2.5/forecast?q="+ city_name + "&appid=" + API_KEY;
			
			URL url = new URL(str_url);

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			//Getting the response code
			int responsecode = conn.getResponseCode();
			
			if (responsecode != 200) {
			    throw new RuntimeException("HttpResponseCode: " + responsecode);
			} else { 
				
				InputStream in = new BufferedInputStream(conn.getInputStream());
	           String result = IOUtils.toString(in, "UTF-8");
	           System.out.println(result);
			  
	           JsonToDatabase.JsonToDB(result);
	           
	           in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
    }


}
