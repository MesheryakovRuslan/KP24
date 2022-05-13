import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.event.ActionEvent
import java.net.URL
import java.util.ResourceBundle


class ChatLoginController extends Initializable {
  @FXML protected var ChatLogIn: AnchorPane = null
  @FXML protected var NameTF: TextField = null
  @FXML protected var PasswordTF: TextField = null
  @FXML protected var NameTitleLabel: Label = null
  @FXML protected var PasswordTitleLabel: Label = null
  @FXML protected var TitleLabel: Label = null
  @FXML protected var LoginBTN: Button = null

  @FXML def actionLoginBTN(Event: ActionEvent): Unit = {}

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
  }
}
