package com.example.finalproject;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Exercise {

    static DatabaseConnector databaseConnector = new DatabaseConnector();

    @FXML
    private Button logout;
    @FXML
    private Button workoutPage;
    @FXML
    private Button trainerPage;
    @FXML
    private Button exercisePage;
    @FXML
    private Button accountPage;

    @FXML
    private TextField groupName;

    @FXML
    private Button generate;


    @FXML private TableView<ExerciseObject> tableView;
    @FXML private TableColumn<ExerciseObject, String> nameCol;
    @FXML private TableColumn<ExerciseObject, String> descCol;

    public static class ExerciseObject{
        public SimpleStringProperty name;
        public SimpleStringProperty description;
        public ExerciseObject(String name, String description){
            this.name = new SimpleStringProperty(name);
            this.description = new SimpleStringProperty(description);
        }

        public String getName(){
            return name.get();
        }
        public String getDescription(){
            return description.get();
        }



    }
    public void populateTable(ActionEvent event) throws IOException {
        tableView.setItems(getExercises());
    }

    public void initialize(){
        nameCol.setCellValueFactory(new PropertyValueFactory<ExerciseObject, String>("name"));
        descCol.setCellValueFactory(new PropertyValueFactory<ExerciseObject, String>("description"));

    }
    public ObservableList<ExerciseObject> getExercises() {
        ObservableList<ExerciseObject> exercises = FXCollections.observableArrayList();

        databaseConnector.connect();
        databaseConnector.connect();
        List<Map<String, Object>> rows = databaseConnector
                .runParametrizedQuery("SELECT * FROM Exercises WHERE egroup = ?", groupName.getText());

        for (Map<String, Object> row : rows) {
            String mapname = (String) row.get("name");
            String mapdescription = (String) row.get("description");

            exercises.add(new ExerciseObject(mapname, mapdescription));

            databaseConnector.close();
        }

        return exercises;
    }

    public void userLogout(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("hello-view.fxml");
    }

    public void changeToWorkout(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Workout.fxml");
    }

    public void changeToTrainer(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Trainer.fxml");
    }

    public void changeToExercise(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Exercises.fxml");
    }

    public void changeToAccount(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("Account.fxml");
    }
}
