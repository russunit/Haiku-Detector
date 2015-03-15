//Name: BmiProgram.java
//Author: Malcolm Chisholm
//Prog. Assignment #1
//Date: 1/25/14
//This program will take a user's input height and weight, calculate the BMI,
//and give a status report on your BMI. In order to accept either metric or
//imperial values, I used two JComboBoxes to select the desired units.



import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class HaikuGUI extends JFrame
{
	private JFrame frame = new JFrame ("Haiku Detector");
	
	private JLabel inputL, outputL;

	private JTextField inputTF;
	private JTextArea outputTA;

	private JButton detectB;


	private DetectButtonHandler detectHandler;

	private static final int WIDTH = 500;
	private static final int HEIGHT = 700;

	public boolean kilos=true, meters=true;

	public HaikuGUI()
	{
		//create Labels

		inputL = new JLabel(" Enter full file path:", SwingConstants.LEFT);
		outputL = new JLabel(" Haikus Detected:", SwingConstants.LEFT);


		//create Text Fields / Areas

		inputTF = new JTextField(10);
		outputTA = new JTextArea(10, 10);

		//create evaluation button

		detectB = new JButton("Detect Haikus");
		detectHandler = new DetectButtonHandler();
		detectB.addActionListener(detectHandler);


		//add item listeners

		//weightUnit.addItemListener(this);
		//heightUnit.addItemListener(this);

		//set title, layout, and add items to the pane

		setTitle("Haiku Detector");

		//Container pane = getContentPane();

		//frame.setLayout(new GridLayout(4,3));

		inputL.setSize(120,30);
		inputTF.setSize(300,30);
		outputL.setSize(120,30);
		outputTA.setSize(450,450);
		detectB.setSize(120,30);

		inputL.setLocation(10,15);
		inputTF.setLocation(10,40);
		outputL.setLocation(10, 115);
		outputTA.setLocation(10,140);
		detectB.setLocation(300,40);
		

		inputTF.setEditable(true);
		outputTA.setEditable(false);
		
		outputTA.setSize(450,500);    

		outputTA.setLineWrap(true);
		outputTA.setEditable(false);
		outputTA.setVisible(true);

	    //FRAME
		frame.setSize(800,500);
		frame.setResizable(false);
		frame.setLayout(null);

		//

		//TEXT AREA

		outputTA.setLineWrap(true);
		outputTA.setEditable(false);
		outputTA.setVisible(true);

		JScrollPane scroll = new JScrollPane (outputTA);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		frame.add(scroll);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(inputL);
		frame.add(inputTF);
		frame.add(outputL);
		frame.add(outputTA);
		frame.add(detectB);


		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}



	private class DetectButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String inString = " ";
			inString = inputTF.getText();

			wordList haikuWords = new wordList(inString, true);
			

			outputTA.setText(haikuWords.getHaikus());
		}
	}




	public static void main(String[] args)
	{
		HaikuGUI rectObject = new HaikuGUI();
	}




	
}






