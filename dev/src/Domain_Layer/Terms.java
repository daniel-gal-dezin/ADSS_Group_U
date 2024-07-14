package Domain_Layer;

import java.time.LocalDate;

public class Terms {
    private LocalDate startWork;
    public String employmentType; // full/paratial
    public String salaryType;// global/hurly
    public int Salary;
    public int vacationDays;


    public Terms() {
    }

    public Terms(LocalDate startWork, String employmentType, String salaryType, int salary, int vacationDays) {
        this.startWork = startWork;
        this.employmentType = employmentType;
        this.salaryType = salaryType;
        Salary = salary;
        this.vacationDays = vacationDays;
    }

    public LocalDate getStartWork() {
        return startWork;
    }


    public String toString(){
        return "Start Work: " + startWork + ", Employment Type: " + employmentType + ", Salary Type: " + salaryType + ", Salary: " + Salary + ", Vacation Days: " + vacationDays;
    }

}
