import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import javafx.application.Platform


object ControllerActor{
  //trait RoomCommand
  trait ChatEvent
  // трейт – это сущность, которая инкапсулирует поля или методы
  case class SendMessage (textMessage:String, recipientName:String, senderName:String, typeCommunication:String) extends ChatEvent
  // сообшение оправленные(личные)
  case class SendMessageToRoom (textMessage:String,recipientName:String) extends ChatEvent
  // сообщения отправленные (публичные)
  case class ReceiveMessageToConversation (textMessage:String,recipientName:String) extends ChatEvent
  // сообщения полученные (публичные)
  case class FriendRequest (friendName: String) extends ChatEvent
  // запроса в друзья
  case class ReplyToFriendRequest (friendName: String, reply: String) extends ChatEvent
  // оваета на запрос в друзья
  case class UserOnline (userName:String) extends ChatEvent


  var controllerChat: ChatMainControllerLogic = _

  def apply(controller : ChatMainControllerLogic): Behavior[ChatEvent] ={
    controllerChat = controller
    behaviorChat()
  }

  private def behaviorChat(): Behavior[ChatEvent] =
    Behaviors.receive { (context, message) =>
      message match {
        case SendMessage(textMessage, recipientName, senderName, typeCommunication) =>
          if (recipientName == controllerChat.login){
            Platform.runLater(() => controllerChat.printReceivedMessage(textMessage, senderName, typeCommunication))
          }else if(typeCommunication == "room"){
            Platform.runLater(() => controllerChat.printReceivedMessage(textMessage, senderName, typeCommunication))
          }
          println(textMessage + " message for " + recipientName)
          Behaviors.same
        case UserOnline(userName) =>{
          if(userName != controllerChat.login) {
            Platform.runLater(() => controllerChat.loadOnlineUser(userName))
          }
          Behaviors.same
        }

        case SendMessageToRoom(textMessage: String, recipientName: String) =>
          println("Actor SendMessageToConversation " + textMessage, recipientName)
          Behaviors.same

        case ReceiveMessageToConversation(textMessage: String, recipientName: String) =>
          println("Actor ReceiveMessageToConversation " + textMessage, recipientName)
          Behaviors.same

        case FriendRequest(friendName: String) =>
          println("Actor FriendRequest " + friendName)
          Behaviors.same

        case ReplyToFriendRequest(friendName: String, reply: String) =>
          println("Actor ReplyToFriendRequest " + friendName, reply)
          Behaviors.same

        case _ =>
          println("default")
          Behaviors.same
      }
    }
}

//https://habr.com/ru/company/piter/blog/266103/ <--
//https://doc.akka.io/docs/akka/current/typed/distributed-pub-sub.html  <--
//https://github.com/steklopod/akka/blob/akka_starter/src/main/resources/readmes/actors/actors.md <--

