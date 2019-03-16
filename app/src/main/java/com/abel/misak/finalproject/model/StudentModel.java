package com.abel.misak.finalproject.model;

import com.abel.misak.finalproject.SignatoryHome;

public class StudentModel {
    private String names, phone,email,matric_no,faculty,department,uid,course,session,photoUrl,current_gpa,cgpa,carry_over,due;
    private String bursar,academic, security;
    private String hod, hostel, liberian;
    private String medical, sao, sport;

    public StudentModel() {
    }

    public StudentModel(String names, String phone, String email, String matric_no, String faculty,
                        String department, String uid, String course, String session, String photoUrl) {
        this.names = names;
        this.phone = phone;
        this.email = email;
        this.matric_no = matric_no;
        this.faculty = faculty;
        this.department = department;
        this.uid = uid;
        this.course = course;
        this.session = session;
        this.photoUrl = photoUrl;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatric_no() {
        return matric_no;
    }

    public void setMatric_no(String matric_no) {
        this.matric_no = matric_no;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCurrent_gpa() {
        return current_gpa;
    }

    public void setCurrent_gpa(String current_gpa) {
        this.current_gpa = current_gpa;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getCarry_over() {
        return carry_over;
    }

    public void setCarry_over(String carry_over) {
        this.carry_over = carry_over;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getBursar() {
        return bursar;
    }

    public void setBursar(String bursar) {
        this.bursar = bursar;
    }

    public String getAcademic() {
        return academic;
    }

    public void setAcademic(String academic) {
        this.academic = academic;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getHod() {
        return hod;
    }

    public void setHod(String hod) {
        this.hod = hod;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getLiberian() {
        return liberian;
    }

    public void setLiberian(String liberian) {
        this.liberian = liberian;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getSao() {
        return sao;
    }

    public void setSao(String sao) {
        this.sao = sao;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
