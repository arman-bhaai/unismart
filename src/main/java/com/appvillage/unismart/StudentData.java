package com.appvillage.unismart;

import java.util.Date;

public class StudentData {
    private Integer id;
    private String studentID;
    private String fullName;
    private String gender;
    private Date birthDate;
    private String year;
    private String course;
    private String section;
    private String semester;
    private Double payment;
    private String statusPayment;
    private String image;
    private Date dateInsert;
    private Date dateUpdate;
    private Date dateDelete;
    private String status;

    public StudentData(Integer id, String studentID, String fullName, String gender, Date birthDate, String year, String course,
                       String section, String semester, Double payment, String statusPayment, String image, Date dateInsert,
                       Date dateUpdate, Date dateDelete, String status) {
        this.id = id;
        this.studentID = studentID;
        this.fullName = fullName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.year = year;
        this.course = course;
        this.section = section;
        this.semester = semester;
        this.payment = payment;
        this.statusPayment = statusPayment;
        this.image = image;
        this.dateInsert = dateInsert;
        this.dateUpdate = dateUpdate;
        this.dateDelete = dateDelete;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }
    public String getStudentID() {
        return studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getYear() {
        return year;
    }

    public String getCourse() {
        return course;
    }

    public String getSection() {
        return section;
    }
    public String getSemester() {return semester;}

    public Double getPayment() {
        return payment;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public String getImage() {
        return image;
    }

    public Date getDateInsert() {
        return dateInsert;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public Date getDateDelete() {
        return dateDelete;
    }

    public String getStatus() {
        return status;
    }
}
