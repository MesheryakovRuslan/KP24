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
}
