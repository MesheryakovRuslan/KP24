import akka.actor.typed.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}

//class ActorStart(port: Int, controller: ChatMainControllerLogic, app: Main) {
class ActorStart(portConnect: Int, controller: ChatMainControllerLogic, app: Main) {

  var search: Boolean = true
  var port = portConnect
  do {
    try {
      controller.actorSystem = ActorSystem(ActorMain(controller), "AkkaController", getConfig(port))
      search = false
    }
    catch {
      case _: Throwable => port += 1
    }
  }
  while (search && portConnect < 2555)

  app.actorSystem = controller.actorSystem

  def getConfig(port: Int): Config = {
    ConfigFactory.parseString(
      s"""
      akka.cluster.seed-nodes = ["akka://AkkaController@127.0.0.1:2551"]
      akka.remote.artery.canonical.hostname = "127.0.0.1"
      akka.remote.artery.canonical.port = $port
    """).withFallback(ConfigFactory.load())
  }
}
