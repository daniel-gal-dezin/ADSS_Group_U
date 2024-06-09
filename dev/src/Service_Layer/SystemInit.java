package Service_Layer;
import Domain_Layer.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class SystemInit {
    private EmployeeManager em;
    private BusinessService bs;
    private EmployeeService es;
    private BusinessManager bm;




    public SystemInit() {
        em = new EmployeeManager();
        es = new EmployeeService(em);
        bm = new BusinessManager(em);
        bs = new BusinessService(bm);
    }




    public BusinessService getBs() {
        return bs;
    }

    public EmployeeService getEs() {
        return es;
    }




    public void adddefualtinit() {
        String filePath = "./docs/data.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String section = "";

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#")) {
                    section = line;
                } else if(line.startsWith("//")){
                    continue;
                }
                else if (!line.isEmpty()) {
                    processLine(section, line, bs, es);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }


    }

    private static void processLine(String section, String line, BusinessService businessService, EmployeeService employeeService) {
        try {
            switch (section) {
                case "# Branches":
                    createBranch(line, businessService);
                    break;
                case "# Employees":
                    addEmployee(line, employeeService);
                    break;
                case "# Shifts":
                    createShift(line, businessService);
                    break;
                case "# Employees in Shifts":
                    addEmployeeToShift(line, businessService);
                    break;
                default:
                    System.err.println("Unknown section: " + section);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error processing line: " + line + " in section: " + section + " - " + e.getMessage());
        }
    }

    private static void createBranch(String line, BusinessService businessService) {
        try {
            String response = businessService.createBranch(line);
        } catch (Exception e) {
            System.err.println("Failed to create branch: " + line + " - " + e.getMessage());
        }
    }

    private static void addEmployee(String line, EmployeeService employeeService) {
        try {
            String[] empData = line.split(";");
            int branchId = Integer.parseInt(empData[0]);
            String name = empData[1];
            String bankAcc = empData[2];
            LocalDate startWork = LocalDate.parse(empData[3]);
            String employmentType = empData[4];
            String salaryType = empData[5];
            int salary = Integer.parseInt(empData[6]);
            int vacationDays = Integer.parseInt(empData[7]);
            boolean isManager = Boolean.parseBoolean(empData[8]);

            String response = employeeService.addEmployee(branchId, name, bankAcc, startWork, employmentType, salaryType, salary, vacationDays, isManager);
        } catch (Exception e) {
            System.err.println("Failed to add employee: " + line + " - " + e.getMessage());
        }
    }

    private static void createShift(String line, BusinessService businessService) {
        try {
            String[] shiftData = line.split(";");
            int brId = Integer.parseInt(shiftData[0]);
            int year = Integer.parseInt(shiftData[1]);
            int month = Integer.parseInt(shiftData[2]);
            int day = Integer.parseInt(shiftData[3]);
            String sType = shiftData[4];
            int managerId = Integer.parseInt(shiftData[5]);

            String response = businessService.createShift(brId, year, month, day, sType, managerId);
        } catch (Exception e) {
            System.err.println("Failed to create shift: " + line + " - " + e.getMessage());
        }
    }

    private static void addEmployeeToShift(String line, BusinessService businessService) {
        try {
            String[] empShiftData = line.split(";");
            int brId = Integer.parseInt(empShiftData[0]);
            int year = Integer.parseInt(empShiftData[1]);
            int month = Integer.parseInt(empShiftData[2]);
            int day = Integer.parseInt(empShiftData[3]);
            String sType = empShiftData[4];
            int empId = Integer.parseInt(empShiftData[5]);

            String response = businessService.addEmployeeToShift(brId, year, month, day, sType, empId);
        } catch (Exception e) {
            System.err.println("Failed to add employee to shift: " + line + " - " + e.getMessage());
        }
    }




}
