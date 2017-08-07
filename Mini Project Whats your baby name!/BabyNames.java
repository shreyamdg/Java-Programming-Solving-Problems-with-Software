
/**
 * Parsing through the data on baby names from the United States, and will answers questions from pdf.
 * 
 * @shreyam Duttagupta
 *
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyNames {
    public int getRank(int year, String name, String gender){
        String fname = ("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        FileResource fr = new FileResource(fname);
        String searchName = null;
        int searchYear = 0;
        String searchGender = null;
        int rankFM = 0;
        int rankM = 0;
        int ranks = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            searchName = rec.get(0);
            searchGender = rec.get(1);
            
            
            if(searchGender.equals("F")){
                rankFM++;
                 if(searchName.equals(name) && searchGender.equals(gender)){
                     ranks = rankFM;
                     break;
                 }
                 
             } 
             else if (searchGender.equals("M")){
                 rankM++;
                 if(searchName.equals(name) && searchGender.equals(gender)){
                     ranks = rankM;
                     break;
             }
             else ranks = -1;
           }
            
          } 
        return ranks;
    }
    
    public String getName(int year, int rank, String gender){
        //String fname = ("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        FileResource fr = new FileResource();
        String searchName = null;
        String searchGender = null;
        int rankFM = 0;
        int rankM = 0;
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            searchGender = rec.get(1);
            
            if(searchGender.equals("F")){
                rankFM++;
                 if(rankFM == rank && searchGender.equals(gender)){
                     searchName = rec.get(0);
                     break;
                 }
                 
             } 
             else if (searchGender.equals("M")){
                 rankM++;
                 if(rankM == rank && searchGender.equals(gender)){
                     searchName = rec.get(0);
                     break;
             }
             else searchName = "NO NAME";
           }
            
          } 
        return searchName;
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        String fname = ("us_babynames/us_babynames_by_year/yob" + newYear + ".csv");
        FileResource frNew = new FileResource(fname);
        int RankOf = getRank(year, name, gender);
        int rankFM = 0;
        int rankM = 0;
        String searchGender = null;
        String searchName = null;
        for (CSVRecord rec : frNew.getCSVParser(false)) {
            searchGender = rec.get(1);
            
            if(searchGender.equals("F")){
                rankFM++;
                 if(rankFM == RankOf && searchGender.equals(gender)){
                     searchName = rec.get(0);
                     break;
                 }
                 
             } 
             else if (searchGender.equals("M")){
                 rankM++;
                 if(rankM == RankOf && searchGender.equals(gender)){
                     searchName = rec.get(0);
                     break;
             }
             else searchName = "NO NAME";
           }
            
          } 
        System.out.println(name + " born in " + year + " would be " + searchName + " if she was born in " + newYear );
    }
    
    public int yearOfHighestRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        int smallestYear = 0;
        int sendYear = 0;
        int count = 0;
        int smallRank=0;
        int []rank = new int [10000];
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            String fname = f.getName();
            sendYear = Integer.parseInt(fname.substring(3,7));
            rank[count] = getRank(sendYear, name, gender);
            
            if(count == 0){
             smallestYear = sendYear;
             smallRank = rank[0];
             }
             else{
                if((rank[count] < smallRank) && rank[count] != -1){
                    smallRank = rank[count];
                    smallestYear = sendYear;
                 }
             } 
             /*System.out.println("");
             for(int i=0; i<=count; i++){
             System.out.print("Rank is : " + rank[i]);
             }*/
             count++;
        }
        
        int countNegetive = 0;
        for(int i =0; i<count; i++){
            if(rank[i] == -1) countNegetive++;
        }
        if(countNegetive == count) smallestYear = -1;
       
        return smallestYear;
    }
    
    
    public double getAverageRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        int rankOfName=0;
        double count = 0;
        double avg = 0;
        int sendYear = 0;
        double sum = 0;
        for (File f : dr.   selectedFiles()) {
            FileResource fr = new FileResource(f);
            String fname = f.getName();
            sendYear = Integer.parseInt(fname.substring(3,7));
            rankOfName= getRank(sendYear, name, gender);
            System.out.println("rank is " + rankOfName + "Count is " + count + " year is " + sendYear);
            if(rankOfName != -1) {
            sum+= rankOfName;
            
           }
           count ++;
           System.out.println("Sum of rank is " + sum + "Count is " + count);
        }
        
        if(sum == 0) avg = -1;
        else avg = (sum/count);
       System.out.println(avg);
        return avg;
    }
    
    public int totalBirths(String gender, int rank, int year){
        String fname = ("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        FileResource fr = new FileResource(fname);
        int totalM = 0;
        int totalMF = 0;
        String searchGender = null;
        int rankMF =0;
        int rankM = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            searchGender = rec.get(1);
            
            if(searchGender.equals("F")){
                rankMF++;
                totalMF+= Integer.parseInt(rec.get(2));
                 if(rankMF == (rank-1) && searchGender.equals(gender)){
                     break;
                 }
                 
             } 
             else if (searchGender.equals("M")){
                 rankM++;
                 totalM+= Integer.parseInt(rec.get(2));
                 if(rankM == (rank-1) && searchGender.equals(gender)){
                     break;
             }
            // else searchName = "NO NAME";
           }
            
          } 
          
          if(gender.equals("M")) return totalM;
          else return rankMF;
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        String fname = ("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        FileResource fr = new FileResource(fname);
        int RankOf = getRank(year, name, gender);
        System.out.println("RankOf " + RankOf);
        int index=RankOf-1;
        int Sum = 0;
        String sName = null;
        if(index!= 0 && index > 0){
            Sum = totalBirths(gender, RankOf, year);
        }
        else Sum =0;
        return Sum;
    }
    
    public void getRanktest(){ 
        //1st method
        //int rank = getRank(1971, "Frank", "M");
        //System.out.println("Rank is : " + rank);
        // End of 1st method
        
        //2nd method
        //String getNames = getName(1982, 450, "M");
        //System.out.println("Name of this Rank is : " + getNames);
        // End of 2nd method
        
        //3rd method
        /*String name = "Owen";
        int year1 = 1974;
        int year2 = 2014;
        String gender = "M";
        whatIsNameInYear(name, year1, year2, gender); */
        // End of 3rd method
        
        //4th method
        //int highestRank = yearOfHighestRank("Mich", "M");
        //System.out.println("Most popular rank is in Year " + highestRank);
        // End of 4th method
        
        //5th method
        double avgRank = getAverageRank("Mich", "M");
        System.out.println("Avg rank is " + avgRank);
        // End of 5th method
        
        //6th method
        //int birthHigher = getTotalBirthsRankedHigher(1990, "Emily", "F");
        //System.out.println("Total Births Ranked Higher " + birthHigher);
        // End of 6th method
    }

}
