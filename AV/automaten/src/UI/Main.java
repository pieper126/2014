package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Parser.Parser;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        Parser.Parse("alfabet: ab\n" +
                "states: s,a,bs\n" +
                "final: a,bs\n" +
                "transitions:\n" +
                "s,a --> a\n" +
                "s,b --> bs\n" +
                "s,eps --> bs\n" +
                "bs,b --> bs \n");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
