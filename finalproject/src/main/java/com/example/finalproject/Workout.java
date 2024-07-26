package com.example.finalproject;
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
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Workout {

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


    @FXML private TableView<WorkoutObjects> tableView;
    @FXML private TableColumn<WorkoutObjects, String> dateCol;
    @FXML private TableColumn<WorkoutObjects, String> groupCol;
    @FXML private TableColumn<WorkoutObjects, String> trainerCol;
    @FXML private TableColumn<WorkoutObjects, String> ex1Col;
    @FXML private TableColumn<WorkoutObjects, String> ex2Col;
    @FXML private TableColumn<WorkoutObjects, String> ex3Col;

    @FXML private Text exercise1Text;
    @FXML private Text exercise2Text;
    @FXML private Text exercise3Text;
    @FXML private Button randomize;

    @FXML private TextField groupBox;
    @FXML private TextField dateBox;

    @FXML private Text trainerBox;
    @FXML private Button generate;

    @FXML private Button submit;


    public class WorkoutObjects{
        public SimpleStringProperty date;
        public SimpleStringProperty group;
        public SimpleStringProperty trainer;
        public SimpleStringProperty exercise1;
        public SimpleStringProperty exercise2;
        public SimpleStringProperty exercise3;
        public WorkoutObjects(String date, String group, String trainer,
                              String exercise1, String exercise2, String exercise3){
            this.date = new SimpleStringProperty(date);
            this.group = new SimpleStringProperty(group);
            this.trainer = new SimpleStringProperty(trainer);
            this.exercise1 = new SimpleStringProperty(exercise1);
            this.exercise2 = new SimpleStringProperty(exercise2);
            this.exercise3 = new SimpleStringProperty(exercise3);

        }

        public String getDate(){
            return date.get();
        }
        public String getGroup(){
            return group.get();
        }
        public String getTrainer(){
            return trainer.get();
        }
        public String getEx1(){
            return exercise1.get();
        }
        public String getEx2(){
            return exercise2.get();
        }
        public String getEx3(){
            return exercise3.get();
        }



    }

    private String IDexercise1;
    private String IDexercise2;
    private String IDexercise3;
    private String IDtrainer;


    public void submitWorkout(ActionEvent event) throws IOException {
        databaseConnector.connect();
        Login.AccountObject cur = Login.currentAccount;
        String uID = cur.uid;
        String date = dateBox.getText();

        String query = "INSERT INTO Workout (uID, tID, date, e1ID, e2ID, e3ID, egroup) VALUES (?, ?, ?, ?, ?, ?, ?)";
        List<Map<String, Object>> exerciseRows = databaseConnector
                .runParametrizedQuery(query,
                        uID,
                        IDtrainer,
                        date,
                        IDexercise1,
                        IDexercise2,
                        IDexercise3,
                        groupBox.getText());

        tableView.setItems(getWorkouts(uID));


        databaseConnector.close();



    }


    public void generateExercises(ActionEvent event) throws IOException {
        databaseConnector.connect();
        List<Map<String, Object>> exerciseRows = databaseConnector
                .runParametrizedQuery("SELECT * FROM Exercises WHERE egroup = ?", groupBox.getText());

        Random random = new Random();

        // Shuffle the list and pick the first three unique exercises
        int exerciseCount = Math.min(3, exerciseRows.size());
        for (int i = 0; i < exerciseCount; i++) {
            int randomExerciseIndex = random.nextInt(exerciseRows.size());
            Map<String, Object> randomExercise = exerciseRows.remove(randomExerciseIndex); // Remove to avoid duplicates

            // Get the exercise's name and ID
            String exerciseName = (String) randomExercise.get("name");
            String exerciseID = String.valueOf(randomExercise.get("eID"));

            // Set the exercise's name and ID in the appropriate box
            if (i == 0) {
                exercise1Text.setText("Name: " + exerciseName);
                IDexercise1 = exerciseID;
            } else if (i == 1) {
                exercise2Text.setText("Name: " + exerciseName);
                IDexercise2 = exerciseID;
            } else if (i == 2) {
                exercise3Text.setText("Name: " + exerciseName);
                IDexercise3 = exerciseID;
            }

            // Print out the selected exercise details for debugging
            System.out.println("Selected Exercise " + (i + 1) + ":");
            for (Map.Entry<String, Object> entry : randomExercise.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            databaseConnector.close();
    }}

    public void generateTrainer(ActionEvent event) throws IOException{
        try {
            // Connect to the database
            databaseConnector.connect();

            // Retrieve trainers based on the group specified in groupBox
            List<Map<String, Object>> rows = databaseConnector
                    .runParametrizedQuery("SELECT * FROM Trainer WHERE egroup = ?", groupBox.getText());

            // Check if rows are empty
            if (rows.isEmpty()) {
                System.out.println("No trainers found for the given group.");
                trainerBox.setText("No trainer found");
                return;
            }

            // Select a random trainer
            Random random = new Random();
            int randomIndex = random.nextInt(rows.size());
            Map<String, Object> randomTrainer = rows.get(randomIndex);

            // Get the trainer's name
            String trainerName = (String) randomTrainer.get("name");
            IDtrainer = String.valueOf(randomTrainer.get("tID"));

            // Set the trainer's name in the trainerBox
            trainerBox.setText(trainerName);

            // Print out the selected trainer details for debugging
            System.out.println("Selected Trainer:");
            for (Map.Entry<String, Object> entry : randomTrainer.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ensure the database connection is closed
            databaseConnector.close();
        }

    }
    public void initialize(){
        Login.AccountObject current = Login.currentAccount;

        dateCol.setCellValueFactory(new PropertyValueFactory<WorkoutObjects,String>("date"));
        groupCol.setCellValueFactory(new PropertyValueFactory<WorkoutObjects, String>("group"));
        trainerCol.setCellValueFactory(new PropertyValueFactory<WorkoutObjects, String>("trainer"));
        ex1Col.setCellValueFactory(new PropertyValueFactory<WorkoutObjects, String>("ex1"));
        ex2Col.setCellValueFactory(new PropertyValueFactory<WorkoutObjects, String>("ex2"));
        ex3Col.setCellValueFactory(new PropertyValueFactory<WorkoutObjects, String>("ex3"));


        tableView.setItems(getWorkouts(current.uid));


    }
    public ObservableList<WorkoutObjects> getWorkouts(String uid) {
        ObservableList<WorkoutObjects> workouts = FXCollections.observableArrayList();

        try {
            databaseConnector.connect();
            List<Map<String, Object>> rows = databaseConnector
                    .runParametrizedQuery("SELECT * FROM Workout WHERE uID = ?", uid);

            for (Map<String, Object> row : rows) {

                System.out.println("Workout:");
                for (Map.Entry<String, Object> entry : row.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
                System.out.println("------------------------------");


                String date = String.valueOf(row.get("date"));
                String group = String.valueOf(row.get("egroup"));

                // Convert trainerID to string properly
                String trainerID = String.valueOf(row.get("tID"));
                List<Map<String, Object>> trainer = databaseConnector
                        .runParametrizedQuery("SELECT * FROM Trainer WHERE tID = ?", trainerID);

                // Check if trainer exists
                if (trainer.isEmpty()) {
                    System.out.println("No trainer found for tID: " + trainerID);
                    continue;
                }
                String trainerName = String.valueOf(trainer.get(0).get("name"));

                // Get exercise names
                String IDex1 = String.valueOf(row.get("e1ID"));
                List<Map<String, Object>> exercise1 = databaseConnector
                        .runParametrizedQuery("SELECT * FROM Exercises WHERE eID = ?", IDex1);

                // Check if exercise1 exists
                if (exercise1.isEmpty()) {
                    System.out.println("No exercise found for eID: " + IDex1);
                    continue;
                }
                String ex1Name = String.valueOf(exercise1.get(0).get("name"));

                String IDex2 = String.valueOf(row.get("e2ID"));
                List<Map<String, Object>> exercise2 = databaseConnector
                        .runParametrizedQuery("SELECT * FROM Exercises WHERE eID = ?", IDex2);

                // Check if exercise2 exists
                if (exercise2.isEmpty()) {
                    System.out.println("No exercise found for eID: " + IDex2);
                    continue;
                }
                String ex2Name = String.valueOf(exercise2.get(0).get("name"));

                String IDex3 = String.valueOf(row.get("e3ID"));
                List<Map<String, Object>> exercise3 = databaseConnector
                        .runParametrizedQuery("SELECT * FROM Exercises WHERE eID = ?", IDex3);

                // Check if exercise3 exists
                if (exercise3.isEmpty()) {
                    System.out.println("No exercise found for eID: " + IDex3);
                    continue;
                }
                String ex3Name = String.valueOf(exercise3.get(0).get("name"));

                workouts.add(new WorkoutObjects(date, group, trainerName, ex1Name, ex2Name, ex3Name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnector.close();
        }
        return workouts;
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
