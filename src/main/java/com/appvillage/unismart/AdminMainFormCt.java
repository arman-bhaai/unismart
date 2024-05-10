package com.appvillage.unismart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private AnchorPane form_addCourse;

    @FXML
    private AnchorPane form_addStudent;

    @FXML
    private AnchorPane form_addSubject;

    @FXML
    private AnchorPane form_addTeacher;

    @FXML
    private AnchorPane form_dashboard;

    @FXML
    private AnchorPane form_payment;

    @FXML
    private AnchorPane form_salary;

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
    private TableView<TeacherData> tableView_addTeacher;

    @FXML
    private TableColumn<TeacherData, String> addTeacher_col_dateInsert;

    @FXML
    private TableColumn<TeacherData, String> addTeacher_col_department;

    @FXML
    private TableColumn<TeacherData, String> addTeacher_col_experience;

    @FXML
    private TableColumn<TeacherData, String> addTeacher_col_gender;

    @FXML
    private TableColumn<TeacherData, String> addTeacher_col_name;

    @FXML
    private TableColumn<TeacherData, String> addTeacher_col_salary;

    @FXML
    private TableColumn<TeacherData, String> addTeacher_col_status;

    @FXML
    private TableColumn<TeacherData, String> addTeacher_col_teacherID;

    @FXML
    private TableColumn<TeacherData, String> addTeacher_col_yearOfExperience;

    @FXML
    private Button addTeacher_deleteBtn;

    @FXML
    private ComboBox<String> addTeacher_department;

    @FXML
    private TextField addTeacher_experience;

    @FXML
    private ComboBox<String> addTeacher_gender;

    @FXML
    private ImageView addTeacher_imageView;

    @FXML
    private Button addTeacher_importBtn;

    @FXML
    private TextField addTeacher_name;

    @FXML
    private TextField addTeacher_salary;

    @FXML
    private ComboBox<String> addTeacher_status;

    @FXML
    private Button addTeacher_updateBtn;

    @FXML
    private ComboBox<String> addTeacher_yearOfExperience;
    // end addTeacher form

    private Image img;
    private String teacherID;
    // Database Connectivity
    private Connection conn;
    private PreparedStatement prStmt;
    private ResultSet rs;
    private Statement stmt;

    private AlertMessage alert = new AlertMessage();

    public ObservableList<StudentData> addStudentGetData() throws SQLException {
        System.out.println("called addStudentGetData()");
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
        System.out.println("called addStudentDisplayData()");
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
    public void loadAddStudentPanel() throws IOException {
        FXMLLoader root = new FXMLLoader(UniSmart.class.getResource("add-student.fxml"));
        Stage stg = new Stage();
        stg.setTitle("UniSmart | Admin Portal | Add Student");
        stg.setScene(new Scene(root.load()));
        stg.show();
    }

    public void addStudentAddBtn() throws IOException, SQLException {
        loadAddStudentPanel();

//        addStudentDisplayData();
    }
    public void addStudentUpdateBtn() throws IOException, SQLException {
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
//        addStudentDisplayData();
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
    public ObservableList<TeacherData> addTeacherGetData() throws SQLException {
        ObservableList<TeacherData> listData = FXCollections.observableArrayList();
        String selectData = "SELECT * FROM teacher WHERE dateDelete IS NULL";
        conn = Database.connectDB();
        TeacherData sData;
        prStmt = conn.prepareStatement(selectData);
        rs = prStmt.executeQuery();

        while(rs.next()){
            sData = new TeacherData(rs.getInt("id"),
                    rs.getString("teacherID"),
                    rs.getString("name"),
                    rs.getString("gender"),
                    rs.getDate("birthDate"),
                    rs.getString("yearOfExperience"),
                    rs.getString("experience"),
                    rs.getString("department"),
                    rs.getDouble("salary"),
                    rs.getString("image"),
                    rs.getDate("dateInsert"),
                    rs.getDate("dateUpdate"),
                    rs.getDate("dateDelete"),
                    rs.getString("status"));
            listData.add(sData);
        }
        return listData;
    }
    private ObservableList<TeacherData> addTeacherListData;
    public void addTeacherDisplayData() throws SQLException {
        addTeacherListData = addTeacherGetData();

        addTeacher_col_teacherID.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
        addTeacher_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        addTeacher_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addTeacher_col_yearOfExperience.setCellValueFactory(new PropertyValueFactory<>("yearOfExperience"));
        addTeacher_col_experience.setCellValueFactory(new PropertyValueFactory<>("experience"));
        addTeacher_col_department.setCellValueFactory(new PropertyValueFactory<>("department"));
        addTeacher_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        addTeacher_col_dateInsert.setCellValueFactory(new PropertyValueFactory<>("dateInsert"));
        addTeacher_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView_addTeacher.setItems(addTeacherListData);
    }
    public void addTeacherSelectedItems(){
        TeacherData sData = tableView_addTeacher.getSelectionModel().getSelectedItem();
        int num = tableView_addTeacher.getSelectionModel().getSelectedIndex();
        if((num-1)<-1){
//            alert.errorMessage("Select the item first!");
            return;
        } else {
            addTeacher_ID.setText(sData.getTeacherID());
            addTeacher_name.setText(sData.getName());
            addTeacher_gender.getSelectionModel().select(sData.getGender());
            addTeacher_yearOfExperience.getSelectionModel().select(sData.getYearOfExperience());
            addTeacher_experience.setText(sData.getExperience());
            addTeacher_department.getSelectionModel().select(sData.getDepartment());
            addTeacher_salary.setText(""+sData.getSalary());
            addTeacher_status.getSelectionModel().select(sData.getStatus());

            ListData.path = sData.getImage();

            img = new Image("File:"+sData.getImage(), 100, 100, false, true);
            addTeacher_imageView.setImage(img);
        }
    }
    public void addTeacherGenderList(){
        List<String> listG = new ArrayList<>();
        for(String data: ListData.gender){
            listG.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listG);
        addTeacher_gender.setItems(listData);
    }
    public void addTeacherYearOfExperienceList(){
        List<String> listG = new ArrayList<>();
        for(String data: ListData.yearOfExperience){
            listG.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listG);
        addTeacher_yearOfExperience.setItems(listData);
    }
    public void addTeacherDepartmentList(){
        List<String> listG = new ArrayList<>();
        for(String data: ListData.department){
            listG.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listG);
        addTeacher_department.setItems(listData);
    }
    public void addTeacherStatusList(){
        List<String> listG = new ArrayList<>();
        for(String data: ListData.status){
            listG.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listG);
        addTeacher_status.setItems(listData);
    }
    public void generateTeacherID() throws SQLException {
        String selectData = "SELECT MAX(id) FROM teacher";
        conn = Database.connectDB();
        String tmp_teacherID = "TID-";
        int tmp_count = 0;

        stmt = conn.createStatement();
        rs = stmt.executeQuery(selectData);
        if(rs.next()){
            tmp_count = rs.getInt("MAX(id)");
        }
        tmp_count++;
        teacherID = tmp_teacherID+tmp_count;
    }
    public void addTeacherDisplayTeacherID() throws SQLException {
        generateTeacherID();
        addTeacher_ID.setText(teacherID);
    }
    public void addTeacherImportBtn(){
        FileChooser fc = new FileChooser();
//        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image", ".jpg", ".jpeg", ".png"));
        File file = fc.showOpenDialog(addTeacher_importBtn.getScene().getWindow());
        if(file != null){
            ListData.path = file.getAbsolutePath();

            img = new Image(file.toURI().toString(), 100, 100, false, true);
            addTeacher_imageView.setImage(img);
        }
    }
    public void addTeacherAddBtn() throws SQLException, IOException {
        if(addTeacher_ID.getText().isEmpty() ||
                addTeacher_name.getText().isEmpty() ||
                addTeacher_gender.getSelectionModel().getSelectedItem().isEmpty() ||
                addTeacher_yearOfExperience.getSelectionModel().getSelectedItem().isEmpty() ||
                addTeacher_department.getSelectionModel().getSelectedItem().isEmpty() ||
                addTeacher_experience.getText().isEmpty() ||
                addTeacher_status.getSelectionModel().getSelectedItem().isEmpty() ||
                addTeacher_salary.getText().isEmpty() ||
                ListData.path.isEmpty()){
            alert.errorMessage("Fill all the blanks");
        } else {
            conn = Database.connectDB();
            String checkStudentID = "SELECT * FROM teacher WHERE teacherID = '"+addTeacher_ID.getText()+"'";
            prStmt = conn.prepareStatement(checkStudentID);
            rs = prStmt.executeQuery();

            if(rs.next()){
                alert.errorMessage("Teacher ID: "+addTeacher_ID.getText()+"is already taken!");
            } else {
                String insertData = "INSERT INTO teacher (teacherID, name, gender, yearOfExperience, experience, department, " +
                        "salary, image, dateInsert, status) "+
                        "VALUES (?,?,?,?,?,?,?,?,?,?)";
                prStmt = conn.prepareStatement(insertData);
                prStmt.setString(1, addTeacher_ID.getText());
                prStmt.setString(2, addTeacher_name.getText());
                prStmt.setString(3, addTeacher_gender.getSelectionModel().getSelectedItem());
                prStmt.setString(4, addTeacher_yearOfExperience.getSelectionModel().getSelectedItem());
                prStmt.setString(5, addTeacher_experience.getText());
                prStmt.setString(6, addTeacher_department.getSelectionModel().getSelectedItem());
                prStmt.setString(7, addTeacher_salary.getText());

                String path = ListData.path;
                path = path.replace("\\", "\\\\");
                prStmt.setString(8, path);

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prStmt.setString(9, String.valueOf(sqlDate));
                prStmt.setString(10, addTeacher_status.getSelectionModel().getSelectedItem());

                prStmt.executeUpdate();

                Path transfer = Paths.get(path);
                Path copy = Paths.get("C:\\Users\\Arman_Bhaai\\dev\\projects\\UniSmart\\src\\main\\java\\" +
                        "teacherDirectory"+addTeacher_ID.getText()+".jpg");
                Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);

                alert.successMessage("Added Successfully!");
                addTeacherDisplayData();
                clearFields();
            }
        }
    }
    public void addTeacherUpdateBtn() throws IOException, SQLException {
        if(addTeacher_ID.getText().isEmpty() ||
                addTeacher_name.getText().isEmpty() ||
                addTeacher_gender.getSelectionModel().getSelectedItem().isEmpty() ||
                addTeacher_yearOfExperience.getSelectionModel().getSelectedItem().isEmpty() ||
                addTeacher_department.getSelectionModel().getSelectedItem().isEmpty() ||
                addTeacher_experience.getText().isEmpty() ||
                addTeacher_status.getSelectionModel().getSelectedItem().isEmpty() ||
                addTeacher_salary.getText().isEmpty() ||
                ListData.path.isEmpty()){
            alert.errorMessage("Fill all the blanks");
        } else {
            String path = ListData.path;
            path = path.replace("\\", "\\\\");
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            if(alert.confirmMessage("Are you sure to Update Teacher ID: "+addTeacher_ID.getText())){
                String updateSql = String.format("UPDATE teacher SET name = '%s', gender = '%s', yearOfExperience = '%s', " +
                                "experience = '%s', department = '%s', salary = '%s', image = '%s', dateInsert = '%s', status = '%s'" +
                                " WHERE teacherID = '%s'",
                        addTeacher_name.getText(),
                        addTeacher_gender.getSelectionModel().getSelectedItem(),
                        addTeacher_yearOfExperience.getSelectionModel().getSelectedItem(),
                        addTeacher_experience.getText(),
                        addTeacher_department.getSelectionModel().getSelectedItem(),
                        addTeacher_salary.getText(),
                        path, sqlDate,
                        addTeacher_status.getSelectionModel().getSelectedItem(),
                        addTeacher_ID.getText()
                );

                Path transfer = Paths.get(path);
                Path copy = Paths.get("C:\\Users\\Arman_Bhaai\\dev\\projects\\UniSmart\\src\\main\\java\\" +
                        "teacherDirectory"+addTeacher_ID.getText()+".jpg");
                Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);

                conn = Database.connectDB();
                prStmt = conn.prepareStatement(updateSql);
                prStmt.executeUpdate();
                addTeacherDisplayData();
                alert.successMessage("Updated Successfully!");
            } else {
                alert.errorMessage("Cancelled");
            }
        }
    }
    public void addTeacherDeleteBtn() throws SQLException {
        TeacherData sData = tableView_addTeacher.getSelectionModel().getSelectedItem();
        int num = tableView_addTeacher.getSelectionModel().getSelectedIndex();

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        if((num-1)<-1){
            alert.errorMessage("Select the item first!");
            return;
        } else {
            if(alert.confirmMessage("Are you sure to Delete Teacher ID: "+sData.getTeacherID()+"?")){
                String query = "UPDATE teacher SET dateDelete = ? WHERE teacherID = ?";
                conn = Database.connectDB();
                prStmt = conn.prepareStatement(query);
                prStmt.setString(1, String.valueOf(sqlDate));
                prStmt.setString(2, sData.getTeacherID());
                prStmt.executeUpdate();
                alert.successMessage("Deleted Succesfully!");
            } else {
                alert.errorMessage("Cancelled!");
            }
        }
        addTeacherDisplayData();
    }
    public void clearFields() throws SQLException {
        addTeacherDisplayTeacherID();
        addTeacher_name.clear();
//        studentBirthDate.setValue(null);
        addTeacher_yearOfExperience.getSelectionModel().clearSelection();
        addTeacher_department.getSelectionModel().clearSelection();
        addTeacher_gender.getSelectionModel().clearSelection();
        addTeacher_experience.clear();
        addTeacher_salary.clear();
        addTeacher_status.getSelectionModel().clearSelection();

        ListData.path = "";
        addTeacher_imageView.setImage(null);
    }
    public void switchForm(ActionEvent e){
        if(e.getSource() == btn_dashboard){
            form_dashboard.setVisible(true);
            form_addStudent.setVisible(false);
            form_addTeacher.setVisible(false);
            form_addCourse.setVisible(false);
            form_addSubject.setVisible(false);
            form_payment.setVisible(false);
            form_salary.setVisible(false);
        } else if(e.getSource() == btn_addStudent){
            form_dashboard.setVisible(false);
            form_addStudent.setVisible(true);
            form_addTeacher.setVisible(false);
            form_addCourse.setVisible(false);
            form_addSubject.setVisible(false);
            form_payment.setVisible(false);
            form_salary.setVisible(false);
        } else if(e.getSource() == btn_addTeacher){
            form_dashboard.setVisible(false);
            form_addStudent.setVisible(false);
            form_addTeacher.setVisible(true);
            form_addCourse.setVisible(false);
            form_addSubject.setVisible(false);
            form_payment.setVisible(false);
            form_salary.setVisible(false);
        } else if(e.getSource() == btn_addCourse){
            form_dashboard.setVisible(false);
            form_addStudent.setVisible(false);
            form_addTeacher.setVisible(false);
            form_addCourse.setVisible(true);
            form_addSubject.setVisible(false);
            form_payment.setVisible(false);
            form_salary.setVisible(false);
        } else if(e.getSource() == btn_addSubject){
            form_dashboard.setVisible(false);
            form_addStudent.setVisible(false);
            form_addTeacher.setVisible(false);
            form_addCourse.setVisible(false);
            form_addSubject.setVisible(true);
            form_payment.setVisible(false);
            form_salary.setVisible(false);
        } else if(e.getSource() == btn_payment){
            form_dashboard.setVisible(false);
            form_addStudent.setVisible(false);
            form_addTeacher.setVisible(false);
            form_addCourse.setVisible(false);
            form_addSubject.setVisible(false);
            form_payment.setVisible(true);
            form_salary.setVisible(false);
        } else if(e.getSource() == btn_salary){
            form_dashboard.setVisible(false);
            form_addStudent.setVisible(false);
            form_addTeacher.setVisible(false);
            form_addCourse.setVisible(false);
            form_addSubject.setVisible(false);
            form_payment.setVisible(false);
            form_salary.setVisible(true);
        }
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
                    "dateUpdate Date, dateDelete Date, status VARCHAR(100), PRIMARY KEY (id));");

            // add dummy data
            // student 1
            String usr1Url = "C:\\Users\\Arman_Bhaai\\dev\\projects\\UniSmart\\src\\main\\java\\images\\demo-user.jpg";
            usr1Url=usr1Url.replace("\\", "\\\\");

            stmt.executeUpdate("INSERT INTO student (studentID, fullName, gender, birthDate, year, course, section, semester, payment, " +
                    "statusPayment, image, dateInsert, dateUpdate, dateDelete, status) " +
                    "VALUES ('1', 'Saiful Islam', 'Male', '1999-01-01', '2nd Year', 'B.Sc', 'A', '4th sem', 1500, 'Pending', '"+usr1Url+"', '2024-01-01', " +
                    "'2024-01-01', NULL, 'pending')");
            // student 2
            String usr2Url = "C:\\Users\\Arman_Bhaai\\dev\\projects\\UniSmart\\src\\main\\java\\images\\demo-user2.jpg";
            usr2Url=usr2Url.replace("\\", "\\\\");
            stmt.executeUpdate("INSERT INTO student (studentID, fullName, gender, birthDate, year, course, section, semester, payment, " +
                    "statusPayment, image, dateInsert, dateUpdate, dateDelete, status) " +
                    "VALUES ('2', 'Abu Bakar Siddique', 'Male', '1999-01-01', '2nd Year', 'B.Sc', 'A', '4th Semester', 2000, 'Accepted', '"+usr2Url+"', '2024-01-01', " +
                    "'2024-01-01', NULL, 'Accepted')");
