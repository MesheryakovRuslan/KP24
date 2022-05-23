import javafx.event.ActionEvent
import javafx.geometry.{Insets, Pos}
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.text.{Font, TextAlignment}

class ChatMainControllerLogic extends ChatMainController  {

  override def actionChatBTNSend(Event: ActionEvent): Unit = {
    if(ChatTFMessage.getText.nonEmpty){
      printUserMessage()
      ChatScrollPane.setVvalue(1)
      ChatTFMessage.setText("")
    }
  }

  override def actionAddFriendBTN(Event: ActionEvent): Unit = {
    println("Add")
    val nameFriend = NameAddFriendTF.getText
    val UIDFriend = ChatUIDTF.getText
    val label = new Label
    label.setText(nameFriend + "\n" + UIDFriend)
    label.setPrefWidth(110.0)
    label.setPrefHeight(39.0)
    label.setStyle("-fx-background-color: #01191A; -fx-border-color: #499094;")
    FriendListVbox.getChildren.add(label)

  }

  override def actionConversationBTN(Event: ActionEvent): Unit = {

  }

  def printUserMessage():Unit = {
    val message = ChatTFMessage.getText
    val label = new Label
    label.setFont(new Font("Arial",18.0))
    label.setTextFill(Color.web("#0076a3"))
    label.setText(message)
    label.setWrapText(true)
    label.setPadding(new Insets(0, 8, 0, VBoxChatMessage.getWidth/2.5))
    label.setMaxWidth(VBoxChatMessage.getWidth)
    label.setTextAlignment(TextAlignment.RIGHT)
    label.setAlignment(Pos.CENTER_RIGHT)
    VBoxChatMessage.getChildren.addAll(label)
  }

  override def actionVueFriendBTN(Event: ActionEvent): Unit = {
    println("Vue")
    if(AddFriendPanel.isVisible){
      AddFriendPanel.setVisible(false)
      FriendListScrollPane.setPrefHeight(470)

    }else{
      AddFriendPanel.setVisible(true)
    }
  }
}
