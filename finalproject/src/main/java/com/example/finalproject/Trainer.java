package com.example.finalproject;
import javafx.beans.property.SimpleIntegerProperty;
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

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Trainer {
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

    static DatabaseConnector databaseConnector = new DatabaseConnector();

    @FXML private TableView<TrainerObject> tableView;
    @FXML private TableColumn<TrainerObject, String> nameCol;
    @FXML private TableColumn<TrainerObject, String> descCol;
    @FXML private TableColumn<TrainerObject, String> numYears;


    public class TrainerObject{
        public SimpleStringProperty name;
        public SimpleStringProperty description;
        public SimpleStringProperty experience;
        public TrainerObject(String name, String description, String years){
            this.name = new SimpleStringProperty(name);
            this.description = new SimpleStringProperty(description);
            this.experience = new SimpleStringProperty(years);
        }

        public String getName() {
            return name.get();
        }
        public String getDescription(){
            return description.get();
        }

        public String getExperience() {
            return experience.get();
        }
    }
    public void populateTable(ActionEvent event) throws IOException {
        tableView.setItems(getTrainers());
    }

    public void initialize(){
        nameCol.setCellValueFactory(new PropertyValueFactory<TrainerObject, String>("name"));
        descCol.setCellValueFactory(new PropertyValueFactory<TrainerObject, String>("description"));
        numYears.setCellValueFactory(new PropertyValueFactory<TrainerObject, String>("experience"));

    }
    public ObservableList<TrainerObject> getTrainers() {
        ObservableList<TrainerObject> trainers = FXCollections.observableArrayList();

        databaseConnector.connect();
        databaseConnector.connect();
        List<Map<String, Object>> rows = databaseConnector
                .runParametrizedQuery("SELECT * FROM Trainer WHERE egroup = ?", groupName.getText());

        for (Map<String, Object> row : rows) {
            String name = (String) row.get("name");
            String description = (String) row.get("description");
            String experience = (String) row.get("experience");

            trainers.add(new TrainerObject(name, description, experience));
        }


        //            trainers.add(new Trainer.TrainerObject(mapname, mapdescription, mapyears));
        return trainers;
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
