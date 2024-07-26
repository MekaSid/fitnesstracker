package com.example.finalproject;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

import java.io.IOException;

public class AfterLogin {

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
