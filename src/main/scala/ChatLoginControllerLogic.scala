import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class ChatLoginControllerLogic extends ChatLoginController {
  //LogIn in chat and open window chat
  override def actionLoginBTN(event: ActionEvent): Unit = {
    if (LoginTF.getText.trim.nonEmpty & PasswordTF.getText.trim.nonEmpty) {
      LoginBTN.getScene.getWindow.hide()
      ChatMainControllerLogic.initializeFrame(LoginTF.getText.trim)
    }
  }
}