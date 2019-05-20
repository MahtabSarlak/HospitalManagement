package controller;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Employee extends Person {
    private StringProperty qualification;
    private StringProperty specialist;
    private ObservableList<Patientinfo>patients= FXCollections.observableArrayList();
    private IntegerProperty salary;
    public Employee()
    {
        super();
        this.qualification=new SimpleStringProperty("");
        this.specialist=new SimpleStringProperty("");
        this.salary=new SimpleIntegerProperty(0);
    }

    public String getQualification() {
        return qualification.get();
    }

    public StringProperty qualificationProperty() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification.set(qualification);
    }

    public String getSpecialist() {
        return specialist.get();
    }

    public StringProperty specialistProperty() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist.set(specialist);
    }
    public int getSalary() {
        return salary.get();
    }

    public IntegerProperty salaryProperty() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary.set(salary);
    }

    public ObservableList<Patientinfo> getPatients() {
        return patients;
    }
}
