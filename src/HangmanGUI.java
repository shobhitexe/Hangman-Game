package com.hangman;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;



/**
 * Class HangmanGUI designs GUI for 
 * the game area, displays user profile
 * and keeps track of time taken to guess the word
 */


class HangmanGUI extends JFrame implements ActionListener
{
	/** Buttons for user to select alphabets to guess word */
	private JButton[] btnletters;
	/** Button to quit game */
	private JButton btnquit;
	/** String to display hidden word */
	public String hiddenWord;
	/** Icon to create image for the page */
	private Icon gameicon;
	/** Label to display image for the page */
	private JLabel gameimg;
	/** Labels to display game information and player stats */
	private JLabel time;
	private JLabel currentincorrect;
	private JLabel currentcorrect;
	private JLabel totincorrect;
	private JLabel totcorrect;
	private JLabel hiddenwordlabel;
	private JLabel attemptsleft;
	private JLabel clue;
	private JLabel info;
	private JLabel title;
	private JLabel uname;
	private JLabel totwin;
	private JLabel totloss;
	private JLabel record;
	/** used to Create new Hangman game */
	private Hangman currentgame;
	/** Timer to keep track of time */
	private Timer timer;
	/** Stores player stats of current player */
	private Player currentplayer;
	/** Variable to store time in seconds taken by user to guess the word */
	private int currentgametime;
	/** Connector to connect to database */
	private Connector con;
	public HangmanGUI(Player player) 
	{
		// Initialize game time to 0
		currentgametime=0;
		// establish db connection
		con=new Connector();
		// create new Hangman game instance
		currentgame=new Hangman();
		// assign current player to logged in player
		currentplayer=player;
		// initialize hidden word to dashes
		hiddenWord=updateWord();
		
		// Container to add GUI elements
		Container container=getContentPane();
		container.setLayout(new BorderLayout());
		
		// Panel containing GUI elements in center region
		JPanel center=new JPanel();
		center.setLayout(new GridBagLayout());
		GridBagConstraints cns=new GridBagConstraints();
		cns.gridx=0;
		cns.gridy=0;
		cns.ipadx=2;
		cns.ipady=100;
		cns.insets=new Insets(3,3,3,3);
		
		// Create GUI elements for center region
		gameicon=new ImageIcon("Images/0.png");
		gameimg=new JLabel(gameicon);
		gameimg.setPreferredSize(new Dimension(gameicon.getIconWidth(),gameicon.getIconHeight()));
		center.add(gameimg,cns);
		hiddenwordlabel=new JLabel(hiddenWord);
		hiddenwordlabel.setFont(new Font("Verdana",Font.BOLD,40));
		cns.gridy=2;
		cns.ipady=500;
		center.add(hiddenwordlabel,cns);
		center.setBackground(Color.WHITE);
	
		// Panel containing GUI elements in west region
		JPanel west=new JPanel();
		west.setLayout(new GridLayout(6,1));
		
		// Create GUI elements for western region
		attemptsleft=new JLabel("REMAINING ATTEMPTS : "+ currentgame.ATTEMPTS_LEFT);
		time=new JLabel("TIME TAKEN : ");
		time.setFont(new Font("Verdana",Font.BOLD,20));
		timer=new Timer(1000,new ActionListener()
		{
			// increments currentgametime every 1000ms 
			public void actionPerformed(ActionEvent e)
			{
				currentgametime++;
				// Convert elapsed time to hours, mins, and seconds
				int currentgamehours=currentgametime/3600;
				int currentgamemins=currentgametime/60;
				int currentgamesecs=currentgametime%60;
				time.setText("TIME TAKEN : "+ currentgamehours + ":" + currentgamemins + ":" + currentgamesecs);
			}
		});
		// start game timer
		timer.start();
		info=new JLabel("GAME INFORMATION");
		info.setFont(new Font("Verdana",Font.BOLD,30));
		attemptsleft.setFont(new Font("Verdana",Font.BOLD,20));
		currentcorrect=new JLabel("CORRECT GUESSES : "+ currentgame.CORRECT_GUESS);
		currentcorrect.setFont(new Font("Verdana",Font.BOLD,20));
		currentincorrect=new JLabel("INCORRECT GUESSES : "+ currentgame.INCORRECT_GUESS);
		currentincorrect.setFont(new Font("Verdana",Font.BOLD,20));
		clue=new JLabel("CLUE : " + currentgame.CLUE);
		clue.setFont(new Font("Verdana",Font.BOLD,20));
		west.add(info);
		west.add(attemptsleft);
		west.add(time);
		west.add(currentcorrect);
		west.add(currentincorrect);
		west.add(clue);
		west.setBackground(Color.WHITE);

		// Panel containing GUI elements in southern region
		JPanel south=new JPanel();
		south.setLayout(new GridLayout(3,9));
		
		// Create GUI elements for southern region
		btnletters=new JButton[26];
		btnquit=new JButton("Quit");
		btnquit.addActionListener(this);
		btnquit.setBackground(Color.WHITE);
		int i=0;
		for (char letter='A'; letter<='Z';letter++,i++)
		{
			btnletters[i]=new JButton(""+ letter);
			btnletters[i].setPreferredSize(new Dimension(50,50));
			btnletters[i].setBackground(Color.WHITE);
			btnletters[i].addActionListener(this);
		}
		for (i=0; i<26;i++)
		{
			south.add(btnletters[i]);
		}
		south.add(btnquit);
		south.setBackground(Color.WHITE);
		
		// Panel containing GUI elements in eastern region
		JPanel east=new JPanel();
		east.setLayout(new GridLayout(7,1));
		east.setBackground(Color.WHITE);
		
		// Create GUI elements for eastern region
		title=new JLabel("PLAYER PROFILE");
		east.add(title);
		uname=new JLabel("USERNAME : " + currentplayer.username);
		east.add(uname);
		totwin=new JLabel("TOTAL WINS : " + currentplayer.wins);
		east.add(totwin);
		totloss=new JLabel("TOTAL LOSSES : " + currentplayer.losses);
		east.add(totloss);
		totcorrect=new JLabel("TOTAL CORRECT GUESSES : " + currentplayer.correct);
		east.add(totcorrect);
		totincorrect=new JLabel("TOTAL INCORRECT GUESSES : " + currentplayer.wrong);
		east.add(totincorrect);
		// Check if user has never won a game before
		if (currentplayer.best==0)
		{
			record=new JLabel("BEST TIME : N/A");
		}	
		else
		{
			// display record of user in proper format
			int besthours=currentplayer.best/3600;
			int bestmins=currentplayer.best/60;
			int bestsecs=currentplayer.best%60;
			record=new JLabel("BEST TIME : "+ besthours + ":" + bestmins + ":" + bestsecs);
		}	
		east.add(record); 
		title.setFont(new Font("Verdana",Font.BOLD,30));
		uname.setFont(new Font("Verdana",Font.BOLD,20));
		totwin.setFont(new Font("Verdana",Font.BOLD,20));
		totloss.setFont(new Font("Verdana",Font.BOLD,20));
		totcorrect.setFont(new Font("Verdana",Font.BOLD,20));
		totincorrect.setFont(new Font("Verdana",Font.BOLD,20));
		record.setFont(new Font("Verdana",Font.BOLD,20));
		
		
		// Add all panels to container
		container.add(south,BorderLayout.SOUTH);
		container.add(west,BorderLayout.WEST);
		container.add(center,BorderLayout.CENTER);
		container.add(east,BorderLayout.EAST);
		
		// Initialize the GUI JFrame
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setSize(600,600);
		this.setTitle("HANGMAN");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setVisible(true);
	}
	
