import javafx.event.ActionEvent
import javafx.fxml.{FXML, FXMLLoader}
import javafx.stage.Stage
import javafx.scene.{Parent, Scene}


class ChatLoginControllerLogic extends ChatLoginController {
  //LogIn in chat and open window chat
   override def actionLoginBTN(event: ActionEvent): Unit = {
     val root: Parent = FXMLLoader.load(getClass.getResource("/FXML/ChatSemple.fxml"))
     val stage = new Stage()
     stage.setTitle("Chat: Main")
     stage.setScene(new Scene(root))
     stage.setResizable(false)
     stage.show()


     LoginBTN.getScene.getWindow.hide()
    }
}

