import akka.actor.typed.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}

//class ActorStart(port: Int, controller: ChatMainControllerLogic, app: Main) {
class ActorStart(controller: ChatMainControllerLogic, app: Main, ip: String, port: String) {

    var nodeIP = ip
    var nodePort = port
    if(nodeIP.isEmpty || nodePort.isEmpty){
      controller.actorSystem = ActorSystem(ActorMain(controller), "AkkaController", getConfigHost())
      app.actorSystem = controller.actorSystem
    }else{
      controller.actorSystem = ActorSystem(ActorMain(controller), "AkkaController", getConfigConnect(nodePort,nodeIP))
      app.actorSystem = controller.actorSystem
    }




  def getConfigConnect(port: String, ip: String): Config = {
    ConfigFactory.parseString(
      s"""
      akka.cluster.seed-nodes = ["akka://AkkaController@$ip:$port"]
      akka.remote.artery.canonical.hostname = "0.0.0.0"
      akka.remote.artery.canonical.port = $port
    """).withFallback(ConfigFactory.load())
  }

  def getConfigHost(): Config = {
    ConfigFactory.parseString(
      s"""
      akka.remote.artery.canonical.hostname = "0.0.0.0"
      akka.remote.artery.canonical.port = $port
    """).withFallback(ConfigFactory.load())
  }
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