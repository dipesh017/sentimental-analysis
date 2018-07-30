package svm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import weka.classifiers.functions.SMO;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.stemmers.LovinsStemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Driver extends Application implements Initializable{
	static String filename = "train.arff";
	String source;
	String test;
	String res;
	
	@FXML
	public TextField train1;
	@FXML
	public TextField test1;
	@FXML
	public TextArea result;
	
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
			 root= FXMLLoader.load(getClass().getResource("Svm.fxml"));
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

	/*public static void main(String[] args) throws Exception {
		
		
		//manipulate path to find .csv to convert to .arff
		String path = System.getProperty("user.dir");
		String source = path + "/src/svm/" + filename;
		String test = path + "/src/svm/" + "test.arff";
//		String arff = Converter.toArff(source);
		Driver.process(source, test);
	}*/
  
//============================action button===============================================================
	
	   public void TrainButtonAction(ActionEvent event){
		   
		   FileChooser fileChooser = new FileChooser();
		   File selectedFile = fileChooser.showOpenDialog(null);

		   if (selectedFile != null) {

		       source= selectedFile.getAbsolutePath();
		       System.out.println(source);
		       train1.setText(source);
		   }
		   else {
		      // actionStatus.setText("File selection cancelled.");
		   }
	    }
	   public void TestButtonAction(ActionEvent event){
		   FileChooser fileChooser = new FileChooser();
		   File selectedFile = fileChooser.showOpenDialog(null);

		   if (selectedFile != null) {

		       test= selectedFile.getAbsolutePath();
		       System.out.println(test);
		       test1.setText(test);
		       try {
			//	Driver.process(source, test);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		   else {
		      // actionStatus.setText("File selection cancelled.");
		   }
	    }
	   public void search()
	   {
		   try {
			process(source, test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setText("Please choose valid Training & Test DataSets.");
			e.printStackTrace();
		}
	   }
//=====================================processing function========================================================================
	
	public String process(String training, String testing) throws Exception
	{
		ArffLoader loader = new ArffLoader();
		loader.setSource(new File(training));


		StringToWordVector swv= new StringToWordVector();
		//AttributeSelection as = new AttributeSelection();
		SMO svm = new SMO();
		// Classifier naive = new NaiveBayes();

		//training data
		DataSource source1 = new DataSource(loader);
		Instances train = source1.getDataSet();
		//set class index to the first attribute
		train.setClassIndex(0);
		swv.setIDFTransform(true);
		swv.setUseStoplist(true);
		LovinsStemmer stemmer = new LovinsStemmer();
		swv.setStemmer(stemmer);
		swv.setLowerCaseTokens(true);
		swv.setInputFormat(train);

		train = Filter.useFilter(train, swv);

		//testing data
		loader.setSource(new File(testing));
		DataSource source2 = new DataSource(loader);
		Instances test= source2.getDataSet();
		//set class index to the first attribute
		test.setClassIndex(0);
		swv.setIDFTransform(true);
		swv.setUseStoplist(true);
		stemmer = new LovinsStemmer();
		swv.setStemmer(stemmer);
		swv.setLowerCaseTokens(true);
		swv.setInputFormat(train);

		Instances test2= Filter.useFilter(test, swv);
		svm.buildClassifier(train);

		for(int i=0; i<test2.numInstances(); i++) 
		{
			System.out.println(test.instance(i));
			Instance s1=test.instance(i);
			System.out.println(s1);
			result.setText(result.getText()+"\n"+s1);
			double index = svm.classifyInstance(test2.instance(i));
			String className = train.classAttribute().value((int)index);
			System.out.println(className);
			result.setText(result.getText()+"\n"+className);
		}

		return "Done";
	}


	private String println(Instance instance) {
		// TODO Auto-generated method stub
		return null;
	}
	public void closecur()
	{
		stage1.hide();

	}
}
