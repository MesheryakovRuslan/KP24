import javafx.application.Application
import javafx.fxml.{FXML, FXMLLoader}
import javafx.stage.Stage
import javafx.scene.{Parent, Scene}

class Main extends Application {
  def start(stage: Stage): Unit = {
    try {
      val root: Parent = FXMLLoader.load(getClass.getResource("/FXML/ChatLogIn.fxml"))
      stage.setTitle("Chat: LogIn")
      stage.setScene(new Scene(root))
      stage.setResizable(false)
      stage.show()
    } catch {
      case e: Exception => println("exception caught: " + e);
    }
  }
}
