package menu;

import javafx.application.Application;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import objects.*;

import java.io.*;
import java.util.*;

/**
 * The type Leaderboard app.
 *
 * @author Cal
 */
public class LeaderboardController extends Application {
    @FXML
    private TableView<PlayerProfile> tableView;
    private Stage stage;
    private final String databaseName;

    public LeaderboardController(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Leaderboard");
        Pane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        tableView = (TableView<PlayerProfile>) root.getChildren().get(1);
        Scene scene = new Scene(root, 350, 500);
        stage.setScene(scene);

        addColumns();
        stage.show();
    }
    
    @FXML
    private void addColumns() {
        PlayerDatabase playerDatabase = new PlayerDatabase();
        playerDatabase.start(getDatabaseName());
        ObservableList<PlayerProfile> data = FXCollections.observableArrayList(playerDatabase.getAllActiveProfiles());

        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("playerName"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("victories"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("losses"));
        tableView.setItems(data);
    }

    public void exit() {
        if (stage != null) {
            stage.close();
        }
    }

    public String getDatabaseName() {
        return databaseName;
    }
}