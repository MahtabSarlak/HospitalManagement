package controller;


import javafx.beans.property.*;

import java.time.LocalDate;

public class Person {
    private IntegerProperty username;
    private StringProperty firstname;
    private StringProperty lastname;
    private StringProperty address;
    private IntegerProperty phone;

    public int getUsername() {
        return username.get();
    }

    public IntegerProperty usernameProperty() {
        return username;
    }

    public void setUsername(int username) {
        this.username.set(username);
    }

    public int getPhone() {
        return phone.get();
    }

    public IntegerProperty phoneProperty() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone.set(phone);
    }

    public int getVerificationcode() {
        return verificationcode.get();
    }

    public IntegerProperty verificationcodeProperty() {
        return verificationcode;
    }

    public void setVerificationcode(int verificationcode) {
        this.verificationcode.set(verificationcode);
    }

    private StringProperty gender;
    private StringProperty nationality;
    private IntegerProperty verificationcode;
    /**
     * Default constructor.
     */
    public Person() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param firstName
     * @param lastName
     */
    public Person(String firstName, String lastName) {
        this.firstname = new SimpleStringProperty(firstName);
        this.lastname = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty("");
        this.phone = new SimpleIntegerProperty(0);
        this.gender = new SimpleStringProperty("");
        this.nationality = new SimpleStringProperty("");
        this.verificationcode = new SimpleIntegerProperty(0);
        this.username = new SimpleIntegerProperty(0);
    }



    public String getFirstname() {
        return firstname.get();
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String getLastname() {
        return lastname.get();
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }


    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getNationality() {
        return nationality.get();
    }

    public StringProperty nationalityProperty() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }


}
