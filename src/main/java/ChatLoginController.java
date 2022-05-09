import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatLoginController implements Initializable {
    @FXML
    protected AnchorPane ChatLogIn;

    @FXML
    protected TextField NameTF;

    @FXML
    protected TextField PasswordTF;

    @FXML
    protected Label NameTitleLabel;

    @FXML
    protected Label PasswordTitleLabel;

    @FXML
    protected Label TitleLabel;

    @FXML
    protected Button LoginBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
