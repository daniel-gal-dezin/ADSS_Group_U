package Domain_Layer;

import java.time.LocalDate;

public class Terms {
    private LocalDate startWork;
    public String employmentType; // full/paratial
    public String salaryType;// global/hurly
    public int Salary;
    public int vacationDays;


    public Terms() {
        //TODO: default terms handle
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
/*
    public void setStartWork(LocalDate startWork) {
        this.startWork = startWork;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) throws IllegalArgumentException{
        if(employmentType.toLowerCase().compareTo("full")==0 || employmentType.toLowerCase().compareTo("partial")==0)
            this.employmentType = employmentType;
        else
            throw new IllegalArgumentException("no such employment type. the options are full or partial");
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) throws IllegalArgumentException{
        if(salaryType.toLowerCase().compareTo("hourly")==0 || salaryType.toLowerCase().compareTo("global")==0)
            this.salaryType = salaryType;
        else
            throw new IllegalArgumentException("no such employment type. the options are hourly or global");
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        if(salary < 0)
            throw new IllegalArgumentException("Can't apply negetive salary! inserted: " + salary);
        Salary = salary;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        if(vacationDays < 0)
            throw new IllegalArgumentException("Can't apply negetive vacationDays! inserted: " + vacationDays);
        this.vacationDays = vacationDays;
    }
    */
}
