import javafx.event.ActionEvent
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control._
import javafx.scene.layout.{AnchorPane, GridPane, HBox, VBox}
import java.net.URL
import java.util.ResourceBundle

class ChatMainController extends Initializable {
  @FXML protected var ChatScrollPane: ScrollPane = _
  @FXML protected var FriendListScrollPane: ScrollPane = _
  @FXML protected var MainWindow: GridPane = _
  @FXML protected var HBoxMessageControl: HBox = _
  @FXML protected var ChatTFMessage: TextArea = _
  @FXML protected var ChatBTNSend: Button = _
  @FXML protected var ChatUIDTF: TextField = _
  @FXML protected var NameAddFriendTF: TextField = _
  @FXML protected var VBoxChatMessage: VBox = _
  @FXML protected var FriendListVbox: VBox = _
  @FXML protected var VBoxFriendList: VBox = _
  @FXML protected var ChatWidthFriendLabel: Label = _
  @FXML protected var UserNameLabel: Label = _
  @FXML protected var ipLabel: Label = _
  @FXML protected var HBoxPanelForOption: HBox = _
  @FXML protected var AddFriendBTN: Button = _
  @FXML protected var VueFriendBTN: Button = _
  @FXML protected var ConversationBTN: Button = _
  @FXML protected var HBoxTitleChat: HBox = _
  @FXML protected var ChatListLabel: Label = _
  @FXML protected var AddFriendPanel: AnchorPane = _

  @FXML def actionChatBTNSend(Event: ActionEvent): Unit = {}

  @FXML def actionAddFriendBTN(Event: ActionEvent): Unit = {}

  @FXML def actionVueFriendBTN(Event: ActionEvent): Unit = {}

  @FXML def actionConversationBTN(Event: ActionEvent): Unit = {}

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

  }
}

