/* Filename: Picture.java 
 * Created by: Gabriel Beal, cs8afrz
 * Date: 11/19/17
 * 
 Progam Description:
 The first program is the picture.java file. In here all the methods are written that are used in the psa6.java file. 
 The first method that we wrote is the embedDigits2. This method removes the 2 least significant digits from a color
 and replaces them with the 2 most significant digits of another color. The next method does the same thing but with 
 any number 1-8 of digits. The next method called getLeastSignificant2 and it gets the least significant 2 digits from 
 a color. The next method does the same thing except it gets any number of digits from a color. The next method is 
 called hideSecretMessage2Bits and what is does is it hides a low quality image in another image. The next method is 
 called recoverSecretMessage2Bits and it recovers the picture that we hid inside the other image. The next two methods
 do the same thing as the previous two but with N bits hidden. 
 
 The next program is psa6.java. In this file is where all the methods that are written are used on images. The first
 portion of code hides an image into another image using the least two significant digits. Then it recovers the 
 hidden image and shows it. The next potion does the same thing but uses the N methods that we wrote.
 
 
 
 
 
 
 
 */ 


import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * Copyright Georgia Institute of Technology 2004-2005
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Picture(int width, int height)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    
    return output;
    
  }
  
  /*
   * This method  preserve the 2 most significant (i.e. leftmost) bits in each 8-bit number
   * Example: The most significant 2 bits of 250 (11111010) are 11 in binary which is equal to 3 in decimal. 
   */
  public static int mostSignificant2( int num )
  {
    return num >> 6;
  }  
  
  
  /* 
   * This method  preserve the N most significant (i.e. leftmost) bits in each 8-bit number
   * Example: If N=3 ,the most significant 3 bits of 250 (11111010) are 111 in binary which is equal to 7 in decimal. 
   */
  public static int mostSignificantN( int num, int N )
  {
    return num >> (8-N);  
  }
  
  public static int shift2BitsTo8( int num )
  {
    return num << 6;
  }
  
  /* 
   * This method  converts num with N bits into 8 bits
   * Example: If if num is 2 and N is 3, it means num is 010 in binary. This method will convert it into 01000000,
   * which is eual to 64 in decimal.
   */
  public static int shiftNBitsTo8( int num, int N )
  {
    return num << (8-N);  // You must implement this
  }
  
  
  /*
   * This method creates a copy of picture passed in the parameter. It changes the red, blue and green components 
   * of each pixel in copied picture. It uses the method mostSignificant2 to convert the 8-bit values into 2 bit values
   *
   *  Hence if you call this method, it will give you a copy of the picture passed in the parameter. But the copied picture has only 2 bits of 
   * information per color. For example, if blue color value for  any pixel in the picture is 01000000 (64 in decimal), the correspoding value 
   * of red color in copied picuture would be just 00000001 (1 in decinal)
   *
   */
  public static Picture degradeColors2Bits(Picture sourcePicture) {
    Picture picCopy = new Picture( sourcePicture.getWidth(), sourcePicture.getHeight() );
    for ( int x = 0; x < sourcePicture.getWidth(); x++ ) {
      for ( int y = 0; y < sourcePicture.getHeight(); y++ ) {
        Pixel source = sourcePicture.getPixel( x, y );
        Pixel target = picCopy.getPixel( x, y );
        
        int red = source.getRed();
        int green = source.getGreen();
        int blue = source.getBlue();
        
        red = mostSignificant2( red );
        green = mostSignificant2( green );
        blue = mostSignificant2( blue );
        
        red = shift2BitsTo8( red);
        green = shift2BitsTo8( green);
        blue = shift2BitsTo8( blue);
        
        target.setRed( red );
        target.setGreen( green );
        target.setBlue( blue );
      }
    }
    return picCopy;
    
  }  
  
  /*
   This method creates a copy of the picture passed in the parameter. It changes the red, blue and green components 
   of each pixel in copied picture. It uses the method mostSignificantN to convert the 8-bit values into N bit values
   and then shift it back to 8 bit values.
   Hence if you call this method with the Picture as one of the parameters, it will give you a copy of the picture. 
   But the copied picture has only N bits of information per color.
   */
  public static Picture degradeColorsNBits(Picture sourcePicture,int N) {
    Picture picCopy = new Picture( sourcePicture.getWidth(), sourcePicture.getHeight() );
    for ( int x = 0; x < sourcePicture.getWidth(); x++ ) {
      for ( int y = 0; y < sourcePicture.getHeight(); y++ ) {
        Pixel source = sourcePicture.getPixel( x, y );
        Pixel target = picCopy.getPixel( x, y );
        int red = source.getRed();
        int green = source.getGreen();
        int blue = source.getBlue();
        
        red = mostSignificantN( red, N );
        green = mostSignificantN( green, N);
        blue = mostSignificantN( blue, N);
        
        red = shiftNBitsTo8( red, N );
        green = shiftNBitsTo8( green, N);
        blue = shiftNBitsTo8( blue, N);
        
        target.setRed( red );
        target.setGreen( green );
        target.setBlue( blue );
      }
    }
    return picCopy;
  } 
  
  //Method to hide a message into the least significant digits of a context image. In the parameters the context is
  //the picture we're hiding the other photo into. Message is the hidden photo. X and Y are where the hidden photo 
  //starts. Returns the context photo with the hidden message.
  public static Picture hideSecretMessage2Bits(Picture context, Picture message, int x, int y){
    
    Picture canvas = new Picture (context);
    
    for (int conX = x, mesX = 0; conX < message.getWidth (); conX ++, mesX ++){
      //conditional statement to make sure message width never goes outside of context 
      if (mesX < canvas.getWidth ()){
        
        for (int conY = y, mesY = 0; conY < message.getHeight (); conY ++, mesY ++){
          
          //conditional statement to make sure message height never extends farther than context
          if (mesY < canvas.getHeight()){
            
            //creates the pixel objects
            Pixel conPix = canvas.getPixel (conX, conY);
            Pixel mesPix = message.getPixel (mesX, mesY);
            
            //grabs RGB values
            int conR = conPix.getRed ();
            int conG = conPix.getGreen ();
            int conB = conPix.getBlue ();
            
            int mesR = mesPix.getRed ();
            int mesG = mesPix.getGreen ();
            int mesB = mesPix.getBlue ();
            
            //gets the 2 most sig digits from message pixel
            mesR = mostSignificant2 (mesR);
            mesG = mostSignificant2 (mesG);
            mesB = mostSignificant2 (mesB);
            
            //embeds the message digits into the context
            conR = embedDigits2 (conR, mesR);
            conG = embedDigits2 (conG, mesG);
            conB = embedDigits2 (conB, mesB);
            
            //sets the RGB of conPix to new values
            conPix.setRed (conR);
            conPix.setGreen (conG);
            conPix.setBlue (conB);
            
          } 
        }
      }
    }
    return canvas; 
  }
  
  //This method recovers the hidden message. The picWithMessage is the context photo with the hidden message and 
  //X and Y are where the hidden image is going to be recovered from. Returns the hidden message.
  public static Picture recoverSecretMessage2Bits(Picture picWithMessage, int x, int y){
    Picture copyPic = new Picture (picWithMessage);
    
    for (int sourceX = x; sourceX < copyPic.getWidth (); sourceX ++){
      for (int sourceY = y; sourceY < copyPic.getHeight (); sourceY ++){
        
        //create pixel object
        Pixel pixelObj = copyPic.getPixel (sourceX, sourceY);
        
        //get RGB values
        int red = pixelObj.getRed ();
        int green = pixelObj.getGreen ();
        int blue = pixelObj.getBlue ();
        
        //get the 2 least sig digits from each color
        red = getLeastSignificant2 (red);
        green = getLeastSignificant2 (green);
        blue = getLeastSignificant2 (blue);
        
        //converts the 2 bit values to 8 bit
        red = shift2BitsTo8(red);
        green = shift2BitsTo8(green);
        blue = shift2BitsTo8(blue);
        
        //set RGB to new values
        pixelObj.setRed (red);
        pixelObj.setGreen (green);
        pixelObj.setBlue (blue);
        
      } 
    }
    
    return copyPic;
  }
  
  //does the same thing as hideSecretMethod2Bits except it hides N bits
  public static Picture hideSecretMessageNBits(Picture context, Picture message, int N , int x, int y){
    
    Picture canvas = new Picture (context);
    
    for (int conX = x, mesX = 0; conX < message.getWidth (); conX ++, mesX ++){
      //conditional statement to make sure message width never goes outside of context 
      if (mesX < canvas.getWidth ()){
        
        for (int conY = y, mesY = 0; conY < message.getHeight (); conY ++, mesY ++){
          
          //conditional statement to make sure message height never extends farther than context
          if (mesY < canvas.getHeight()){
            
            //creates the pixel objects
            Pixel conPix = canvas.getPixel (conX, conY);
            Pixel mesPix = message.getPixel (mesX, mesY);
            
            //grabs RGB values
            int conR = conPix.getRed ();
            int conG = conPix.getGreen ();
            int conB = conPix.getBlue ();
            
            int mesR = mesPix.getRed ();
            int mesG = mesPix.getGreen ();
            int mesB = mesPix.getBlue ();
            
            //gets the 2 most sig digits from message pixel
            mesR = mostSignificantN (mesR, N);
            mesG = mostSignificantN (mesG, N);
            mesB = mostSignificantN (mesB, N);
            
            //embeds the message digits into the context
            conR = embedDigitsN (conR, mesR, N);
            conG = embedDigitsN (conG, mesG, N);
            conB = embedDigitsN (conB, mesB, N);
            
            //sets the RGB of conPix to new values
            conPix.setRed (conR);
            conPix.setGreen (conG);
            conPix.setBlue (conB);
            
          } 
        }
      }
    }
    return canvas; 
  }
  
  //does the same thing as recoverSecretMessage2Bits except it recovers N bits
  public static Picture recoverSecretMessageNBits(Picture context, int N ,int x, int y){
    
    Picture copyPic = new Picture (context);
    
    for (int sourceX = x; sourceX < copyPic.getWidth (); sourceX ++){
      for (int sourceY = y; sourceY < copyPic.getHeight (); sourceY ++){
        
        //create pixel object
        Pixel pixelObj = copyPic.getPixel (sourceX, sourceY);
        
        //get RGB values
        int red = pixelObj.getRed ();
        int green = pixelObj.getGreen ();
        int blue = pixelObj.getBlue ();
        
        //get the 2 least sig digits from each color
        red = getLeastSignificantN (red, N);
        green = getLeastSignificantN (green, N);
        blue = getLeastSignificantN (blue, N);
        
        //converts the 2 bit values to 8 bit
        red = shiftNBitsTo8(red, N);
        green = shiftNBitsTo8(green, N);
        blue = shiftNBitsTo8(blue, N);
        
        //set RGB to new values
        pixelObj.setRed (red);
        pixelObj.setGreen (green);
        pixelObj.setBlue (blue);
        
      } 
    }
    
    return copyPic;
  }
  
  //Implement this method
  public static int embedDigits2( int contextVal, int messageVal ){
    
    //returns the updated number
    return (contextVal >> 2) << 2 | messageVal;
  }
  
  //Implement this method
  public static int embedDigitsN( int contextVal, int messageVal, int N ){
    
    //returns the updated number
    return (contextVal >> N) << N | messageVal;
  }
  
  //Implement this method
  public static int getLeastSignificant2( int num ){
    
    //returns the 2 least sig digits
    return num % 4;
  }
  
  //Implement this method
  public static int getLeastSignificantN( int num, int N ){
    
    //returns the N least sig digits
    return num % (1 << N);
  }
  
  
  
  
}// this } is the end of class Picture, put all new methods before this

