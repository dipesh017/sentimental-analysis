package NaiveBayes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NaiveBayesClassifier extends Application implements Initializable {
	//FXML Fields Declaration=================================================================================

		@FXML
		public TextArea result1;
		@FXML
		public TextArea result2;
		@FXML
		public TextArea result3;
		@FXML
		public TextArea result4;
		public static void main(String[] args) throws IOException {
		      System.out.println("scsac");
	    	   launch(args);
	    	}	
	   
	    Parent root;
	    static Stage stage1;

		@Override
		public void start(Stage stage) throws Exception {
		
			// TODO Auto-generated method stub
	    	
			try {
				 root= FXMLLoader.load(getClass().getResource("NaiveBayes.fxml"));
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

	
	public  void NBClassifier(boolean binaryNB, String trainFile, String testFile, String vocabFile, String stopwordFile, boolean removeStopwords) throws IOException{
		
		long time = System.currentTimeMillis();
		String s;
		BufferedReader br;
		HashSet<Integer> stopwords = new HashSet<>();
		int distinctWords = 0;
		
		HashSet<String> stopwordsStr = new HashSet<>();
		if(removeStopwords) {
			br = new BufferedReader(new FileReader(stopwordFile));
			while((s = br.readLine())!=null)	stopwordsStr.add(s);
			br.close();
		}
		
		br = new BufferedReader(new FileReader(vocabFile));
		while((s = br.readLine())!=null) {
			if(stopwordsStr.contains(s))	stopwords.add(distinctWords);	
			distinctWords++;
		}
		br.close();
		int[] countPos = new int[distinctWords];//countPos[0] = Count(word=vocab[0] && Review=positive)
		int[] countNeg = new int[distinctWords];
		int posReviews = 0, negReviews = 0, totalWordsInPosReviews = 0, totalWordsInNegReviews = 0;
		
		br = new BufferedReader(new FileReader(trainFile));
		while((s = br.readLine())!=null) {
			StringTokenizer st = new StringTokenizer(s," :");
			if(st.countTokens()==0)	continue;
			int rating = Integer.parseInt(st.nextToken());
			if(rating > 5) { // Positive review
				posReviews++;
				while(st.hasMoreTokens()) {
					int word = Integer.parseInt(st.nextToken());
					int freq = Integer.parseInt(st.nextToken());
					freq = binaryNB ? 1 : freq;
					if(stopwords.contains(word))	continue;
					countPos[word]+=freq;
					totalWordsInPosReviews+=freq;
				}
			}else { // Negative Review
				negReviews++;
				while(st.hasMoreTokens()) {
					int word = Integer.parseInt(st.nextToken());
					int freq = Integer.parseInt(st.nextToken());
					freq = binaryNB ? 1 : freq;
					if(stopwords.contains(word))	continue;
					countNeg[word]+=freq;
					totalWordsInNegReviews+=freq;
				}
			}
		}
		br.close();
		
		br = new BufferedReader(new FileReader(testFile));
		int truePositive = 0, falsePositive = 0, falseNegative = 0, correctClassification = 0, incorrectClassification = 0;
		while((s = br.readLine())!=null) {
			StringTokenizer st = new StringTokenizer(s, " :");
			int rating = Integer.parseInt(st.nextToken());
			int actual = rating>5 ? 1 : 0;//1-->yes, 0-->no
			double probOfPos = Math.log(posReviews/(posReviews+negReviews+0.0));
			double probOfNeg = Math.log(negReviews/(posReviews+negReviews+0.0));
			
			while(st.hasMoreTokens()) {
				int word = Integer.parseInt(st.nextToken());
				int freq = Integer.parseInt(st.nextToken());
				freq = binaryNB ? 1 : freq;
				if(stopwords.contains(word))	continue;
				probOfPos+=freq*Math.log((countPos[word]+1)/(totalWordsInPosReviews+distinctWords+0.0));
				probOfNeg+=freq*Math.log((countNeg[word]+1)/(totalWordsInNegReviews+distinctWords+0.0));
			}
			
			int predicted = (probOfPos>probOfNeg ? 1 : 0);
			//System.out.println(predicted);
			if(predicted  == actual )	correctClassification++;
			else 						incorrectClassification++;
		
			if(predicted==1 && actual==1)			truePositive++;
			else if(predicted==1 && actual==0)		falseNegative++;
			else if(predicted==0 && actual==1)		falsePositive++;
		}
		br.close();
		double accuracy = correctClassification/(correctClassification + incorrectClassification + 0.0);
		double precision = truePositive/(truePositive+falsePositive+0.0);
		double recall = truePositive/(truePositive+falseNegative+0.0);
		double fscore = 2*precision*recall/(precision+recall);
		if(binaryNB==false && removeStopwords== false)
		{
		
			System.out.println("Accuracy="+accuracy+"\nPrecision="+precision+" Recall="+recall+" F-Score="+fscore);
		    String str1="Accuracy="+accuracy+"\nPrecision="+precision+" Recall="+recall+" F-Score="+fscore;
			result1.setText(result1.getText()+str1);
			time = System.currentTimeMillis()-time;
			System.out.println("Time:"+time/1000d+"s");
			result1.setText(result1.getText()+"\n"+"Time:"+time/1000d+"s");
			
		}
		else if(binaryNB==false && removeStopwords== true)
		{
			System.out.println("Accuracy="+accuracy+"\nPrecision="+precision+" Recall="+recall+" F-Score="+fscore);
		    String str2="Accuracy="+accuracy+"\nPrecision="+precision+" Recall="+recall+" F-Score="+fscore;
			result2.setText(result2.getText()+str2);
			time = System.currentTimeMillis()-time;
			System.out.println("Time:"+time/1000d+"s");
			result2.setText(result2.getText()+"\n"+"Time:"+time/1000d+"s");
		}
		else if(binaryNB==true && removeStopwords== false)
		{
			System.out.println("Accuracy="+accuracy+"\nPrecision="+precision+" Recall="+recall+" F-Score="+fscore);
		    String str3="Accuracy="+accuracy+"\nPrecision="+precision+" Recall="+recall+" F-Score="+fscore;
			result3.setText(result3.getText()+str3);
			time = System.currentTimeMillis()-time;
			System.out.println("Time:"+time/1000d+"s");
			result3.setText(result3.getText()+"\n"+"Time:"+time/1000d+"s");
			
		}
		else if(binaryNB==true && removeStopwords== true)
		{
			System.out.println("Accuracy="+accuracy+"\nPrecision="+precision+" Recall="+recall+" F-Score="+fscore);
		    String str4="Accuracy="+accuracy+"\nPrecision="+precision+" Recall="+recall+" F-Score="+fscore;
			result4.setText(result4.getText()+str4);
			time = System.currentTimeMillis()-time;
			System.out.println("Time:"+time/1000d+"s");
			result4.setText(result4.getText()+"\n"+"Time:"+time/1000d+"s");
			
		}
        
	}
	
	
	
//========================================Search Function==========================================================
	
	public void search()
	{
		String trainFile = "src/Dataa/Train.data", testFile = "src/Dataa/Test.data", vocabFile = "src/Dataa/Vocab.data", stopwordFile = "src/Dataa/stopwords.txt";
		System.out.println("Without removing stopwords");
		//result1.setText("wdws");
		try {
			NBClassifier(false, trainFile, testFile, vocabFile, stopwordFile, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("After removing stopwords");
		try {
			NBClassifier(false, trainFile, testFile, vocabFile, stopwordFile, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("\nBinary Naive Bayes Classification:");
		System.out.println("Without removing stopwords");
		try {
			NBClassifier(true, trainFile, testFile, vocabFile, stopwordFile, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("After removing stopwords");
		try {
			NBClassifier(true, trainFile, testFile, vocabFile, stopwordFile, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		}
	public void closecur()
	{
		stage1.hide();

	}
	}
