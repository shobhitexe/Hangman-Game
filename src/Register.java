package com.hangman;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.sql.*;

/**
 * Class Register designs the Registration Page for new users. It obtains
 * user credentials and stores it in a database for future login purposes. 
 */


class Register extends JFrame implements ActionListener
{
	/** Label to display title of the page */
	private JLabel title;
	/** Icon to create image for the page */
	private Icon regicon;
	/** Label to display image for the page */
	private JLabel regimg;
	/** Labels to guide user to enter details */
	private JLabel namelabel;
	private JLabel userlabel;
	private JLabel genderlabel;
	private JLabel pwdlabel;
	private JLabel cpwdlabel;
	/** Text Fields to get name and username from user */
	private JTextField namefield;
	private JTextField usernamefield;
	/** Radio Buttons for user to select gender */
	private JRadioButton male;
	private JRadioButton female;
	/** Button for user to submit form */
	private JButton submit;
	/** Button for user to navigate back to Home Page */
	private JButton back;
	/** Password Fields to get password from user */
	private JPasswordField pwdfield;
	private JPasswordField cpwdfield;
	/** Connector to connect to database */
	private Connector con;
	public Register()
	{
		// Connect to database by creating object of Connector Class
		con=new Connector();
		
		// Container to add GUI elements
		Container container=getContentPane();
		container.setLayout(new GridBagLayout());
		container.setBackground(Color.WHITE);
		GridBagConstraints cns=new GridBagConstraints();
		cns.gridx=0;
		cns.gridy=0;
		cns.ipadx=2;
		cns.ipady=2;
		
		// Panel containing GUI elements in the north region of Frame
		JPanel north =new JPanel();
		north.setLayout(new GridBagLayout());
		north.setBackground(Color.WHITE);
		GridBagConstraints cnsnorth=new GridBagConstraints();
		cnsnorth.gridx=0;
		cnsnorth.gridy=1;
		cnsnorth.ipadx=5;
		cnsnorth.ipady=5;
		cnsnorth.insets=new Insets(1,1,1,1);
		
		// Creating GUI elements for the north region
		title=new JLabel("REGISTRATION FORM");
		title.setFont(new Font("Verdana",Font.BOLD,40));
		north.add(title,cnsnorth);
		cnsnorth.ipadx=0;
		cnsnorth.ipady=0;
		regicon=new ImageIcon("Images/form.png");
		regimg=new JLabel(regicon);
		cnsnorth.gridy=2;
		north.add(regimg,cnsnorth);
		// Add panel to Container
		container.add(north,cns);
		
		
		// Panel containing GUI elements in the center region of Frame
		JPanel center=new JPanel();
		center.setLayout(new GridBagLayout());
		center.setBackground(Color.WHITE);
		GridBagConstraints cnscen=new GridBagConstraints();
		cnscen.gridx=0;
		cnscen.gridy=0;
		cnscen.ipadx=5;
		cnscen.ipady=5;
		cnscen.insets=new Insets(2,2,2,2);
		
		// Creating GUI elements for the center region
		namelabel=new JLabel("Name :");
	    namefield=new JTextField(30);
		center.add(namelabel,cnscen);
		cnscen.gridx=1;
		center.add(namefield,cnscen);
		userlabel=new JLabel("Username :");
	    usernamefield=new JTextField(30);
		cnscen.gridx=0;
		cnscen.gridy=1;
		center.add(userlabel,cnscen);
		cnscen.gridx=1;
		center.add(usernamefield,cnscen);
		cnscen.gridx=0;
		cnscen.gridy=3;
	    genderlabel=new JLabel("Gender :");
		center.add(genderlabel,cnscen);
		ButtonGroup grp=new ButtonGroup();
		male=new JRadioButton("Male");
		male.setSelected(true);
		female=new JRadioButton("Female");
		male.setBackground(Color.WHITE);
		female.setBackground(Color.WHITE);
		grp.add(male);
		grp.add(female);
		JPanel genderpanel=new JPanel();
		genderpanel.setLayout(new GridLayout(1,2));
		genderpanel.setBackground(Color.WHITE);
		genderpanel.add(male);
		genderpanel.add(female);
		cnscen.gridx=1;
		center.add(genderpanel,cnscen);
		cnscen.gridx=0;
		cnscen.gridy=4;
		pwdlabel=new JLabel("Password :");
		center.add(pwdlabel,cnscen);
		cnscen.gridx=1;
		pwdfield=new JPasswordField(30);
		center.add(pwdfield,cnscen);
		cnscen.gridx=0;
		cnscen.gridy=5;
		cpwdlabel=new JLabel("Confirm Password :");
		center.add(cpwdlabel,cnscen);
		cnscen.gridx=1;
		cpwdfield=new JPasswordField(30);
		center.add(cpwdfield,cnscen);
		cns.gridx=0;
		cns.gridy=2;
		cns.ipadx=2;
		cns.ipady=2;
		// Add panel to Container
		container.add(center,cns); 
		
		// Panel containing GUI elements in the south region of Frame
		JPanel south =new JPanel();
		south.setLayout(new GridBagLayout());
		south.setBackground(Color.WHITE);
		GridBagConstraints cnssouth=new GridBagConstraints();
		cnssouth.insets=new Insets(4,4,4,4);
		cnssouth.gridx=0;
		cnssouth.gridy=0;
		cnssouth.ipadx=0;
		cnssouth.ipady=0;
		
		// Creating GUI elements for the southern region
		submit=new JButton("Submit");
		submit.setBackground(Color.WHITE);
		submit.addActionListener(this);
		back=new JButton("Home");
		back.setBackground(Color.WHITE);
		back.addActionListener(this);
		south.add(submit,cnssouth);
		cnssouth.gridx=1;
		south.add(back,cnssouth);
		cns.gridx=0;
		cns.gridy=4;
		cns.ipadx=2;
		cns.ipady=2;
		// Add panel to Container
		container.add(south,cns); 
		
		// Initialize the GUI JFrame
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setTitle("REGISTRATION PAGE");
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
		if (src==back)
		{
			// Redirect user to home page
			this.setVisible(false);
			new Home();
		}
		if (src==submit)
		{
			try
			{
				// Check if user entered valid credentials
				if (isValidCredentials())
				{
					// Display Success message and ask user to login or return to Home Page
					Object[] options={"YES","NO"};
					int choice=JOptionPane.showOptionDialog(null,"Proceed to login?","Registration Successful !!",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,JOptionPane.YES_OPTION);
					if (choice==JOptionPane.YES_OPTION)
					{
						// User chose to login so redirect to Login Page
						this.setVisible(false);
						new Login();			
					}
					else
					{
						// User chose to return to Home so redirect to Home Page
						this.setVisible(false);
						new Home();
					}
				}
			}
			catch (SQLException e)
			{
				// Could not connect to database so display server down message
				JOptionPane.showMessageDialog(null,"Please try again later..","SERVER DOWN",JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}	
		}
	}
	
	/**
	 * Checks if user entered valid credentials for 
	 * successful registration
	 */
	public boolean isValidCredentials() throws SQLException
	{
		// Get data entered by user
		String gender="Male";
		if (male.isSelected())
		{
			gender="Male";
		}
		else
		{
			gender="Female";
		}
		String name=namefield.getText();
		String username=usernamefield.getText();
		String pwd=new String(pwdfield.getPassword());
		String cpwd=new String(cpwdfield.getPassword());
		// Check if name is valid
		if (!isNameValid(name))
		{
			// Display error message and show requirements
			JOptionPane.showMessageDialog(null,"<html><ul>" + "<li>Name is a required field</li>" +  "<li>Name should not begin with space</li>" + "<li>Name should not contain any digits</li>" + "</ul></html>","INVALID NAME !!",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Check if username is valid
		else if (!isUsernameValid(username))
		{
			// Display error message and show requirements
			JOptionPane.showMessageDialog(null,"<html><ul>" + "<li>Username is a required field</li>" +  "<li>Username should contain atleast 5 characters</li>" + "<li>Username should not contain any spaces</li>" + "</ul></html>","INVALID USERNAME !!",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Check if username already taken
		else if (isUsernameExist(username))
		{
			// Display error message as username already exists
			JOptionPane.showMessageDialog(null,"Username already exists..","INVALID USERNAME !!",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Check if password is valid
		else if (!isPasswordValid(pwd))
		{
			// Display error message and show requirements
			JOptionPane.showMessageDialog(null,"<html><ul>" + "<li>Password is a required field</li>" +  "<li>Password should contain atleast 6 characters</li>" + "<li>Password should not contain any spaces</li>" + "</ul></html>","INVALID PASSWORD !!",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Check if passwords match
		else if (!isPasswordMatching(pwd,cpwd))
		{
			// Display error message as passwords do not match
			JOptionPane.showMessageDialog(null,"Passwords entered do not match..","INCORRECT PASSWORD !!",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Credentials are valid at this point so create a new player for game
		Player newplayer=new Player(name,username,pwd,gender);
		// Store user credentials in database
		con.registerUser(newplayer);
		return true;
	}	
	
	/**
	 * Checks if user entered valid name 
	 */
	public boolean isNameValid(String name)
	{
		if (name.length()==0)
		{
			return false;
		}
		if (name.charAt(0)==' ')
		{
			return false;
		}
		for (int i=0;i<name.length();i++)
		{
			if (Character.isDigit(name.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if user entered valid password 
	 */
	public boolean isPasswordValid(String pwd)
	{
		if (pwd.length()<6)
		{
			return false;
		}
		for (int i=0;i<pwd.length();i++)
		{
			if (pwd.charAt(i)==' ')
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if user entered valid username 
	 */
	public boolean isUsernameValid(String username)
	{
		if (username.length()<5)
		{
			return false;
		}
		for (int i=0;i<username.length();i++)
		{
			if (username.charAt(i)==' ')
			{
				return false;
			}
		}
		return true;
	}	
	
	/**
	 * Checks if username exists in database 
	 */
	public boolean isUsernameExist(String username) throws SQLException
	{
		return con.searchUsername(username);
	}
	
	/**
	 * Checks if passwords entered match 
	 */	
	public boolean isPasswordMatching(String pwd,String cpwd)
	{
		if (pwd.equals(cpwd))
		{
			return true;
		}
		return false;		
	}	
}




