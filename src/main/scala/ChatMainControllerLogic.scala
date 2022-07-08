import ControllerActor._
import akka.actor.typed.ActorSystem
import com.typesafe.config.ConfigFactory
import javafx.event.ActionEvent
import javafx.geometry.Insets
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Label}
import javafx.scene.paint.Color
import javafx.scene.text.Font

import java.io._
import java.net.URL
import java.util.ResourceBundle
import scala.io.Source


class ChatMainControllerLogic extends ChatMainController {

  var activeChatPath = ""
  var activeChatUser = "@all"
  var login = ""
  val actorSystem: ActorSystem[ControllerActor.ChatEvent] = ActorSystem(ControllerActor(), "AkkaController")
  var chatController=""

  def startClusterSystem(): Unit = {
    val config = ConfigFactory.parseString(s"""
      akka {
      actor {
        provider = "cluster"
      }
      remote.artery {
        canonical {
          hostname = application.conf
          port = 2551
        }
      }

      cluster {
        seed-nodes = [
          "akka://ClusterSystem@127.0.0.1:2551",
          "akka://ClusterSystem@127.0.0.1:2552"]

        downing-provider-class = "akka.cluster.sbr.SplitBrainResolverProvider"
      }
      }
     """)
  }



  override def actionChatBTNSend(Event: ActionEvent): Unit = {

    if (ChatTFMessage.getText.nonEmpty) {

      printUserMessage()
      ChatScrollPane.setVvalue(1)
      ChatTFMessage.setText("")
    }
  }

  override def actionAddFriendBTN(Event: ActionEvent): Unit = {

    val PathToFile = "src/main/resources/Chats/" + NameAddFriendTF.getText + ".txt"
    val fileObject = new File(PathToFile)
    val flag = fileObject.createNewFile()

    if (flag) {

      addFriendIntoFriendList()
      print(fileObject.getPath)
      val printWriter = new PrintWriter(fileObject)
      printWriter.close()
      NameAddFriendTF.setText("")
      ChatUIDTF.setText("")
    } else {

      val alert = new Alert(AlertType.WARNING)
      alert.setTitle("error")
      alert.setContentText("friend add failed")
      alert.show()
    }
  }

  override def actionConversationBTN(Event: ActionEvent): Unit = {

  }

  def printReceivedMessage(textMessage:String): Unit = {
    val label = new Label
    label.setFont(new Font("Arial", 18.0))
    label.setTextFill(Color.web("##D33CB5"))
    label.setText(textMessage)
    label.setWrapText(true)
    label.setPadding(new Insets(0, 0, 10, 0))

    VBoxChatMessage.getChildren.addAll(label)

    val fileWriter = new FileWriter(activeChatPath,true)
    fileWriter.write(textMessage + "\n")
    fileWriter.close()
  }

  def printUserMessage(): Unit = {

    val message = login + " => " +  ChatTFMessage.getText
    val label = new Label
    val fileWriter = new FileWriter(activeChatPath,true)
    //println(message)
    fileWriter.write(message + "\n")
    fileWriter.close()

    label.setFont(new Font("Arial", 18.0))
    label.setTextFill(Color.web("#0076a3"))
    label.setText(message)
    label.setWrapText(true)
    label.setPadding(new Insets(0, 0, 10, 0))

    VBoxChatMessage.getChildren.addAll(label)

    actorSystem ! SendMessage(message,activeChatUser)
    actorSystem ! ReceiveMessages("@"+activeChatUser+" Привет",login)
  }

  override def actionVueFriendBTN(Event: ActionEvent): Unit = {

    if (AddFriendPanel.isVisible) {

      AddFriendPanel.setVisible(false)
      FriendListScrollPane.setPrefHeight(470)
    } else {

      AddFriendPanel.setVisible(true)
    }
  }

  def addFriendIntoFriendList(): Unit = {

    val nameFriend = NameAddFriendTF.getText
    val UIDFriend = ChatUIDTF.getText
    val label = new Label

    label.setText(nameFriend)
    label.setPrefWidth(110.0)
    label.setPrefHeight(39.0)
    label.setStyle("-fx-background-color: #01191A; -fx-border-color: #499094;")
    label.setOnMouseClicked(ActionEvent => {

      VBoxChatMessage.getChildren.clear()
      activeChatPath = "src/main/resources/Chats/" + label.getText + ".txt"
      UserNameLabel.setText(label.getText)
      loadChat(activeChatPath)
      activeChatUser = "@"+label.getText.trim
      println(activeChatPath)
      println(activeChatUser)

    })

    FriendListVbox.getChildren.add(label)
  }

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    AddFriendPanel.setVisible(false)
    ChatScrollPane.setFitToWidth(true)
    //Загрузка друзей в FriendListVbox
    val file = new File("src/main/resources/Chats")
    val arrFiles = file.listFiles

    arrFiles.foreach{f:File =>

      val label = new Label
      label.setText(f.getName.substring(0,f.getName.length-4))
      label.setPrefWidth(130.0)
      label.setPrefHeight(39.0)
      label.setStyle("-fx-background-color: #01191A; -fx-border-color: #499094;")
      label.setOnMouseClicked(ActionEvent =>{

        VBoxChatMessage.getChildren.clear()
        activeChatPath = "src/main/resources/Chats/"+ label.getText+".txt"
        //println(activeChat)
        UserNameLabel.setText(label.getText)
        loadChat(activeChatPath)
        activeChatUser = "@"+label.getText.trim
        println(activeChatPath)
        println(activeChatUser)
      })

      UserNameLabel.setText("")
      FriendListVbox.getChildren.add(label)
    }
  }

  def  loadChat(path: String) {

    val lines = Source.fromFile(path).getLines.toList
    lines.foreach{f:String=>

      val label = new Label
      label.setFont(new Font("Arial", 18.0))
      label.setTextFill(Color.web("#0076a3"))
      val fixed = new String(f.getBytes())
      label.setText(fixed)
      label.setWrapText(true)
      label.setPadding(new Insets(0, 0, 10, 0))
      VBoxChatMessage.getChildren.addAll(label)

    }
  }
}

