package example;

import com.github.heinrichwizardkreuser.javafxchatbubbles.*;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Example extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    // let's setup an example of how a chat would look like
    Chat exampleChat = new Chat("Finn"); // we pass our username as an argument
    exampleChat.add(new Message("Jake", "Hi there!"));
    exampleChat.add(new Message("Finn", "What up, Jake!"));
    ChatPane chatPane = new ChatPane(exampleChat);

    // let's set the colors of Finn and Jake's text bubbles
    exampleChat.setColor("Finn", Color.web("#29D0FA")); // Blue
    exampleChat.setColor("Jake", Color.web("FFA519")); // Orange

    // we can set the title of the ChatPane like so
    chatPane.setTitle("Chat with Jake");

    /*next, we will put the chat pan into a random splitpane to showcase how
      it can be used alongside other panes*/
    SplitPane splitPane = new SplitPane();
    splitPane.getItems().addAll(new Label("something here"), 
      chatPane);

    /*we can also add listeners to new messages entered by the user if the 
      developer would like to add more actions to when messages are typed in.
      For example, we can make that the message typed by the user be sent 
      across a network from here.*/
    chatPane.setMessageListener((msg, chat) -> {
      System.out.printf("new Message '%s' from '%s' in chat '%s'\n", 
        msg.content, msg.sender, chat.toString());
    });

    primaryStage.setScene(new Scene(splitPane));
    primaryStage.show();
  }
}