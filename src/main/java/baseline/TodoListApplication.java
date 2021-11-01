/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Leonardo Herrera
 */

package baseline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TodoListApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TodoList.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("To do List");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
       launch(args);
    }

}
