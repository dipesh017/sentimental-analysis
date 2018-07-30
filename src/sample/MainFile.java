package sample;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainFile extends Application {
	Parent root;
	 static Stage stage1;

    @Override
    
    public void start(Stage stage) throws Exception{
    	try {
			 root= FXMLLoader.load(getClass().getResource("sample.fxml"));
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
		stage.setTitle("Spam");
		stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
