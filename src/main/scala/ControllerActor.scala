//import akka.actor.TypedActor.context
//import akka.actor.typed.scaladsl.Behaviors
//import akka.actor.typed.{ActorRef, Behavior}
//
//object ControllerActor {
////  trait RoomCommand
////  final case class getSession (userName : String, replyTo:ActorRef[ChatEvent]) extends RoomCommand
//
//  trait ChatEvent                                                                       //трейт – это сущность, которая инкапсулирует поля или методы
//  case class SendMessage (textMessage:String,recipientName:String) extends ChatEvent    //тип сообшение оправленные(личные)
//  case class ReceiveMessages (textMessage:String,senderName:String) extends ChatEvent   //тип сообщения принятые(личные)
////case class FriendRequest (friendName:String) extends ChatEvent                        //тип запроса в друзья
////case class ReplyToFriendRequest (friendName:String, reply: Boolean) extends ChatEvent //тип оваета на запрос в друзья
////case class SendMessageToConversation ()                                               //тип сообщения отправленные в беседу
////case class ReceiveMessageToConversation ()                                            //тип сообщения полученные из беседы
//
//  def apply():Behavior[ChatEvent]
//  Behaviors.same
//
//  def controllerActor(sessions: ActorRef[ChatEvent]):Behavior[ChatEvent] =
//    Behaviors.receive{(context,message) =>
//      message match {
//        case sendMessage(textMessage,recipientName) =>
//          //логика отправки сообщений
//          recipientName ! PostMessage(textMessage)
//
//      }
//    }
//  }
//
