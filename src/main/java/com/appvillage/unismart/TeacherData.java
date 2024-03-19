package com.appvillage.unismart;

import java.util.Date;

public class TeacherData {
    private Integer id;
    private String teacherID;
    private String name;
    private String gender;
    private String yearOfExperience;
    private String experience;
    private String department;
    private Double salary;
    private String image;
    private Date dateInsert;
    private Date dateUpdate;
    private Date dateDelete;
    private String status;
    private String salaryStatus;

    public TeacherData(Integer id, String teacherID, String name, String gender, Date birthDate, String yearOfExperience,
                       String experience, String department, Double salary, String image, Date dateInsert,
                       Date dateUpdate, Date dateDelete, String status) {
        this.id = id;
        this.teacherID = teacherID;
        this.name = name;
        this.gender = gender;
        this.yearOfExperience = yearOfExperience;
        this.experience = experience;
        this.department = department;
        this.salary = salary;
        this.image = image;
        this.dateInsert = dateInsert;
        this.dateUpdate = dateUpdate;
        this.dateDelete = dateDelete;
        this.status = status;
        this.salaryStatus = salaryStatus;
    }
    public TeacherData(Integer id, String teacherID, String name, String gender, Double salary, String salaryStatus,
                       Date dateInsert, Date dateUpdate, String status) {
        this.id = id;
        this.teacherID = teacherID;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.dateInsert = dateInsert;
        this.dateUpdate = dateUpdate;
        this.status = status;
        this.salaryStatus = salaryStatus;
    }
    public TeacherData(Integer id, String teacherID, String name, String gender, String yearOfExperience,
                       Date dateInsert) {
        this.id = id;
        this.teacherID = teacherID;
        this.name = name;
        this.gender = gender;
        this.yearOfExperience = yearOfExperience;
        this.dateInsert = dateInsert;
    }
    public Integer getId() {
        return id;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getYearOfExperience() {
        return yearOfExperience;
    }

    public String getExperience() {
        return experience;
    }

    public String getDepartment() {
        return department;
    }

    public Double getSalary() {
        return salary;
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

    public String getSalaryStatus() {
        return salaryStatus;
    }
}
