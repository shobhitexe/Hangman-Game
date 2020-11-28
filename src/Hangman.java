package com.hangman;

import javax.swing.*;
import java.sql.*;
import java.util.Random;



/**
 * Class Hangman creates a new Hangman game by initializing Game Stats 
 * for player and randomly choosing a word from db for user to guess
 */


class Hangman
{
	// String containing word user has to guess
	public final String WORD;
	// String containing clue to guess the word
	public final String CLUE;
	// variables to store current game stats
	public int ATTEMPTS_LEFT;
	public int CORRECT_GUESS;
	public int INCORRECT_GUESS;
	// variable to check if user has won
	public boolean won;
	// char array to keep track of correctly guessed words and hide rest
	public char[] hidden;
	// variable to check whether letter guessed by user is found in the word
	private boolean isFound;
	public String[] wordclue;
	Hangman() 
	{
		// Initialize game stats
		won=false;
		this.ATTEMPTS_LEFT=4;
		this.CORRECT_GUESS=0;
		this.INCORRECT_GUESS=0;
		try
		{
			// get word and clue from db for user to guess
			wordclue=getWordAndClue();
		}
		catch (SQLException e)
		{
			// display error
			JOptionPane.showMessageDialog(null,"Please try again later..","SERVER DOWN",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		WORD=wordclue[0];
		CLUE=wordclue[1];
		hidden=new char[WORD.length()];
		// initializing hidden word 
		for (int i=0;i<WORD.length();i++)
		{
			hidden[i]='_';
		}
	}
	
	/**
	 * Establishes connection with database to get
	 * word and clue for user to guess
	 */
	public String[] getWordAndClue() throws SQLException
	{
		Connector con=new Connector();
		String[] word_clue=con.fetchWordAndClue();
		return word_clue;
	}
	
	/**
	 * Searches for letter guessed by user in the word
	 */
	public void search(char letter)
	{
		isFound=false;
		boolean checkwin=true;
		for (int i=0;i<WORD.length();i++)
		{
			if (letter==WORD.charAt(i))
			{
				hidden[i]=WORD.charAt(i);
				isFound=true;
			}
		}
		for (int i=0;i<WORD.length();i++)
		{
			if (hidden[i]!=WORD.charAt(i))
			{
				checkwin=false;
				break;
			}
		}
		if (checkwin)
		{
			won=true;
		}
	}
	
	/**
	 * Checks if letter guessed by user is present in word
	 */
	public boolean isCorrect()
	{
		return isFound;
	}
	
}

