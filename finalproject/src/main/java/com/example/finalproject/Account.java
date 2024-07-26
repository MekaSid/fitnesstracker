package com.example.finalproject;
import com.mysql.cj.log.Log;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
public class Account {

    public static Login.AccountObject curAccount;
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


    @FXML private Text name;
    @FXML private Text age;
    @FXML private Text gender;
    @FXML private Text username;
    @FXML private Text password;
    @FXML private Text height;
    @FXML private Text weight;

    public void initialize() {
        Login.AccountObject account = Login.currentAccount;
        curAccount = account;
        String sname = account.name;
        String sage = account.age;
        String sgender = account.gender;
        String susername = account.username;
        String spassword = account.accpassword;
        String sheight = account.height;
        String sweight = account.weight;

        System.out.print(account);


        name.setText(sname);
        age.setText(sage);
        gender.setText(sgender);
        username.setText(susername);
        password.setText(spassword);
        weight.setText(sweight);
        height.setText(sheight);
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
