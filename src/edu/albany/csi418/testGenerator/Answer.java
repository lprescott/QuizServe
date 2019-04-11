package tester;

import java.util.Random;

public class Answer 
{//generates a random number within a range min to max
	private int answer;

	public Answer(int min, int max) 
	{
		Random rand = new Random();
		this.answer = rand.nextInt(max-min+1)+min;
	}
	
	public int getAnswer()
	{
		return this.answer;
	}
}
