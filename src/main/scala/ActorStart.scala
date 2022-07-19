import akka.actor.typed.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}

//class ActorStart(port: Int, controller: ChatMainControllerLogic, app: Main) {
class ActorStart(controller: ChatMainControllerLogic, app: Main, ip: String, port: String) {

    var nodeIP = ip
    var nodePort = port
    if(nodeIP.isEmpty || nodePort.isEmpty){
      nodeIP = "127.0.0.1"
      nodePort = "2551"
    }
//  var search: Boolean = true
//  var port = portConnect
//  do {
//    try {
//      controller.actorSystem = ActorSystem(ActorMain(controller), "AkkaController", getConfig(port))
//      search = false
//    }
//    catch {
//      case _: Throwable => port += 1
//    }
//  }
//  while (search && portConnect < 2555)


  controller.actorSystem = ActorSystem(ActorMain(controller), "AkkaController", getConfig(port,ip))
  app.actorSystem = controller.actorSystem

  def getConfig(port: String, ip: String): Config = {
    ConfigFactory.parseString(
      s"""
      akka.cluster.seed-nodes = ["akka://AkkaController@$ip:$port"]
      akka.remote.artery.canonical.hostname = "127.0.0.1"
      akka.remote.artery.canonical.port = $port
    """).withFallback(ConfigFactory.load())
  }

}
