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

import javax.swing.*;
import java.io.IOException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;
import java.util.Map;


public class Login {

        public Login(){}

        public class AccountObject{
            public String uid;
            public String name;
            public String age;
            public String gender;
            public String username;
            public String accpassword;
            public String height;
            public String weight;
            public AccountObject
                    (String uid, String name, String age, String gender, String username,
                    String accpassword, String height, String weight){
                this.uid = uid;
                this.name = name;
                this.age = age;
                this.gender = gender;
                this.username = username;
                this.accpassword = accpassword;
                this.height = height;
                this.weight = weight;
            }
        }

    static DatabaseConnector databaseConnector = new DatabaseConnector();

    public static AccountObject currentAccount;
    @FXML
    private PasswordField password;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField username;
    @FXML
    private Button button;

    @FXML Button newAccount;

    public void goNewAccount(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("NewUser.fxml");
    }

    public void userLogin(ActionEvent event) throws IOException, SQLException {
        checkLogin();
    }

    private void checkLogin() throws IOException, SQLException {
        Main m = new Main();
        databaseConnector.connect();
        databaseConnector.connect();
        List<Map<String, Object>> rows = databaseConnector
                .runParametrizedQuery("SELECT * FROM User WHERE username = ?",
                        username.getText());
        if (rows.size() == 1) {
            String passwordString = rows.get(0).get("password").toString();
            if(passwordString.equals(password.getText())) {
                wrongLogin.setText("Success");
                String uid = String.valueOf(rows.get(0).get("uID"));

                //String uid = (String) rows.get(0).get("uid");
                String name = (String) rows.get(0).get("name");
                String age = String.valueOf(rows.get(0).get("age")); // Converting Integer to String
                String gender = (String) rows.get(0).get("gender");
                String username = (String) rows.get(0).get("username");
                String accpassword = (String) rows.get(0).get("password");
                String height = String.valueOf(rows.get(0).get("height")); // Converting Float to String
                String weight = String.valueOf(rows.get(0).get("weight"));

                System.out.println("uid: " + uid);
                System.out.println("name: " + name);
                System.out.println("age: " + age);
                System.out.println("gender: " + gender);
                System.out.println("username: " + username);
                System.out.println("password: " + accpassword);
                System.out.println("height: " + height);
                System.out.println("weight: " + weight);
                currentAccount = new AccountObject(uid, name, age, gender, username, accpassword, height, weight);
                m.changeScene("afterLogin.fxml");
            }
            else{
                wrongLogin.setText("Wrong password.");

            }



        } else {
            wrongLogin.setText("User not found.");

        }

        databaseConnector.close();

    }
}
