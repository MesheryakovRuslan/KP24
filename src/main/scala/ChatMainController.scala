import javafx.event.ActionEvent
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control._
import javafx.scene.layout.{AnchorPane, GridPane, HBox, VBox}

import java.net.URL
import java.util.ResourceBundle


class ChatMainController extends Initializable {
  @FXML protected var ChatScrollPane: ScrollPane = null
  @FXML protected var FriendListScrollPane: ScrollPane = null
  @FXML protected var MainWindow: GridPane = null
  @FXML protected var HBoxMessageControl: HBox = null
  @FXML var ChatTFMessage: TextArea = null
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

  }
}

