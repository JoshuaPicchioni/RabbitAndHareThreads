//HOME ASSIGNMENT 10
//By Joshua Picchioni
//Student ID: 110035605
//April 9th, 2022



//Imports
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;



//Begin the JFrame component class
public class JFrameComponent extends JFrame
{

   //Delcare variables to be used
   JPanel colorPanel;
   int turtleSpeed;
   int hareSpeed;
   JPannelComponent gamePanel;

   //Begin the constructor
   public JFrameComponent()
   {
      //Create the frame for the program
      JFrame frame = new JFrame("Capture Photo Game");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


      //Create a new JPanel, setting its background color to white
      colorPanel = new JPanel();
      colorPanel.setBackground(Color.WHITE);


      //Prompt the user for the speed of the hare
      String speedInput = JOptionPane.showInputDialog("Please enter the speed of the hare\n(Millisecond delay between each step): ");
      hareSpeed = Integer.parseInt(speedInput);


      //Prompt the user for the speed of the turtle
      speedInput = JOptionPane.showInputDialog("Please enter the speed of the turtle\n(Millisecond delay between each step): ");
      turtleSpeed = Integer.parseInt(speedInput);



      //Ask the user if they want the hare to sleep
      int rabitSleep = JOptionPane.showConfirmDialog(null, "Would you like the hare to sleep? ");


      //Create a new contrianer for the content pane
      Container pane = this.getContentPane();
      pane.add(colorPanel);


      gamePanel = new JPannelComponent(hareSpeed, turtleSpeed, rabitSleep); 

      //Centre the JComboBox
      getContentPane().add(gamePanel, "Center");

      //Exit program upon closing it
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      //Set the size of the window
      setSize(1000,600);

      //Make the window visable
      setVisible(true);


   } 



   
}
