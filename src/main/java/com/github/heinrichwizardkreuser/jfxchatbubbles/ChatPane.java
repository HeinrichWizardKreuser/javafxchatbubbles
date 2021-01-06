package com.github.heinrichwizardkreuser.javafxchatbubbles;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.text.TextAlignment;

import java.util.function.BiConsumer;

/**
 * Type of BorderPane that is used to render a chat.
 * It will render messages from the chat with messages from the sender with name
 * denoted in the Chat.ourUsername veriable on one side and messages from all
 * other users on the other side.
 */
public class ChatPane extends BorderPane {

  /**
   * Constructor for a ChatPane object. Takes a Chat and renderes a new 
   * BorderPane responsible for rendering messages from different users in a 
   * scrollable view. 
   *
   * @param chat the Chat.
   */
  public ChatPane(Chat chat) {
    this.chat = chat;
    // set the chat view
    this.chatLogView = new ScrollPane();
    this.setCenter(chatLogView);
    // bottom text box for messaging
    BorderPane textboxPane  = new BorderPane();
    this.textfield = new TextField();
    textfield.setOnAction(e -> {
      String msgText = textfield.getText();
      textfield.clear();
      Message msg = new Message(this.chat.ourUsername, msgText);
      this.chat.add(msg);
      if (this.messageListener != null) {
        this.messageListener.accept(msg, this.chat);
      }
      this.render(this.widthProperty().get());
      chatLogView.layout();
      chatLogView.setVvalue(1.0);
    });
    textboxPane.setCenter(textfield);
    this.setBottom(textboxPane);
    // add listener to rerender chat when width is changed
    this.widthProperty().addListener((obs, oldVal, newVal) -> {
      if (this.chat.isuptodate()) {
        return;
      }
      this.render((double)newVal);
      this.chat.setuptodate();
    });
  }

  /**
   * Renders this chatpane with the given scrollpane width.
   *
   * @param width the width of the scrollpane in which messages must fit.
   */
  private void render(double width) {
    VBox messages = this.chat.msgBox;
    messages.setPrefWidth(width);
    messages.getChildren().clear();
    double messagesWidth = messages.getWidth() * 0.8;
    if (messagesWidth != 0) {
      width = messagesWidth;
    }
    messages.getChildren().addAll(this.chat.renderMessages(width, 
      this.ourAlignment, this.theirAlignment));

    // create scrollpane with all messages
    this.chatLogView.setContent(messages);
    this.chatLogView.setFitToWidth(true);
  }

  /** the chat that this ChatPane must constantly render */
  private Chat chat;
  /** the scrollpane in which the messages will be rendered */
  private ScrollPane chatLogView;
  /** the textfield in which the user can type messages */
  private TextField textfield;
  
  public TextField getTextField() { return this.textfield; }

  /* sets the alignment of our messages to the right and all other messages to
    the left */
  public void setOurAllignmentRight() {
    this.ourAlignment = TextAlignment.RIGHT;
    this.theirAlignment = TextAlignment.LEFT;
  }
  /* sets the alignment of our messages to the left and all other messages to
    the right */
  public void setOurAllignmentLeft() {
    this.ourAlignment = TextAlignment.LEFT;
    this.theirAlignment = TextAlignment.RIGHT;
  }
  /*the alignment of messages from all users*/
  private TextAlignment ourAlignment = TextAlignment.LEFT;
  private TextAlignment theirAlignment = TextAlignment.RIGHT;

  /**
   * Sets a listener for whenever a new message is added to the chat via the
   * textfield. If the user types a new message into the chat via the textfield,
   * then the provided function will be called, with the arguments being the 
   * Message object as well as the Chat the Message object belongs to
   *
   * @param listener the BiConsumer<Message, Chat> the biconsumer which will be
   *        called whenever the a new message is added via the textfield.
   */
  public void setMessageListener(BiConsumer<Message, Chat> listener) {
    this.messageListener = listener;
  }
  private BiConsumer<Message, Chat> messageListener;

  /**
   * sets the title label for the top of the chat
   *
   * @param titleText the String text for the title of the pane.
   */
  public void setTitle(String titleText) {
    this.setTop(new Label(titleText));
  }
}