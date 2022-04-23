import javafx.application.Application
import javafx.application.Application.launch
import javafx.fxml.{FXML, FXMLLoader}
import javafx.stage.Stage

class Main extends Application {
  def main(args: Array[String]) = {
    //launch(args)
  }

  def start(stage: Stage): Unit = {
    val root = FXMLLoader.load(getClass.getResource("ChatSemple.fxml"))
    import javafx.scene.Scene
    stage.setTitle("Simple Chat")
    stage.setScene(new Scene(root, 600, 546))

  }

  @FXML
  def click(actionEvent: Nothing): Unit = {
    ChatBTNSend.setText("Hey!")
  }
}
