//HOME ASSIGNMENT 10
//By Joshua Picchioni
//Student ID: 110035605
//April 9th, 2022

//Import the required libraries
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



//Begin the JPanel class
public class JPannelComponent extends JPanel 
{
   
   //Create the variables for the images
   BufferedImage backgroundImage;
   BufferedImage turtleImage;
   BufferedImage hareImage;
   BufferedImage finishImage;
   BufferedImage turtleWinImage;
   BufferedImage hareWinImage;
   BufferedImage tiedImage;
   BufferedImage xImage;



   //Ints to store the location of the hare and turtle
   int hareX;
   int turtleX;


   //Assorted variables to store differnt aspects globally between functions
   int turtleActive = 0;
   int hareActive = 0;
   int runFirstTime = 1;
   int rabitNumOfSleep = 0;
   int clickedTimes = 0;


   //Sleep points of the hare
   int sleepPoint1 = -100;
   int sleepPoint2 = -100;


   //Sleep duration of the hare
   int sleepDuration = 0;


   //Global hare and turtle speeds
   int hSpeed;
   int tSpeed;



   //Grapics 2D objects for the images
   Graphics2D g2dBackground;
   Graphics2D g2dTurtle;
   Graphics2D g2dHare;
   Graphics2D g2dFinish;
   Graphics2D g2dTurtleWin;
   Graphics2D g2dHareWin;
   Graphics2D g2dTied;
   Graphics2D g2dX1;
   Graphics2D g2dX2;

   //AffineTransform objects (also for the images)
   AffineTransform atBackground;
   AffineTransform atTurtle;
   AffineTransform atHare;
   AffineTransform atFinish;
   AffineTransform turtleWin;
   AffineTransform hareWin;
   AffineTransform atTied;
   AffineTransform atX1;
   AffineTransform atX2;


   TurtleTimer turtleMove;

   HareTimer hareMove;

   SleepHareTimer hareSleepCheck;

   RepaintTimer repaintTheScreen;


   //Import the song file
   Sound music = new Sound("song.wav");

   //Variable to detect when the user has pressed a valid area
   int sucessfulPress = 0;

   int hasPlayed = 0;



   //Constructor, takes in the hare's speed, turtl5es speed and sleep time
   public JPannelComponent(int hareSpeed, int turtleSpeed, int rabitSleep)   {


      //Set the global variables versions to the inputted values
      hSpeed = hareSpeed;
      tSpeed = turtleSpeed;


      //Create new listiner for the mouse
      mouseDrawCheck mouseListen = new mouseDrawCheck();


      //Add the mouse listiners to mouseListen
      addMouseListener(mouseListen);
      addMouseMotionListener(mouseListen);


      //Set the color of the draw JPannel to white
      setBackground(Color.white);


      //Attempt to import all the photos for the program
      try{
         turtleImage = ImageIO.read(getClass().getResource("turtle.png"));
         }catch(IOException e){e.printStackTrace();}
         catch(Exception e){e.printStackTrace();}

      try{
         hareImage = ImageIO.read(getClass().getResource("hare.png"));
         }catch(IOException e){e.printStackTrace();}
         catch(Exception e){e.printStackTrace();}

      try{
         finishImage = ImageIO.read(getClass().getResource("finish.png"));
         }catch(IOException e){e.printStackTrace();}
         catch(Exception e){e.printStackTrace();}

      try{
         turtleWinImage = ImageIO.read(getClass().getResource("TurtleWins.png"));
         }catch(IOException e){e.printStackTrace();}
         catch(Exception e){e.printStackTrace();}

      try{
         hareWinImage = ImageIO.read(getClass().getResource("HareWins.png"));
         }catch(IOException e){e.printStackTrace();}
         catch(Exception e){e.printStackTrace();}

      try{
         tiedImage = ImageIO.read(getClass().getResource("tie.png"));
         }catch(IOException e){e.printStackTrace();}
         catch(Exception e){e.printStackTrace();}

      try{
         backgroundImage = ImageIO.read(getClass().getResource("background.png"));
         }catch(IOException e){e.printStackTrace();}
         catch(Exception e){e.printStackTrace();}

      try{
         xImage = ImageIO.read(getClass().getResource("sleepPoint.png"));
         }catch(IOException e){e.printStackTrace();}
         catch(Exception e){e.printStackTrace();}



         //Check if rabitSleep was set to 0
         if (rabitSleep == 0) {

            hareSelectSleep(hareSpeed, turtleSpeed);


         }


         //Else (Must be 1 or 2)
         else{ 

            startTimers(hareSpeed, turtleSpeed);     
      
         }


     
   }






