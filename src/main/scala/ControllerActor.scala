import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import javafx.application.Platform


object ControllerActor {
  //trait RoomCommand
  trait ChatEvent

  // трейт – это сущность, которая инкапсулирует поля или методы
  case class SendMessage(textMessage: String, recipientName: String, senderName: String) extends ChatEvent

  // сообшение оправленные(личные)
  case class SendMessageToRoom(textMessage: String, roomName: String, senderName: String) extends ChatEvent

  // отрисовывает чат с новым пользователем
  case class MessageDelivered(replyTo: String, responsible: String) extends ChatEvent

  // отрисовка пользователя онлайн
  case class UserOnline(userName: String) extends ChatEvent

  // отрисовка пользователя онлайн
  case class UserOffline(userName:String) extends ChatEvent

  var controllerChat: ChatMainControllerLogic = _

  def apply(controller: ChatMainControllerLogic): Behavior[ChatEvent] = {
    controllerChat = controller
    behaviorChat()
  }

  private def behaviorChat(): Behavior[ChatEvent] =
    Behaviors.receive { (context, message) =>
      message match {
        case SendMessage(textMessage, recipientName, senderName) =>
          if (recipientName == controllerChat.login) {
            Platform.runLater(() => controllerChat.printReceivedPrivateMessage(textMessage, senderName))
          }
          println(textMessage + " message for " + recipientName + "/SendMessage")
          Behaviors.same

        case SendMessageToRoom(textMessage, roomName, senderName) =>
          if (roomName.nonEmpty && senderName != controllerChat.login) {
            Platform.runLater(() => controllerChat.printReceivedPublicMessage(textMessage, roomName))
            println("message: " + textMessage + " sender: " + senderName + " room => " + roomName + "/SendMessageToRoom")
          }
          Behaviors.same

        case MessageDelivered(replyTo: String, responsible: String) =>
          if (replyTo == controllerChat.login) {
            Platform.runLater(() => controllerChat.chatServices.createFile(responsible))
            Platform.runLater(() => controllerChat.loadChatPanel())
            println("Actor MessageDelivered ok " + replyTo)
          }
          println("ok")
          Behaviors.same

        case UserOnline(userName) =>
          if (userName != controllerChat.login) {
            println(userName + " => online")
            Platform.runLater(() => controllerChat.addOnlineUser(userName))
          }else{
            println(userName + " => not online")
          }
          Behaviors.same

        case UserOffline(userName) =>
          Platform.runLater(() => controllerChat.delOfflineUser(userName))
          Behaviors.same

        case _ =>
          println("!!!")
          Behaviors.same
      }
    }
}

