//import ControllerActor.SendMessage
//import akka.actor.typed.ActorSystem
//
//
//object MainActor{
//  def startActorSystem(){
//    val ActorTest: ActorSystem[ControllerActor.SendMessage] =ActorSystem(ControllerActor(), "AkkaController")
//    ActorTest ! SendMessage("Message","Path")
//  }
//}