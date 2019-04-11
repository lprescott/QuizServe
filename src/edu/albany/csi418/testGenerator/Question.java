package tester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Question 
{//This class takes in two integers that set a range for random 
 //number generator. Creates a list of answers.
	
	int question;
	List<Answer> answers = new ArrayList<Answer>();

	public Question(int min, int max) 
	{//Constructor, member initialization
		Random randQuestion = new Random();
		this.question = randQuestion.nextInt(max-min)+min;
		this.answers=null;
	}
	
	private boolean inList(int randInt)
	{//checks if random number is in list
		boolean found = false;
		
		for(Answer a: this.answers)
			found = (randInt == a.getAnswer());
		return found;
	}
	
	public void setAnswers(int numberAns)
	{//generates list of answers
		int i=1;
		while (i < numberAns)
		{
			Answer nuAns = new Answer(0,3);
			if(this.answers.isEmpty())
			{
				answers.add(nuAns);
				i++;
			}
			else if (!this.inList(nuAns.getAnswer()))
			{
				answers.add(nuAns);
				i++;
			}
		}
	}
	
	public int getQuestion()
	{
		return this.question;
	}
}
