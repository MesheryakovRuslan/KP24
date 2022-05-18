import javafx.event.ActionEvent
import javafx.scene.control
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.text.Font

class ChatMainControllerLogic extends ChatMainController  {

  override def actionChatBTNSend(Event: ActionEvent): Unit = {
    val message = ChatTFMessage.getText
    val label = new Label
    label.setFont(new Font("Arial",34.0))
    label.setTextFill(Color.web("#0076a3"))
    label.setText("Me: -> "+message)
    label.setWrapText(true)
    label.setMaxWidth(420)
    label.setStyle("-fx-border-color:green")
    label.setMinHeight(label.getMaxHeight)
    VBoxChatMessage.getChildren.addAll(label)
    ChatScrollPane.setVvalue(1)


    VBoxChatMessage.setStyle("-fx-border-color:red")
  }

  override def actionAddFriendBTN(Event: ActionEvent): Unit = {

  }

  override def actionConversationBTN(Event: ActionEvent): Unit = {

  }
}
