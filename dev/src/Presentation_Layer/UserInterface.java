package Presentation_Layer;

import Service_Layer.EmployeeService;
import Service_Layer.BusinessService;
import Service_Layer.SystemInit;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class UserInterface {

    SystemInit si;
    EmployeeService es;
    BusinessService bs;

    public UserInterface(SystemInit in){
        si = in;
        es = si.getEs();
        bs = si.getSs();
    }



    public void main_loop(){

        System.out.println("welcome to the system\n");
        System.out.println("would you like to recover default data? y/n");
        String input = System.console().readLine();
        if(input.equals("y")){
            si.adddefualtinit();
        }

        System.out.println("Who is using the system? \n1.hr\n2.worker");
        String input1 = System.console().readLine();
        int bId;
        while(true) {
            System.out.println("on which branch are you working?");
            bId = numberChecker();
            String bExsist = bs.checkBranch(bId);
            if(bExsist == "") break;
            System.out.println(bExsist);
        }

        if(input1.equals("1")){
            hr_loop(bId);
        }
        else if(input1.equals("2")){
            worker_loop(bId);
        }
        else{
            System.out.println("invalid input");
        }
    }


    public void worker_loop(int bId){
        while(true) {
            System.out.println("what is your id?");
            int id = numberChecker();
            System.out.println("what would you like to do? \n1.add constraint\n2.remove constraint\n3.exit");
            String input = System.console().readLine();
            String output = "";
            int[] date = inputShiftDetails();
            int year = date[0];
            int month = date[1];
            int day = date[2];
            int sType = date[3];

            if (input.equals("1")) {
                output = bs.addConstraint(bId, year, month, day, sType == 1? "morning":"evening", id);
            } else if (input.equals("2")) {
                output = bs.removeConstraint(bId, year, month, day, sType == 1? "morning":"evening", id);
            } else if(input.equals("3")){
                break;
            }
            else{
                System.out.println("invalid input!");
            }
            new Response(output).print();
        }
    }


    public void hr_loop(int bId) {
        while (true) {
            System.out.println("what would you like to do? \n1.add employee\n2.add role\n3.set employee\n4.get employee\n5.set default roles\n6.create shift\n7.block shift\n8.unblock shift\n9.get available employees for shift\n10. change shift\n11. create new branch\n12 exit");
            String input = System.console().readLine();
            String output = "";
            if (input.equals("1")) {
                System.out.println("enter name");
                String name = System.console().readLine();
                System.out.println("enter bank account");
                String bankAcc = System.console().readLine();
                int employmentType;
                while(true) {
                    System.out.println("enter employment type\n1.Full\n2.Partial");
                    employmentType = numberChecker();
                    if (employmentType != 1 && employmentType != 2)
                        System.out.println("invalid input!");
                    else
                        break;
                }
                System.out.println("enter salary");
                int salary = numberChecker();
                int salaryType;
                while(true){
                    System.out.println("enter salary type\n1.Global\n2.Hourly");

                    salaryType = numberChecker();
                    if (salaryType != 1 && salaryType != 2)
                        System.out.println("invalid input!");
                    else
                        break;
                }

                System.out.println("enter vacation days");
                int vacationDays= numberChecker();
                int isManager;
                while(true){
                    System.out.println("Is the user manager?\n1.yes\n2.no");
                    isManager = numberChecker();
                    if (salaryType != 1 && salaryType != 2)
                        System.out.println("invalid input!");
                    else
                        break;
                }

                output = es.addEmployee(bId, name, bankAcc, LocalDate.now(), employmentType == 1? "full":"partial", salaryType==1? "global":"hourly", salary, vacationDays, isManager == 1);
            } else if (input.equals("2")) {
                System.out.println("enter id");
                int id = numberChecker();
                System.out.println("enter role");
                String role = System.console().readLine();
                output = es.addRole(id, role);
            } else if (input.equals("3")) {
                System.out.println("enter id");
                int id = numberChecker();
                System.out.println("what property tou want to set? \n1.salary\n2.vacation days\n3.employment type\n4.salary type\n5.bank account\n6.name\n7.is manager");
                String prop = System.console().readLine();
                output = setEmployee(id, prop);
            } else if (input.equals("4")) {
                System.out.println("enter id");
                int id = numberChecker();
                output = es.getEmployee(id);
            } else if (input.equals("5")) {
                System.out.println("enter roles with spaces between them");
                String roles = System.console().readLine();
                List<String> rolesList = Arrays.asList(roles.split(" ")).stream().toList();
                output = bs.setDefaultRoles(rolesList);
            } else if (input.equals("6")) {
                int[] date = inputShiftDetails();
                int year = date[0];
                int month = date[1];
                int day = date[2];
                int sType = date[3];
                System.out.print("roles needed(seperated with spaces) (if you want default roles, press enter): ");
                String roles = System.console().readLine();
                System.out.print("manager id: ");
                int managerId = numberChecker();
                output = bs.createShift(bId, year, month, day, sType == 1? "morning":"evening", Arrays.stream(roles.split(" ")).toList(), managerId);
            } else if (input.equals("7")) {
                int[] date = inputShiftDetails();
                if (date == null) output =  "invalid date inserted!:";
                int year = date[0];
                int month = date[1];
                int day = date[2];
                int sType = date[3];
                output = bs.blockShift(bId, year, month, day, sType==1?"morning":"evening");
            } else if (input.equals("8")) {
                int[] date = inputShiftDetails();
                if (date == null) output =  "invalid date inserted!:";
                int year = date[0];
                int month = date[1];
                int day = date[2];
                int sType = date[3];
                output = bs.unblockShift(bId, year, month, day, sType == 1? "morning":"evening");
            } else if (input.equals("9")) {
                int[] date = inputShiftDetails();
                int year = date[0];
                int month = date[1];
                int day = date[2];
                int sType = date[3];
                output = bs.getAvailableEmployees(bId, year, month, day, sType==1?"morning":"evening");
            } else if (input.equals("10")) {
                int[] date = inputShiftDetails();
                int year = date[0];
                int month = date[1];
                int day = date[2];
                int sType = date[3];
                System.out.print("new manager id: ");
                int managerId = numberChecker();
                output = bs.changeManager(bId, year, month, day, sType==1?"morning":"evening", managerId);

            } else if (input.equals("11")) {
                String name = System.console().readLine();
                output = bs.createBranch(name);
            } else if (input.equals("12")) {
                break;
            }
            else {
                output = "invalid input";
            }

            new Response(output).print();
        }
    }
    public String setEmployee(int id,String prop){
        String output = "";
        if(prop.equals("1")){

            System.out.println("enter salary");
            int salary = numberChecker();
            output = es.setSalary(id,salary);
        }
        else if(prop.equals("2")){
            System.out.println("enter vacation days");
            int vacationDays = numberChecker();
            output = es.setVacationDays(id,vacationDays);
        }
        else if(prop.equals("3")){

            System.out.println("enter employment type");
            String employmentType = System.console().readLine();
            output = es.setEmplymentType(id,employmentType);
        }
        else if(prop.equals("4")){
            System.out.println("enter salary type");
            String salaryType = System.console().readLine();
            output = es.setSalaryType(id,salaryType);
        }
        else if(prop.equals("5")){
            System.out.println("enter bank account");
            String bankAcc = System.console().readLine();
            output = es.setBankAccount(id,bankAcc);
        }
        else if(prop.equals("6")){
            System.out.println("enter name");
            String name = System.console().readLine();
            output = es.setName(id,name);
        }
        else if(prop.equals("7")){
            System.out.println("enter is manager\n1.");
            boolean isManager = Boolean.parseBoolean(System.console().readLine());
            output = es.setIsManager(id,isManager);
        }
        else{
            output = "invalid input: ";
        }
        return output;



    }

    private boolean isValidDate(int year, int month, int day, int sType){
        if(day > 31)
            return false;
        if (month > 12)
            return false;
        if(year<1990)
            return false;
        if(sType!=1 && sType != 2)
            return false;
        return true;
    }

    private int[] inputShiftDetails(){
        int[] ans = new int[4];
        while(true) {
            System.out.println("enter shifts deteailes - ");
            System.out.print("year: ");
            ans[0] = numberChecker();
            System.out.print("month: ");
            ans[1] = numberChecker();
            System.out.print("day: ");
            ans[2] = numberChecker();
            System.out.println("shift type: \n1.morning\n2.evening");
            ans[3] = numberChecker();
            if (isValidDate(ans[0],ans[1],ans[2],ans[3])) {
                return ans;
            }
            System.out.println("input invalid");
        }
    }

    private int numberChecker(){
        int ans;
        while(true){
            try{
                String input = System.console().readLine();
                ans = Integer.parseInt(input);
                break;
            }catch (Exception e){
                System.out.println("invalid input- insert number");
            }
        }
        return ans;
    }


}