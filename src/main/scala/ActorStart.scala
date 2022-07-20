import akka.actor.typed.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import java.net.{DatagramSocket, InetAddress, ServerSocket}
import scala.util.{Try, Using}

object ActorStart{

  def start(controller: ChatMainControllerLogic, ip:String, port:String):
  ActorSystem[ControllerActor.ChatEvent] = {
    def findFreePort(): Try[Int] = Using(new ServerSocket(0)) (_.getLocalPort)
    val machineIP = getIP()
    println(machineIP)
    val freePort = findFreePort().getOrElse(0)
    val config: Config = if(ip.isEmpty) getConfigHost(freePort,machineIP)
    else getConfigConnect(port,ip, freePort,machineIP)
    ActorSystem( ActorMain(controller), "ControllerActor",config)
  }

  def getIP(): String = {
    Using(new DatagramSocket) { systemIP =>
      systemIP.connect(InetAddress.getByName("8.8.8.8"), 10002)
      systemIP.getLocalAddress.getHostAddress
    }.getOrElse("localhost")
  }

  def getConfigConnect(port: String, ip: String, freePort:Int, machineIP:String): Config = {
    ConfigFactory.parseString(
      s"""
      akka.cluster.seed-nodes = ["akka://ControllerActor@$ip:$port", "akka://ControllerActor@$machineIP:$freePort"]
      akka.remote.artery.canonical.hostname = $machineIP
      akka.remote.artery.canonical.port = 0
    """).withFallback(ConfigFactory.load())
  }

  def getConfigHost(freePort:Int, machineIP:String): Config = {
    ConfigFactory.parseString(
      s"""
      akka.cluster.seed-nodes = ["akka://ControllerActor@$machineIP:$freePort"]
      akka.remote.artery.canonical.hostname = $machineIP
      akka.remote.artery.canonical.port = $freePort
    """).withFallback(ConfigFactory.load())
  }
}
