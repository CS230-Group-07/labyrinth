package menu;

import javafx.animation.*;
import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import javafx.util.*;
import objects.*;

import java.io.*;
import java.util.*;

/**
 * The type Menu controller.
 *
 * @author Luke Young
 * @author Callum Adair
 * @author Jeffrey
 */
public class MenuController extends Application {

    private Stage stage;
    private Stage gameStage;
    private Stage leaderboardStage;
    private Game game;
    private String boardName;
    private Board board;
    private ArrayList<PlayerProfile> players;
    @FXML
    private StackPane root;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Pane mainView;
    @FXML
    private Label textLabelID;
    @FXML
    private ImageView imageView;
    @FXML
    private Button musicOnOffButton;
    @FXML
    private Button playButton;
    @FXML
    private Button quitButton;
    private static MediaPlayer menuMusic;
    private ArrayList<PlayerDatabase> databases = new ArrayList<>();

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Make fade out.
     *
     * @param fadeOut the fade out
     */
    private void fadeOut(Pane fadeOut) {
        TranslateTransition windowTransition = new TranslateTransition();
        windowTransition.setDuration(Duration.millis(500));
        windowTransition.setNode(fadeOut);
        windowTransition.setFromX(700);
        windowTransition.setToX(0);
        windowTransition.play();
    }

    /**
     * Will play the music on the main menu screen
     *
     * @param filepath the filepath
     */
    public static void playMusic(String filepath) {
        Media music = new Media(new File(filepath).toURI().toString());
        menuMusic = new MediaPlayer(music);
        menuMusic.setCycleCount(MediaPlayer.INDEFINITE);
        menuMusic.play();
    }

    /**
     * Creates the Stage for the scenes and loads the MainMenu
     *
     * @param primaryStage
     */
    @FXML
    @Override
    public void start(Stage primaryStage) {
        playMusic("src\\resources\\MenuMusic.wav");

        stage = primaryStage;
        root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        borderPane = (BorderPane) root.getChildren().get(1);
        Label message = (Label) ((HBox) borderPane.getBottom()).getChildren().get(0);
        try {
            message.setText(MessageOfTheDay.finalMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackgroundEffects();

        Scene primaryScene = new Scene(root, 1125, 650);
        stage.setScene(primaryScene);
        stage.show();
    }

    /**
     * Button for turning the music on or off
     */
    @FXML
    private void musicOnOffButtonClick() {
        if (menuMusic.getStatus().equals(Status.PLAYING)) {
            menuMusic.pause();
            musicOnOffButton.setText("Music Off");
        } else {
            menuMusic.play();
            musicOnOffButton.setText("Music On");
        }
    }

    /**
     * Sets background effects.
     */
    private void setBackgroundEffects() {
        imageView = (ImageView) root.getChildren().get(0);
        imageView.fitWidthProperty().bind(root.widthProperty().multiply(1.1));
        imageView.fitHeightProperty().bind(root.heightProperty().multiply(1.1));
        imageView.setPreserveRatio(false);

        TranslateTransition backgroundMove = new TranslateTransition();

        backgroundMove.setDuration(Duration.millis(5000));
        backgroundMove.setNode(imageView);
        backgroundMove.setFromX(0);
        backgroundMove.setToX(30);
        backgroundMove.setAutoReverse(true);
        backgroundMove.setCycleCount(Animation.INDEFINITE);
        backgroundMove.play();

    }

    /**
     * This will create a second window that you will be taken to when you click the play button
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handlePlayButtonAction(ActionEvent actionEvent) {
        borderPane.getChildren().remove(mainView);
        try {
            mainView = FXMLLoader.load(MenuController.class.getResource("/menu/AnotherPlay.fxml"));
            fadeOut(mainView);
            borderPane.setCenter(mainView);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Handle quit button action.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handleQuitButtonAction(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        System.exit(100);
    }


    /**
     * Handle menu button action.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void handleMenuButton(ActionEvent actionEvent) {
        borderPane.getChildren().remove(mainView);
        try {
            StackPane menu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            mainView = (Pane) ((BorderPane) menu.getChildren().get(1)).getChildren().get(0);
            fadeOut(mainView);
            borderPane.setCenter(mainView);

            playButton = (Button) ((VBox) mainView.getChildren().get(0)).getChildren().get(0);
            playButton.setOnAction(this::handlePlayButtonAction);

            quitButton = (Button) ((VBox) mainView.getChildren().get(0)).getChildren().get(1);
            quitButton.setOnAction(this::handleQuitButtonAction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewGame(ActionEvent actionEvent) throws IOException {
        if (gameStage != null) {
            gameStage.close();
        }
        boardName = ((TextField) ((HBox) ((Button)
                actionEvent.getSource()).getParent()).getChildren().get(1)).getText();

        players = new ArrayList<>();
        players.add(new PlayerProfile("Cal", 1, 3, 1));
        players.add(new PlayerProfile("Luke", 3, 1, 2));

        board = FileManager.loadBoard(boardName, players);
        game = new Game(board);

        BorderPane gamePane = game.getPane();
        Scene scene = new Scene(gamePane, 800, 600, Color.WHITE);
        gameStage = new Stage();
        gameStage.setScene(scene);
        gameStage.show();
    }

    @FXML
    private void handleLoadGame(ActionEvent actionEvent) throws FileNotFoundException {
        if (gameStage != null) {
            gameStage.close();
        }
        String fileName = ((TextField) ((HBox) ((Button)
                actionEvent.getSource()).getParent()).getChildren().get(1)).getText();

        board = FileManager.loadGame(fileName);
        game = new Game(board);

        BorderPane gamePane = game.getPane();
        Scene scene = new Scene(gamePane, 800, 600, Color.WHITE);
        gameStage = new Stage();
        gameStage.setScene(scene);
        gameStage.show();
    }

    @FXML
    private void handleSaveGame(ActionEvent actionEvent) throws IOException {
        System.out.println(board);
        System.out.println(board.getHeight());
        System.out.println(board.getWidth());
        FileManager.saveGame(board, ((TextField) ((HBox) ((Button)
                actionEvent.getSource()).getParent()).getChildren().get(1)).getText());
    }

    /**
     * Will open up the leaderboard after this button is pressed
     *
     * @param actionEvent
     */
    @FXML
    private void openLeaderboard(ActionEvent actionEvent) {
        if (leaderboardStage == null) {
            leaderboardStage = new Stage();
            BorderPane leaderboardPane = Leaderboard.getLeaderboard(boardName);
            Scene leaderboard = new Scene(leaderboardPane, 350, 450);
            leaderboardStage.setScene(leaderboard);
            leaderboardStage.show();
        }
    }

    /**
     * Will return all player profiles
     *
     * @param actionEvent
     */
    @FXML
    private void getAllProfiles(ActionEvent actionEvent) {
        borderPane.setCenter(Profiles.getAllProfiles(databases));

    }

    private void gameFinishedListener(){
        game.getIsGameFinished().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(game.getIsGameFinished().getValue()){
                    
                }
            }
        });
    }
}
