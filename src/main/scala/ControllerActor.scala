import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import javafx.application.Platform


object ControllerActor{
  //trait RoomCommand
  trait ChatEvent
  // трейт – это сущность, которая инкапсулирует поля или методы
  case class SendMessage (textMessage:String, recipientName:String, senderName:String) extends ChatEvent
  // сообшение оправленные(личные)
  case class SendMessageToRoom (textMessage:String,roomName:String,senderName:String) extends ChatEvent
  // сообщения отправленные (публичные)
  case class MessageDelivered (replyTo:String,responsible:String) extends ChatEvent
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
        case SendMessage(textMessage, recipientName, senderName) =>
          if (recipientName == controllerChat.login){
            Platform.runLater(() => controllerChat.printReceivedPrivateMessage(textMessage, senderName))
          }
          println(textMessage + " message for " + recipientName + "/SendMessage")
          Behaviors.same

        case UserOnline(userName) =>{
          if(userName != controllerChat.login) {
            Platform.runLater(() => controllerChat.loadOnlineUser(userName))
          }
          Behaviors.same
        }

        case SendMessageToRoom(textMessage, roomName,senderName) =>
          if(roomName.nonEmpty && senderName != controllerChat.login) {
            Platform.runLater(() => controllerChat.printReceivedPublicMessage(textMessage, roomName))
            println("message: " + textMessage + " sender: " + senderName + " room => "+ roomName +"/SendMessageToRoom")
          }

          Behaviors.same
        case MessageDelivered(replyTo:String,responsible:String) =>
          if(replyTo == controllerChat.login) {
            Platform.runLater(() => controllerChat.chatServices.createFile(responsible))
            Platform.runLater(() => controllerChat.loadChatPanel())
            println("Actor MessageDelivered ok " + replyTo)
          }
          println("ok")
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