//            stmt.executeUpdate("INSERT INTO users (username, email, password, role, date) VALUES ('teacher', 'john@example.com', '123', 'teacher', '2024-03-16')");
//            stmt.executeUpdate("INSERT INTO users (username, password, role, date) VALUES ('admin', '123', 'admin', '2024-03-16')");

            // create teacher table
            stmt.executeUpdate("DROP TABLE IF EXISTS teacher;");
            stmt.executeUpdate("CREATE TABLE teacher (id INT NOT NULL AUTO_INCREMENT, teacherID VARCHAR(100), " +
                    "name VARCHAR(100), gender VARCHAR(100), birthDate Date, yearOfExperience VARCHAR(100), " +
                    "experience VARCHAR(100), department VARCHAR(100), salary DOUBLE, image VARCHAR(100), " +
                    "dateInsert Date,  dateUpdate Date, " +
                    "dateDelete Date, status VARCHAR(100), PRIMARY KEY (id));");
            // add dummy data
            // teacher 1
            usr1Url = "C:\\Users\\Arman_Bhaai\\dev\\projects\\UniSmart\\src\\main\\java\\images\\kamal-sir.jpg";
            usr1Url=usr1Url.replace("\\", "\\\\");
            stmt.executeUpdate("INSERT INTO teacher (teacherID, name, gender, birthDate, yearOfExperience, experience, " +
                    "department, " +
                    "salary, dateInsert, status, image)"  +
                    "VALUES ('TID-1', 'Dr. Md. Kamal Uddin', 'Male', '1999-01-01', ' 3', 'Programming', 'CSTE', '50000', '1999-01-01', 'pending', '"+usr1Url+"')");
            // teacher 2
            usr2Url = "C:\\Users\\Arman_Bhaai\\dev\\projects\\UniSmart\\src\\main\\java\\images\\rana-sir.jpg";
            usr2Url=usr2Url.replace("\\", "\\\\");
            stmt.executeUpdate("INSERT INTO teacher (teacherID, name, gender, birthDate, yearOfExperience, experience, " +
                    "department, " +
                    "salary, dateInsert, status, image)"  +
                    "VALUES ('TID-2', 'A.R.M Mahamudul Hasan Rana', 'Male', '1999-01-01', '3', 'Algorithm', 'CSTE', '40000', '1999-01-01', 'pending', '"+usr2Url+"')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        addTeacherGenderList();
        addTeacherYearOfExperienceList();
        addTeacherDepartmentList();
        addTeacherStatusList();
        try {

            addTeacherDisplayTeacherID();
//            addStudentDisplayData();
            addTeacherDisplayData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // initial ui
        form_dashboard.setVisible(true);
        form_addStudent.setVisible(false);
        form_addTeacher.setVisible(false);
        form_addCourse.setVisible(false);
        form_addSubject.setVisible(false);
        form_payment.setVisible(false);
        form_salary.setVisible(false);
    }
}
