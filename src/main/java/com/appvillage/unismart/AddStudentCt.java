package com.appvillage.unismart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddStudentCt implements Initializable {
    @FXML
    private ComboBox<String> studentCourse;

    @FXML
    private TextField studentFullName;

    @FXML
    private TextField studentID;

    @FXML
    private DatePicker studentBirthDate;

    @FXML
    private TextField studentPay;

    @FXML
    private ComboBox<String> studentPaymentStatus;

    @FXML
    private ComboBox<String> studentStatus;

    @FXML
    private ComboBox<String> studentSection;

    @FXML
    private ComboBox<String> studentYear;

    @FXML
    private ComboBox<String> studentGender;

    @FXML
    private Button student_btnAdd;

    @FXML
    private Button student_btnImport;

    @FXML
    private Button student_btnUpdate;

    @FXML
    private AnchorPane student_form;

    @FXML
    private ImageView student_imageView;

    @FXML
    private ComboBox<String> studentSemester;

    @FXML
    private Label studentPrice;



    private Image img;
    private double price = 0;

    private Connection conn;
    private PreparedStatement prStmt;
    private ResultSet rs;

    private AlertMessage alert = new AlertMessage();

//    public void addBtn(){
//        if()
//    }
    public void displayStudentID() throws SQLException {
        UniSmartCt controller = new UniSmartCt();
        int id = controller.getStudentIDGenerator();
        String leading_zero = String.format("%02d", id);
        String fullID = "ASH21010"+leading_zero;
        studentID.setText(fullID);
    }
    public void studentIDGenerator(){

    }
    public void importBtn(){
        FileChooser fc = new FileChooser();
//        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image", ".jpg", ".jpeg", ".png"));
        File file = fc.showOpenDialog(student_btnImport.getScene().getWindow());
        if(file != null){
            ListData.path = file.getAbsolutePath();

            img = new Image(file.toURI().toString(), 110, 110, false, true);
            student_imageView.setImage(img);
        }
    }
    public void AddBtn() throws SQLException, IOException {
        if(studentID.getText().isEmpty() ||
                studentFullName.getText().isEmpty() ||
                studentYear.getSelectionModel().getSelectedItem().isEmpty() ||
                studentCourse.getSelectionModel().getSelectedItem().isEmpty() ||
                studentSection.getSelectionModel().getSelectedItem().isEmpty() ||
                studentPay.getText().isEmpty() ||
                studentPaymentStatus.getSelectionModel().getSelectedItem().isEmpty() ||
                studentStatus.getSelectionModel().getSelectedItem().isEmpty() ||
                ListData.path.isEmpty() ||
                studentBirthDate.getValue()==null ||
                studentGender.getSelectionModel().getSelectedItem().isEmpty() ||
                studentSemester.getSelectionModel().getSelectedItem().isEmpty()){
            alert.errorMessage("Fill all the blanks");
        } else {
            conn = Database.connectDB();
            String checkStudentID = "SELECT * FROM student WHERE studentID = '"+studentID.getText()+"'";
            prStmt = conn.prepareStatement(checkStudentID);
            rs = prStmt.executeQuery();

            if(rs.next()){
                alert.errorMessage("Student ID: "+studentID.getText()+"is already taken!");
            } else {
                String insertData = "INSERT INTO student (studentID, fullName, gender, birthDate, year, course, section, " +
                        "semester, payment, statusPayment, image, dateInsert, status) "+
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                prStmt = conn.prepareStatement(insertData);
                prStmt.setString(1, studentID.getText());
                prStmt.setString(2, studentFullName.getText());
                prStmt.setString(3, studentGender.getSelectionModel().getSelectedItem());
                prStmt.setString(4, String.valueOf(studentBirthDate.getValue()));
                prStmt.setString(5, studentYear.getSelectionModel().getSelectedItem());
                prStmt.setString(6, studentCourse.getSelectionModel().getSelectedItem());
                prStmt.setString(7, studentSection.getSelectionModel().getSelectedItem());
                prStmt.setString(8, studentSemester.getSelectionModel().getSelectedItem());
                prStmt.setString(9, String.valueOf(studentPay.getText()));
                prStmt.setString(10, studentPaymentStatus.getSelectionModel().getSelectedItem());
//
                String path = ListData.path;
                path = path.replace("\\", "\\\\");
                prStmt.setString(11, path);

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prStmt.setString(12, String.valueOf(sqlDate));
//                prStmt.setDate(11, );
                prStmt.setString(13, studentStatus.getSelectionModel().getSelectedItem());

                prStmt.executeUpdate();

                Path transfer = Paths.get(path);
                Path copy = Paths.get("C:\\Users\\Arman_Bhaai\\dev\\projects\\UniSmart\\src\\main\\java\\" +
                        "studentDirectory"+studentID.getText()+".jpg");
                Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);

                alert.successMessage("Added Successfully!");
                clearFields();
            }
        }
    }
    public void updateBtn() throws IOException, SQLException {
        if(studentID.getText().isEmpty() ||
                studentFullName.getText().isEmpty() ||
                studentYear.getSelectionModel().getSelectedItem().isEmpty() ||
                studentCourse.getSelectionModel().getSelectedItem().isEmpty() ||
                studentSection.getSelectionModel().getSelectedItem().isEmpty() ||
                studentPay.getText().isEmpty() ||
                studentPaymentStatus.getSelectionModel().getSelectedItem().isEmpty() ||
                studentStatus.getSelectionModel().getSelectedItem().isEmpty() ||
                ListData.path.isEmpty() ||
                studentBirthDate.getValue()==null ||
                studentGender.getSelectionModel().getSelectedItem().isEmpty() ||
                studentSemester.getSelectionModel().getSelectedItem().isEmpty()){
            alert.errorMessage("Fill all the blanks");
        } else {
            String path = ListData.path;
            path = path.replace("\\", "\\\\");
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            priceList();

            if(alert.confirmMessage("Are you sure to Update Student ID: "+studentID.getText())){
                String updateSql = String.format("UPDATE student SET fullName = '%s', gender = '%s', birthDate = '%s', " +
                        "year = '%s', course = '%s', section = '%s', semester = '%s', payment = '%s', statusPayment = '%s', image = '%s', " +
                        "dateInsert = '%s', status = '%s' WHERE studentID = '%s'",
                        studentFullName.getText(),
                        studentGender.getSelectionModel().getSelectedItem(),
                        studentBirthDate.getValue(),
                        studentYear.getSelectionModel().getSelectedItem(),
                        studentCourse.getSelectionModel().getSelectedItem(),
                        studentSection.getSelectionModel().getSelectedItem(),
                        studentSemester.getSelectionModel().getSelectedItem(),
                        price, studentPaymentStatus.getSelectionModel().getSelectedItem(),
                        path, sqlDate,
                        studentStatus.getSelectionModel().getSelectedItem(),
                        studentID.getText()
                    );

                Path transfer = Paths.get(path);
                Path copy = Paths.get("C:\\Users\\Arman_Bhaai\\dev\\projects\\UniSmart\\src\\main\\java\\" +
                        "studentDirectory"+studentID.getText()+".jpg");
                Files.copy(transfer, copy, StandardCopyOption.REPLACE_EXISTING);

                conn = Database.connectDB();
                prStmt = conn.prepareStatement(updateSql);
                prStmt.executeUpdate();

                alert.successMessage("Updated Successfully!");
            } else {
                alert.errorMessage("Cancelled");
            }
        }
    }
    public void clearFields() throws SQLException {
        displayStudentID();
        studentFullName.clear();
        studentBirthDate.setValue(null);
        studentYear.getSelectionModel().clearSelection();
        studentCourse.getSelectionModel().clearSelection();
        studentGender.getSelectionModel().clearSelection();
        studentSection.getSelectionModel().clearSelection();
        studentSemester.getSelectionModel().clearSelection();
        studentPay.clear();
        studentPaymentStatus.getSelectionModel().clearSelection();
        studentStatus.getSelectionModel().clearSelection();

        ListData.path = "";
        student_imageView.setImage(null);
    }
    public void setFields() throws SQLException {
        if(ListData.tmpStudentID != null){
            String query = String.format("SELECT * FROM student WHERE studentID='%s'", ListData.tmpStudentID);
            conn = Database.connectDB();
            prStmt=conn.prepareStatement(query);
            rs = prStmt.executeQuery();

            if(rs.next()){
                if(rs.getString("fullName") != null){
                    studentID.setText(ListData.tmpStudentID);
                    studentFullName.setText(rs.getString("fullName"));
                    studentBirthDate.setValue(LocalDate.parse(rs.getString("birthDate")));
                    studentYear.getSelectionModel().select(rs.getString("year"));
                    studentCourse.getSelectionModel().select(rs.getString("course"));
                    studentSection.getSelectionModel().select(rs.getString("section"));
                    studentGender.getSelectionModel().select(rs.getString("gender"));
                    studentSemester.getSelectionModel().select(rs.getString("semester"));
                    studentPaymentStatus.getSelectionModel().select(rs.getString("statusPayment"));
                    studentPay.setText(rs.getString("payment"));

                    ListData.path = rs.getString("image");

                    img = new Image("File:"+ListData.path, 100, 100, false, true);
                    student_imageView.setImage(img);
                } else {
                    studentID.setText(ListData.tmpStudentID);
                    studentStatus.getSelectionModel().select(rs.getString("status"));
                }
            }
        }
    }
    public void semesterList(){
        List<String> listSM = new ArrayList<>();
        for(String data: ListData.semester){
            listSM.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listSM);
        studentSemester.setItems(listData);
    }
    public void statusList(){
        List<String> listS = new ArrayList<>();
        for(String data: ListData.status){
            listS.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listS);
        studentStatus.setItems(listData);
    }
    public void genderList(){
        List<String> listG = new ArrayList<>();
        for(String data: ListData.gender){
            listG.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listG);
        studentGender.setItems(listData);
    }
    public void statusPaymentList(){
        List<String> listSP = new ArrayList<>();
        for(String data: ListData.paymentStatus){
            listSP.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listSP);
        studentPaymentStatus.setItems(listData);
    }
    public void priceList(){
        if(studentCourse.getSelectionModel().getSelectedItem() != null) {
            if (studentCourse.getSelectionModel().getSelectedItem().equals("B.Sc")) {
                price = 100;
            } else if (studentCourse.getSelectionModel().getSelectedItem().equals(("M.Sc"))) {
                price = 105;
            }
//            studentPrice.setText("$ " + String.valueOf(price));
            studentPay.setText(String.valueOf(price));
        }
    }
    public void yearList(){
        List<String> listY = new ArrayList<>();
        for(String data: ListData.year){
            listY.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listY);
        studentYear.setItems(listData);
    }
    public void courseList(){
        List<String> listC = new ArrayList<>();
        for(String data: ListData.course){
            listC.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listC);
        studentCourse.setItems(listData);
        priceList();
    }
    public void sectionList(){
        List<String> listS = new ArrayList<>();

        for(String data: ListData.sections){
            listS.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listS);
        studentSection.setItems(listData);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        yearList();
        courseList();
        sectionList();
        statusPaymentList();
        statusList();
        semesterList();
        genderList();


        try {
            displayStudentID();
            setFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
