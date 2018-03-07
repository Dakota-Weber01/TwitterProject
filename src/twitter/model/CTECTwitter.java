package twitter.model;
import twitter.controller.*;
import twitter.model.*;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.GeoLocation;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;
public class CTECTwitter
{
private TwitterController appController;
private Twitter Twitter;
private List<javax.net.ssl.SSLEngineResult.Status> searchedTweets;
private List<String> tweetedWords;
private long totalWordCount;

private void collectTweets(String username)
{
	searchedTweets.clear();
	tweetedWords.clear();
	
	Paging statusPage = new Paging(1,100);
	int page = 1;
	long lastID = Long.MAX_VALUE;
	
	while(page <= 10)
	{
		statusPage.setPage(page);
		try
		{
			ResponseList<Status> listedTweets = Twitter.getUserTimeline(username, statusPage);
			for(Status current : listedTweets)
			{
				if(current.getID() < lastID)
				{
					searchedTweets.add(Current);
					lastID = current.getID();
				}
			}
		}
		catch(TwitterException searchTweetError)
		{
			appController.handleErrors(searchTweetError);
		}
		page++;
	}
}
public String getMostCommonWord(String username)
{
	String mostCommon = "";
	
	collectTweets(username);
	turnStatusesToWords();
	totalWordCount = tweetedWords.size();
	String [] boring = createIgnoredWordArray();
	
	return mostCommon;
}
private void trimTheBoringWords(String [] boringWords)
{
	for (int index = tweetedWords.size() - 1; index >= 0; index--)
	{
		for(int removeIndex = 0; removeIndex < boringWords.length; removeIndex++)
		{
			if(tweetedWords.get(index).equals(boringWords[removeIndex]))
			{
				tweetedWords.remove(index);
				removeIndex = boringWords.length;
			}

		}
	}
}
private String [] createIgnoredWordArray()
{
	String [] boringWords;
	String fileText = IOController.loadFromFile(appController, "commonWords.txt");
	int wordCount = 0;
	
	Scanner wordScanner = new Scanner(fileText);
	
	while(wordScanner.hasNextLine())
	{
		wordScanner.nextLine();
		wordCount++;
	}
	boringWords = new String [wordCount];
	wordScanner.close();
	
	wordScanner = new Scanner(this.getClass().getResourceAsStream("data/commonWords.txt"));
	for(int indedx = 0; index < boringWords.length; index++)
	{
		boringWords[index] = wordScanner.nextLine();
	}
	wordScanner.close();
	return boringWords;
	
}
}
