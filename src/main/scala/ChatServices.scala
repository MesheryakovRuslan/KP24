import java.io.{File, FileWriter}

class ChatServices {
  val PATH_TO_CHAT = "src/main/resources/Chats/"
  val PATH_TO_ROOM = "src/main/resources/Room/"
  val PUBLIC_CHAT = "chat"
  val PRIVATE_CHAT = "room"
  var activeChatPath = ""

  def createFile(nameFile:String): Boolean ={
    val pathToFile = PATH_TO_CHAT + nameFile.trim + ".txt"
    val fileObject = new File(pathToFile)
    val flag = fileObject.createNewFile()
    print(fileObject.getPath)
    flag
  }
  def writeMessageToFile (nameFile:String, message:String,directory:String): Unit ={
    if (directory.equals(PRIVATE_CHAT)){
      val fileWriter = new FileWriter(PATH_TO_CHAT + nameFile.trim + ".txt",true)
      fileWriter.write(message + "\n")
    }else {
      val fileWriter = new FileWriter(PATH_TO_ROOM + nameFile.trim + ".txt",true)
      fileWriter.write(message + "\n")
      fileWriter.close()
    }
  }

  def loadFile(path:String): Array [File] ={
    val fileFriend = new File(path)
    val arrFriend = fileFriend.listFiles
    arrFriend
  }

  def getActiveChat(): String = {
    activeChatPath
  }

  def setActiveChat(path:String): Unit = {
    activeChatPath = path
  }

}
