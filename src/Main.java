
import board.Board;
import player.Player;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import screen.controller.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    private static Board board;
    private static Player player1, player2;
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(screen.application.MainScreen.class.getResource("screen/resource/MainScreen.fxml"));
            fxmlLoader.setController(new MainScreenController(board, player1, player2));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Image icon = new Image("main/demo/icon.jpg");
            stage.getIcons().add(icon);
            stage.setTitle("O an quan");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void logout(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Are you sure you want to quit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        board = new Board();
        player1 = new Player();
        player2 = new Player();

        launch();
    }
}