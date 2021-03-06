import ControllerActor._
import akka.actor.typed.ActorSystem
import akka.cluster.Cluster
import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.geometry.Insets
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, Label}
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import java.io._
import java.net.URL
import java.util.ResourceBundle
import scala.collection.mutable.ListBuffer

object ChatMainControllerLogic {
  def initializeFrame(login: String, app: Main, ip: String, port: String): Unit = {
    val loader = new FXMLLoader(getClass.getResource("FXML/ChatSample.fxml"))
    val root: Parent = loader.load()
    val controller: ChatMainControllerLogic = loader.getController
    val stage = new Stage()

    controller.login = login
    controller.chatServices = new ChatServices(login)
    stage.setTitle("Chat: " + login)
    stage.setScene(new Scene(root))
    stage.setResizable(false)
    stage.show()
    controller.chatServices.createRoom()
    controller.loadChatPanel()

    val actorSystem = ActorStart.start(controller, ip, port)
    val cluster = Cluster(actorSystem)

    app.actorSystem = actorSystem
    controller.actorSystem = actorSystem
    controller.printIP(actorSystem.address.toString)
    controller.userOnline()
  }
}

class ChatMainControllerLogic extends ChatMainController {
  var chatType = ""
  var activeChatUser = ""
  var login = ""
  var chatServices: ChatServices = _
  var actorSystem: ActorSystem[ControllerActor.ChatEvent] = _
  var onlineUsersList = ListBuffer[String]()

  def userOnline(): Unit = {
    Thread.sleep(4000)
    actorSystem ! UserOnline(login)
  }

  def printIP(ip: String): Unit = {
    ipLabel.setText(ip.substring(23))
  }

  def addOnlineUser(userName: String): Unit = {
    var addToOnlineList: Boolean = true
    onlineUsersList.foreach(user =>
      if (user == userName) {
        addToOnlineList = false
      }
    )
    if (addToOnlineList) {
      actorSystem ! UserOnline(login)
      onlineUsersList += userName
    }
    loadChatPanel()
  }

  def PrintOnlineUser(): Unit = {
    val titleOnlineBorder = new Label
    titleOnlineBorder.setText("Online chat")
    FriendListVbox.getChildren.add(titleOnlineBorder)
    onlineUsersList.foreach(userOnline => {
      val label = new Label
      label.setText(userOnline)
      labelOnClick(label)
    })
  }

  def delOfflineUser(userName: String): Unit = {
    onlineUsersList.foreach(user =>
      if (user == userName) {
        onlineUsersList -= userName
      }
    )
    loadChatPanel()
  }

  override def actionChatBTNSend(Event: ActionEvent): Unit = {
    if (ChatTFMessage.getText.nonEmpty && chatType.trim.nonEmpty) {
      printUserMessage()
      ChatScrollPane.setVvalue(1)
      ChatTFMessage.setText("")
    }
  }

  override def actionAddFriendBTN(Event: ActionEvent): Unit = {
    if (chatServices.createFile(NameAddFriendTF.getText)) {
      addFriendIntoFriendList()
      NameAddFriendTF.setText("")
    } else {
      val alert = new Alert(AlertType.WARNING)
      alert.setTitle("error")
      alert.setContentText("friend add failed")
      alert.show()
    }
  }

  def printReceivedPrivateMessage(textMessage: String, receiveName: String): Unit = {
    if (receiveName == activeChatUser) {
      val label = new Label
      label.setFont(new Font("Arial", 18.0))
      label.setTextFill(Color.web("#D33CB5"))
      label.setText(textMessage)
      label.setWrapText(true)
      label.setPadding(new Insets(0, 0, 10, 0))
      VBoxChatMessage.getChildren.addAll(label)
    }
    actorSystem ! MessageDelivered(receiveName, login)
    chatServices.writeMessageToFile(receiveName, textMessage, chatServices.PRIVATE_CHAT)
    VBoxChatMessage.getChildren.clear()
    loadChat(chatServices.getActivePath())
    loadChatPanel()
  }

