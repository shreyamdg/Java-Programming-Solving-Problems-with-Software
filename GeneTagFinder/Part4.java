/**
 * Finding a gene with CGRatio above 0.35, with sr longer than 60, and number of DNA strands
 * @ShreyamDuttagupta
 */

import edu.duke.*;
public class Part4 {
public void processGenes(StorageResource sr){
    //print strings longer than 9 characters
    
    int geneCount = 0;
    int geneCountAbove60 = 0;
    int cgRatioCount = 0;
    int geneLength = 0;
    String longestGene = "";
    
    for (String s: sr.data()){
        geneCount++;
       // if (s.length() > 9){
          if (s.length() > 60){ //modified for second test case
            //System.out.println("This string has a length greater than 60: " + s);
            geneCountAbove60 = geneCountAbove60 + 1;           
        }
        
        double out = cgRatio(s);
        
        if (out > 0.35){
           // System.out.println("This string has a C-G-ratio greater than .35: " + s);
            cgRatioCount = cgRatioCount + 1;
        }
        
        if (s.length() > geneLength){
            geneLength = s.length();
            longestGene = s;
        }
    }
    
    System.out.println("The number of strings in sr longer than 60 characters: " + geneCountAbove60);
    System.out.println("The number of strings in sr with C-G-ratio higher than 0.35: " + cgRatioCount);
    System.out.println("Length of longest gene: " + geneLength);
    System.out.println("Longest gene is: " + longestGene);
    System.out.println("Number of genes in the storage list is: " + geneCount);
}

public void testProcessGenes(){
    String dna1 = "ACAAGATGCCCTAAGTCCCCCGGCCTC" +
    "CTGCTGCTGCTGCTCTCCGGGGCCACGGCCACCGCTGCCCTGCCCCTGGAGGGTGGCCCCACCGGCCGAGACAGCGAGCATATGCAGGAAGCGGCAGGAAGGTAGGAAAAGCAGCCTCCTGACTTTCCTCGCTTGGTGGTTTGAGTGGACCTCCCAGGCCATGGCCGGGCCCCTCATAGGAGAGGAAGCTCGGGAGGTGGCCAGGCGGCAGGAAGGCGCACCCCCCCAGCAATCCGCGCGCCGGGACAGAATGCCCTGCAGGAACTTCTTCTGGAAGACCTTCTCCTCCTGCAAATAAAACCTCACCCATGAATGCTCACGCAAGTTTAATTACAGACCTGAA";
    String dna2 = "CATGTAGTAAAATGACCTGATAGATATGCTTGTATGCTATGAAAATTAAGTGAAATGACCCA";//multiple stop codons
    String dna3 = "CATCATT"; //no ATG, TAA
    String dna4 = "ATGCATCCCCCCCCCCCCGGGGGGGGGAATAGAGAA"; //no TAA
    String dna5 = "CATCCCCCCCCCCCCGGGGGGGGGAATAGAGAATAAGGGGGGCCCCCCACCCCTGCCCCC"; //no ATG
    
    System.out.println("Dna 2 is " + dna2);
    StorageResource dnaList = getAllGenes(dna2);
    
    for (String s: dnaList.data()){
        System.out.println("Gene is " + s);
    }
    
    processGenes(dnaList);   
}
public void testProcessGenesFromFile(){
    FileResource fr = new FileResource("GRch38dnapart.fa");
    String dna = fr.asString().toUpperCase();
    
    System.out.println("dna is " + dna);
    StorageResource geneList = getAllGenes(dna);    
    //for (String s: geneList.data()){
     //   System.out.println("Gene is " + s);
   // }
   processGenes(geneList);
}
    
    
public double cgRatio(String dna){
   int startIndex = 0;
   int num = 0;
   int denom = dna.length();
   int currIndex = 0;
   
   while (true){
       int cIndex = dna.indexOf("C", startIndex);
       //System.out.println("cIndex is " + cIndex);
       int gIndex = dna.indexOf("G", startIndex);
       //System.out.println("gIndex is " + gIndex);
       
        if (cIndex == -1){
            currIndex = gIndex;
            //System.out.println("currIndex is " + currIndex);
        }
        else if (gIndex == -1){
            currIndex = cIndex;
           // System.out.println("currIndex is " + currIndex);
        }
        else {
            currIndex = Math.min(cIndex, gIndex);
            //System.out.println("currIndex is " + currIndex);
        }
          
       if (currIndex == -1){
           break;
        }
        else
        {
            num = num + 1;
            startIndex = currIndex + 1;
        }
    }
    return ((double) num)/denom ;  
}
    public int findStopCodon(String dna, int startIndex, String stopCodon){
        int currIndex = dna.indexOf(stopCodon, startIndex+3);
        
        while(currIndex != -1){
            int indexDifferential = currIndex - startIndex;
            if ((indexDifferential) % 3 == 0){                
                return currIndex;               
            }
            else
                currIndex = dna.indexOf(stopCodon, currIndex+1);
        }
        
        return dna.length();     
    }
public String findGene(String dna, int where){
        int startIndex = dna.indexOf("ATG", where);
        //System.out.println("Start Index is: " + startIndex);
        if (startIndex == -1){
            return "";
        }
        int stopIndexTAA = findStopCodon(dna, startIndex, "TAA");
       // System.out.println("stopIndexTAA: " + stopIndexTAA);
        int stopIndexTAG = findStopCodon(dna, startIndex, "TAG");
      // System.out.println("stopIndexTAG: " + stopIndexTAG);
        int stopIndexTGA = findStopCodon(dna, startIndex, "TGA");
      //  System.out.println("stopIndexTGA " + stopIndexTGA);
        
        int minStopIndex1 = Math.min(stopIndexTAA, stopIndexTAG);
        int minStopIndex2 = Math.min(minStopIndex1, stopIndexTGA);
        
        if (minStopIndex2 == dna.length()){
            return "";
        }
        else
        {
        //System.out.println("Gene Stop index is: " + minStopIndex2);
        String gene = dna.substring(startIndex, minStopIndex2+3);
       // System.out.println("Gene is " + gene);
        return gene;   
    }
}
public StorageResource getAllGenes(String dna){
    StorageResource geneList = new StorageResource();
    int startIndex = dna.indexOf("ATG");
    //System.out.println("start Index is " + startIndex);
    
    
    while (startIndex != -1){
        String currGene = findGene(dna,startIndex);
       // System.out.println("Current gene is: " + currGene);
        
        if (currGene.isEmpty()){
            startIndex = dna.indexOf("ATG",startIndex + 3);
           // System.out.println("New Start index is " + startIndex);
        }
        else{
        geneList.add(currGene);
       // System.out.println("Gene added to storage list");
        startIndex = dna.indexOf(currGene, startIndex) + currGene.length();
        //System.out.println("New Start index is " + startIndex);
        
    }
    
  
    }
    return geneList;
}
}