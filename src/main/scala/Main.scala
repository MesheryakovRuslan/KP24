import ControllerActor.{ChatEvent, UserOffline, controllerChat}
import akka.actor.typed.ActorSystem
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class Main extends Application {
  var actorSystem: ActorSystem[ChatEvent] = _

  def start(stage: Stage): Unit = {
    val loader = new FXMLLoader(getClass.getResource("FXML/ChatLogIn.fxml"))
    val root: Parent = loader.load()
    stage.setTitle("Chat: LogIn")
    stage.setScene(new Scene(root))
    val controllerLogin: ChatLoginControllerLogic = loader.getController
    controllerLogin.app = this
    stage.setResizable(false)
    stage.show()
  }

  override def stop(): Unit = {
    super.stop()
    actorSystem ! UserOffline(controllerChat.login)
    Option(actorSystem).foreach(f =>
      f.terminate()
    )
  }
}