   //Overtide the paint component
   @Override
   public void paintComponent(Graphics g)
   {

      //Inherit methods from paintCComponent
      super.paintComponent(g); 

      //Initiate the AffineTransform variables
      atBackground = new AffineTransform();
      atTurtle = new AffineTransform();
      atHare = new AffineTransform();
      atFinish = new AffineTransform();
      turtleWin = new AffineTransform();
      hareWin = new AffineTransform();
      atTied = new AffineTransform();
      atX1 = new AffineTransform();
      atX2 = new AffineTransform();



      //Check if the program is running for the first time (timer disabled)
      if (runFirstTime == 1){

         //Set the variables to 0
         hareX = 0;
         turtleX = 0;
         runFirstTime = 0;

         //Add the backround image
         atBackground.translate(20, 0);
         g2dBackground = (Graphics2D) g;
         g2dBackground.drawImage(backgroundImage, atFinish, null);



         //Add the finish line image
         atFinish.translate(700, 0);
         g2dFinish = (Graphics2D) g;
         g2dFinish .drawImage(finishImage, atFinish, null);




         //Add the visual for the first sleep point (only 1 point)
         if(rabitNumOfSleep == 1)   {
            atX1.translate(sleepPoint1, 75);
            g2dX1 = (Graphics2D) g;
            g2dX1 .drawImage(xImage, atX1, null);

         }


         //Add the visual for the first and secound sleep point (two points)
         if(rabitNumOfSleep == 2)   {

            atX1.translate(sleepPoint1, 75);

            g2dX1 = (Graphics2D) g;
            g2dX1 .drawImage(xImage, atX1, null);

            atX2.translate(sleepPoint2, 75);

            g2dX2= (Graphics2D) g;
            g2dX2.drawImage(xImage, atX2, null);



         }


         //Add the hare
         atHare.translate(0, 30);
         g2dHare = (Graphics2D) g;
         g2dHare.drawImage(hareImage, atHare, null);



         //Add the turtle
         atTurtle.translate(0, 400);
         g2dTurtle = (Graphics2D) g;
         g2dTurtle.drawImage(turtleImage, atTurtle, null);


      }



      //Else (not first time running)
      else  { 


         //Check if the turtle has won
         if (turtleX > 700){



            //Check if the hare also won
            if (hareX > 700){



               //Add the tied image
               atTied.translate(0, 0);
               g2dTied = (Graphics2D) g;
               g2dTied.drawImage(tiedImage, atTied, null);


               if (hasPlayed == 0){

                  hasPlayed = 1;
                  //Play and loop the music
                  music.play();
                  music.loop();
               }

   
            }


            //Else (only turtle won)
            else  {
   


               //Add the turtle won image
               turtleWin.translate(0, 0);
               g2dTurtleWin = (Graphics2D) g;
               g2dTurtleWin.drawImage(turtleWinImage, turtleWin, null);





               if (hasPlayed == 0){

                  hasPlayed = 1;
                  //Play and loop the music
                  music.play();
                  music.loop();
               }
   
   
            }
   
   
         }
   

         //Else if (check if hare won)
         else if (hareX > 700){

            //Check if turtle lost
            if (turtleX < 700){
   
   
               //Add the hare won image
               hareWin.translate(0, 0);
               g2dHareWin = (Graphics2D) g;
               g2dHareWin.drawImage(hareWinImage, hareWin, null);




               if (hasPlayed == 0){

                  hasPlayed = 1;
                  //Play and loop the music
                  music.play();
                  music.loop();
               }
   
   
            }
   
         }



         //Else (Race is stil going)
         else  {


            //Add the tree background image
            atBackground.translate(20, 0);
            g2dBackground = (Graphics2D) g;
            g2dBackground.drawImage(backgroundImage, atFinish, null);



            //Add the finish line
            atFinish.translate(700, 0);
            g2dFinish = (Graphics2D) g;
            g2dFinish.drawImage(finishImage, atFinish, null);


            //Add the sleep point (if rabit only sleeps once)
            if(rabitNumOfSleep == 1)   {

               atX1.translate(sleepPoint1, 75);
               g2dX1 = (Graphics2D) g;
               g2dX1 .drawImage(xImage, atX1, null);
   
            }
   

            //Add two sleep points (if rabit sleeps twice)
            if(rabitNumOfSleep == 2)   {
   
               atX1.translate(sleepPoint1, 75);
               g2dX1 = (Graphics2D) g;
               g2dX1 .drawImage(xImage, atX1, null);
   
               atX2.translate(sleepPoint2, 75);
               g2dX2= (Graphics2D) g;
               g2dX2.drawImage(xImage, atX2, null);
   
   
   
            }

      
            //Add the hare image
            atHare.translate(hareX, 30);
            g2dHare = (Graphics2D) g;
            g2dHare.drawImage(hareImage, atHare, null);



            //Add the turtle image
            atTurtle.translate(turtleX, 400);
            g2dTurtle = (Graphics2D) g;
            g2dTurtle.drawImage(turtleImage, atTurtle, null);

         }

      }

      
   } 




