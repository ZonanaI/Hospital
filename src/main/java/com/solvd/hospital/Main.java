package com.solvd.hospital;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


import com.solvd.hospital.Procedures.DefaultProcedures;
import com.solvd.hospital.exceptions.InvalidAgeException;
import com.solvd.hospital.exceptions.InvalidBloodTypeException;
import com.solvd.hospital.exceptions.InvalidPayRateException;
import com.solvd.hospital.exceptions.InvalidWorkingDayException;
import com.solvd.hospital.people.*;
import com.solvd.hospital.rooms.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Thread {


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


        //Testing threads
        TestThread  t1 = new TestThread();
        t1.start();
        TestRunnable test2 = new TestRunnable();
        Thread t2 = new Thread(test2,"test2");
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ignored) {}

        //Testing connection pool
        ConnectionPool pool = ConnectionPool.getInstance();

        ConnectingThread cT1 = new ConnectingThread(5);
        ConnectingThread cT2 = new ConnectingThread(10);
        ConnectingThread cT3 = new ConnectingThread(15);
        ConnectingThread cT4 = new ConnectingThread(20);
        ConnectingThread cT5 = new ConnectingThread(30);
        ConnectingThread cT6 = new ConnectingThread(35);
        ConnectingThread cT7 = new ConnectingThread(40);

        cT1.start();
        cT2.start();
        cT3.start();
        cT4.start();
        cT5.start();
        cT6.start();
        cT7.start();

        try {
            cT1.join();
            cT2.join();
            cT3.join();
            cT4.join();
            cT5.join();
            cT6.join();
            cT7.join();
        } catch (InterruptedException ignored) {}

        pool.closeAllIdleConnections();

        while (!command.toLowerCase(Locale.ROOT).equals("f")) {
            log.info("Initial configuration of hospital rooms");
            log.info("Enter:" +
                    "\n\"de\" to load default rooms, patients, employees and procedures" +
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

            if (command.equals("de")) {
                for (DefaultRooms dR :
                        DefaultRooms.values()) {
                    String roomInitial = dR.getType();
                    String location = dR.getLocation();
                    if (roomSet.stream()
                            .noneMatch(p -> p.toString().startsWith(roomInitial.toUpperCase()))) {
                        roomSet.add(addHospitalRoom(roomInitial, location));
                    } else {
                        log.error("Room already exists");
                    }
                }

                for (DefaultPatients dP :
                        DefaultPatients.values()) {
                    HospitalRoom currentRoom = searchHospitalRoom(roomSet, dP.getRoomInitial(),
                            dP.getSector());
                    if (currentRoom != null) {
                        try {
                            currentRoom.addPatientToSet(dP.getAge(), dP.getGender(), dP.getFullName(), dP.getID(),
                                    dP.getComplexity(), dP.getBloodType());
                        } catch (InvalidAgeException | InvalidBloodTypeException e) {
                            log.error(e.getMessage());
                        }
                    }
                }

                for (DefaultEmployees dE :
                        DefaultEmployees.values()) {
                    ArrayList<Integer> workingDays = convertWorkingDays(dE.getWorkingDays());
                    HospitalRoom currentRoom = searchHospitalRoom(roomSet, dE.getRoomInitial(), dE.getSector());
                    if (currentRoom != null) {
                        try {
                            currentRoom.addEmployeeToSet(dE.getAge(), dE.getGender(), dE.getFullName(),
                                    dE.getProfession(), dE.getID(), dE.getPayRate(), workingDays, dE.getEntryTime(),
                                    dE.getLeavingTime());
                        } catch (InvalidAgeException | InvalidPayRateException | InvalidWorkingDayException e) {
                            log.error(e.getMessage());
                        }
                    }
                }

                for (DefaultProcedures dP :
                        DefaultProcedures.values()) {
                    Patient patient = searchPatientByID(roomSet, dP.getPatientID());
                    roomSet.forEach(j -> j.getEmployeeSet()
                            .stream()
                            .filter(k -> k.getID().equals(dP.getPhysicianID()) && k instanceof Physician)
                            .map(Physician.class::cast)
                            .forEach((l -> l
                                    .addProcedure(dP.getType(), patient, dP.getLocalDateTime()))));

                    Physician physician = (Physician) searchEmployeeByID(roomSet, dP.getPhysicianID());
                    roomSet.forEach(j -> j.getPatientsSet()
                            .stream()
                            .filter(k -> k.getID().equals(dP.getPatientID()))
                            .forEach(l -> l
                                    .addProcedure(dP.getType(), physician, dP.getLocalDateTime())));
                }
                break;
            }
            log.info("Please specify the location of the room (Floor-sector e.g., " +
                    "first floor sector A: 1-a)");
            String location = scanner.nextLine().toLowerCase();
            String finalCommand = command;
            if (roomSet.stream()
                    .noneMatch(p -> p.toString().startsWith(finalCommand.toUpperCase()))) {
                roomSet.add(addHospitalRoom(command, location));
            } else {
                log.error("Room already exists");
            }
        }

        while (!command.toLowerCase(Locale.ROOT).equals("exit")) {
            log.info("Enter:" +
                    "\n\"a\" to add new patient/employee," +
                    "\n\"gp\" to get patients list," +
                    "\n\"ge\" for employees list," +
                    "\n\"c\" to call patient/employee " +
                    "\n\"e\" to evacuate the room," +
                    "\n\"m\" to set patient vital signs," +
                    "\n\"rv\" to request patient vital signs history," +
                    "\n\"rc\" to request patient charges," +
                    "\n\"cd\" to check if patient can receive blood from other," +
                    "\n\"s\" to schedule an appointment," +
                    "\n\"v\" to set employee vacation days," +
                    "\n\"w\" to set employee worked hours," +
                    "\n\"rp\" to request employee paycheck," +
                    "\n\"exit\" to close de application:");
            command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "exit":
                    break;
                case "a":
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
                                log.info("Enter the patient's bloodType (AB_NEGATIVE format):");
                                String bloodType = scanner.nextLine().toUpperCase();
                                try {
                                    currentRoom.addPatientToSet(age, gender, fullName, ID, complexity,
                                            bloodType);
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
                                log.info("Enter the employee's entry time (HH:MM format):");
                                LocalTime entryTime = LocalTime.parse(scanner.nextLine());
                                log.info("Enter the employee's leaving time (HH:MM format):");
                                LocalTime leavingTime = LocalTime.parse(scanner.nextLine());
                                ArrayList<Integer> workingDaysList = convertWorkingDays(workingDays);
                                try {
                                    currentRoom.addEmployeeToSet(age, gender, fullName, profession,
                                            ID, payRate, workingDaysList, entryTime, leavingTime);
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
                    break;
                case "gp":
                    log.info("Enter " +
                            "\n\"c\" to get list filter by complexity," +
                            "\n\"r\" for room filtered," +
                            "\n\"a\" to get all patients," +
                            "\n\"b\" to go back:");
                    command = scanner.nextLine().toLowerCase();
                    Iterator<HospitalRoom> roomIterator = roomSet.iterator();
                    switch (command) {

                        case "c":
                            log.info("Enter the desired complexity filter:");
                            String complexity = scanner.nextLine();
                            long totalPatientCount = 0;
                            while (roomIterator.hasNext()) {
                                int partialPatientCount = 0;
                                HospitalRoom currentRoom = roomIterator.next();
                                partialPatientCount += currentRoom.getPatientsCount(complexity);
                                totalPatientCount += currentRoom.getPatientsCount(complexity);
                                log.info("In the " + currentRoom + " there are " +
                                        partialPatientCount + " " + complexity + " complexity patients");
                                if (!currentRoom.getPatientsSet().isEmpty()) {
                                    currentRoom.getPatientsSet().forEach(i -> log.info("Patient: " + i));
                                }
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
                                if (!currentRoom.getPatientsSet().isEmpty()) {
                                    currentRoom.getPatientsSet().forEach(i -> log.info("Patient: " + i));
                                }
                            }
                            break;

                        case "a":
                            totalPatientCount = 0;
                            while (roomIterator.hasNext()) {
                                currentRoom = roomIterator.next();
                                totalPatientCount += currentRoom.getPatientsCount();
                            }
                            log.info("There are " + totalPatientCount + " patients in the hospital");

                            roomSet.forEach(j -> j.getPatientsSet().forEach(i -> log.info("Patient: " + i +
                                    " in " + j)));

                            break;

                        case "b":
                            break;

                        default:
                            log.error("Unknown command");
                            break;
                    }
                    break;

                case "ge":
                    roomSet.forEach(j -> j.getEmployeeSet().forEach(i ->
                            log.info(i + " ID: " + i.getID() + " in " + j)));
                    break;

                case "c":
                    log.info("Enter the desired calling room initial:");
                    String room = scanner.nextLine().toLowerCase();
                    log.info("Enter the desired calling room location (e.g. 1-a):");
                    String location = scanner.nextLine().toLowerCase();
                    log.info("Enter \"p\" to call patient or \"e\" for employee:");
                    String personType = scanner.nextLine().toLowerCase();
                    log.info("Enter the desired patient/employee ID to call:");
                    String personID = scanner.nextLine().toLowerCase();
                    HospitalRoom currentRoom = searchHospitalRoom(roomSet, room, location);
                    if (currentRoom != null) {
                        if (personType.equals("p")) {
                            Patient calledPatient = searchPatientByID(roomSet, personID);
                            if (calledPatient != null) {
                                calledPatient.callPerson(currentRoom);
                            } else {
                                log.error("Patient not found");
                            }
                        } else if (personType.equals("e")) {
                            Employee calledEmployee = searchEmployeeByID(roomSet, personID);
                            if (calledEmployee != null) {
                                calledEmployee.callPerson(currentRoom);
                            } else {
                                log.error("Patient not found");
                            }
                        }
                    }
                    break;
                case "e":
                    log.info("Enter the desired to evacuate room initial:");
                    room = scanner.nextLine().toLowerCase();
                    log.info("Enter the desired to evacuate room location (e.g. 1-a):");
                    location = scanner.nextLine().toLowerCase();
                    log.info("Enter the evacuation cause:");
                    String cause = scanner.nextLine().toLowerCase();
                    currentRoom = searchHospitalRoom(roomSet, room, location);
                    if (currentRoom != null) {
                        if (!currentRoom.getPatientsSet().isEmpty()) {
                            for (Patient patient :
                                    currentRoom.getPatientsSet()) {
                                patient.evacuateTheRoom(cause);
                            }
                        } else {
                            log.info("Room has no patients");
                        }
                        if (!currentRoom.getEmployeeSet().isEmpty()) {
                            for (Employee employee :
                                    currentRoom.getEmployeeSet()) {
                                employee.evacuateTheRoom(cause);
                            }
                        } else {
                            log.info("Room has no employees");
                        }
                    }
                    break;
                case "m":
                    log.info("Enter the desired employee ID to perform the measurements:");
                    String employeeID = scanner.nextLine().toLowerCase();
                    log.info("Enter the desired patient ID to receive the measurements:");
                    String patientID = scanner.nextLine().toLowerCase();
                    Employee employee = searchEmployeeByID(roomSet, employeeID);
                    Patient patient = searchPatientByID(roomSet, patientID);
                    if (patient == null) {
                        log.error("Patient not found");
                    } else {
                        if (employee == null) {
                            log.error("Employee not found");
                        } else {
                            employee.measureVitalSigns(patient);
                        }
                    }
                    break;
                case "rv":
                    log.info("Enter the desired patient ID to request vital signs:");
                    patientID = scanner.nextLine().toLowerCase();
                    patient = searchPatientByID(roomSet, patientID);
                    if (patient == null) {
                        log.error("Patient not found");
                    } else {
                        log.info(patient.getVitalSignsHistory());
                    }
                    break;
                case "rc":
                    log.info("Enter the desired patient ID to request charges:");
                    patientID = scanner.nextLine().toLowerCase();
                    roomSet.forEach(j -> j.getPatientsSet()
                            .stream()
                            .filter(k -> k.getID().equals(patientID))
                            .forEach(Patient::requestCharges));

                    break;
                case "s":
                    log.info("Enter the desired physician ID to schedule the appointment:");
                    employeeID = scanner.nextLine().toLowerCase();
                    log.info("Enter the desired patient ID to schedule the appointment:");
                    patientID = scanner.nextLine().toLowerCase();
                    employee = searchEmployeeByID(roomSet, employeeID);
                    patient = searchPatientByID(roomSet, patientID);
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
                    break;

                case "v":
                    log.info("Enter the desired employee ID to request vacations:");
                    employeeID = scanner.nextLine().toLowerCase();
                    employee = searchEmployeeByID(roomSet, employeeID);
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
                    break;

                case "w":
                    log.info("Enter the desired employee ID to set worked hours:");
                    employeeID = scanner.nextLine().toLowerCase();
                    employee = searchEmployeeByID(roomSet, employeeID);
                    if (employee == null) {
                        log.error("Employee not found");
                    } else {
                        log.info("Enter the amount of worked hours:");
                        int workedHours = Integer.parseInt(scanner.nextLine());
                        employee.setWorkedHours(workedHours);
                    }
                    break;

                case "rp":
                    log.info("Enter the desired employee ID to request pay check:");
                    employeeID = scanner.nextLine().toLowerCase();
                    employee = searchEmployeeByID(roomSet, employeeID);
                    if (employee == null) {
                        log.error("Employee not found");
                    } else {
                        log.info(employee + " has a: $" + employee.getPayCheck() + " to be paid");
                    }
                    break;

                case "cd":
                    log.info("Enter the receiving blood patient ID to check compatibility:");
                    String receivingPatientID = scanner.nextLine().toLowerCase();
                    log.info("Enter the giving blood patient ID to check compatibility:");
                    String givingPatientID = scanner.nextLine().toLowerCase();
                    Patient receivingPatient = searchPatientByID(roomSet, receivingPatientID);
                    Patient givingPatient = searchPatientByID(roomSet, givingPatientID);
                    if (receivingPatient == null) {
                        log.error("Receiving patient not found");
                    } else {
                        if (givingPatient == null) {
                            log.error("Giving patient not found");
                        } else {
                            receivingPatient.canReceiveFrom(givingPatient);
                        }
                    }
                    break;
            }
        }
        scanner.close();
    }

    static HospitalRoom addHospitalRoom(String roomInitial, String location) {
        switch (roomInitial) {
            case "c":
                return new ConsultingRoom(location);
            case "d":
                return new DeliveryRoom(location);
            case "e":
                return new EmergencyRoom(location);
            case "i":
                return new IntensiveCareRoom(location);
            case "m":
                return new MaternityRoom(location);
            case "n":
                return new NurseryRoom(location);
            case "r":
                return new RecoveryRoom(location);
            case "s":
                return new SurgeryRoom(location);
            default:
                log.error("Unknown room initial");
                return null;
        }
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
        return LocalDate.parse(strDate, formatter);
    }

    static Patient searchPatientByID(Set<HospitalRoom> roomSet, String ID) {
        for (HospitalRoom room :
                roomSet) {
            for (Patient patient :
                    room.getPatientsSet()) {
                if (patient.getID().toLowerCase().equals(ID)) {
                    return patient;
                }
            }
        }
        return null;
    }

    static Employee searchEmployeeByID(Set<HospitalRoom> roomSet, String ID) {
        for (HospitalRoom room :
                roomSet) {
            for (Employee employee :
                    room.getEmployeeSet()) {
                if (employee.getID().toLowerCase().equals(ID)) {
                    return employee;
                }
            }
        }
        return null;
    }
}
