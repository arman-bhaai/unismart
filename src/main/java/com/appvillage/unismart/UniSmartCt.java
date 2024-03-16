package com.appvillage.unismart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UniSmartCt implements Initializable {
    @FXML
    private Button adminBtnRegister;

    @FXML
    private AnchorPane adminForm;

    @FXML
    private Hyperlink adminHlLogin;

    @FXML
    private PasswordField adminPassConfirm;

    @FXML
    private PasswordField adminPassword;

    @FXML
    private TextField adminUsername;

    @FXML
    private ComboBox<String> cmbChooseRole;

    @FXML
    private Button loginBtnLogin;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private TextField loginUsername;

    @FXML
    private Button studentBtnRegister;

    @FXML
    private TextField studentEmail;

    @FXML
    private AnchorPane studentForm;

    @FXML
    private Hyperlink studentHlLogin;

    @FXML
    private PasswordField studentPassConfirm;

    @FXML
    private PasswordField studentPassword;

    @FXML
    private TextField studentUsername;

    @FXML
    private Button teacherBtnRegister;

    @FXML
    private TextField teacherEmail;

    @FXML
    private AnchorPane teacherForm;

    @FXML
    private Hyperlink teacherHlLogin;

    @FXML
    private PasswordField teacherPassConfirm;

    @FXML
    private PasswordField teacherPassword;

    @FXML
    private TextField teacherUsername;

    Connection conn;
    PreparedStatement prStmt;
    String sql;
    String role;
    Statement stmt;
    ResultSet rs;
    AlertMessage alert = new AlertMessage();

    public void populateRoleList(){
        List<String> listR = new ArrayList<>();

        for(String s : ListData.roles){
            listR.add(s);
        }

        ObservableList<String> listData = FXCollections.observableArrayList(listR);
        cmbChooseRole.setItems(listData);
    }

    public void initialize(URL url, ResourceBundle rb){
        populateRoleList();
        try {
            conn = Database.connectDB();
            stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS users;");
            stmt.executeUpdate("CREATE TABLE users (id INT NOT NULL AUTO_INCREMENT, username VARCHAR(100), password VARCHAR(100), email VARCHAR(100), role VARCHAR(100), date Date, PRIMARY KEY (id));");
            stmt.executeUpdate("INSERT INTO users (username, email, password, role, date) VALUES ('student', 'john@example.com', '123', 'student', '2024-03-16')");
            stmt.executeUpdate("INSERT INTO users (username, email, password, role, date) VALUES ('teacher', 'john@example.com', '123', 'teacher', '2024-03-16')");
            stmt.executeUpdate("INSERT INTO users (username, password, role, date) VALUES ('admin', '123', 'admin', '2024-03-16')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void hideAllForms(){
        loginForm.setVisible(false);
        adminForm.setVisible(false);
        studentForm.setVisible(false);
        teacherForm.setVisible(false);
    }
    public void switchForm(){
        switch(cmbChooseRole.getSelectionModel().getSelectedItem()){
            case "Admin":
                hideAllForms();
                adminForm.setVisible(true);
                break;
            case "Student":
                hideAllForms();
                studentForm.setVisible(true);
                break;
            case "Teacher":
                hideAllForms();
                teacherForm.setVisible(true);
        }
    }

    public void showLoginForm(){
        hideAllForms();
        loginForm.setVisible(true);
    }

    public void handleLogin() throws SQLException, IOException, InterruptedException {
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        conn = Database.connectDB();
        PreparedStatement prStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? and password = ?");
        prStmt.setString(1, username);
        prStmt.setString(2, password);
        ResultSet rs = prStmt.executeQuery();
        if(rs.next()){
            String role = rs.getString("role");
            System.out.println("logged in as : "+ role);
            Thread.sleep(1000);
//            alert.successMessage("Logged In");
            if(role.equals("admin")){
                // link to Main Form for Admin
                FXMLLoader root = new FXMLLoader(UniSmart.class.getResource("admin-main-form.fxml"));
                Stage stg = new Stage();
                stg.setTitle("UniSmart | Admin Portal");
                stg.setScene(new Scene(root.load()));
                stg.show();

                // hide login form
                loginBtnLogin.getScene().getWindow().hide();
            }
        } else {
            alert.errorMessage("Incorrect username or password!");
        }
    }
    private boolean userExists(Statement stmt, String username) throws SQLException {
        sql = String.format("SELECT * FROM users WHERE username='%s'", username);
//        sql = "SELECT * FROM users WHERE username='bbb'";
        rs = stmt.executeQuery(sql);
        return rs.next();
    }
    private boolean validateRegistrationForm(String username, String password, String cfPassword, String email){
        if(email!=null){
            //### This is for student and teacher section
            if(username.isEmpty() || password.isEmpty() || cfPassword.isEmpty() || email.isEmpty()){
                alert.errorMessage("Fill up all the fields!");
                return false;
            }
            if(password.length()<3){
                alert.errorMessage("Password must contain more than 8 characters!");
                return false;
            }
            if(!password.equals(cfPassword)){
                alert.errorMessage("Passwords don't match!");
                return false;
            }
        } else {
            //### This is for admin
            if(username.isEmpty() || password.isEmpty() || cfPassword.isEmpty()){
                alert.errorMessage("Fill up all the fields!");
                return false;
            }
            if(password.length()<3){
                alert.errorMessage("Password must contain more than 8 characters!");
                return false;
            }
            if(!password.equals(cfPassword)){
                alert.errorMessage("Passwords don't match!");
                return false;
            }
        }
        return true;
    }
    private void registerUser(String username, String password, String cfPassword, String email, String role) throws SQLException {

        conn = Database.connectDB();
        stmt = conn.createStatement();
        String sql;
        if(email!=null){
            //### This is for student and teacher section
            if(!validateRegistrationForm(username, password, cfPassword, email))
                return;
            if(userExists(stmt, username)){
                alert.errorMessage("This user already exists! Try another username.");
                return;
            }
            sql = String.format("INSERT INTO users (username, password, email, role, date) VALUES ('%s','%s','%s','%s',NOW())",
                    username, password, email, role);
            stmt.executeUpdate(sql);
        } else {
            //### This is for admin
            if(username.isEmpty() || password.isEmpty() || cfPassword.isEmpty()){
                alert.errorMessage("Fill up all the fields!");
                return;
            }
            if(!password.equals(cfPassword)){
                alert.errorMessage("Passwords don't match!");
                return;
            }
            if(userExists(stmt, username)){
                alert.errorMessage("This user already exists! Try another username.");
                return;
            }
            sql = String.format("INSERT INTO users (username, password, role, date) VALUES ('%s','%s','%s', NOW())",
                    username, password, role);
            stmt.executeUpdate(sql);
        }
        alert.successMessage("Account created successfully!");
    }
    public void registerAdmin() throws SQLException {
        // GUI data
        String username = adminUsername.getText();
        String password = adminPassword.getText();
        String cfPass = adminPassConfirm.getText();
        String email = null;

        role = "admin";
        registerUser(username, password, cfPass, email, role);
    }
    public void registerStudent() throws SQLException {
        String username = studentUsername.getText();
        String password = studentPassword.getText();
        String cfPass = studentPassConfirm.getText();
        String email = studentEmail.getText();

        role = "student";
        registerUser(username, password, cfPass, email, role);
    }
    public void registerTeacher() throws SQLException {
        String username = teacherUsername.getText();
        String password = teacherPassword.getText();
        String cfPass = teacherPassConfirm.getText();
        String email = teacherEmail.getText();

        role = "teacher";
        registerUser(username, password, cfPass, email, role);
    }
}
