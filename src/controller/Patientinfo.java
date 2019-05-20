package controller;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Patientinfo extends Person {
    private IntegerProperty fee;
    private StringProperty describtion;
    private IntegerProperty doctor;
    private IntegerProperty nurse;

    public Patientinfo() {
        super();
        this.describtion=new SimpleStringProperty("");
        this.fee=new SimpleIntegerProperty(0);
        this.doctor=new SimpleIntegerProperty(0);
        this.nurse=new SimpleIntegerProperty(0);


    }

    public int getFee() {
        return fee.get();
    }

    public IntegerProperty feeProperty() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee.set(fee);
    }

    public String getDescribtion() {
        return describtion.get();
    }

    public StringProperty describtionProperty() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion.set(describtion);
    }

    public int getDoctor() {
        return doctor.get();
    }

    public IntegerProperty doctorProperty() {
        return doctor;
    }

    public void setDoctor(int doctor) {
        this.doctor.set(doctor);
    }

    public int getNurse() {
        return nurse.get();
    }

    public IntegerProperty nurseProperty() {
        return nurse;
    }

    public void setNurse(int nurse) {
        this.nurse.set(nurse);
    }
}
