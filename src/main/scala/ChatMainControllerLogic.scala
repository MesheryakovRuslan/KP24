import javafx.event.ActionEvent
import javafx.geometry.{Insets, Pos}
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Label}
import javafx.scene.paint.Color
import javafx.scene.text.{Font, TextAlignment}

import java.io._


class ChatMainControllerLogic extends ChatMainController {
  var activeChat = ""

  override def actionChatBTNSend(Event: ActionEvent): Unit = {
    if (ChatTFMessage.getText.nonEmpty) {
      printUserMessage()
      ChatScrollPane.setVvalue(1)
      ChatTFMessage.setText("")
    }
  }

  override def actionAddFriendBTN(Event: ActionEvent): Unit = {

    val PathToFile = "C:\\KP24\\src\\main\\resources\\Chats\\" + NameAddFriendTF.getText + ".txt"
    val fileObject = new File(PathToFile)
    val flag = fileObject.createNewFile()

    if (flag) {

      addFriendIntoFriendList()
      print(fileObject.getPath)
      val printWriter = new PrintWriter(fileObject)
      printWriter.write(NameAddFriendTF.getText + " " + ChatUIDTF.getText)
      printWriter.close()
      NameAddFriendTF.setText("")
      ChatUIDTF.setText("")

    } else {

      val alert = new Alert(AlertType.WARNING)
      alert.setTitle("error")
      alert.setContentText("friend add failed")
      alert.show()

    }
  }

  override def actionConversationBTN(Event: ActionEvent): Unit = {

  }

  def printUserMessage(): Unit = {

    val message = ChatTFMessage.getText
    val label = new Label
    label.setFont(new Font("Arial", 18.0))
    label.setTextFill(Color.web("#0076a3"))
    label.setText(message)
    label.setWrapText(true)
    label.setPadding(new Insets(0, 8, 0, VBoxChatMessage.getWidth / 2.5))
    label.setMaxWidth(VBoxChatMessage.getWidth)
    label.setTextAlignment(TextAlignment.RIGHT)
    label.setAlignment(Pos.CENTER_RIGHT)
    VBoxChatMessage.getChildren.addAll(label)

  }

  override def actionVueFriendBTN(Event: ActionEvent): Unit = {

    println("Vue")
    if (AddFriendPanel.isVisible) {
      AddFriendPanel.setVisible(false)
      FriendListScrollPane.setPrefHeight(470)

    } else {

      AddFriendPanel.setVisible(true)
    }
  }

  def addFriendIntoFriendList(): Unit = {

    val nameFriend = NameAddFriendTF.getText
    val UIDFriend = ChatUIDTF.getText
    val label = new Label
    label.setText(nameFriend)
    label.setPrefWidth(110.0)
    label.setPrefHeight(39.0)
    label.setStyle("-fx-background-color: #01191A; -fx-border-color: #499094;")

    label.setOnMouseClicked(ActionEvent => {
      VBoxChatMessage.getChildren.clear()
      activeChat = "C:\\KP24\\src\\main\\resources\\Chats\\" + label.getText + ".txt"
      println(activeChat)
      UserNameLabel.setText(label.getText)
      println("click")
    })

    FriendListVbox.getChildren.add(label)
  }
}
