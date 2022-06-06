//import akka.actor.ActorRef
//import akka.actor.typed.Behavior
//import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
//
//object MainAktor {
//  trait JsonSerializable
//
//  case class sendMessage(FriendName: String, Message: String)
//  case class addFriend(FriendName: String, UIDFriend: String)
//
//  protected def createActor(controllerLogic: ChatMainControllerLogic): Behavior[String] = {
//    Behaviors.setup(context => new MainAktor(context,controllerLogic))
//  }
//
//  def apply(controller: ChatMainControllerLogic):Behavior[String] = createActor(controller)
//
//  class MainAktor(value: ActorContext[String], logic: ChatMainControllerLogic){
//    val login = login
//  }
//
//}