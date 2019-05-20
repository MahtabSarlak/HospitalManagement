package controller;

public class Finance {
    private static int total=10000000;

    public  void setSalary(int salary) {
        this.salary = salary;
    }

    private int salary;

    public  Finance()
    {
        int temp=0;
        for(Doctorinfo d:HomeController.getDoctorinfoData())
        {
            temp+=d.getSalary();
        }
        for(Nurseinfo n:AdminDoctorController.getNurseinfoData())
        {
            temp+=n.getSalary();
        }
        this.salary=temp;

    }
    public void getPatientFee(int fee)
    {
        int temp=0;
        temp+=fee;
        temp+=this.total;
        this.total=temp;
    }
    public void paySalary()
    {
        int temp=0;
        temp=this.total-this.salary;
        this.total=temp;
        this.salary=0;
    }

    public static int getTotal() {
        return total;
    }

    public int getSalary() {
        return salary;
    }

}
