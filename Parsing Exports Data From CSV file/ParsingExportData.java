/**
 * The CSV file exportdata.csv has information on the export products of countries. The methods parse through the CSV FILE.
 * 
 * @author Shreyam Duttagupta 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.math.BigInteger;

public class ParsingExportData {
    public void listExporters(CSVParser parser, String exportOfInterest) {
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Exports" column
            String export = record.get("Exports");
            //Check if it contains exportOfInterest
            if (export.contains(exportOfInterest)) {
                //If so, write down the "Country" from that row
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }

    public String countryInfo(CSVParser parser, String country){
		String lists=null;
        for (CSVRecord record : parser){
		    String c = record.get("Country");
            //Check if it contains exportOfInterest
            if (c.contains(country)) {
                //If so, write down the "Country" from that row
                lists = ((record.get("Country")) + " " + (record.get("Exports")) + " " + (record.get("Value (dollars)")));
            }
        }
        return lists;
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        String lists=null;
        
        for (CSVRecord record : parser){
		    String export = record.get("Exports");
            //Check if it contains exportOfInterest
            if (export.contains(exportItem1) && export.contains(exportItem2)) {
                //If so, write down the "Country" from that row
                System.out.println(record.get("Country"));
            }
        }
    
    
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem){
        int count = 0;
        for (CSVRecord record : parser){
        String export = record.get("Exports");
            //Check if it contains exportOfInterest
            if (export.contains(exportItem)) {
                //If so, write down the "Country" from that row
                count++;
            }
        }
        return count;
    }
    
    public void bigExporters(CSVParser parser, String amount){
        for (CSVRecord record : parser){
        String dollar = record.get("Value (dollars)");
            //Check if it contains exportOfInterest
            if (dollar.length() > amount.length()) {
                //If so, write down the "Country" from that row
                System.out.println(record.get("Country") + " " + record.get("Value (dollars)") );
            }
        }
    
    }
    
    public void tester() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        //String s = countryInfo(parser, "Nauru");
        //System.out.println(s);
        //listExportersTwoProducts(parser, "cotton", "flowers");
        //int numofCountries = numberOfExporters(parser, "cocoa");
        //System.out.println(numofCountries);
        bigExporters(parser, "$999,999,999,999");
    }

}
