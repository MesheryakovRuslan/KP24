import akka.actor.typed.ActorSystem
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class Main extends Application {
  def start(stage: Stage): Unit = {
//    val system: ActorSystem[MainActor.SayHello] = ActorSystem(MainActor(),"hi")
//
//    system ! MainActor.SayHello("World")
//    system ! MainActor.SayHello("Akka")
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
