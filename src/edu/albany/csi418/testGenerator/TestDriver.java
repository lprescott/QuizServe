/********************************************************
 * This is a driver class for test creation of the group project 
 * in ICSI 418. This will take a minimum number, a maximum number, 
 * a test id, and number of questions. This will output a list of
 * random questions with answers in random order. 
 */
package edu.albany.csi418.testGenerator;

import java.util.ArrayList;
import java.util.List;

public class TestDriver 
{
	private List<Question> questions = new ArrayList<Question>();
	private int testId;
	private int numQuest;
	private int min, max;
	
	public TestDriver(int min, int max, int testId, int numQuest) 
	{//constructor, member initialization
		this.questions = null;
		this.testId = testId;
		this.numQuest = numQuest;
		this.min = min;
		this.max = max;
	}
	
	private boolean inList(int nuQuest)
	{//checks if new question is in list 
		boolean found = false;
		for(Question q : this.questions)
			found = (nuQuest == q.getQuestion());
		return found;
	}
	
	public void setQuestions()
	{//sets question list with answers and no duplicates
		int i = 1;
		while (i < this.numQuest)
		{
			Question nuQuest = new Question(this.min,this.max);
			if(this.questions.isEmpty())
			{
				nuQuest.setAnswers(4);
				questions.add(nuQuest);
				i++;
			}
			if(!this.inList(nuQuest.getQuestion()))
			{
				nuQuest.setAnswers(4);
				questions.add(nuQuest);
				i++;
			}
		}
	}
	
	public int getTestId()
	{//returns test id
		return this.testId;
	}
}
