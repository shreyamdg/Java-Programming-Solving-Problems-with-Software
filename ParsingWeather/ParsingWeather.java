
/**
 * Parsing weather data from CSV file and categorizing according to the needs. See PDF.
 * 
 * @ShreyamDuttaGupta
 * 
 */


import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class ParsingWeather {
    public CSVRecord hottestHourInFile(CSVParser parser) {
		//start with largestSoFar as nothing
		CSVRecord largestSoFar = null;
		//For each row (currentRow) in the CSV File
		for (CSVRecord currentRow : parser) {
			// use method to compare two records
			largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
		}
		//The largestSoFar is the answer
		return largestSoFar;
	}

	public CSVRecord hottestInManyDays() {
		CSVRecord largestSoFar = null;
		DirectoryResource dr = new DirectoryResource();
		// iterate over files
		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			// use method to get largest in file.
			CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
			// use method to compare two records
			largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
		}
		//The largestSoFar is the answer
		return largestSoFar;
	}

	public CSVRecord getLargestOfTwo (CSVRecord currentRow, CSVRecord largestSoFar) {
		//If largestSoFar is nothing
		if (largestSoFar == null) {
			largestSoFar = currentRow;
		}
		//Otherwise
		else {
			double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
			double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
			//Check if currentRow’s temperature > largestSoFar’s
			if (currentTemp > largestTemp) {
				//If so update largestSoFar to currentRow
				largestSoFar = currentRow;
			}
		}
		return largestSoFar;
	}
	
	public CSVRecord coldestInManyDays(){
	   CSVRecord coldestSoFar = null;
	   DirectoryResource dr = new DirectoryResource();
		// iterate over files
		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			// use method to get largest in file.
			CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
			// use method to compare two records
			coldestSoFar = getColdestofTwo(currentRow, coldestSoFar);
		}
		//The largestSoFar is the answer
		return coldestSoFar;
	   
	   
	   }
	
	public CSVRecord getColdestofTwo (CSVRecord currentRow, CSVRecord smallestSoFar) {
	   
	    
	    if (smallestSoFar == null) {
			smallestSoFar = currentRow;
		}
		//Otherwise
		else {
			double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
			double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
			//Check if currentRow’s temperature > largestSoFar’s
			if (currentTemp  < smallestTemp && currentTemp != -9999) {
				//If so update largestSoFar to currentRow
				smallestSoFar = currentRow;
			}
		}
	   
	   return smallestSoFar;
	}
	
	public CSVRecord coldestHourInFile(CSVParser parser){
	   CSVRecord smallestSoFar = null;
	   String fName = null;
		//For each row (currentRow) in the CSV File
		for (CSVRecord currentRow : parser) {
			// use method to compare two records
			smallestSoFar = getColdestofTwo(currentRow, smallestSoFar);
		}
		//The largestSoFar is the answer
		return smallestSoFar;
	}
	
	public String fileWithColdestTemperature(){
	   CSVRecord smallestSoFar = null;
		DirectoryResource dr = new DirectoryResource();
		String fName = null;
		// iterate over files
		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			// use method to get largest in file.
			CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
			// use method to compare two records
			smallestSoFar = getColdestofTwo(currentRow, smallestSoFar);
			fName = f.getPath();
		}
		//The largestSoFar is the answer
		return fName;
	   
	}
	

    public CSVRecord getHumidOfTwo(CSVRecord currentRow, CSVRecord lowHumid){
    
          if (lowHumid == null) {
			lowHumid = currentRow;
            }
		//Otherwise
		else {
		    if(currentRow.get("Humidity").length() != 3){
			double currentHumid = Integer.parseInt(currentRow.get("Humidity"));
			double smallestHumid = Integer.parseInt(lowHumid.get("Humidity"));
			//Check if currentRow’s temperature > largestSoFar’s
			if (currentHumid  < smallestHumid && currentHumid != -9999) {
				//If so update largestSoFar to currentRow
				lowHumid = currentRow;
			}
		}
		}
		return lowHumid;
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser){
	    CSVRecord lowHumid = null;
		
		for (CSVRecord currentRow : parser) {
		    lowHumid = getHumidOfTwo(currentRow, lowHumid);
			
	   }
	   return lowHumid;
     }
     
     public CSVRecord lowestHumidityInManyFiles() {
		CSVRecord lowHumid = null;
		DirectoryResource dr = new DirectoryResource();
		// iterate over files
		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			// use method to get largest in file.
			CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
			// use method to compare two records
			lowHumid = getHumidOfTwo(currentRow, lowHumid);
		}
		//The largestSoFar is the answer
		return lowHumid;
	}
	
	public double averageTemperatureInFile(CSVParser parser){
	   double avg = 0.0;
	   double sum = 0.0;
	   int count = 0;
	   for (CSVRecord currentRow : parser) {
		  count++;
	      sum = (sum + Double.parseDouble(currentRow.get("TemperatureF")));
	      avg = sum/ count;			
	   }
	   
	   return avg;
	   }
	
	public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
	   double avg = 0;
	   double sum = 0;
	   int count = 0;
	    for (CSVRecord currentRow : parser) {
		  if(Integer.parseInt(currentRow.get("Humidity")) >= value){
	      count++;
	      sum = (sum + Double.parseDouble(currentRow.get("TemperatureF")));
	      avg = sum/ count;		
	      
	   }
	   
	   } 
	   return avg;
     }
	   
	
	public void testAverageTemperatureWithHighHumidityInFile(){
	   FileResource fr = new FileResource();
	   CSVParser parser = fr.getCSVParser();
	   double avg = averageTemperatureWithHighHumidityInFile(parser, 80);
	   if(avg == 0){
	   System.out.println(" No temperatures with that humidity" );
       }
       else
	   System.out.println(" Average temperature in file is " + avg );
	   }   
	   
	   public void testAverageTemperatureInFile(){
	   FileResource fr = new FileResource();
	   CSVParser parser = fr.getCSVParser();
	   double avg = averageTemperatureInFile(parser);
	   System.out.println(" Average temperature in file is " + avg );
	   
	   }
	
	
	
	public void testLowestHumidityInManyFiles(){
	   CSVRecord csv = lowestHumidityInManyFiles();
	   System.out.println("Lowest Humidity was " + csv.get("Humidity") +
				   " at " + csv.get("DateUTC"));
	
	   }
	
	
	public void testLowestHumidityInFile(){
	   FileResource fr = new FileResource();
	   CSVParser parser = fr.getCSVParser();
	   CSVRecord csv = lowestHumidityInFile(parser);
	   System.out.println("Lowest Humidity was " + csv.get("Humidity") +
				   " at " + csv.get("DateUTC"));
	   
	   }
	   
	   public void testFileWithColdestTemperature(){
	   //FileResource fr = new FileResource("nc_weather/2014/weather-2014-01-03.csv");
		String small = fileWithColdestTemperature();
		int x = small.indexOf("weather-201");
		String fname = small.substring(x);
		System.out.println("Coldest day was in file " + fname);
	    FileResource fr = new FileResource(small);
		CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
		System.out.println("Coldest temperature was " + smallest.get("TemperatureF")); 
		System.out.println("All the Temperature on the coldest day were:");
		
		for (CSVRecord currentRow : fr.getCSVParser()) {
            // use method to compare two records
            System.out.println(currentRow.get("DateUTC") + ": " + currentRow.get("TemperatureF") );;
        }
		
	   
	}
	
	public void testColdestHourInFile () {
	   FileResource fr = new FileResource("nc_weather/2014/weather-2014-05-01.csv");
		CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
		System.out.println("Coldest temperature was " + smallest.get("TemperatureF"));
	   
	}
	
	public void testHottestInDay () {
		FileResource fr = new FileResource("data/2015/weather-2015-01-01.csv");
		CSVRecord largest = hottestHourInFile(fr.getCSVParser());
		System.out.println("hottest temperature was " + largest.get("TemperatureF") +
				   " at " + largest.get("TimeEST"));
	}
	
	public void testHottestInManyDays () {
		CSVRecord largest = hottestInManyDays();
		System.out.println("hottest temperature was " + largest.get("TemperatureF") +
				   " at " + largest.get("DateUTC"));
	}

	   public void testColdestdayInManyFile(){
	   CSVRecord coldest = coldestInManyDays();
	   System.out.println("coldest temperature was " + coldest.get("TemperatureF") +
				   " at " + coldest.get("DateUTC"));
	
	  }
	
}
