package com.github.heinrichwizardkreuser.javafxchatbubbles;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.beans.value.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.application.*;
import javafx.stage.WindowEvent;


public class Message {

  public String sender;
  public String content;

  public Message(String sender, String content) {
    this.sender = sender;
    this.content = content;
  }

  /*Constructs a bubble that represents this message and can be visualized
    in the chat.*/
  public Group toBubble(Color rectColor, TextAlignment textAlignment, 
    double width) {
    
    Color textFill = Color.BLACK;

    Group bubble = new Group();
    Rectangle rect = new Rectangle();
    rect.setArcHeight(10);
    rect.setArcWidth(10);
    rect.setFill(rectColor);
    
    Label text = new Label(this.content);
    text.setWrapText(true);
    text.setTextAlignment(textAlignment);
    text.setMaxWidth(width);
    text.setTextFill(textFill);
    text.setPadding(new Insets(5, 5, 5, 5));

    rect.setWidth(5 + width + 5);

    Text theText = new Text(text.getText());
    double textWidth = theText.getBoundsInLocal().getWidth();
    double textHeight = theText.getBoundsInLocal().getHeight();
    int numlines = (int)textWidth / (int)(width) + 1;
    double height = numlines * textHeight;
    if (numlines == 1) {
      rect.setWidth( 5 + textWidth + 5);
    }
    rect.setHeight(5 + height + 5);

    bubble.getChildren().addAll(rect, text);

    return bubble;
  }

}