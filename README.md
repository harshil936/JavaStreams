# JavaStreams
ECS160 Software Engineer - Running Queries on a Tweeter dataset using Java Streams from Java 8

# STREAMS 

You will create a class <br/>

TweetProc in package edu.ucdavis.ecs160.hw3 with  several static methods that process streams of <br/>
tweets and return Map <T, R> or Map<T, Map<R,S>> datastructures. Use the seg3.java file as an <br/>
example to process the tweets. We will give you a sample tweet data file. Each method takes as <br/>
argument a file name containing tweets, according to the csv format given in the seg3.java example, <br/>
and in the attached example tweet file. Each method will have a Stream pipeline, starting with the <br/>
file, streaming each csv row as a separate line, and then processing it, and consuming it all a <br/>
Terminal operation that Collects everything into the required Map data structure. We encourage <br/>
you to drop a  “parallel().” early in your pipeline to ensure it can be parallelized safely, <br/>
although we won’t test for it. <br/>

Submit: One Java file, TweetProc.java. You are free to have helper methods in addition to the <br/>
static methods listed below. We will only test the static methods we ask for below. You must use <br/>
Java 8 Streams, and you may NOT use any for, do, for each or while loops. We’ll check. If you don’t <br/>
follow these rules, you will get zero credit. <br/>

R E Q U I R E D-M E T H O D S: <br/>

These are the methods. Each takes a filename as input and returns a MAP data structure.
Specifically, the parameter “FullPathname” is the file path to a csv file we will provide that
contains all of the tweet information.
1. Static Map <String, Long> getPerTweeterCount(String FullPathname)
-
A “Tweeter” is the account name of the person writing the tweet, found in the
“name” column of the csv file.  This method should return a map where the keys
are the tweeters and the values are the number of tweets they have made.
2. Static Map <String, Long> getPerTaggeeCount(String FullPathname)
Consider a “taggee” in a Tweet to be any word of text that begins with a “@”
symbol.  For this method, return a map where the keys are the distinct taggees
and the value is the count of the number of tweets the taggees appear in.  If a
taggee is marked more than once in a tweet, count the tweet only once.
Taggees can be found in the ‘text’ column of the provided csv file. <br/>

3. Static Map <String, Long> getTweeterURLtweetCount(String
FullPathname) Return a map where the keys are Tweeters and the values are the number of
their tweets that contain any URL in the “text” column.  For this problem we
define a URL to be any word in the text that begins with “http://”.  You do not
need to consider other variations like “https://:” or “www.” for the purpose of this
question.
4. Static Map <String, Long>
    getTweeterWordCount(String FullPathname, String word)
-
Return a map where the keys are Tweeters and the values are a count of the
number of tweets that contain a specific word, where this word is passed in via
the “word” parameter.
5. Static Map <String, Double>
     getTweeterAverageVerbosity(String FullPathname)
-
Return a map where the keys are Tweeters and the values are a count of the
average length of each Tweeter’s tweets.  The length of a tweet is defined in
terms of the number of words, where words are elements in the tweet divided by
whitespace.  For example, “Hi, I’m a tweet!”  would have length 4.
6. Static Map <String, Map<String, Long>>
getTweeterTaggeeCount(String FullPathname)
-
Return a map where the keys are Tweeters and the values are another map.
This additional
​
​
Map<String, Long> 
​
is keyed by taggees and map to the
number of tweets in which these taggees occur.  So the overall counts the
number of tweets by a Tweeter in which a specific tagge occurs
