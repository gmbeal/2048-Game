/* Filename: PSA6.java 
 * Created by: Gabriel Beal, cs8afrz
 * Date: 11/19/17
 */ 
public class PSA6 extends Picture
{ 
//The line below is magic, you don't have to understand it (yet)
  public static void main (String[] args) 
  { 
    
    //testing methods to hide and recover and image after being hidden in the least sig 2 bits of another image
    String contextPic = FileChooser.pickAFile();
    Picture context = new Picture(contextPic);
    context.explore();
    
    String messagePic = FileChooser.pickAFile();
    Picture message = new Picture(messagePic);
    message.show();
    
    Picture myPicWithMessage= Picture.hideSecretMessage2Bits( context, message, 0, 0);
    myPicWithMessage.explore(); 
    myPicWithMessage.write(System.getProperty("user.home")+"/my_picture_with_hidden_msg.bmp");
    
    Picture secretMessage = new Picture (Picture.recoverSecretMessage2Bits (myPicWithMessage, 0, 0));
    secretMessage.show (); 
    secretMessage.write(System.getProperty("user.home")+"/my_hidden_msg.bmp");
    
    
    //Generalized method testing for hiding and recovering a picture
    String contextPicN = FileChooser.pickAFile();
    Picture contextN = new Picture(contextPicN);
    contextN.explore();
    String messagePicN = FileChooser.pickAFile();
    Picture messageN = new Picture(messagePicN);
    messageN.show();
    Picture myPicWithMessageN = Picture.hideSecretMessageNBits( contextN, messageN, 2, 0, 0);
    myPicWithMessageN.explore(); 
    
    Picture secretMessageN = new Picture (Picture.recoverSecretMessageNBits (myPicWithMessageN, 2, 0, 0));
    secretMessageN.explore (); 
    
    //testing to make sure the recovered secret message was correct
    String sourcePic = FileChooser.pickAFile();
    Picture degradeCompare = new Picture(Picture.degradeColorsNBits(new Picture(sourcePic),2));
    degradeCompare.explore();
    
    
  } 
}
