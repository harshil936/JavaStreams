/*   Harshil Shah
 * 	 ECS 160
 * 	 HW: 3 
 * 
 */
package edu.ucdavis.ecs160.hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TweetProc {

	
	public static void main(String[] args) {
		String PathName = "/home/harshil/eclipse-workspace/hw3/tweets.csv"; 
		System.out.println(getPerTweeterCount(PathName));
		System.out.println(getPerTaggeeCount(PathName));
		System.out.println(getTweeterURLtweetCount(PathName));
		System.out.println(getTweeterWordCount(PathName, "on"));
		System.out.println(getTweeterAverageVerbosity(PathName));
		System.out.println(getTweeterTaggeeCount(PathName));
	}
	

		public static final int tweetpos = 8;
		public static final int textpos = 11;
				
					
/* =====================================================================================================================*
	 * getPerTweeterCount()										Column #: 8												*
	 * 			Returns Map													_____| PART : 1 |____						*
	 * 					Keys: name 
	 * 					Values: Number of tweets
* ======================================================================================================================
*/
				
	//------------------------------------- Helper Function for getPerTweeterCount(). ----------------------------------
	public static Function<String,String> getContent1 = 
				(line)->{
					if(line == null || line.trim().equals(""));
					String[] items = line.split(",");
					return(items[tweetpos]);
					};

	static Map <String, Long> getPerTweeterCount(String PathName){
		
		Map<String, Long> partOne = null; 
		try {
			File fl = new File(PathName); //declaring the file 
			InputStream inputFS = new FileInputStream(fl); // inputing streams to the file
		    BufferedReader br = new BufferedReader(new InputStreamReader(inputFS)); // setting up a buffer that will run the stream on the file.
		    partOne = br.lines().skip(1).collect(Collectors.groupingBy(getContent1, Collectors.counting()));
		    br.close();
		}
		catch (IOException e) {
			System.out.println("no such file");
		};
		return partOne;
	}
	
/* ======================================================================================================================
	 * getPerTaggeeCount()										Column #: 11
	 * 			Returns Map													_____| PART : 2 |____
	 * 					Keys:  Distinct Taggees
	 * 					Values: Number of tweets the taggees appear in
* =======================================================================================================================
*/
	
	//------------------------------------- Helper Function for getPerTaggeeCount(). ------------------------------------
	
	public static String tagged1(String tw)	{
		String patternStr1 = "@[A-Za-z0-9-_]+";			 /* FOR CHECKING: If its any taggee that starts with @ and followed by any character and not a space  */	
		String patternStr2 = "@ ";						 /* FOR CHECKING: If its actually a taggee and not just a character "@" in the text */
		String[] cols = tw.split(",");
		Pattern pattern1 = Pattern.compile(patternStr1);
		Pattern pattern2 = Pattern.compile(patternStr2);
		Matcher m1 = pattern1.matcher(cols[textpos]);
		Matcher m2 = pattern2.matcher(cols[textpos]);
		if (m1.find() && !m2.find())
		     return m1.group(0);
		else return "@NOTAG";
		}
	
	
	static Map <String, Long> getPerTaggeeCount(String FullPathname){
		Map<String, Long> partTwo = null; 
		try {
			File fl = new File(FullPathname);
			InputStream inputFS = new FileInputStream(fl);
		    BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
		    partTwo = br.lines().skip(1).   
    		  		collect(Collectors.groupingBy(TweetProc::tagged1, Collectors.counting()));
		    br.close();
		}
		catch (IOException e) {
			System.out.println("no such file");
		};
		return partTwo;
	}
	
	
/* =====================================================================================================================
	 * getTweeterURLtweetCount()										Column #: 8 & 11
	 * 			Returns Map																_____| PART : 3 |____
	 * 					Keys:  Tweeters
	 * 					Values:  the number of their tweets that contain any URL in the “text” column
* ======================================================================================================================
*/

	//------------------------------------- Helper Function for getTweeterURLtweetCount(). -------------
	public static String tagged2(String tw) {
		String patternStr1 = "http://[A-Za-z0-9-_]+";
		String patternStr2 = "http:// ";                /* FOR CHECKING: If the link is broken or not. */
		String[] cols = tw.split(",");
		Pattern pattern1 = Pattern.compile(patternStr1);
		Pattern pattern2 = Pattern.compile(patternStr2);
		Matcher m1 = pattern1.matcher(cols[textpos]);
		Matcher m2 = pattern2.matcher(cols[textpos]);
		if (m1.find() && !m2.find())
		     return cols[tweetpos];
		else return "Number of Tweets without URL";
		}		
				
	static Map <String, Long> getTweeterURLtweetCount(String FullPathname){
		
		Map<String, Long> partThree = null; 
		try {
			File fl = new File(FullPathname); //declaring the file 
			InputStream inputFS = new FileInputStream(fl); // inputing streams to the file
		    BufferedReader br = new BufferedReader(new InputStreamReader(inputFS)); // setting up a buffer that will run the stream on the file.
		    partThree = br.lines().
		    		skip(1).
		    		collect(Collectors.groupingBy(TweetProc::tagged2, Collectors.counting()));
		    br.close();
		}
		catch (IOException e) {
			System.out.println("no such file");
		};
		return partThree;
		
	}
	
	
/* =====================================================================================================================
	 * getTweeterURLtweetCount()										Column #: 7 & 11
	 * 			Returns Map																_____| PART : 4 |____
	 * 					Keys:  Tweeters
	 * 					Values:   a count of the number of tweets that contain a specific word
* ======================================================================================================================
*/
	
	public static  String toCheck = "";
	private static String containsWord(String checkInside )	{

		String patternStr1 = toCheck;						 /* FOR CHECKING: If its actually a taggee and not just a character "@" in the text */
		String[] cols = checkInside.split(",");
		Pattern pattern1 = Pattern.compile(patternStr1);
		Matcher m1 = pattern1.matcher(cols[textpos]);
		if (m1.find() )
		     return cols[tweetpos];
		else return "no one used that word in their tweet";
		}
	
	static Map <String, Long>  getTweeterWordCount(String FullPathname, String word){
		
		toCheck = word;
		Map<String, Long> partFour = null; 
		try {
			File fl = new File(FullPathname); //declaring the file 
			InputStream inputFS = new FileInputStream(fl); // inputing streams to the file
		    BufferedReader br = new BufferedReader(new InputStreamReader(inputFS)); // setting up a buffer that will run the stream on the file.
		    partFour = br.lines().
		    		skip(1).
		    		parallel().
		    		collect(Collectors.groupingByConcurrent(TweetProc::containsWord, Collectors.counting()));
		    br.close();
		}
		catch (IOException e) {
			System.out.println("no such file");
		};
		return partFour;
		
		
		
	}

/* =====================================================================================================================
	 * getTweeterURLtweetCount()										Column #: 7 & 11
	 * 			Returns Map																_____| PART : 5 |____
	 * 					Keys:  Tweeters
	 * 					Values:   a count of the average length of each Tweeter’s tweets
* ======================================================================================================================
*/
	
	private static ToIntFunction<String> averageWords = 
		(line)->{	
				String[] cols = line.split(",");
				String[] splittedCol = cols[textpos].split(" ");
				return splittedCol.length;
		};
	
	static Map <String, Double> getTweeterAverageVerbosity(String FullPathname){
		Map<String, Double> partFive = null; 
		try {
			File fl = new File(FullPathname); //declaring the file 
			InputStream inputFS = new FileInputStream(fl); // inputing streams to the file
		    BufferedReader br = new BufferedReader(new InputStreamReader(inputFS)); // setting up a buffer that will run the stream on the file.
		    partFive = br.lines().
		    			skip(1).
		    			collect(Collectors.groupingBy(getContent1, Collectors.averagingInt(averageWords)));
		    br.close();
		}
		catch (IOException e) {
			System.out.println("no such file");
		};
		return partFive;
	}

	
/* =====================================================================================================================
	 * getTweeterURLtweetCount()										Column #: 7 & 11
	 * 			Returns Map																_____| PART : 6 |____
	 * 					Keys:  Tweeters
	 * 					Values:    are another map
* ======================================================================================================================
*/
	static Map <String, Map<String, Long>>	getTweeterTaggeeCount(String FullPathname){	
		Map<String, Map<String, Long>> partSix = null; 
		try {
			File fl = new File(FullPathname); //declaring the file 
			InputStream inputFS = new FileInputStream(fl); // inputing streams to the file
		    BufferedReader br = new BufferedReader(new InputStreamReader(inputFS)); // setting up a buffer that will run the stream on the file.
		    partSix = br.lines().
		    		parallel().
		    		collect(Collectors.groupingBy(getContent1, 
							Collectors.groupingBy(TweetProc::tagged1, Collectors.counting())));
		    		br.close();
		    			    
		}
		catch (IOException e) {
			System.out.println("no such file");
		};
		return partSix;
	}
}





