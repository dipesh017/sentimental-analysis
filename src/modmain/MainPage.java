package modmain;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import NaiveBayes.NaiveBayesClassifier;
import TwitterSentiment2.TwitterSearch;
import TwitterSentiment2.SentimentsResource;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.MainFile;
import svm.Driver;


public class MainPage extends Application implements Initializable {
	@FXML TextField username;
	@FXML PasswordField password;
	@FXML Button box21;
	@FXML Button box22;
	@FXML Button box23;
	@FXML Button box24; 
	@FXML Button box25;
	@FXML Button box26;
	@FXML Button box27;
	@FXML Button box211;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("running...");
		launch(args);
	}
    
	static Stage stage=new Stage();
	static Stage stage1=new Stage();
	TranslateTransition trans;
	Scene scene;
	Parent root;
	@Override
	public void start(Stage stage2) throws Exception {
		// TODO Auto-generated method stub  
		stage2=stage1;
		root=FXMLLoader.load(getClass().getResource("Login.fxml"));
		
		stage.initStyle(StageStyle.UNDECORATED);
		stage1.initStyle(StageStyle.UNDECORATED);
		
		scene=new Scene(root,1200,650);
		//scene.getStylesheets().add(MainPage.class.getResource("NewFile.css").toExternalForm());
		//box3.setTranslateY(800);
		
		
		//stage.setResizable(false);
		stage1.setScene(scene);
		
		stage1.setTitle("Main Page");
		stage1.show();
	}
	public void csschange()
	{
		scene.getStylesheets().add(MainPage.class.getResource("NewFile.css").toExternalForm());
		
	}
	public void dologin()
	{
		String user=username.getText();
		String pass=password.getText();
		if(user.equals("ccvtspikers")&&pass.equals("minor@1712"))
		{
			try {
				root=FXMLLoader.load(getClass().getResource("MainPage.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scene=new Scene(root,1200,650);
			//stage.setResizable(false);
			stage1.setScene(scene);
			scene.getStylesheets().add
			 (MainPage.class.getResource("NewFile.css").toExternalForm());
			stage1.show();
		}
		else
		{
			Alert alert=new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
			alert.setHeaderText("Enter Correct Username and Password ");
			alert.setTitle("Wrong Credentials");
			alert.setContentText("Details:  Enter correct details.If unauthorised access,Kindly close the application");
			alert.show(); 
		}
		
	}
	
	//developer page==============================================================================================================================
	static Stage stage0;
	public void developer()
	{
		Stage stage2=new Stage();
		stage0=stage2;
		stage0.initStyle(StageStyle.UNDECORATED);
		
			try {
				root=FXMLLoader.load(getClass().getResource("developer.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scene=new Scene(root,1200,650);
			//stage.setResizable(false);
			stage2.setScene(scene);
			scene.getStylesheets().add
			 (MainPage.class.getResource("NewFile.css").toExternalForm());
			stage2.show();
		}
	public void developer2()
	{
		System.out.println("close");
		
			//stage0.close();
			stage0.hide();
		}
	static Stage stage3;
	public void developer3()
	{
		Stage stage2=new Stage();
		stage3=stage2;
		stage3.initStyle(StageStyle.UNDECORATED);
		
			try {
				root=FXMLLoader.load(getClass().getResource("developer2.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scene=new Scene(root,1200,650);
			//stage.setResizable(false);
			stage2.setScene(scene);
			scene.getStylesheets().add
			 (MainPage.class.getResource("NewFile.css").toExternalForm());
			
			stage2.show();
		}
	public void developer4()
	{
		System.out.println("close");
		
			//stage0.close();
			stage3.hide();
		}
		
	
	

		public void openeventreg() throws IOException
		{
			TwitterSearch t1=new TwitterSearch();
			try {
				t1.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}

	
		}
		//open add  event
		public void openstureg() throws IOException
			{
			SentimentsResource t2=new SentimentsResource();
			try {
				t2.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}

			}
		
	//open committee head
		public void opencomhead() throws IOException
			{
			NaiveBayesClassifier t3=new NaiveBayesClassifier();
			try {
				t3.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			}
		
	//open student table
		public void openeventdetail() throws IOException
			{
			Driver t4=new Driver();
			try {
				t4.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	//open event table
		public void openstudetail() throws IOException
			{
			MainFile t5=new MainFile();
			try {
				t5.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	//open committee head details
		public void opencomdetails() throws IOException
			{
			Team t6=new Team();
			try {
				t6.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		
	public void slide() {
		// TODO Auto-generated method stub
		TranslateTransition trans1=new TranslateTransition(new Duration(2500),box21);
		trans1.setToX(-430);
		
		TranslateTransition trans2=new TranslateTransition(new Duration(2500),box22);
		trans2.setToX(-430);
		
		
		TranslateTransition trans3=new TranslateTransition(new Duration(2500),box23);
		trans3.setToX(-430);
		
		
		TranslateTransition trans4=new TranslateTransition(new Duration(2500),box24);
		trans4.setToX(-430);
		
		TranslateTransition trans5=new TranslateTransition(new Duration(2500),box25);
		trans5.setToX(-430);
		
		TranslateTransition trans6=new TranslateTransition(new Duration(2500),box26);
		trans6.setToX(-430);
		
		TranslateTransition trans7=new TranslateTransition(new Duration(2500),box27);
		trans7.setToX(-430);
		
		trans7.play();
		trans6.play();
		trans5.play();
		trans4.play();
		trans3.play();
		trans2.play();
		trans1.play();
		box211.setText("Close");
	}
	public void slideclose() {
		// TODO Auto-generated method stub
		TranslateTransition trans1=new TranslateTransition(new Duration(2500),box21);
		trans1.setToX(0);
		
		TranslateTransition trans2=new TranslateTransition(new Duration(2500),box22);
		trans2.setToX(0);
		
		
		TranslateTransition trans3=new TranslateTransition(new Duration(2500),box23);
		trans3.setToX(0);
		
		
		TranslateTransition trans4=new TranslateTransition(new Duration(2500),box24);
		trans4.setToX(0);
		
		TranslateTransition trans5=new TranslateTransition(new Duration(2500),box25);
		trans5.setToX(0);
		
		TranslateTransition trans6=new TranslateTransition(new Duration(2500),box26);
		trans6.setToX(0);
		
		TranslateTransition trans7=new TranslateTransition(new Duration(2500),box27);
		trans7.setToX(0);
		
		trans7.play();
		trans6.play();
		trans5.play();
		trans4.play();
		trans3.play();
		trans2.play();
		trans1.play();
		box211.setText("Enter");
	}
    public void slidemain()
    {
    	String value=box211.getText();
    	if(value.equals("Enter"))
    	{
    		slide();
    	}
    	else{
    		slideclose();
    	}
    }
    public void exit()
    {
    	System.exit(0);
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
		
	


}

