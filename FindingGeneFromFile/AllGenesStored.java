
/**
 * Write a description of AllGenesStored here.
 * 
 * @ShreyamDuttaGupta
 */
import edu.duke.*;
public class AllGenesStored {
    public int findStopCodon(String dnaStr,
                             int startIndex, 
                             String stopCodon){                                 
            int currIndex = dnaStr.indexOf(stopCodon,startIndex+3);
            while (currIndex != -1 ) {
               int diff = currIndex - startIndex;
               if (diff % 3  == 0) {
                   return currIndex;
               }
               else {
                   currIndex = dnaStr.indexOf(stopCodon, currIndex + 1);
               }
            }
            return -1;
        
    }
    
    private boolean isCorrectGene(int start, int end){
    
        if ((end == -1) || ((end - start)%3!=0)) return false;
        
        return true;
        
    }
    
    
    
    public int findStartIndex(String dna, int start) {
     
        return dna.indexOf("ATG", start);
        
    }
    
    public int findStopIndex(String dna, int start) {
        
        //exclude start codon from search string
        start += 3;
        
        // for each codon type:
        //find stop codon and check if number of elements from start to codon index is multiply of 3
        int tagIndex = dna.indexOf("TAG", start);
        int tagStopIndex = dna.length();
        if  (isCorrectGene(start, tagIndex)) tagStopIndex = tagIndex;
        
        int tgaIndex = dna.indexOf("TGA", start);
        int tgaStopIndex = dna.length();
        if (isCorrectGene(start, tgaIndex)) tgaStopIndex = tgaIndex;
        
        int taaIndex = dna.indexOf("TAA", start);
        int taaStopIndex = dna.length();
        if (isCorrectGene(start, taaIndex)) taaStopIndex = taaIndex;
        
        return Math.min(tagStopIndex, Math.min(tgaStopIndex, taaStopIndex));
    }
    
    
    
