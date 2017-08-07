/**
 * Create a Inverted version of an image by subtracting RGB values from 255.
 * 
 * @author ShreyamDuttaGupta
 */
import edu.duke.*;
import java.io.*;

public class ImageInversion {
    //I started with the image I wanted (inImage)
    public ImageResource makeInversion(ImageResource inImage) {
        //I made a blank image of the same size
            ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        //for each pixel in outImage
            for(Pixel pixel: outImage.pixels()){
            //look at the corresponding pixel in inImage
            Pixel inPixel= inImage.getPixel(pixel.getX(), pixel.getY());
            int r = 255 - inPixel.getRed();
            int g = 255 - inPixel.getGreen();
            int b = 255 - inPixel.getBlue();

            pixel.setRed(inPixel.getBlue());

            pixel.setGreen(inPixel.getGreen());

            pixel.setBlue(inPixel.getRed());
    }
            //outImage is your answer
            return outImage;
    }

    public void SelectAndConvert() {
        DirectoryResource dir = new DirectoryResource();
        for(File f : dir.selectedFiles()){
        ImageResource inImage = new ImageResource(f);
        ImageResource gray = makeInversion(inImage);
        String fname = inImage.getFileName();
        String newName = "inverted-" + fname;
        inImage.setFileName(newName);
        gray.draw();
        inImage.save();
        }
        
    }
}
