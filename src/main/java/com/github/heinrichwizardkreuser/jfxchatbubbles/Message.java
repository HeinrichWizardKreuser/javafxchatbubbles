package com.github.heinrichwizardkreuser.javafxchatbubbles;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

/**
 * Represents a text message that can be added to a Chat. A text message 
 * consists of a sender as well as the content of the text message.
 */
public class Message {

  public String sender;
  public String content;

  /**
   * Constructor for this text message
   *
   * @param sender the username of the sender of this message.
   * @param content the String text of this message.
   */
  public Message(String sender, String content) {
    this.sender = sender;
    this.content = content;
  }

  /**
   * Constructs a bubble that represents this message and can be visualized
   * in the chat.
   *
   * @param rectColor the Color that the bubbles must be rendered with.
   * @param textAlignment the TextAlignment of the text within the bubble.
   * @param width the xxx.
   * @return Group containing the rendered bubble and text.
   */
  public Group toBubble(Color rectColor, 
                        TextAlignment textAlignment, 
                        double width) {
    // set the text of the message
    Label text = new Label(this.content);
    Color textFill = Color.BLACK;
    text.setWrapText(true);
    text.setTextAlignment(textAlignment);
    text.setMaxWidth(width);
    text.setTextFill(textFill);
    text.setPadding(new Insets(5, 5, 5, 5));
    // set the bubble of the message
    Rectangle rect = new Rectangle();
    rect.setArcHeight(10);
    rect.setArcWidth(10);
    rect.setFill(rectColor);
    rect.setWidth(5 + width + 5);
    // calculate how the text must be rendered to fit within the bubble
    Text theText = new Text(text.getText());
    double textWidth = theText.getBoundsInLocal().getWidth();
    double textHeight = theText.getBoundsInLocal().getHeight();
    int numlines = (int)textWidth / (int)(width) + 1;
    if (numlines == 1) {
      rect.setWidth( 5 + textWidth + 5);
    }
    double height = numlines * textHeight;
    rect.setHeight(5 + height + 5);
    // build the bubble
    Group bubble = new Group();
    bubble.getChildren().addAll(rect, text);
    return bubble;
  }
}