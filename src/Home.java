package com.hangman;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * Class Home designs the Home Page containing Login and Registration options for users
 *
 */

public class Home extends JFrame implements ActionListener
{
	/** Label to display game title */
	private JLabel title;
	/** Icon to create cover image */
	private Icon covericon;
	/** Label to display cover image */
	private JLabel coverimg;
	/** Button to redirect user to Login page */
	private JButton login;
	/** Button to redirect user to Registration page */
	private JButton register;
	public Home()
	{
		// Container to add GUI elements
		Container container=getContentPane();
		container.setLayout(null);
		container.setBackground(Color.WHITE);
		
		
		// Creating various GUI elements
		title=new JLabel("HANGMAN");
		title.setFont(new Font("Verdana",Font.BOLD,40));
		title.setBounds(600,0,400,100);
		
		covericon=new ImageIcon("Images/cover.png");
		coverimg=new JLabel(covericon);
		coverimg.setBounds(650,200,covericon.getIconWidth(),covericon.getIconHeight());
		
		login=new JButton("LOGIN");
		login.setBounds(600,600,100,50);
		login.setBackground(Color.WHITE);
		login.addActionListener(this);
		
		
		register=new JButton("REGISTER");
		register.setBounds(750,600,100,50);
		register.setBackground(Color.WHITE);
		register.addActionListener(this);
	
	    // Adding the elements to the Container
		container.add(title);
		container.add(coverimg);
		container.add(login);
		container.add(register);
		
		// Initialize the GUI JFrame
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setTitle("HOME");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Performs actions based on the button clicked by the user 
	 * i.e. source of ActionEvent 
	 * @param event ActionEvent performed by user
	 */
	public void actionPerformed(ActionEvent event)
	{
		// Get the button which was clicked
		Object src=event.getSource();
		// Check to see which button was clicked 
		if (src==register)
		{
			// Redirect user to Registration Page
			this.setVisible(false);
			new Register();
		}
		if (src==login)
		{
			// Redirect user to Login Page
			this.setVisible(false);
			new Login();
		}
	}	
}	





