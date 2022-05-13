import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

import java.net.URL
import java.util.ResourceBundle


class ChatMainController extends Initializable {
  @FXML protected var MainWindow: GridPane = null
  @FXML protected var HBoxMessageControl: HBox = null
  @FXML protected var ChatTFMessage: TextArea = null
  @FXML protected var ChatBTNSend: Button = null
  @FXML protected var VBoxChatMessage: VBox = null
  @FXML protected var VBoxFriendList: VBox = null
  @FXML protected var ChatWidthFriendLabel: Label = null
  @FXML protected var HBoxPanelForOption: HBox = null
  @FXML protected var AddFriendBTN: Button = null
  @FXML protected var ConversationBTN: Button = null
  @FXML protected var HBoxTitleChat: HBox = null
  @FXML protected var ChatListLabel: Label = null

  @FXML def actionChatBTNSend(Event: ActionEvent): Unit = {}
  @FXML def actionAddFriendBTN(Event: ActionEvent): Unit = {}
  @FXML def actionConversationBTN(Event: ActionEvent): Unit = {}

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
  }
}

