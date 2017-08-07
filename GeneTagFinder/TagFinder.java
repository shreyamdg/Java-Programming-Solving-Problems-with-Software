/**
 * Finds a protein within a strand of DNA represented as a string of c,g,t,a letters.
 * A protein is a part of the DNA strand marked by start and stop codons (DNA triples)
 * that is a multiple of 3 letters long.
 *
 * @ShreyamDuttaGupta
 */
import edu.duke.*;
import java.io.*;

public class TagFinder {
    public String findProtein(String dna) {
        String lowerCaseDna = dna.toLowerCase();
        int start = lowerCaseDna.indexOf("atg");
        if (start == -1) {
            return "";
        }
        int stop = lowerCaseDna.indexOf("tag", start+3);
        if ((stop - start) % 3 == 0) {
            return lowerCaseDna.substring(start, stop+3);
        } 
        stop = lowerCaseDna.indexOf("tga", start+3);
        if((stop-start)%3 == 0){
            return lowerCaseDna.substring(start, stop+3);
          }
        stop = lowerCaseDna.indexOf("taa", start+3);
        if((stop-start)%3 == 0){
            return lowerCaseDna.substring(start, stop+3);
          }
        else {
            return "";
        }
    }
    
    public String findStopCodon(String gene){
        if ((gene.length()%3 == 0)&&(gene.length()!= 0)){
            return gene.substring(gene.length()-3, gene.length());
        } else {
            return "";
        }
    }
    
    public void testing() {
        //String a = "AATGCTAGTTTAAATCTGA";
        //String ap = "ATGCTAGTTTAAATCTGA".toLowerCase();
        //String a = "ataaactatgttttaaatgt";
        //String ap = "atgttttaa";
        String a = "acatgataacctaag";
        String ap = "";
        //String a = "cccatggggtttaaataataataggagagagagagagagttt";
        //String ap = "atggggtttaaataataatag";
        //String a = "atgcctag";
        //String ap = "";
        //String a = "ATGCCCTAG";
        //String ap = "ATGCCCTAG";
        String result = findProtein(a);
        if (ap.equals(result)) {
            System.out.println("success for: " + ap + " length: " + ap.length() + " stopCodon: " + findStopCodon(result));
        }
        else {
            System.out.println("mistake for input: " + a);
            System.out.println("got: " + result);
            System.out.println("not: " + ap);
        }
    }
}
