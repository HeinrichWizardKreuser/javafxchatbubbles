# javafxchatbubbles
Chat Bubble library for JavaFX


![image](https://user-images.githubusercontent.com/48674623/103811083-0625ed80-5065-11eb-9311-ecf3bbce36c2.png)

Add to maven dependency:
```xml
<dependency>
  <groupId>com.github.heinrichwizardkreuser</groupId>
  <artifactId>javafxchatbubbles</artifactId>
  <version>1.0.0</version>
</dependency>
```

Import the relevant classes:
```java
import com.github.heinrichwizardkreuser.javafxchatbubbles.ChatPane;
import com.github.heinrichwizardkreuser.javafxchatbubbles.Chat;
import com.github.heinrichwizardkreuser.javafxchatbubbles.Message;
```

Create Chat object and add messages to it:
```java
Chat exampleChat = new Chat("Finn"); // we pass our username as an argument
exampleChat.add(new Message("Jake", "Hi there!"));
exampleChat.add(new Message("Finn", "What up, Jake!"));
```

Create a ChatPane from the chat
```java
ChatPane chatPane = new ChatPane(exampleChat);
```

Set colors of each user
```java
exampleChat.setColor("Finn", Color.web("#29D0FA")); // Blue
exampleChat.setColor("Jake", Color.web("#FFA519")); // Orange
```

Add a title for the chat
```java
chatPane.setTitle("Chat with Jake");
```

Add a listener event for every time the user types in a message in the text field
```java
chatPane.setMessageListener((msg, chat) -> {
  System.out.printf("new Message '%s' from '%s' in chat '%s'\n", 
    msg.content, msg.sender, chat.toString());
});
```

Add the ChatPane as you would any pane to (for example) a SplitPane
```java
SplitPane splitPane = new SplitPane();
splitPane.getItems().addAll(new Label("something here"), chatPane);
```
![image](https://user-images.githubusercontent.com/48674623/103811245-4c7b4c80-5065-11eb-888d-091fe22bd8f8.png)
