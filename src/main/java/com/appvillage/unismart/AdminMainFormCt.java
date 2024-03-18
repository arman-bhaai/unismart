package com.appvillage.unismart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminMainFormCt implements Initializable {
    @FXML
    private Button addStudent_btn_add;

    @FXML
    private Button addStudent_btn_delete;

    @FXML
    private Button addStudent_btn_update;

    @FXML
    private TableView<StudentData> tableView_addStudent;

    @FXML
    private TableColumn<StudentData, String> addStudent_col_course;

    @FXML
    private TableColumn<StudentData, String> addStudent_col_dateInsert;

    @FXML
    private TableColumn<StudentData, String> addStudent_col_name;

    @FXML
    private TableColumn<StudentData, String> addStudent_col_pay;

    @FXML
    private TableColumn<StudentData, String> addStudent_col_section;

    @FXML
    private TableColumn<StudentData, String> addStudent_col_status;

    @FXML
    private TableColumn<StudentData, String> addStudent_col_statusPayment;

    @FXML
    private TableColumn<StudentData, String> addStudent_col_studentNo;

    @FXML
    private TableColumn<StudentData, String> addStudent_col_year;

    @FXML
    private Button btn_addCourse;

    @FXML
    private Button btn_addStudent;

    @FXML
    private Button btn_addSubject;

    @FXML
    private Button btn_addTeacher;

    @FXML
    private Button btn_dashboard;

    @FXML
    private Button btn_payment;

    @FXML
    private Button btn_salary;

    @FXML
    private AnchorPane form_addStudent;

    @FXML
    private Label greetUsername;

    // start addTeacher form
    @FXML
    private TextField addTeacher_ID;

    @FXML
    private Button addTeacher_addBtn;

    @FXML
    private Button addTeacher_clearBtn;

    @FXML
    private TableColumn<?, ?> addTeacher_col_dateInsert;

    @FXML
    private TableColumn<?, ?> addTeacher_col_department;

    @FXML
    private TableColumn<?, ?> addTeacher_col_experience;

    @FXML
    private TableColumn<?, ?> addTeacher_col_gender;

    @FXML
    private TableColumn<?, ?> addTeacher_col_name;

    @FXML
    private TableColumn<?, ?> addTeacher_col_salary;

    @FXML
    private TableColumn<?, ?> addTeacher_col_status;

    @FXML
    private TableColumn<?, ?> addTeacher_col_teacherID;

    @FXML
    private TableColumn<?, ?> addTeacher_col_yearOfExperience;

    @FXML
    private Button addTeacher_deleteBtn;

    @FXML
    private ComboBox<?> addTeacher_department;

    @FXML
    private TextField addTeacher_experience;

    @FXML
    private ComboBox<?> addTeacher_gender;

    @FXML
    private ImageView addTeacher_imageView;

    @FXML
    private Button addTeacher_importBtn;

    @FXML
    private TextField addTeacher_name;

    @FXML
    private TextField addTeacher_salary;

    @FXML
    private ComboBox<?> addTeacher_status;

    @FXML
    private Button addTeacher_updateBtn;

    @FXML
    private ComboBox<?> addTeacher_yearOfExperience;
    // end addTeacher form

    // Database Connectivity
    private Connection conn;
    private PreparedStatement prStmt;
    private ResultSet rs;
    private Statement stmt;

    private AlertMessage alert = new AlertMessage();

    public ObservableList<StudentData> addStudentGetData() throws SQLException {
        ObservableList<StudentData> listData = FXCollections.observableArrayList();
        String selectData = "SELECT * FROM student WHERE dateDelete IS NULL";
        conn = Database.connectDB();
        StudentData sData;
        prStmt = conn.prepareStatement(selectData);
        rs = prStmt.executeQuery();

        while(rs.next()){
            sData = new StudentData(rs.getInt("id"), rs.getString("studentID"),
                    rs.getString("fullName"), rs.getString("gender"), rs.getDate("birthDate"),
                    rs.getString("year"), rs.getString("course"), rs.getString("section"),
                    rs.getString("semester"), rs.getDouble("payment"),
                    rs.getString("statusPayment"), rs.getString("image"),
                    rs.getDate("dateInsert"), rs.getDate("dateUpdate"), rs.getDate("dateDelete"),
                    rs.getString("status"));
            listData.add(sData);
        }
        return listData;
    }
    private ObservableList<StudentData> addStudentListData;
    public void addStudentDisplayData() throws SQLException {
        addStudentListData = addStudentGetData();

        addStudent_col_studentNo.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        addStudent_col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        addStudent_col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        addStudent_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        addStudent_col_section.setCellValueFactory(new PropertyValueFactory<>("section"));
        addStudent_col_pay.setCellValueFactory(new PropertyValueFactory<>("payment"));
        addStudent_col_statusPayment.setCellValueFactory(new PropertyValueFactory<>("statusPayment"));
        addStudent_col_dateInsert.setCellValueFactory(new PropertyValueFactory<>("dateInsert"));
        addStudent_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView_addStudent.setItems(addStudentListData);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            conn = Database.connectDB();
            stmt = conn.createStatement();
            // create student table
            stmt.executeUpdate("DROP TABLE IF EXISTS student;");
            stmt.executeUpdate("CREATE TABLE student (id INT NOT NULL AUTO_INCREMENT, studentID VARCHAR(100), " +
                    "fullName VARCHAR(100), gender VARCHAR(100), birthDate Date, year VARCHAR(100), course VARCHAR(100), " +
                    "section VARCHAR(100), semester VARCHAR(100), payment DOUBLE, statusPayment VARCHAR(100), image VARCHAR(100), dateInsert Date, " +
                    "dateUpdate Date, dateDelete VARCHAR(100), status VARCHAR(100), PRIMARY KEY (id));");
            // add dummy data
            // student 1
            stmt.executeUpdate("INSERT INTO student (studentID, fullName, gender, birthDate, year, course, section, semester, payment, " +
                    "statusPayment, image, dateInsert, dateUpdate, dateDelete, status) " +
                    "VALUES ('04', 'arman bhaai', 'male', '1999-01-01', '3', 'bangla', 'c', '1st sem', 300.4, 'payment ok', 'url', '2024-01-01', " +
                    "'2024-01-01', NULL, 'pending')");
            // student 2
            stmt.executeUpdate("INSERT INTO student (studentID, fullName, gender, birthDate, year, course, section, semester, payment, " +
                    "statusPayment, image, dateInsert, dateUpdate, dateDelete, status) " +
                    "VALUES ('10', 'raihan bro', 'male', '1999-01-01', '3', 'bangla', 'c', '2nd sem', 300.4, 'payment ok', 'url', '2024-01-01', " +
                    "'2024-01-01', NULL, 'pending')");
//            stmt.executeUpdate("INSERT INTO users (username, email, password, role, date) VALUES ('teacher', 'john@example.com', '123', 'teacher', '2024-03-16')");
//            stmt.executeUpdate("INSERT INTO users (username, password, role, date) VALUES ('admin', '123', 'admin', '2024-03-16')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            addStudentDisplayData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadAddStudentPanel() throws IOException {
        FXMLLoader root = new FXMLLoader(UniSmart.class.getResource("add-student.fxml"));
        Stage stg = new Stage();
        stg.setTitle("UniSmart | Admin Portal | Add Student");
        stg.setScene(new Scene(root.load()));
        stg.show();
    }

    public void addStudentAddBtn() throws IOException {
        loadAddStudentPanel();
    }
    public void addStudentUpdateBtn() throws IOException {
        StudentData sData = tableView_addStudent.getSelectionModel().getSelectedItem();
        int num = tableView_addStudent.getSelectionModel().getSelectedIndex();
        if((num-1)<-1){
            alert.errorMessage("Select the item first!");
            return;
        } else {
            ListData.tmpStudentID = sData.getStudentID();
            ListData.tmpStudentFullName = sData.getFullName();
            ListData.tmpStudentBirthDate = sData.getBirthDate();
            ListData.tmpStudentYear = sData.getYear();
            ListData.tmpStudentCourse = sData.getCourse();
            ListData.tmpStudentGender = sData.getGender();
            ListData.tmpStudentSemester = sData.getSemester();
            ListData.tmpStudentSection = sData.getSection();
            ListData.tmpStudentPay = sData.getPayment();
            ListData.tmpStudentPaymentStatus = sData.getStatusPayment();
            ListData.tmpStudentStatus = sData.getStatus();


            FXMLLoader root = new FXMLLoader(UniSmart.class.getResource("add-student.fxml"));
            Stage stg = new Stage();
            stg.setTitle("UniSmart | Admin Portal | Update Student");
            stg.setScene(new Scene(root.load()));
            stg.show();
        }
    }
    public void addStudentDeleteBtn() throws SQLException {
        StudentData sData = tableView_addStudent.getSelectionModel().getSelectedItem();
        int num = tableView_addStudent.getSelectionModel().getSelectedIndex();

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        if((num-1)<-1){
            alert.errorMessage("Select the item first!");
            return;
        } else {
            if(alert.confirmMessage("Are you sure to Delete Student ID: "+sData.getStudentID()+"?")){
                String query = "UPDATE student SET dateDelete = ? WHERE studentID = ?";
                conn = Database.connectDB();
                prStmt = conn.prepareStatement(query);
                prStmt.setString(1, String.valueOf(sqlDate));
                prStmt.setString(2, sData.getStudentID());
                prStmt.executeUpdate();
                alert.successMessage("Deleted Succesfully!");
            } else {
                alert.errorMessage("Cancelled!");
            }
        }
        addStudentDisplayData();
    }
}