  def printReceivedPublicMessage(textMessage: String, roomName: String): Unit = {
    if (roomName == activeChatUser) {
      val label = new Label
      label.setFont(new Font("Arial", 18.0))
      label.setTextFill(Color.web("#D33CB5"))
      label.setText(textMessage)
      label.setWrapText(true)
      label.setPadding(new Insets(0, 0, 10, 0))
      VBoxChatMessage.getChildren.addAll(label)
    }
    chatServices.writeMessageToFile(roomName, textMessage, chatServices.PUBLIC_CHAT)
    VBoxChatMessage.getChildren.clear()
    loadChat(chatServices.getActivePath())
    loadChatPanel()
  }

  def printUserMessage(): Unit = {
    if (ChatTFMessage.getText.nonEmpty) {
      val message = login + " => " + ChatTFMessage.getText
      val label = new Label
      val fileWriter = new FileWriter(chatServices.getActivePath(), true)
      fileWriter.write(message + "\n")
      fileWriter.close()
      label.setFont(new Font("Arial", 18.0))
      label.setTextFill(Color.web("#0076a3"))
      label.setText(message)
      label.setWrapText(true)
      label.setPadding(new Insets(0, 0, 10, 0))
      VBoxChatMessage.getChildren.addAll(label)
      if (chatType == chatServices.PUBLIC_CHAT) {
        actorSystem ! SendMessage(message, activeChatUser, login)
      } else {
        actorSystem ! SendMessageToRoom(message, activeChatUser, login)
      }
    }
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
    val label = new Label
    label.setText(nameFriend)
    labelOnClick(label)
  }

  def labelOnClick(label: Label): Unit = {
    label.setPrefWidth(110.0)
    label.setPrefHeight(39.0)
    label.setStyle("-fx-background-color: #01191A; -fx-border-color: #499094;")
    label.setOnMouseClicked(_ => {
      VBoxChatMessage.getChildren.clear()
      chatServices.setActiveChat(label.getText)
      chatServices.createFile(label.getText)
      UserNameLabel.setText(label.getText)
      chatType = chatServices.PUBLIC_CHAT
      loadChat(chatServices.getActivePath())
      activeChatUser = label.getText.trim
    })
    FriendListVbox.getChildren.add(label)
  }

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    AddFriendPanel.setVisible(false)
    ChatScrollPane.setFitToWidth(true)
  }

  def loadChatPanel(): Unit = {
    FriendListVbox.getChildren.clear()
    loadRoom()
    loadStoryChat()
    PrintOnlineUser()
  }

  def loadStoryChat(): Unit = {
    //???????????????? ??????????(??????????????????)
    val titleFriendBorder = new Label
    titleFriendBorder.setText("Friend chat")
    FriendListVbox.getChildren.add(titleFriendBorder)
    chatServices.loadFile(chatServices.PATH_TO_CHAT).foreach { f: File =>
      val label = new Label
      label.setText(f.getName.substring(0, f.getName.length - 4))
      labelOnClick(label)
    }
  }

  def loadRoom(): Unit = {
    //???????????????? ??????????(??????????????????)
    val titleRoomBorder = new Label
    titleRoomBorder.setText("Room chat")
    FriendListVbox.getChildren.add(titleRoomBorder)
    chatServices.loadFile(chatServices.PATH_TO_ROOM).foreach { f: File =>
      val label = new Label
      label.setText(f.getName.substring(0, f.getName.length - 4))
      //labelOnClick(label)
      label.setPrefWidth(130.0)
      label.setPrefHeight(39.0)
      label.setStyle("-fx-background-color: #010f1a; -fx-border-color: #949049;")
      label.setOnMouseClicked(_ => {
        VBoxChatMessage.getChildren.clear()
        chatServices.setActiveRoom(label.getText)
        chatType = chatServices.PRIVATE_CHAT
        UserNameLabel.setText(label.getText)
        loadChat(chatServices.getActivePath())
        activeChatUser = label.getText.trim
      })
      FriendListVbox.getChildren.add(label)
    }
  }

  def loadChat(path: String): Unit = {
    chatServices.loadChatMessage(path).foreach { f: String =>
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

