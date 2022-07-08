import ControllerActor.{ChatMessageEvent, SendMessage}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import javafx.fxml.FXMLLoader
import javafx.scene.Parent

object ControllerActor {
  trait RoomCommand
  trait ChatEvent                                                                     // трейт – это сущность, которая инкапсулирует поля или методы
  case class SendMessage (textMessage:String,recipientName:String) extends ChatEvent  // сообшение оправленные(личные)
  //case class ReceiveMessages (textMessage:String,senderName:String) extends ChatMessageEvent // сообщения принятые(личные)
  //case class SendMessageToConversation () extends RoomCommand                              // сообщения отправленные в беседу
  //case class ReceiveMessageToConversation () extends RoomCommand                           // сообщения полученные из беседы

  //trait ChatFriendEvent
  //case class FriendRequest (friendName:String) extends ChatEvent extends ChatFriend      // запроса в друзья
  //case class ReplyToFriendRequest (friendName:String, reply: Boolean) extends ChatFriend // оваета на запрос в друзья

  def apply(): Behavior[ChatEvent] = Behaviors.receiveMessage({message =>
      println("Actor print " + message.toString)
      Behaviors.same
    })

}
//class ControllerActor(message:ChatMessageEvent){
//  message match {
//    case SendMessage(textMessage, recipientName) =>
//    println("Actor print" + textMessage,recipientName)
//  }
//}
//https://doc.akka.io/docs/akka/current/typed/distributed-pub-sub.html  <--

