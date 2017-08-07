
/**
 * Finding the links from a webpage which redirects to Youtube
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class LinkFinder {
    public void findLink(String url){
    URLResource htmlPage = new URLResource(url);
    for (String word: htmlPage.words()){
        word.toLowerCase();
        int index = word.indexOf("youtube.com");
        if(index!=-1){
            //find /" to the left
            int start = word.lastIndexOf("\"",index-1);
            //find /" to the right
            int stop = word.indexOf("\"", index+1);
            System.out.println(word.substring(start+1,stop));
        }
    }
    }
    public void test(){
    findLink("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
    }
    
   private StorageResource findUrls(String url){
       StorageResource urlStorage = new StorageResource();
       URLResource htmlPage = new URLResource(url);
       for (String word: htmlPage.words()){
           word.toLowerCase();
           int index = word.indexOf("href=");
           if (index != -1){
               int start = word.indexOf("\"",index);
               int stop = word.indexOf("\"",start+1);
               if (start != -1 && stop != -1){
                   urlStorage.add(word.substring(start+1,stop));
               }
           }
       }
       return urlStorage;
   }
   
   private StorageResource findUrls(){
       StorageResource urlStorage = new StorageResource();
       FileResource htmlPage = new FileResource("index.html");
       //URLResource htmlPage = new URLResource(url);
       for (String word: htmlPage.words()){
           word.toLowerCase();
           int index = word.indexOf("href=");
           if (index != -1){
               int start = word.indexOf("\"",index);
               int stop = word.indexOf("\"",start+1);
               if (start != -1 && stop != -1){
                   urlStorage.add(word.substring(start+1,stop));
               }
           }
       }
       return urlStorage;
   }
   
   private int findSecureLinks(StorageResource urls){
       int count = 0;
       for (String url: urls.data()){
           if (url.startsWith("https")){
               count = count+1;
            }
       }
       return count;
   }
   
   private int printComLinks(StorageResource urls){
       int count = 0;
       for (String url: urls.data()){
           if (url.indexOf(".com")!=-1){
               count = count+1;
            }
       }
       return count;
   }
   
   private int printComEndLinks(StorageResource urls){
       int count = 0;
       for (String url: urls.data()){
           if (url.endsWith(".com") || url.endsWith(".com/")){
               count = count+1;
            }
       }
       return count;
   }
   
   private int printDotCount(StorageResource urls){
       int count = 0;
       for (String url: urls.data()){
           int index = 0;
           while(true){
               index = url.indexOf(".", index);
               if(index == -1){
                   break;
                }
               count = count+1;
               index = index+1;      
           }
       }
       return count;
   }
   
   
   public void testURLWithStorage(){
       //String urlIn = "https://users.cs.duke.edu/~rodger/manylinks.html";
       String urlIn = "http://www.dukelearntoprogram.com/course2/data/newyorktimes.html";
       StorageResource urls = findUrls(urlIn);
       for(String url: urls.data()){
           System.out.println(url);
       }
       System.out.println("Then number of URLs on the Page is: " +urls.size());
       System.out.println("Then number of secure URLs on the Page is: " +findSecureLinks(urls));
       System.out.println("number of links that have “.com” in the link: " +printComLinks(urls));
       System.out.println("number of links that have “.com” at the end of link: " +printComEndLinks(urls));
       System.out.println("number of “.” in all links: " +printDotCount(urls));
      
    } 
}

