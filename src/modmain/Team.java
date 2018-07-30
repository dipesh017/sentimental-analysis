package modmain;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Team extends Application implements Initializable{
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
			 root= FXMLLoader.load(getClass().getResource("Team.fxml"));
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
	public void closecur()
	{
		stage1.hide();

	}


}
