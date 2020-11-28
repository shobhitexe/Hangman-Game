package com.hangman;

/**
 * Class Player contains game profile information 
 * of a user to display in game area and to store
 * updated stats in database
 */


class Player
{
	/** Variables to store player information and game stats */
	public String name;
	public String username;
	public String password;
	public String gender;
	public int wins;
	public int losses;
	public int best;
	public int correct;
	public int wrong;
	Player(String name,String username,String password,String gender)
	{
		this.name=name;
		this.username=username;
		this.password=password;
		this.gender=gender;
	}
	Player(String username,int wins, int losses,int best, int correct, int wrong)
	{
		this.username=username;
		this.wins=wins;
		this.losses=losses;
		this.best=best;
		this.correct=correct;
		this.wrong=wrong;
	}
}