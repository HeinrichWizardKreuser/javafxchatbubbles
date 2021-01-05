package com.github.heinrichwizardkreuser.javafxchatbubbles;

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

// import javafx.stage.Stage;
// import javafx.stage.Modality;
// import javafx.collections.*;
// import javafx.concurrent.Task;
// import javafx.application.*;
// import javafx.stage.WindowEvent;
// also, when you're done with this project, you'll be paid to build our website 
import java.util.*;

public class Chat {

  /*the vbox's children are all of the messages of this chat*/
  public VBox msgBox = new VBox(5);

  public String ourUsername;
  public Chat(String ourUsername) {
    this.ourUsername = ourUsername;
    this.messages = new LinkedList<Message>();
  }

  /** messages registerd on this Chat */
  private LinkedList<Message> messages;
  public void add(Message msg) {
    this.registerUsername(msg.sender);
    this.messages.add(msg);
    this.uptodate = false;
  }
  public void add(String sender, String content) {
    this.add(new Message(sender, content));
  }

  public boolean uptodate = true;

  /** mapping of users in this chat to the colors of their messages */
  private HashMap<String, Color> username2color = new HashMap<String, Color>();
  public void setColor(String username, Color color) {
      this.username2color.put(username, color);
  }
  private void registerUsername(String username) {
    Color color = username2color.get(username);
    if (color == null) {
      color = Color.rgb(
        (int)(Math.random() * 255),
        (int)(Math.random() * 255),
        (int)(Math.random() * 255)
      );
      this.setColor(username, color);
    }
  }

  public LinkedList<BorderPane> renderMessages(double width, 
    TextAlignment senderAlignment, TextAlignment receiverAlignment) {
    LinkedList<BorderPane> renderedMessages = new LinkedList<BorderPane>();
    // render all messages
    for (Message msg : this.messages) {
      BorderPane bp = new BorderPane();
      boolean isUs = msg.sender.equals(this.ourUsername);
      TextAlignment ta = isUs ? senderAlignment : receiverAlignment;
      Group bubble = msg.toBubble(username2color.get(msg.sender), ta, width);
      if (isUs) {
        bp.setRight(bubble);
      } else {
        bp.setLeft(bubble);
      }
      bp.setPadding(new Insets(0, 20, 0, 20));
      renderedMessages.add(bp);
    }
    return renderedMessages;
  }

  /** Gets the list of members for this chat */
  public LinkedList<String> getMembers() {
    LinkedList<String> members = new LinkedList<String>();
    for (Message msg : this.messages) {
      members.add(msg.sender);
    }
    return members;
  }
}
