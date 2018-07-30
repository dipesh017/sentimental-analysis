package TwitterSentiment2;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
 
public class TwitterSearch extends Application implements Initializable {
 
 
	    public static void main(String[] args) 
	    	{
	    	launch(args);
	    	}	
	   
        Parent root;
        static Stage stage1;

		@Override
		public void start(Stage stage) throws Exception {
		
			// TODO Auto-generated method stub
	    	
			try {
				 root= FXMLLoader.load(getClass().getResource("TwitterSearchFXML.fxml"));
				} 
			catch (IOException e)
				{
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
				stage1=stage;
		
				Scene scene=new Scene(root,1200,650);
				//stage.initStyle(StageStyle.UNDECORATED);
				stage.setResizable(false);
				stage.setScene(scene);
				stage.setTitle("Twitter Search");
				stage.show();
			
		}


		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			
		}
// FXML Fields Declaration=================================================================================
		@FXML
		private TextField keyword;
		@FXML
		private TextArea tweets;
		
//========================================Search Function==========================================================
		
		public void search()
		{
			tweets.setText("");
			if(keyword.getText().isEmpty())
			{
				String str="No Keyword Entered";
				tweets.setText(str);
			}
			else 
			{
				 TwitterSearch twitterSearch = new TwitterSearch();
			        List<Status> statuses = twitterSearch.search(keyword.getText());
			        for (Status status : statuses) 
			        {
			            System.out.println(status.getText());
			            String str=tweets.getText();
			            tweets.setText(str+"\n"+status.getText());
			        }
		
			}
		}
		
//=============================List Function called in Search Function=============================================================
		 public List<Status> search(String keyword) {
		        ConfigurationBuilder cb = new ConfigurationBuilder();
		        cb.setDebugEnabled(true)
		        .setOAuthConsumerKey("DcxsqW035gztx1MtyM9KwbOWd")
				.setOAuthConsumerSecret("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
				.setOAuthAccessToken("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
				.setOAuthAccessTokenSecret("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	        TwitterFactory tf = new TwitterFactory(cb.build());
	        Twitter twitter = tf.getInstance();
	        Query query = new Query(keyword + " -filter:retweets -filter:links -filter:replies -filter:images");
	        query.setCount(100);
	        query.setLocale("en");
	        query.setLang("en");;
	        try 
	        	{
	            QueryResult queryResult = twitter.search(query);
	            return queryResult.getTweets();
	        	} 
	        catch (TwitterException e)
		        {
		            // ignore
	        	    System.out.println("Error");
		            e.printStackTrace();
		        }
	        return Collections.emptyList();
	 
	    }
		 
		 public void closecur()
			{
				stage1.hide();

			}
}
