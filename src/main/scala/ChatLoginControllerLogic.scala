import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class ChatLoginControllerLogic extends ChatLoginController {
  //LogIn in chat and open window chat
  override def actionLoginBTN(event: ActionEvent): Unit = {
    if (LoginTF.getText.trim.nonEmpty & PasswordTF.getText.trim.nonEmpty) {

      val loader = new FXMLLoader(getClass.getResource("FXML/ChatSemple.fxml"))
      val root:Parent = loader.load()
      val controller:ChatMainControllerLogic = loader.getController
      val stage = new Stage()


      controller.login = LoginTF.getText.trim
      stage.setTitle("Chat: Main")
      stage.setScene(new Scene(root))
      stage.setResizable(false)
      stage.show()

      LoginBTN.getScene.getWindow.hide()
    }
  }
}