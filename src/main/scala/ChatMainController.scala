import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.{Button, Label, ScrollPane, TextArea, TextField}
import javafx.scene.layout.{AnchorPane, GridPane, HBox, VBox}

import java.io.File
import java.net.URL
import java.util.ResourceBundle


class ChatMainController extends Initializable {
  @FXML protected var ChatScrollPane: ScrollPane = null
  @FXML protected var FriendListScrollPane: ScrollPane = null
  @FXML protected var MainWindow: GridPane = null
  @FXML protected var HBoxMessageControl: HBox = null
  @FXML protected var ChatTFMessage: TextArea = null
  @FXML protected var ChatBTNSend: Button = null
  @FXML protected var ChatUIDTF: TextField = null
  @FXML protected var NameAddFriendTF: TextField = null
  @FXML protected var VBoxChatMessage: VBox = null
  @FXML protected var FriendListVbox: VBox = null
  @FXML protected var VBoxFriendList: VBox = null
  @FXML protected var ChatWidthFriendLabel: Label = null
  @FXML protected var UserNameLabel: Label = null
  @FXML protected var HBoxPanelForOption: HBox = null
  @FXML protected var AddFriendBTN: Button = null
  @FXML protected var VueFriendBTN: Button = null
  @FXML protected var ConversationBTN: Button = null
  @FXML protected var HBoxTitleChat: HBox = null
  @FXML protected var ChatListLabel: Label = null
  @FXML protected var AddFriendPanel: AnchorPane = null


  @FXML def actionChatBTNSend(Event: ActionEvent): Unit = {}
  @FXML def actionAddFriendBTN(Event: ActionEvent): Unit = {}
  @FXML def actionVueFriendBTN(Event: ActionEvent): Unit = {}
  @FXML def actionConversationBTN(Event: ActionEvent): Unit = {}

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    AddFriendPanel.setVisible(false)
    //Загрузка друзей в FriendListVbox
    val file = new File("C:\\KP24\\src\\main\\resources\\Chats\\")
    val arrFiles = file.listFiles
    arrFiles.foreach{f:File =>

      val label = new Label
      label.setText(f.getName.substring(0,f.getName.length-4))
      label.setPrefWidth(110.0)
      label.setPrefHeight(39.0)
      label.setStyle("-fx-background-color: #01191A; -fx-border-color: #499094;")

      label.setOnMouseClicked(ActionEvent =>{
        VBoxChatMessage.getChildren.clear()
        var pathToChat = "C:\\KP24\\src\\main\\resources\\Chats\\"+ label.getText+".txt"
        println(pathToChat)
        UserNameLabel.setText(label.getText)
        println("click")
      })

      FriendListVbox.getChildren.add(label)
    }
  }
}

