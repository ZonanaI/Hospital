package com.solvd.hospital;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


import com.solvd.hospital.exceptions.InvalidAgeException;
import com.solvd.hospital.exceptions.InvalidBloodTypeException;
import com.solvd.hospital.exceptions.InvalidPayRateException;
import com.solvd.hospital.exceptions.InvalidWorkingDayException;
import com.solvd.hospital.people.Employee;
import com.solvd.hospital.people.Patient;
import com.solvd.hospital.people.Physician;
import com.solvd.hospital.rooms.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String command = "";

        Scanner scanner = new Scanner(System.in);
        Set<HospitalRoom> roomSet = new HashSet<>();
        CustomLinkedList<String> welcomeMessages = new CustomLinkedList<>();
        welcomeMessages.insert("Hello, this is Hospital demo program");
        welcomeMessages.insert("You can choose between customizing the hospital rooms, employees and patients");
        welcomeMessages.insert("Also, you can choose to load default configuration");
        welcomeMessages.insert("Testing removing");
        welcomeMessages.remove("Testing removing");
        welcomeMessages.remove("Testing removing");
        for (String message : welcomeMessages) {
            log.info(message);
        }
        while (!command.toLowerCase(Locale.ROOT).equals("f")) {
            log.info("Initial configuration of hospital rooms");
            log.info("Enter:" +
                    "\n\"c\" to add new Consulting room," +
                    "\n\"d\": Delivery room," +
                    "\n\"e\" Emergency room," +
                    "\n\"i\" Intensive care room," +
                    "\n\"m\" Maternity room," +
                    "\n\"n\" Nursery room," +
                    "\n\"r\" Recovery room," +
                    "\n\"s\" Surgery room." +
                    "\n\"f\" to end rooms configuration:" +
                    "\n\"exit\" to close the application,");
            command = scanner.nextLine().toLowerCase();
            if (command.equals("exit") || command.equals("f")) {
                break;
            }
            log.info("Please specify the location of the room (Floor-sector e.g., " +
                    "first floor sector A: 1-a)");
            String location = scanner.nextLine().toLowerCase();
            if (!roomAlreadyExists(roomSet, command, location)) {
                switch (command) {
                    case "c":
                        roomSet.add(new ConsultingRoom(location));
                        break;
                    case "d":
                        roomSet.add(new DeliveryRoom(location));
                        break;
                    case "e":
                        roomSet.add(new EmergencyRoom(location));
                        break;
                    case "i":
                        roomSet.add(new IntensiveCareRoom(location));
                        break;
                    case "m":
                        roomSet.add(new MaternityRoom(location));
                        break;
                    case "n":
                        roomSet.add(new NurseryRoom(location));
                        break;
                    case "r":
                        roomSet.add(new RecoveryRoom(location));
                        break;
                    case "s":
                        roomSet.add(new SurgeryRoom(location));
                        break;
                    default:
                        log.error("Unknown command");
                        break;
                }
            }
        }

        while (!command.toLowerCase(Locale.ROOT).equals("exit")) {
            log.info("Enter:" +
                    "\n\"a\" to add new patient/employee, " +               //Done
                    "\n\"r\" to remove," +                                  //Done
                    "\n\"gp\" to get patients list," +                      //Done
                    "\n\"ge\" for employees list," +                        //Done
                    "\n\"c\" to call patient/employee " +                   //Done
                    "\n\"e\" to evacuate the room," +                       //Done
                    "\n\"m\" to set patient vital signs," +                 //Done
                    "\n\"rv\" to request patient vital signs history," +    //Done
                    "\n\"rc\" to request patient charges," +                //Done
                    "\n\"s\" to schedule an appointment," +                 //Done
                    "\n\"v\" to set employee vacation days," +              //Done
                    "\n\"w\" to set employee worked hours," +               //Done
                    "\n\"rp\" to request employee paycheck," +              //Done
                    "\n\"exit\" to close de application:");                 //Done
            command = scanner.nextLine().toLowerCase();
            if (command.equals("exit")) {
                break;
            }
            if (command.equals("a")) {

                log.info("Enter:" +
                        "\n\"p\" to add patient," +
                        "\n\"e\" to add employee," +
                        "\n\"b\" to go back:");
                command = scanner.nextLine().toLowerCase();

                switch (command) {
                    case "p":
                        log.info("Enter the room initial the patient will be:");
                        String patientRoom = scanner.nextLine().toLowerCase();
                        log.info("Enter the sector room the patient will be (e.g. 1-a):");
                        String patientLocation = scanner.nextLine().toLowerCase();
                        HospitalRoom currentRoom = searchHospitalRoom(roomSet, patientRoom, patientLocation);
                        if (currentRoom != null) {
                            log.info("Enter the patient's age:");
                            int age = Integer.parseInt(scanner.nextLine());
                            log.info("Enter the patient's gender:");
                            String gender = scanner.nextLine().toLowerCase(Locale.ROOT);
                            log.info("Enter the patient's full name:");
                            String fullName = scanner.nextLine();
                            log.info("Enter the patient's ID:");
                            String ID = scanner.nextLine();
                            log.info("Enter the patient's complexity status:");
                            String complexity = scanner.nextLine().toLowerCase(Locale.ROOT);
                            log.info("Enter the patient's bloodType:");
                            String bloodType = scanner.nextLine().toUpperCase();
                            try {
                                currentRoom.addPatientToSet(age, gender, fullName, ID, complexity, bloodType);
                            } catch (InvalidAgeException | InvalidBloodTypeException e) {
                                log.error(e.getMessage());
                            }

                        }
                        break;

                    case "e":
                        log.info("Enter the room initial the employee will be:");
                        String employeeRoom = scanner.nextLine().toLowerCase();
                        log.info("Enter the sector room the employee will be (e.g. 1-a):");
                        String employeeLocation = scanner.nextLine().toLowerCase();
                        currentRoom = searchHospitalRoom(roomSet, employeeRoom, employeeLocation);
                        if (currentRoom != null) {
                            log.info("Enter the employee's age:");
                            int age = Integer.parseInt(scanner.nextLine());
                            log.info("Enter the employee's gender:");
                            String gender = scanner.nextLine().toLowerCase(Locale.ROOT);
                            log.info("Enter the employee's full name:");
                            String fullName = scanner.nextLine();
                            log.info("Enter the employee's profession status:");
                            String profession = scanner.nextLine().toLowerCase(Locale.ROOT);
                            log.info("Enter the employee's ID:");
                            String ID = scanner.nextLine();
                            log.info("Enter the employee's pay rate:");
                            double payRate = Double.parseDouble(scanner.nextLine());
                            log.info("Enter the employee's working days separated by commas (first 3 initials):");
                            String workingDays = scanner.nextLine();
                            ArrayList<Integer> workingDaysList = convertWorkingDays(workingDays);
                            try {
                                currentRoom.addEmployeeToSet(age, gender, fullName, profession,
                                        ID, payRate, workingDaysList);
                            } catch (InvalidAgeException | InvalidPayRateException | InvalidWorkingDayException e) {
                                log.error(e.getMessage());
                            }

                        }
                        break;

                    case "b":
                        break;

                    default:
                        log.error("Wrong command");
                        break;
                }
            }
            if (command.equals("gp")) {
                log.info("Enter \"c\" to get list filter by complexity," +
                        "\n\"r\" for room filtered," +
                        "\n\"a\" to get all patients," +
                        "\n\"b\" to go back:");
                command = scanner.nextLine().toLowerCase();
                Iterator<HospitalRoom> roomIterator = roomSet.iterator();
                switch (command) {

                    case "c":
                        log.info("Enter the desired complexity filter:");
                        String complexity = scanner.nextLine();
                        int totalPatientCount = 0;
                        while (roomIterator.hasNext()) {
                            int partialPatientCount = 0;
                            HospitalRoom currentRoom = roomIterator.next();
                            partialPatientCount += currentRoom.getPatientsCount(complexity);
                            totalPatientCount += currentRoom.getPatientsCount(complexity);
                            log.info("In the " + currentRoom + " there are " +
                                    partialPatientCount + " " + complexity + " complexity patients");
                        }
                        log.info("There are " + totalPatientCount + " " + complexity +
                                " patients in the hospital");
                        break;

                    case "r":
                        log.info("Enter the desired room initial:");
                        String room = scanner.nextLine().toLowerCase();
                        log.info("Enter the desired room location (e.g. 1-a):");
                        String location = scanner.nextLine().toLowerCase();
                        HospitalRoom currentRoom = searchHospitalRoom(roomSet, room, location);
                        if (currentRoom != null) {
                            totalPatientCount = currentRoom.getPatientsCount();
                            log.info("In the " + currentRoom + " there are " +
                                    totalPatientCount + " patients");
                        }
                        break;

                    case "a":
                        totalPatientCount = 0;
                        while (roomIterator.hasNext()) {
                            currentRoom = roomIterator.next();
                            totalPatientCount += currentRoom.getPatientsCount();
                        }
                        log.info("There are " + totalPatientCount + " patients in the hospital");
                        break;

                    case "b":
                        break;

                    default:
                        log.error("Unknown command");
                        break;
                }
            }
            if (command.equals("c")) {
                log.info("Enter the desired calling room initial:");
                String room = scanner.nextLine().toLowerCase();
                log.info("Enter the desired calling room location (e.g. 1-a):");
                String location = scanner.nextLine().toLowerCase();
                log.info("Enter \"p\" to call patient or \"e\" for employee:");
                String personType = scanner.nextLine().toLowerCase();
                log.info("Enter the desired patient/employee name to call:");
                String personName = scanner.nextLine().toLowerCase();
                HospitalRoom currentRoom = searchHospitalRoom(roomSet, room, location);
                if (currentRoom != null) {
                    if (personType.equals("p")) {
                        Patient calledPatient = currentRoom.searchPatient(personName);
                        if (calledPatient != null) {
                            calledPatient.callPerson(currentRoom);
                        } else {
                            log.error("Patient not found");
                        }
                    } else if (personType.equals("e")) {
                        Employee calledEmployee = currentRoom.searchEmployee(personName);
                        if (calledEmployee != null) {
                            calledEmployee.callPerson(currentRoom);
                        } else {
                            log.error("Patient not found");
                        }
                    }
                }
            }
            if (command.equals("e")) {
                log.info("Enter the desired to evacuate room initial:");
                String room = scanner.nextLine().toLowerCase();
                log.info("Enter the desired to evacuate room location (e.g. 1-a):");
                String location = scanner.nextLine().toLowerCase();
                log.info("Enter the evacuation cause:");
                String cause = scanner.nextLine().toLowerCase();
                HospitalRoom currentRoom = searchHospitalRoom(roomSet, room, location);
                if (currentRoom != null) {
                    if (!currentRoom.getPatientsSet().isEmpty()) {
                        for (Patient patient : currentRoom.getPatientsSet()) {
                            patient.evacuateTheRoom(cause);
                        }
                    }
                    if (!currentRoom.getEmployeeSet().isEmpty()) {
                        for (Employee employee : currentRoom.getEmployeeSet()) {
                            employee.evacuateTheRoom(cause);
                        }
                    }
                }
            }
            if (command.equals("m")) {
                log.info("Enter the desired employee name to perform the measurements:");
                String employeeName = scanner.nextLine().toLowerCase();
                log.info("Enter the desired patient name to receive the measurements:");
                String patientName = scanner.nextLine().toLowerCase();
                Employee employee = null;
                Patient patient = null;
                for (HospitalRoom currentRoom : roomSet) {
                    patient = currentRoom.searchPatient(patientName);
                    if (patient != null) {
                        break;
                    }
                }
                for (HospitalRoom currentRoom : roomSet) {
                    employee = currentRoom.searchEmployee(employeeName);
                    if (employee != null) {
                        break;
                    }
                }
                if (patient == null) {
                    log.error("Patient not found");
                } else {
                    if (employee == null) {
                        log.error("Employee not found");
                    } else {
                        employee.measureVitalSigns(patient);
                    }
                }
            }
            if (command.equals("rv")) {
                log.info("Enter the desired patient name to request vital signs:");
                String patientName = scanner.nextLine().toLowerCase();
                Patient patient = null;
                for (HospitalRoom currentRoom : roomSet) {
                    patient = currentRoom.searchPatient(patientName);
                    if (patient != null) {
                        break;
                    }
                }
                if (patient == null) {
                    log.error("Patient not found");
                } else {
                    log.info(patient.getVitalSignsHistory());
                }
            }
            if (command.equals("rc")) {
                log.info("Enter the desired patient name to request charges:");
                String patientName = scanner.nextLine().toLowerCase();
                Patient patient = null;
                for (HospitalRoom currentRoom : roomSet) {
                    patient = currentRoom.searchPatient(patientName);
                    if (patient != null) {
                        break;
                    }
                }
                if (patient == null) {
                    log.error("Patient not found");
                } else {
                    patient.requestCharges();
                }
            }
            if (command.equals("rp")) {
                log.info("Enter the desired employee name to request pay check:");
                String employeeName = scanner.nextLine().toLowerCase();
                Employee employee = null;
                for (HospitalRoom currentRoom : roomSet) {
                    employee = currentRoom.searchEmployee(employeeName);
                    if (employee != null) {
                        break;
                    }
                }
                if (employee == null) {
                    log.error("Employee not found");
                } else {
                    log.info(employee + "has a: $" + employee.getPayCheck() + "to be paid");
                }
            }
            if (command.equals("v")) {
                log.info("Enter the desired employee name to request vacations:");
                String employeeName = scanner.nextLine().toLowerCase();
                Employee employee = null;
                for (HospitalRoom currentRoom : roomSet) {
                    employee = currentRoom.searchEmployee(employeeName);
                    if (employee != null) {
                        break;
                    }
                }
                if (employee == null) {
                    log.error("Employee not found");
                } else {
                    log.info("Please enter the start day of the employees vacation " +
                            "(use yyyy-MM-dd format please):");
                    String startDay = scanner.nextLine();
                    log.info("Please enter the end day of the employees vacation " +
                            "(use yyyy-MM-dd format please):");
                    String endDay = scanner.nextLine();
                    employee.setVacationDays(parseDate(startDay), parseDate(endDay));
                }
            }
            if (command.equals("w")) {
                log.info("Enter the desired employee name to set worked hours:");
                String employeeName = scanner.nextLine().toLowerCase();
                Employee employee = null;
                for (HospitalRoom currentRoom : roomSet) {
                    employee = currentRoom.searchEmployee(employeeName);
                    if (employee != null) {
                        break;
                    }
                }
                if (employee == null) {
                    log.error("Employee not found");
                } else {
                    log.info("Enter the amount of worked hours:");
                    int workedHours = Integer.parseInt(scanner.nextLine());
                    employee.setWorkedHours(workedHours);
                }
            }
            if (command.equals("s")) {
                log.info("Enter the desired physician name to schedule the appointment:");
                String employeeName = scanner.nextLine().toLowerCase();
                log.info("Enter the desired patient name to schedule the appointment:");
                String patientName = scanner.nextLine().toLowerCase();
                Employee employee = null;
                Patient patient = null;
                for (HospitalRoom currentRoom : roomSet) {
                    patient = currentRoom.searchPatient(patientName);
                    if (patient != null) {
                        break;
                    }
                }
                for (HospitalRoom currentRoom : roomSet) {
                    employee = currentRoom.searchEmployee(employeeName);
                    if (employee != null) {
                        break;
                    }
                }
                if (patient == null) {
                    log.error("Patient not found");
                } else {
                    if (employee == null) {
                        log.error("Employee not found");
                    } else if (employee instanceof Physician) {
                        log.info("Enter the date and time for the appointment " +
                                "(use yyyy-MM-dd HH:mm format please):");
                        String strDateTime = scanner.nextLine();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime dateTime = LocalDateTime.parse(strDateTime, formatter);
                        ((Physician) employee).scheduleAppointment(dateTime, patient);
                    } else {
                        log.error("Employee is not a physician");
                    }
                }
            }
        }
        scanner.close();
    }

    static boolean roomAlreadyExists(Set<HospitalRoom> rooms, String roomInitial, String roomSector) {
        for (HospitalRoom currentRoom : rooms) {
            if (currentRoom.getClass().getSimpleName().toLowerCase(Locale.ROOT).startsWith(roomInitial)) {
                if (currentRoom.getLocation().equals(roomSector)) {
                    log.error("Room already exists");
                    return true;
                }
            }
        }
        return false;
    }

    static HospitalRoom searchHospitalRoom(Set<HospitalRoom> rooms, String roomInitial, String roomSector) {
        for (HospitalRoom currentRoom : rooms) {
            if (currentRoom.getClass().getSimpleName().toLowerCase(Locale.ROOT).startsWith(roomInitial)) {
                if (currentRoom.getLocation().equals(roomSector)) {
                    return currentRoom;
                }
            }
        }
        log.error("Room not found");
        return null;
    }

    static ArrayList<Integer> convertWorkingDays(String workingDays) {
        ArrayList<Integer> days = new ArrayList<>();
        workingDays = workingDays.toUpperCase();
        if (workingDays.contains("MON"))
            days.add(1);
        if (workingDays.contains("TUE"))
            days.add(2);
        if (workingDays.contains("WED"))
            days.add(3);
        if (workingDays.contains("THU"))
            days.add(4);
        if (workingDays.contains("FRI"))
            days.add(5);
        if (workingDays.contains("SAT"))
            days.add(6);
        if (workingDays.contains("SUN"))
            days.add(7);
        return days;
    }

    static LocalDate parseDate(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(strDate, formatter);
        return date;
    }


}
