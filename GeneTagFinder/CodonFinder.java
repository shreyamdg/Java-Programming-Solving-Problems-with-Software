
/**
 * This program finds the codons, number of DNA strands and CG Ratio above 0.35
 * 
 * @ShreyamDuttaGupta
 *
 */
import edu.duke.*;

public class CodonFinder {
    
    private int countCodons(String dnaIn, String codonIn){
        String dna = dnaIn.toLowerCase();
        int count = 0;
        int lastIndex = 0;
        String findStr = codonIn.toLowerCase();
        while ((lastIndex = dna.indexOf(findStr, lastIndex)) != -1) {
            count++;
            lastIndex += findStr.length();
        }
        
        return count;
    }
    
    private float cgRatio(String dnaIn){
        String dna = dnaIn.toLowerCase();
        int count = 0;
        int lastIndex = 0;
        String findStr = "c";
        while ((lastIndex = dna.indexOf(findStr, lastIndex)) != -1) {
            count++;
            lastIndex += findStr.length();
        }
        // save c count
        float cCount = count;
        //reset count and index
        lastIndex = 0;
        count = 0;
        findStr = "g";
        while ((lastIndex = dna.indexOf(findStr, lastIndex)) != -1) {
            count++;
            lastIndex += findStr.length();
        }
        float gCount = count;
        
        
        return (cCount/gCount);
        
    }
    
    private int findStartIndex(String dna,int index){
        //standardize dna to lower case
        //dna.toLowerCase();
        int start = dna.toLowerCase().indexOf("atg", index);
        return start;
    }
    
    private int findStopIndex(String dnaIn, int index){
        //first standardize DNA to lowercase
        String dna = dnaIn.toLowerCase();
        int stopTga = dna.indexOf("tga", index);
        if(stopTga == -1 || (stopTga-index)%3 != 0){
            stopTga = dna.length();
        }
        int stopTag = dna.indexOf("tag", index);
        if(stopTag == -1 || (stopTag-index)%3 != 0){
            stopTag = dna.length();
        }
        int stopTaa = dna.indexOf("taa", index);
        if(stopTaa == -1 || (stopTaa-index)%3 != 0){
            stopTaa = dna.length();
        }
        return Math.min(Math.min(stopTag, stopTga), stopTaa);
    }
    
    private void printGenes(StorageResource store){
        StorageResource longDna = new StorageResource();
        StorageResource cgDna = new StorageResource();
        int maxLengthOfGene = 0;
        
        for (String dna: store.data()){
            if (dna.length()>60){
                longDna.add(dna);
            }
            if(cgRatio(dna)>0.35){
                cgDna.add(dna);
            }
            if(dna.length() > maxLengthOfGene){
                maxLengthOfGene = dna.length();
            }
            
        }
        System.out.println("The following " +longDna.size()+ "genes have more than 60 nucliotides:");
        //for (String dna: longDna.data()){
        //    System.out.println(dna);
        //}
        System.out.println("The following " +cgDna.size()+ "genes have a C/G Ratio of higher than 0,35:");
        //for (String dna: cgDna.data()){
        //    System.out.println(dna);
        //}
        System.out.println("The longest gene has the length: " +maxLengthOfGene);
    }
    
    private StorageResource storeAll(String dna){
        int index = 0;
        StorageResource store = new StorageResource();
        while (true){
            int startCodonIndex = findStartIndex(dna,index);
            if (startCodonIndex == -1){
                break;
            }
            int endCodonIndex = findStopIndex(dna, startCodonIndex+3);
            if ((((endCodonIndex+3)-startCodonIndex) % 3 == 0 )&&endCodonIndex != dna.length()){
                if(endCodonIndex == dna.length()){
                    store.add(dna.substring(startCodonIndex, endCodonIndex));
                    index = endCodonIndex;
                }else{
                    store.add(dna.substring(startCodonIndex, endCodonIndex+3));
                    index = endCodonIndex+3;
                }
            } else{
                index = startCodonIndex+3;
            }
            
        }
        return store;
    }
    
    private void printAll(String dna){
        int index = 0;
        while (true){
            int startCodonIndex = findStartIndex(dna,index);
            if (startCodonIndex == -1){
                break;
            }
            int endCodonIndex = findStopIndex(dna, startCodonIndex+3);
            if (((endCodonIndex+3)-startCodonIndex) % 3 == 0){
                System.out.println(dna.substring(startCodonIndex, endCodonIndex+3));
                index = endCodonIndex+3;
            }else{ 
                index = startCodonIndex+3; 
            }
        }
        
    }
    
    public void testFinder(){
    //String dna = "ATGTGCAACCCGGGTTTAAAATAAGTTCCCAAATTTTTAA";
    //String dna = "ATGAAATGAAAA";
    //String dna = "ccatgccctaataaatgtctgtaatgtaga";
    String dna = "CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA";
    System.out.println("DNA string is:");
    System.out.println(dna);
    System.out.println("Gene found is: ");
    //printAll(dna);
    StorageResource store = storeAll(dna);
    for (String gene: store.data()){
        System.out.println(gene);
    }
    }
    
    public void testStorageFinder(){
    FileResource dnaFile = new FileResource("brca1line.fa");
    //FileResource dnaFile = new FileResource("GRch38dnapart.fa");
    String dna = dnaFile.asString();
    //String dna = "CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA";
    StorageResource store = storeAll(dna);
    System.out.println("Ther are " +store.size()+ " potential genes in the data.");
    printGenes(store);
    System.out.println("The codon CTG appear in a strand of DNA the following times: " + countCodons(dna, "CTG")); 
    }
    
    
    
    public void testCGRatio(){
    String dna = "ATGAACACCTGA";
    System.out.println("DNA string is:");
    System.out.println(dna);
    float result = cgRatio(dna);
    System.out.println("The C to G Nocliotide Ratio is: " +result);
    }
}
