package example;

import com.github.heinrichwizardkreuser.javafxchatbubbles.*;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.beans.value.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.application.*;
import javafx.stage.WindowEvent;


public class Example extends Application {

  /** launches the GUI */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    Chat exampleChat = new Chat("us");
    exampleChat.add(new Message("Friend 1", "Hi there!"));
    exampleChat.add(new Message("us", "what is up friend 1!"));
    ChatPane chatPane = new ChatPane(exampleChat);

    // main pane 
    SplitPane splitPane = new SplitPane();
    splitPane.getItems().addAll(new Label("something here"), 
      chatPane);

    // TextField textfield = chatPane.getTextField();
    // textfield.setOnAction(e -> {
    //   String msg = textfield.getText();
    //   System.out.println("message:" + msg);
    //   textfield.clear();
    // });

    primaryStage.setScene(new Scene(splitPane));
    primaryStage.show();
  }
}