package Presentation_Layer;

import Domain_Layer.EmployeeManager;
import Domain_Layer.Shift;
import Domain_Layer.ShiftManager;
import Service_Layer.EmployeeService;
import Service_Layer.ShiftService;
import Service_Layer.SystemInit;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class UserInterface {

    SystemInit si;
    EmployeeService es;
    ShiftService ss;

    public UserInterface(SystemInit in){
        si = in;
        es = si.getEs();
        ss = si.getSs();
    }



    public void main_loop(){

        System.out.printf("welcome to the system\n");
        System.out.printf("would you like to recover default data? y/n");
        String input = System.console().readLine();
        if(input.equals("y")){
            si.adddefualtinit();
        }

        System.out.printf("Who is using the system? \n1.hr\n2.worker");
        String input1 = System.console().readLine();

        if(input1.equals("1")){
            hr_loop();
        }
        else if(input1.equals("2")){
            worker_loop();
        }
        else{
            System.out.printf("invalid input");
        }
    }


    public String worker_loop(){
        System.out.printf("what is your id?");
        int id =Integer.parseInt(System.console().readLine());
        System.out.printf("what would you like to do? \n1.add constraint\n2.remove constraint");
        String input = System.console().readLine();
        String output = "";

        System.out.printf("enter shifts deteailes - ");
        System.out.print("year: ");
        int year = Integer.parseInt(System.console().readLine());
        System.out.print("month: ");
        int month = Integer.parseInt(System.console().readLine());
        System.out.print("day: ");
        int day = Integer.parseInt(System.console().readLine());
        if(!checkDate(year,month,day)){
            return "invalid date inserted!:";
        }
        System.out.print("shift type: ");
        String sType = System.console().readLine();

        if(input.equals("1")){
            output = ss.addConstraint(year,month,day,sType,id);
        }
        else if(input.equals("2")){
            output = ss.removeConstraint(year,month,day,sType,id);
        }
        return output;
    }


    public String hr_loop(){

        System.out.printf("what would you like to do? \n1.add employee\n2.add role\n3.set employee\n4.get employee\n5.set default roles\n6.create shift\n7.block shift\n8.unblock shift\n9.get available employees for shift\n10 change shift");
        String input = System.console().readLine();
        String output = "";
        if(input.equals("1")){
            System.out.printf("enter name");
            String name = System.console().readLine();
            System.out.printf("enter bank account");
            String bankAcc = System.console().readLine();
            System.out.printf("enter employment type");
            String employmentType = System.console().readLine();
            System.out.printf("enter salary");
            int salary = Integer.parseInt(System.console().readLine());
            System.out.printf("enter salary type");
            String salaryType = System.console().readLine();
            System.out.printf("enter vacation days");
            int vacationDays = Integer.parseInt(System.console().readLine());
            System.out.printf("enter roles (seperated by space)");
            String roles = System.console().readLine();
            output = es.addEmployee(name,bankAcc, Arrays.stream(roles.split(" ")).toList(), LocalDate.now(),employmentType,salaryType,salary,vacationDays);
        }
        else if(input.equals("2")){
            System.out.printf("enter id");
            int id = Integer.parseInt(System.console().readLine());
            System.out.printf("enter role");
            String role = System.console().readLine();
            output = es.addRole(id,role);
        }
        else if(input.equals("3")){
            System.out.printf("enter id");
            int id = Integer.parseInt(System.console().readLine());
            System.out.printf("what property tou want to set? \n1.salary\n2.vacation days\n3.employment type\n4.salary type\n5.bank account\n6.name\n7.is manager");
            String prop = System.console().readLine();
            output = setEmployee(id, prop);
        }
        else if (input.equals("4")) {
            System.out.println("enter id");
            int id = Integer.parseInt(System.console().readLine());

            output = es.getEmployee(id);
        }
        else if (input.equals("5")) {
            System.out.println("enter roles with spaces between them");
            String roles = System.console().readLine();
            List<String> rolesList = Arrays.asList(roles.split(" ")).stream().toList();
            output = ss.setDefaultRoles(rolesList);
        }
        else if (input.equals("6")) {
            System.out.println("enter shifts deteailes - ");
            System.out.print("year: ");
            int year = Integer.parseInt(System.console().readLine());
            System.out.print("month: ");
            int month = Integer.parseInt(System.console().readLine());
            System.out.print("day: ");
            int day = Integer.parseInt(System.console().readLine());
            if(!checkDate(year,month,day)){
                return "invalid date inserted!:";
            }
            System.out.print("shift type: ");
            String sType = System.console().readLine();
            System.out.print("roles needed(seperated with spaces) (if you want default roles, press enter): ");
            String roles = System.console().readLine();
            System.out.print("manager id: ");
            int managerId = Integer.parseInt(System.console().readLine());
            output = ss.createShift(year,month,day,sType,Arrays.stream(roles.split(" ")).toList(),managerId);
        }
        else if (input.equals("7")) {
            System.out.println("enter shifts deteailes - ");
            System.out.print("year: ");
            int year = Integer.parseInt(System.console().readLine());
            System.out.print("month: ");
            int month = Integer.parseInt(System.console().readLine());
            System.out.print("day: ");
            int day = Integer.parseInt(System.console().readLine());
            if(!checkDate(year,month,day)){
                return "invalid date inserted!:";
            }
            System.out.print("shift type: ");
            String sType = System.console().readLine();
            output = ss.blockShift(year,month,day,sType);
        }
        else if (input.equals("8")) {
            System.out.println("enter shifts deteailes - ");
            System.out.print("year: ");
            int year = Integer.parseInt(System.console().readLine());
            System.out.print("month: ");
            int month = Integer.parseInt(System.console().readLine());
            System.out.print("day: ");
            int day = Integer.parseInt(System.console().readLine());
            if(!checkDate(year,month,day)){
                System.out.println("input invalid");
                return "invalid date inserted!:";
            }
            System.out.print("shift type: ");
            String sType = System.console().readLine();
            output = ss.unblockShift(year,month,day,sType);
        }
        else if (input.equals("9")) {
            System.out


        }


    }
    public String setEmployee(int id,String prop){
        String output = "";
        if(prop.equals("1")){

            System.out.printf("enter salary");
            int salary = Integer.parseInt(System.console().readLine());
            output = es.setSalary(id,salary);
        }
        else if(prop.equals("2")){
            System.out.printf("enter vacation days");
            int vacationDays = Integer.parseInt(System.console().readLine());
            output = es.setVacationDays(id,vacationDays);
        }
        else if(prop.equals("3")){

            System.out.printf("enter employment type");
            String employmentType = System.console().readLine();
            output = es.setEmplymentType(id,employmentType);
        }
        else if(prop.equals("4")){
            System.out.printf("enter salary type");
            String salaryType = System.console().readLine();
            output = es.setSalaryType(id,salaryType);
        }
        else if(prop.equals("5")){
            System.out.printf("enter bank account");
            String bankAcc = System.console().readLine();
            output = es.setBankAccount(id,bankAcc);
        }
        else if(prop.equals("6")){
            System.out.printf("enter name");
            String name = System.console().readLine();
            output = es.setName(id,name);
        }
        else if(prop.equals("7")){
            System.out.printf("enter is manager");
            boolean isManager = Boolean.parseBoolean(System.console().readLine());
            output = es.setIsManager(id,isManager);
        }
        else{
            output = "invalid input: ";
        }
        return output;



    }

    private boolean checkDate(int year, int month, int day){
        if(day > 31)
            return false;
        if (month > 12)
            return false;
        if(year<LocalDate.now().getYear())
            return false;
        return true;
    }
}