   //Create gloabl variable to record the timer
   int timerStart = 0;
    //Begin the mouseDrawCheck class, which monitors mouse activity
    class mouseDrawCheck extends MouseAdapter {


      //Method to see if mouse has been pressed
      public void mousePressed(MouseEvent event) {



         //Record the user's mouce input for sleep pos (if one)
         if((rabitNumOfSleep == 1) && (sleepPoint1 < 0) && (timerStart == 0))   {

            sleepPoint1 = event.getX();

            if (sleepPoint1 > 0 ) {

               timerStart++;

               startTimers(hSpeed, tSpeed); 

            }



         }

         //Record the users mouse positions for sleeps (if two)
         if((rabitNumOfSleep == 2) && (sleepPoint2 < 0) && (timerStart == 0))   {


            if (sleepPoint1 < 0){

               sleepPoint1 = event.getX();

            }


            else{

               sleepPoint2 = event.getX();

            }

            if (sleepPoint1 > 0){

               repaint();

            }
   
   
            if (sleepPoint2 > 0 ) {
   
               timerStart++;
   
   
               startTimers(hSpeed, tSpeed); 

   
            }
   
         }

      }

   }





   

   //Mathod to start the imers
   int sleepOnOrOff = 0;
   long start;
   public void startTimers(int hareSpeed, int turtleSpeed){


      turtleMove = new TurtleTimer();

      hareMove = new HareTimer();

      hareSleepCheck = new SleepHareTimer();

      repaintTheScreen = new RepaintTimer();



      turtleMove.start();
      hareMove.start();
      hareSleepCheck.start();
      repaintTheScreen.start();






   }








   //Method for initalization of the program, gets inputs from the user
   public void hareSelectSleep(int hareSpeed, int turtleSpeed)   {


      //Paint the JPanel
      repaint();

      //Variables used in the method
      String numOfSleep;
      int sleepInt = 0;


      //Get number of sleeps the hare will do
      while ((sleepInt != 1) && (sleepInt != 2) ){
         numOfSleep = JOptionPane.showInputDialog("How many times will the hare sleep? (1 or 2 times) ");
         sleepInt = Integer.parseInt(numOfSleep);
      }

      //Set number of sleeps to sleepInt (global)
      rabitNumOfSleep = sleepInt;


      //Ask how long the hare will sleep for
      if(sleepInt == 1){
         String durOfSleep = JOptionPane.showInputDialog("How long will hare sleep for (in milliseconds)?");
         sleepDuration = Integer.parseInt(durOfSleep);

         JOptionPane.showMessageDialog(null, "Now, please (with the mouse) click where you want the sleep point.");


      }


      //Ask how long the hare will sleep for
      else if(sleepInt == 2){
         String durOfSleep = JOptionPane.showInputDialog("How long will hare sleep for (in milliseconds)? ");
         sleepDuration = Integer.parseInt(durOfSleep);

         JOptionPane.showMessageDialog(null, "Now, please (with the mouse) click where you want the two sleep points.");
      }

      //Else (start the timers)
      else{
         //Begin the timers
         startTimers(hareSpeed, turtleSpeed);
      }

   }












