package com.github.heinrichwizardkreuser.javafxchatbubbles;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Represents a conversation containing a list of ordered messages, each with a
 * message content as well as who the message came from.
 */
public class Chat {

  /** the vbox's children are all of the messages of this chat */
  public VBox msgBox = new VBox(5);

  /** the username of us. Used for aligning bubbles */
  public String ourUsername;

  /**
   * Constructor for the Chat object.
   * 
   * @param ourUsername the username of us. This helps with rendering messages
   *        such that it is clear from just the alignment from the bubble who 
   *        the message is from
   */
  public Chat(String ourUsername) {
    this.ourUsername = ourUsername;
    this.messages = new LinkedList<Message>();
  }

  /** messages registerd on this Chat */
  private LinkedList<Message> messages;

  /**
   * Adds the given message to this chat object
   *
   * @param msg the Message to add.
   */
  public void add(Message msg) {
    this.registerUsername(msg.sender);
    this.messages.add(msg);
    this.uptodate = false;
  }

  /**
   * Constructs a Message object from the given parameters and adds the Message
   * to the chat
   *
   * @param sender the username of the sender of the message.
   * @param content the message string of the message.
   */
  public void add(String sender, String content) {
    this.add(new Message(sender, content));
  }

  /* Specifies whether a new message has been added to this chat since it was 
   * last rendered */
  private boolean uptodate = true;
  public boolean isuptodate() { return this.uptodate; }
  public void setuptodate() { this.uptodate = true; }

  /** mapping of users in this chat to the colors of their messages */
  private HashMap<String, Color> username2color = new HashMap<String, Color>();

  /**
   * Sets the color of the chat bubbles for the given user.
   *
   * @param username the name of user who's bubbles must be changed to the
   *        specified color.
   * @param color the new Color to set the given username's bubbles to.
   */
  public void setColor(String username, Color color) {
      this.username2color.put(username, color);
  }

  /**
   * Adds the given username to the list of known usernames for this chat
   * along with a random color
   *
   * @param username the name of the user to add.
   */
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

  /**
   * Renders all of the message bubbles of this chat and sends them back as a 
   * list of borderpanes.
   *
   * @param width the width of the XXX.
   * @param ourAlignment the TextAlignment of the host's message texts. This is 
   *        used to calculate whether the host's text messages must be alligned
   *        to the right or to the left.
   * @param theirAlignment the TextAlignment of all other message texts. This is
   *        used to calculate whether the other text messages must be alligned
   *        to the right or to the left.
   * @return LinkedList<BorderPane> of all of the rendered message bubbles.
   */
  public LinkedList<BorderPane> renderMessages(double width, 
                                               TextAlignment ourAlignment,
                                               TextAlignment theirAlignment) {
    LinkedList<BorderPane> renderedMessages = new LinkedList<BorderPane>();
    for (Message msg : this.messages) {
      BorderPane bp = new BorderPane();
      boolean isUs = msg.sender.equals(this.ourUsername);
      TextAlignment ta = isUs ? ourAlignment : theirAlignment;
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

  /**
   * Gets the hashset of all unique members for this chat
   *
   * @return HashSet<String> the set of usernames in the chat.
   */
  public HashSet<String> getMembers() {
    HashSet<String> members = new HashSet<String>();
    for (Message msg : this.messages) {
      members.add(msg.sender);
    }
    return members;
  }

  /** String representation of this object, example:
   * new Message 'hi' from 'Jake' in chat 'Chat{members=[Jake, Finn], #msgs=3>'
   */
  public String toString() {
    String members = "";
    for (String name : this.getMembers()) {
      members += name + ", ";
    }
    if (members.length() == 0) {
      members = "Empty";
    } else {
      members = members.substring(0, members.length()-2);
    }
    return String.format("Chat{members=[%s], #msgs=%d>", members, 
      this.messages.size());
  }
}
