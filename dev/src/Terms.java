import java.time.LocalDate;

public class Terms {
    private LocalDate startWork;
    private String employmentType; // full/paratial
    private String salaryType;// global/hurly
    private int Salary;
    private int vacationDays;


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

    public void setStartWork(LocalDate startWork) {
        this.startWork = startWork;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }
}