   //Sound class, to play the music
   public class Sound {

      //Audio clip variable
      private Clip clip;

      //Constructor
      public Sound(String fileName) {

         //Try to open a file, with fileName (given by constructor)
          try {
              File music = new File(fileName);
              if (music.exists()) {
                  AudioInputStream sound = AudioSystem.getAudioInputStream(music);
                  clip = AudioSystem.getClip();
                  clip.open(sound);
              }

              //Throw an exception saying the file could not be found
              else {

                  throw new RuntimeException("File was not found.");

              }

          }
          catch (Exception e) {
              e.printStackTrace();
              throw new RuntimeException("An error occoured");
          }

      }

      //Method to play tge music
      public void play(){
          clip.setFramePosition(0); 
          clip.start();
      }

      //Method to loop the music
      public void loop(){
          clip.loop(Clip.LOOP_CONTINUOUSLY);
      }

      //Method to stop the song
      public void stop(){
              clip.stop();
          }
      }



      //Thread for Turtle speed
      public class TurtleTimer extends Thread{


         //Override the conventional run method in thread
         @Override
         public void run(){


               //Repeat until the game has finished
               while(hasPlayed == 0){


                  //Add 10 to the turtle timer
                  turtleX = turtleX + 10;



                  //Make the thread sleep for the user inputted amout of time
                  try {
                     Thread.sleep(tSpeed);
                  } catch (InterruptedException e) {
                     e.printStackTrace();
                  }

               }

            //Print when the turtle thread is finished
            System.out.println("Turtle thread as finished");

            


         }



      }

      public class HareTimer extends Thread{

         //Override the conventional run method in thread
         @Override
         public void run(){


               //Repeat until the game has finished
               while(hasPlayed == 0){


                  //Add 10 to the hare position
                  hareX = hareX + 10;



                  //Make the thread sleep for the user inputted amout of time
                  try {
                     Thread.sleep(hSpeed);
                  } catch (InterruptedException e) {
                     e.printStackTrace();
                  }

               }

            //Print when the hare thread is finished
            System.out.println("Hare thread as finished");

            
         }




      }


      //Thread to continuously check if the hare needs to sleep
      @Deprecated
      public class SleepHareTimer extends Thread{


         //Override the conventional run method in thread
         @Override
         public void run(){

            //Repeat until the game is done
            while(hasPlayed == 0){

               //Check if only one sleep point was used, and if hare is past it
               if (((hareX > sleepPoint1 - 50) && (sleepPoint1 > 0))){

                  //If sleep is not off yet
                  if(sleepOnOrOff == 0){

                     //Stop hare timer, and record system time in milisecounds
                     //Stop the timers
                     hareMove.suspend();
                     sleepOnOrOff = 1;
                     start = System.currentTimeMillis();

                  }

                  //If time passed is more than the sleep duration
                  if (System.currentTimeMillis()-start > sleepDuration){

                     //Remove the sleep point, and start the timer
                     sleepPoint1 = -100;

                     hareMove.resume();
                     sleepOnOrOff = 0;
                  }

               }


               //Check if sleep point two was used, and if hare is past them
               if (((hareX > sleepPoint2- 50)) && (sleepPoint2 > 0)){

                  //If sleep is not off yet
                  if(sleepOnOrOff == 0){

                     //Stop hare timer, and record system time in milisecounds

                     hareMove.suspend();

                     sleepOnOrOff = 1;
                     start = System.currentTimeMillis();

                  }


                  //If time passed is more than the sleep duration
                  if (System.currentTimeMillis()-start > sleepDuration){

                     //Remove the sleep point, and start the timer
                     sleepPoint2 = -100;

                     hareMove.resume();
                     sleepOnOrOff = 0;

                  }

               }

                  try {
                     Thread.sleep(1);
                  } catch (InterruptedException e) {
                     e.printStackTrace();
                  }
         }


         System.out.println("Sleep Hare thread as finished");

            
         }




      }


      //Thread to continuiouslty repaint the screen
      public class RepaintTimer extends Thread{

         @Override
         public void run(){

            while(hasPlayed == 0){


               //Call repaint
               repaint();


               //Sleep for 30 ms
               try {
                  Thread.sleep(30);
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }



            }

            System.out.println("Repaint timer has finished.");

            
         }




      }



   }