    public String findGene(String dna, int where) {
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1) {
            return "";
        }
        int taaIndex = findStopCodon(dna,startIndex,"TAA");
        int tagIndex = findStopCodon(dna,startIndex,"TAG");
        int tgaIndex = findStopCodon(dna,startIndex,"TGA");
        int minIndex = 0;
        if (taaIndex == -1 ||
            (tgaIndex != -1 && tgaIndex < taaIndex)) {
            minIndex = tgaIndex;
        }
        else {
            minIndex = taaIndex;
        }
        if (minIndex == -1 ||
            (tagIndex != -1 && tagIndex < minIndex)) {
            minIndex = tagIndex;
        }
        if (minIndex == -1){
            return "";
        }
        return dna.substring(startIndex,minIndex + 3);
    }
    public StorageResource getAllGenes(String dna) {
      //create an empty StorageResource, call it geneList
      StorageResource geneList = new StorageResource();
      //Set startIndex to 0
      int startIndex = 0;
      //Repeat the following steps
      while ( true ) {
          //Find the next gene after startIndex
          String currentGene = findGene(dna, startIndex);
          //If no gene was found, leave this loop 
          if (currentGene.isEmpty()) {
              break;
          }
          //Add that gene to geneList
          geneList.add(currentGene);
          //Set startIndex to just past the end of the gene
          startIndex = dna.indexOf(currentGene, startIndex) +
                       currentGene.length();
        }
      //Your answer is geneList
      return geneList;
    }
    
    
    public StorageResource storeAll(String dna) {
    
        StorageResource store = new StorageResource();
        final String DNA = dna.toUpperCase();
        // loop whole dna strand
        int start = 0;
        int end = 0;
        while (true) {
            // find start codon
            start = findStartIndex(DNA, start);
            
            // find closest stop codon
            end = findStopIndex(DNA, start);
            
            // break if no start or no end found
             if (start == -1)  break;
            
            // check if not an EOF
            if (end < DNA.length() - 3) {
                    store.add((DNA.substring(start, end+3)));
                    // repeat starting from end of found gene 
                    start = end + 3;
                }
            else {
                // gene not foud: restart search just after previous start codon 
                start += 3;
            }
     
            
            
        }
        return store;
    }
    
    
    public double cgRatio(String dna){
        String dnaUpper = dna.toUpperCase();
        int count =0;
        for(char c : dnaUpper.toCharArray() ){
            if((c == 'G') ||(c == 'C')){
            count++;
        }
        }
        return (double) count/dnaUpper.length();
        
    }
    
    public int countCTG(String dna){
        String DNA = dna.toUpperCase();
        int countCtg = 0;
        int start = 0;
        int ctgIndex = 0;
        
        while(true){
        ctgIndex = DNA.indexOf("CTG", start);
        if(ctgIndex == -1) 
            break;
        countCtg++;
        start= ctgIndex + 3;
       
    }
    return countCtg;
    }
    
    public void processGenes(StorageResource sr){
        
        System.out.println("printing all the Strings in sr that are longer than 60 characters");
        int count = 0;
        for (String gene1 : sr.data()) {
        
            if (gene1.length() > 60) {
                count++;
                System.out.println(gene1); 
            }
            
        }

        //"printing the number of Strings in sr that are longer than 9 characters"
        count = 0;
        for (String gene1 : sr.data()) {
        
            if (gene1.length() > 60) {
                count++;
            }
        }
       System.out.println("Number of gene longer than 60 characters :" + count); 
       
       // Number of genes
       
       count = 0;
        for (String gene5 : sr.data()) {
        
            
                count++;
            
        }
       System.out.println("Number of gene is :" + count);
       
       
       //"printing the Strings in sr whose C-G-ratio is higher than 0.35"
       count = 0;
       for(String gene2 : sr.data()) {
            double cgr = cgRatio(gene2);
            if (cgr > 0.35) {
                count++;
                System.out.println("String, C-G-ratio is higher than 0.35: " + gene2);
            }
        
        }
        
        System.out.println("Genes with CG ratio > 0.35 count: " +count);
        
        //"printing the length of the longest gene in sr"
        int maxLength = 0;
        for (String gene : sr.data()) {
            if (gene.length() > maxLength) {
                maxLength = gene.length();
                }
        }
       System.out.println("The length of the longest gene in sr is: " + maxLength);

    }
    
    public void testProcessGenes(){
    /*StorageResource store = new StorageResource();
    // Test1
    //one DNA string that has some genes longer than 9 characters
    String s = "CATTATGATAAACTGTCTCTATCTAA";
    store.add(s);
    // Test2
    //one DNA string that has no genes longer than 9 characters
    s = "CATTATGATTAA";
    store.add(s);
    // Test3
    //one DNA string that has some genes whose C-G-ratio is higher than 0.35
    s = "ATGCCATAG";
    store.add(s);
    // Test4
    //one DNA string that has some genes whose C-G-ratio is lower than 0.35
    s = "ATGTAG";
    store.add(s);
    
    processGenes(store); */
    
    FileResource dnaFile = new FileResource("brca1line.fa");
    String dna = dnaFile.asString();
    StorageResource store = storeAll(dna);
    processGenes(store);
    
    }
    

    public void testcgRatio(){
        String s = "ATGCCATAG";
        double x = cgRatio (s);
        System.out.println(" CG Ratio of " + s + " is "+ x);
    
    }
    
    public void testCTG(){
        String s = "ATGCCTGCTGCATACTG";
        int x = countCTG (s);
        System.out.println(" CTG Count of " + s + " is "+ x);
    
    }
    
    public void testOn(String dna) {
        System.out.println("Testing getAllGenes on " + dna);
        StorageResource genes = getAllGenes(dna);
        for (String g: genes.data()) {
            System.out.println(g);
        }
    }
    public void test() {
        //      ATGv  TAAv  ATG   v  v  TGA   
        testOn("ATGATCTAATTTATGCTGCAACGGTGAAGA");
        testOn("");
        //      ATGv  v  v  v  TAAv  v  v  ATGTAA
        testOn("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
    }
}
