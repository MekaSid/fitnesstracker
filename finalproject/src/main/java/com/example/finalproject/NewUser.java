package com.example.finalproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class NewUser {

    @FXML private Button backLogin;

    @FXML private TextField name;
    @FXML private TextField age;
    @FXML private TextField gender;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private TextField weight;
    @FXML private TextField height;

    @FXML private Button makeNew;

    static DatabaseConnector databaseConnector = new DatabaseConnector();

    public void makeNewAccount(ActionEvent event) throws IOException{
        databaseConnector.connect();
        String query =
                "INSERT INTO User (name, age, gender, username, password, weight, height) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
        databaseConnector.runParametrizedQuery(query,
                name.getText(),
                age.getText(),
                gender.getText(),
                username.getText(),
                password.getText(),
                weight.getText(),
                height.getText());
        databaseConnector.close();
    }

    public void goBackLogin(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("hello-view.fxml");
    }
}
