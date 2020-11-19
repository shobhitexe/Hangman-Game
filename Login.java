import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Class Login designs the Login Page for users. 
 * It checks user credentials with registered data 
 * and redirects user to game area if login successful. 
 */


class Login extends JFrame implements ActionListener
{
	/** Label to display title of the page */
	private JLabel title;
	/** Icon to create image for the page */
	private Icon logicon;
	/** Label to display image for the page */
	private JLabel logimg;
	/** Labels to guide user to enter details */
	private JLabel userlabel;
	private JLabel pwdlabel;
	/** Button for user to submit form */
	private JButton login;
	/** Button for user to navigate back to Home Page */
	private JButton back;
	/** Text Field to check username entered by user */
	private JTextField usernamefield;
	/** Password Field to check password entered by user */
	private JPasswordField pwdfield;
	/** Connector to connect to database */
	private Connector con;
	public Login()
	{
		// establish db connection
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
		
		// Panel containing GUI elements in north region
		JPanel north =new JPanel();
		north.setLayout(new GridBagLayout());
		north.setBackground(Color.WHITE);
		GridBagConstraints cnsnorth=new GridBagConstraints();
		cnsnorth.gridx=0;
		cnsnorth.gridy=1;
		cnsnorth.ipadx=5;
		cnsnorth.ipady=5;
		cnsnorth.insets=new Insets(1,1,1,1);
		
		// Create GUI elements for north region
		title=new JLabel("LOGIN FORM");
		title.setFont(new Font("Verdana",Font.BOLD,40));
		north.add(title,cnsnorth);
		cnsnorth.ipadx=0;
		cnsnorth.ipady=0;
		logicon=new ImageIcon("Images/form.png");
		logimg=new JLabel(logicon);
		cnsnorth.gridy=2;
		north.add(logimg,cnsnorth);
		// Add panel to container
		container.add(north,cns);
		
		// Panel containing GUI elements in center region
		JPanel center=new JPanel();
		center.setLayout(new GridBagLayout());
		center.setBackground(Color.WHITE);
		GridBagConstraints cnscen=new GridBagConstraints();
		cnscen.gridx=0;
		cnscen.gridy=0;
		cnscen.ipadx=5;
		cnscen.ipady=5;
		cnscen.insets=new Insets(2,2,2,2);
		
		// Create GUI elements for center region
		userlabel=new JLabel("Username :");
	    usernamefield=new JTextField(30);
		cnscen.gridx=0;
		cnscen.gridy=1;
		center.add(userlabel,cnscen);
		cnscen.gridx=1;
		center.add(usernamefield,cnscen);
		cnscen.gridx=0;
		cnscen.gridy=4;
		pwdlabel=new JLabel("Password :");
		center.add(pwdlabel,cnscen);
		cnscen.gridx=1;
		pwdfield=new JPasswordField(30);
		center.add(pwdfield,cnscen);
		cns.gridx=0;
		cns.gridy=2;
		// Add panel to container
		container.add(center,cns); 
		
		// Panel containing GUI elements in southern region
		JPanel south =new JPanel();
		south.setLayout(new GridBagLayout());
		south.setBackground(Color.WHITE);
		GridBagConstraints cnssouth=new GridBagConstraints();
		cnssouth.insets=new Insets(4,4,4,4);
		cnssouth.gridx=0;
		cnssouth.gridy=0;
		cnssouth.ipadx=0;
		cnssouth.ipady=0;
		
		// Create GUI elements for southern region
		login=new JButton("Login");
		login.setBackground(Color.WHITE);
		login.addActionListener(this);
		back=new JButton("Home");
		back.setBackground(Color.WHITE);
		back.addActionListener(this);
		south.add(login,cnssouth);
		cnssouth.gridx=1;
		south.add(back,cnssouth);
		cns.gridx=0;
		cns.gridy=4;
		// Add panel to container
		container.add(south,cns); 
		
		// Initialize the GUI JFrame
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setTitle("LOGIN PAGE");
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
			// Redirect to home page
			this.setVisible(false);
			new Home();
		}
		if (src==login)
		{
			try
			{
				// Check if username password are correct
				if (isValidCredentials())
				{
					// Login successful so load user profile from db
					Player currentplayer=con.loadUser(usernamefield.getText());
					JOptionPane.showMessageDialog(null,"Redirecting To Game Area..","Login Successful !!",JOptionPane.INFORMATION_MESSAGE);
					this.setVisible(false);
					// Redirect player to game area
					new HangmanGUI(currentplayer);
				}
			}
			catch (SQLException e)
			{
				// display error
				JOptionPane.showMessageDialog(null,"Please try again later..","SERVER DOWN",JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}	
		}
	}
	
	/**
	 * Checks if user entered correct username 
	 * and password
	 */
	public boolean isValidCredentials() throws SQLException
	{
		String username=usernamefield.getText();
		String pwd=new String(pwdfield.getPassword());
		if (!isUsernamePasswordCorrect(username,pwd))
		{
			// login failed so display error
			JOptionPane.showMessageDialog(null,"Please type the correct credentials or Register if you are a new player..","INCORRECT USERNAME OR PASSWORD !!",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}	
	
	/**
	 * Matches user data with data in db
	 */
	public boolean isUsernamePasswordCorrect(String username,String pwd) throws SQLException
	{
		return con.validateUser(username,pwd);
	}
}


