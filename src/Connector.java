package com.hangman;

import java.sql.*;
import java.util.Random;
import javax.swing.*;


/**
 * Class Connector establishes connection with mysql database
 * for login,registration and updating user profile after each game
 */


class Connector extends JFrame
{
	/** Connector to connect to database */
	private Connection con;
	/** Statements to execute queries */
	private Statement st;
	private PreparedStatement pst;
	/** ResultSet for getting database data */
	private ResultSet rs;
	public Connector()
	{
		try
		{
			// establish connection with database
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hangmandb","hangman","hangman");
			st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		}
		catch (SQLException e)
		{
			// display error 
			JOptionPane.showMessageDialog(null,"Please try again later..","SERVER DOWN",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	/**
	 * Gets Word and Clue from db to start new Hangman Game 
	 */
	public String[] fetchWordAndClue() throws SQLException
	{
		rs=st.executeQuery("select * from words");
		rs.last();
		int size=rs.getRow();
		rs.first();
		Random rand=new Random();
		int row=rand.nextInt(size)+1;
		while (rs.getRow()!=row)
		{
			rs.next();
		}
		String WORD=rs.getString("word");
		String CLUE=rs.getString("clue");
		CLUE=CLUE.toUpperCase();
		String[] words={WORD,CLUE};
		return words;
	}
	
	/**
	 * Checks if username already exists 
	 */
	public boolean searchUsername(String username) throws SQLException
	{
		rs=st.executeQuery("select * from user");
		while (rs.next())
		{
			if (username.equals(rs.getString("username")))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if username and password for login matches 
	 * with registered data
	 */
	public boolean validateUser(String username,String pwd) throws SQLException
	{
		rs=st.executeQuery("select * from user");
		while (rs.next())
		{
			if (username.equals(rs.getString("username")))
			{
				if (pwd.equals(rs.getString("password")))
				{
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Loads user game profile upon login
	 */
	public Player loadUser(String username) throws SQLException
	{
		rs=st.executeQuery("select * from user");
		int totwins=0;
		int totlosses=0;
		int best=0;
		int correct=0;
		int wrong=0;
		while (rs.next())
		{
			if (username.equals(rs.getString("username")))
			{
				totwins=rs.getInt("wins");
				totlosses=rs.getInt("losses");
				best=rs.getInt("best");
				correct=rs.getInt("correct");
				wrong=rs.getInt("wrong");
			}
		}
		return new Player(username,totwins,totlosses,best,correct,wrong);
	}
	
	/**
	 * Updates user profile after each game 
	 */
	public void updateUserProfile(Player currentplayer) throws SQLException
	{
		String sql="UPDATE user SET wins=?,losses=?,best=?,correct=?,wrong=? WHERE username= ?";
		pst=con.prepareStatement(sql);
		pst.setInt(1,currentplayer.wins);
		pst.setInt(2,currentplayer.losses);
		pst.setInt(3,currentplayer.best);
		pst.setInt(4,currentplayer.correct);
		pst.setInt(5,currentplayer.wrong);
		pst.setString(6,currentplayer.username);
		pst.executeUpdate();
	}
	
	/**
	 * Register user for future login 
	 * by storing user data in db
	 */
	public void registerUser(Player newplayer) throws SQLException
	{
		String sql="INSERT INTO user (name,username,password,gender,wins,losses,best) VALUES (?,?,?,?,?,?,?)";
		pst=con.prepareStatement(sql);
		pst.setString(1,newplayer.name);
		pst.setString(2,newplayer.username);
		pst.setString(3,newplayer.password);
		pst.setString(4,newplayer.gender);
		pst.setInt(5,newplayer.wins);
		pst.setInt(6,newplayer.losses);
		pst.setInt(7,newplayer.best);
		pst.executeUpdate();
	}
}


