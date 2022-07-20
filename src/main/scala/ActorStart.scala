import akka.actor.typed.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import java.net.ServerSocket

class ActorStart(controller: ChatMainControllerLogic, app: Main, ip: String, port: String) {

    if (ip.isEmpty) {
      controller.actorSystem = ActorSystem(ActorMain(controller), "AkkaController", getConfigHost())
      app.actorSystem = controller.actorSystem
    } else {
      try {
        controller.actorSystem = ActorSystem(ActorMain(controller), "AkkaController", getConfigConnect(port, ip))
        println("seed-nodes" + ip + ":" + port)
        //println("app" + ip + ":" + nodePort)
      }
      catch {
        case _: Throwable =>
      }
    }

    controller.printIP(controller.actorSystem.address.port)


  def getConfigConnect(port: String, ip: String): Config = {
    ConfigFactory.parseString(
      s"""
      akka.cluster.seed-nodes = ["akka://AkkaController@$ip:$port"]
      akka.remote.artery.canonical.hostname = "192.168.0.0"
      akka.remote.artery.canonical.port = 0
    """).withFallback(ConfigFactory.load())
  }

  def getConfigHost(): Config = {
    ConfigFactory.parseString(
      s"""
      akka.remote.artery.canonical.hostname = "192.168.0.0"
      akka.remote.artery.canonical.port = 0
    """).withFallback(ConfigFactory.load())
  }
}
