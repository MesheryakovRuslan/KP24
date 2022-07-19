import javafx.event.ActionEvent
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.{Button, Label, TextField}
import javafx.scene.layout.AnchorPane

import java.net.URL
import java.util.ResourceBundle


class ChatLoginController extends Initializable {
  @FXML protected var ChatLogIn: AnchorPane = _
  @FXML protected var LoginTF: TextField = _
  @FXML protected var PortTF: TextField = _
  @FXML protected var IPTF: TextField = _
  @FXML protected var LoginTitleLabel: Label = _
  @FXML protected var PortLabelTitle: Label = _
  @FXML protected var TitleLabel: Label = _
  @FXML protected var LoginBTN: Button = _

  @FXML def actionLoginBTN(Event: ActionEvent): Unit = {}


  override def initialize(location: URL, resources: ResourceBundle): Unit = {
  }
}
