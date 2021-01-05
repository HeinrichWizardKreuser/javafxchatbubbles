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

import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.application.*;
import javafx.stage.WindowEvent;

/**
 * Helps with renderering the Chat object
 */
public class ChatPane extends BorderPane {

  public ChatPane(Chat chat) {
    this.chat = chat;
    // top label
    this.setTop(new Label("The top label"));
    // centre chat log
    this.chatLogView = new ScrollPane();
    this.setCenter(chatLogView);
    // bottom text box for messaging
    BorderPane textboxPane  = new BorderPane();
    this.textfield = new TextField();
    textfield.setOnAction(e -> {
      String msg = textfield.getText();
      textfield.clear();
      chat.add(new Message(chat.ourUsername, msg));
      this.render(this.widthProperty().get());
      chatLogView.layout();
      chatLogView.setVvalue(1.0);
    });
    textboxPane.setCenter(textfield);
    this.setBottom(textboxPane);

    this.widthProperty().addListener((obs, oldVal, newVal) -> {
      if (this.chat.uptodate) {
        return;
      }
      this.render((double)newVal);
      this.chat.uptodate = true;
    });
  }

  private void render(double width) {
    VBox messages = this.chat.msgBox;
    messages.setPrefWidth(width);
    messages.getChildren().clear();
    double messagesWidth = messages.getWidth() * 0.8;
    if (messagesWidth == 0) {
      messagesWidth = 1;
    } else {
      width = messagesWidth;
    }
    messages.getChildren().addAll(this.chat.renderMessages(width, 
      this.senderAlignment, this.receiverAlignment));

    // create scrollpane with all messages
    this.chatLogView.setContent(messages);
    this.chatLogView.setFitToWidth(true);
  }

  private Chat chat;
  private ScrollPane chatLogView;
  private TextField textfield;
  public TextField getTextField() {
    return this.textfield;
  }

  /*setting alignment of text messages*/
  public void setSenderAllignmentRight() {
    this.senderAlignment = TextAlignment.RIGHT;
    this.receiverAlignment = TextAlignment.LEFT;
  }
  public void setSenderAllignmentLeft() {
    this.senderAlignment = TextAlignment.LEFT;
    this.receiverAlignment = TextAlignment.RIGHT;
  }
  private TextAlignment senderAlignment = TextAlignment.LEFT;
  private TextAlignment receiverAlignment = TextAlignment.RIGHT;
}