import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import javafx.fxml.FXMLLoader


object ControllerActor{
  //trait RoomCommand
  trait ChatEvent
  // трейт – это сущность, которая инкапсулирует поля или методы
  case class SendMessage (textMessage:String,recipientName:String) extends ChatEvent
  // сообшение оправленные(личные)
  case class ReceiveMessages (textMessage:String,senderName:String) extends ChatEvent
  // сообщения принятые(личные)
  case class SendMessageToConversation (textMessage:String,recipientName:String) extends ChatEvent
  // сообщения отправленные в беседу
  case class ReceiveMessageToConversation (textMessage:String,recipientName:String) extends ChatEvent
  // сообщения полученные из беседы

  //trait ChatFriendEvent
  case class FriendRequest (friendName: String) extends ChatEvent
  // запроса в друзья
  case class ReplyToFriendRequest (friendName: String, reply: String) extends ChatEvent
  // оваета на запрос в друзья

  val loader = new FXMLLoader(getClass.getResource("FXML/ChatSemple.fxml"))
  val controller: ChatMainControllerLogic = loader.getController

  def apply(): Behavior[ChatEvent] ={
    behaviorChat()
  }
  private def behaviorChat(): Behavior[ChatEvent] =
    Behaviors.receive { (context, message) =>
      message match {
        case SendMessage(textMessage, recipientName) =>
          //Отправка сообщений
          println(textMessage + " message for " + recipientName)
          Behaviors.same

        case ReceiveMessages(textMessage: String, senderName: String) =>
          println(message)
          println(senderName)
          println(controller.login)
          if ("@"+senderName.trim == "@"+controller.login.trim){ //del @ senderName
            println("Message received")
            controller.printReceivedMessage(textMessage)
          }else{
            println("Message not received")
            controller.printReceivedMessage("Message not received")
          }
          Behaviors.same

        case SendMessageToConversation(textMessage: String, recipientName: String) =>
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

