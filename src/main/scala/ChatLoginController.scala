import javafx.event.ActionEvent
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.{Button, Label, TextField}
import javafx.scene.layout.AnchorPane

import java.net.URL
import java.util.ResourceBundle


class ChatLoginController extends Initializable {
  @FXML protected var ChatLogIn: AnchorPane = null
  @FXML protected var LoginTF: TextField = null
  @FXML protected var PasswordTF: TextField = null
  @FXML protected var LoginTitleLabel: Label = null
  @FXML protected var PasswordTitleLabel: Label = null
  @FXML protected var TitleLabel: Label = null
  @FXML protected var LoginBTN: Button = null

  @FXML def actionLoginBTN(Event: ActionEvent): Unit = {}


  override def initialize(location: URL, resources: ResourceBundle): Unit = {
  }
}
