package TwitterSentiment2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import TwitterSentiment2.TweetWithSentiment;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SentimentsResource extends Application implements Initializable
{
	
// FXML Fields Declaration=================================================================================
			@FXML
			public TextField keyword;
			@FXML
			public TextArea negtweets;
			@FXML
			public TextArea neutweets;
			@FXML
			public TextArea postweets;
	
//========================================Sentiment function======================================================================================================================================================
	public List<Result> sentiments() {
		
		negtweets.clear();
		neutweets.clear();
		postweets.clear();
   	 
        List<Result> results = new ArrayList<>();
        if(keyword.getText().isEmpty())
		{
			String str="No Keyword Entered";
			negtweets.setText(str);
			neutweets.setText(str);
			postweets.setText(str);
			return results;
		}
        else
        {
        	String str="Calculating.......";
			negtweets.setText(str);
			neutweets.setText(str);
			postweets.setText(str);
        }
        
        String searchKeywords=keyword.getText();
        Set<String> keywords = new HashSet<>(); //as duplicate keywords should not be there
        for (String keyword : searchKeywords.split(",")) {
            keywords.add(keyword.trim().toLowerCase());
        }
        if (keywords.size() > 3) 
        	{
            keywords = new HashSet<>(new ArrayList<>(keywords).subList(0, 3));
        	}
        System.out.println(searchKeywords);
 //========================twitter 4j connection==================================================================================================
        ConfigurationBuilder cb = new ConfigurationBuilder(); 
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey("DcxsqW035gztx1MtyM9KwbOWd")
		.setOAuthConsumerSecret("txs5YAREMSYlAy39DHzdDbFR9eQxfc2ONyYOiR3uqVuykFElUg")
		.setOAuthAccessToken("851457113851404289-5JRQfKsINo323R3dqFjPn2zky5X35S1")
		.setOAuthAccessTokenSecret("drTvN0E7tMyObnU6LFLSY3Qwd1WHKqkRIJXiSL9i1hlJM");
	    TwitterFactory tf = new TwitterFactory(cb.build());
	    Twitter twitter = tf.getInstance();
	    for (String keyword : keywords)
	    {
	    Query query = new Query(keyword + " -filter:retweets -filter:links -filter:replies -filter:images");
	    query.setCount(50);
	    query.setLocale("en");
	    query.setLang("en");;
	    try 
	    	{
	    	
	        QueryResult queryResult = twitter.search(query);
	        List<Status> statuses = queryResult.getTweets();    
	      
	            System.out.println("Found statuses ... " + statuses.size());
	            List<TweetWithSentiment> sentiments = new ArrayList<>();
	        	for (Status status : statuses) 
	            {  
	            String line =status.getText();
	            System.out.println("Program running at line 103");
	            Properties props = new Properties();
	            props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
	            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	            int mainSentiment = 0;
	            if  (line != null && line.length() > 0) 
	            	{
	                int longest = 0;
	                Annotation annotation = pipeline.process(line);
	                for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) 
	                	{
	                    Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
	                    int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
	                    String partText = sentence.toString();
	                    if (partText.length() > longest) 
	                    	{
	                        mainSentiment = sentiment;
	                        longest = partText.length();
	                    	}

	                	}
	            	}
	            if ( mainSentiment > 4 || mainSentiment < 0)
	            	{
	                return null;
	            	}
	            TweetWithSentiment tweetWithSentiment = new TweetWithSentiment(line, toCss(mainSentiment));
	            if ( mainSentiment==0 || mainSentiment==1)
	            {
	            		String str=negtweets.getText();
	    	            negtweets.setText(str+"\n"+"\n"+tweetWithSentiment);
	    	            System.out.println(tweetWithSentiment);
	            }
	            else if ( mainSentiment==2)
	            {
	            		String str=neutweets.getText();
	    	            neutweets.setText(str+"\n"+"\n"+tweetWithSentiment);
	    	            System.out.println(tweetWithSentiment);
	            }
	            else if ( mainSentiment==3|| mainSentiment==4)
	            {
	    	            String str=postweets.getText();
	    	            postweets.setText(str+"\n"+"\n"+tweetWithSentiment);
	    	            System.out.println(tweetWithSentiment);
	            }
	            
	               /* TweetWithSentiment tweetWithSentiment = s2.findSentiment(status.getText());
	                if (tweetWithSentiment != null) 
	                {
	                    sentiments.add(tweetWithSentiment);
	                }
	            }
	                  */
	            Result result = new Result(keyword, sentiments);
	            results.add(result);
	    	}}
		    catch (TwitterException e)
		    {
		        System.out.println("Error");
		        e.printStackTrace();
		    }
	    }
	        return results;
	    }
	
	
	

    private String toCss(int sentiment) 
    {
        switch (sentiment) {
        case 0:
            return "alert VERY NEGATIVE";
        case 1:
            return "alert NEGATIVE";
        case 2:
            return "alert NEUTRAL";
        case 3:
            return "alert POSITIVE";
        case 4:
            return "alert VERY POSITIVE";
        default:
            return "";
        }
    }


// public static void MAIN function=================================================================================================================
	    
 public static void main(String[] args) 
	    {
	 		launch(args);
	       
	    }


@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub
	
}

Parent root;
static Stage stage1;
@Override
public void start(Stage stage) throws Exception {
	// TODO Auto-generated method stub
     
	
	try {
		 root= FXMLLoader.load(getClass().getResource("SentimentsResourceFXML.fxml"));
		} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		     stage1=stage;
			Scene scene=new Scene(root,1200,650);
			//stage.initStyle(StageStyle.UNDECORATED);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.setTitle("Sentiment Analysis");
			stage.show();
}

public void closecur()
	{
		stage1.hide();

	}
	}