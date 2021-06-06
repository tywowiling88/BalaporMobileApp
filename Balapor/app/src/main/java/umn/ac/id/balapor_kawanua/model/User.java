package umn.ac.id.balapor_kawanua.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class User {
    String namaLengkap, jenisKelamin, nomorHP,
            email, password, uID,
            createdAT, role, isLoggedIn;

    public User(){

    }

    public User(String namaLengkap, String jenisKelamin, String nomorHP,
                String email, String password, String formattedDate,
                String role, String isLoggedIn, String uID) {
        this.namaLengkap = namaLengkap;
        this.jenisKelamin = jenisKelamin;
        this.nomorHP = nomorHP;
        this.email = email;
        this.password = password;
        this.createdAT = formattedDate;
        this.role = role;
        this.isLoggedIn = isLoggedIn;
        this.uID = uID;
    }


    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public void setNomorHP(String nomorHP) {
        this.nomorHP = nomorHP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(String createdAT) {
        this.createdAT = createdAT;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(String isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }
}