	/**
	 * Updates the hidden word as and when user guesses
	 * the correct letter
	 */
	public String updateWord()
	{
		String hiddenword="";
		for (int i=0; i<currentgame.WORD.length();i++)
		{
			hiddenword+=currentgame.hidden[i] + " ";
		}
		return hiddenword;
	}
	
	/**
	 * Checks if user lost the game
	 */
	public boolean hasLost()
	{
		if (currentgame.ATTEMPTS_LEFT==0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if user won the game
	 */
	public boolean hasWon()
	{
		if (currentgame.won)
		{
			return true;
		}
		return false;
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
		for (int i=0; i<26;i++)
		{
			// Check if button clicked by user was a letter
			if (btnletters[i]==src)
			{
				// Check if letter guesses by user is present in the word
				currentgame.search(btnletters[i].getText().charAt(0));
				// Update game stats according to outcome of guess
				if (currentgame.isCorrect())
				{
					// user guessed correctly
					currentgame.CORRECT_GUESS++;
				}
				else
				{
					// user was incorrect
					currentgame.INCORRECT_GUESS++;
					currentgame.ATTEMPTS_LEFT--;
					gameicon=new ImageIcon("Images/"+ (4-currentgame.ATTEMPTS_LEFT) + ".png");
					gameimg.setIcon(gameicon);
				}
				// Update hidden word to show the correctly guessed letters
				hiddenWord=updateWord();
				hiddenwordlabel.setText(hiddenWord);
				// Update current game info
				currentcorrect.setText("Correct Guesses : "+ currentgame.CORRECT_GUESS);
				currentincorrect.setText("Incorrect Guesses : "+ currentgame.INCORRECT_GUESS);
				attemptsleft.setText("Remaining Attempts : "+ currentgame.ATTEMPTS_LEFT);
				// Disable letter button once user guessed a letter
				btnletters[i].setEnabled(false);
				// Check if game ends
				if (hasWon() || hasLost())
				{
					// Stop timer
					timer.stop();
					endGame();
				}
				break;
			}
		}
		// Check if user clicked on quit
		if (btnquit==src)
		{
			// Exit Game
			System.exit(0);
		}
	}
	
	/**
	 * Updates player stats and stores in db
	 * before ending current game
	 */
	public void endGame() 
	{
		Object[] options={"YES","NO"};
		int choice=0;
		// Update player profile
		currentplayer.correct+=currentgame.CORRECT_GUESS;
		currentplayer.wrong+=currentgame.INCORRECT_GUESS;
		if (hasLost())
		{
			currentplayer.losses++;
			choice=JOptionPane.showOptionDialog(null,"Play Again?","GAME OVER, YOU LOSE !!",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,JOptionPane.YES_OPTION);
		}
		if (hasWon())
		{
			currentplayer.wins++;
			// Update record of user if user beats current record or if user wins first game
			if (currentplayer.best==0 || currentgametime<currentplayer.best) 
			{
				currentplayer.best=currentgametime;
			}
			gameicon=new ImageIcon("Images/0.png");
			gameimg.setIcon(gameicon);
			choice=JOptionPane.showOptionDialog(null,"Play Again?","CONGRATULATIONS, YOU WIN !!",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,JOptionPane.YES_OPTION);
		}
		try
		{
			// Update db with updated player stats
			con.updateUserProfile(currentplayer);
		}
        catch (SQLException e)
		{
			// display error
			JOptionPane.showMessageDialog(null,"Failed to save changes..","SERVER DOWN",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}		
		if (choice==JOptionPane.YES_OPTION)
		{
			// User chooses to play again so load new game with same user
			this.setVisible(false);
			new HangmanGUI(currentplayer);			
		}
		else
		{
			// User chooses not to play again so redirect user to home page
			this.setVisible(false);
			new Home();
		}
	}
}



