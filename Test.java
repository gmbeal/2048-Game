import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;

import java.io.*;

public class Test extends Application{

@Override
public void start(Stage primaryStage){
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
    pane.setPadding(new Insets(11.5,12.5,13.5,14.5));
    pane.setHgap(5.5);
    pane.setVgap(5.5);
    pane.setStyle("-fx-background-color: rgb(187,173,160)");

    Text text2048 = new Text();
    text2048.setText("2048");
    text2048.setFont(Font.font("Times New Roman",
            FontWeight.BOLD, FontPosture.ITALIC,30));
    text2048.setFill(Color.BLACK);
    pane.add(text2048,0,0,2,1);
    
    Text scoreText = new Text();
    scoreText.setText("Score: ");
    scoreText.setFont(Font.font("Times New Roman",FontWeight.BOLD,30));
    scoreText.setFill(Color.BLACK);
    pane.add(scoreText,2,0,2,1);

    

    for(int i = 0; i < 4; i++){
        for(int j = 1; j < 5; j++){
            Rectangle square = new Rectangle();
            square.setWidth(100);
            square.setHeight(100);
            square.setFill(Color.TAN);

            pane.add(square,i,j);
          
          
        }
    }

   
    

    Scene scene = new Scene(pane);
    primaryStage.setTitle("Gui2048");
    primaryStage.setScene(scene);
    primaryStage.show();
}
}
