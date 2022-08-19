package com.solvd.hospital;


import java.util.*;


import com.solvd.hospital.exceptions.InvalidAgeException;
import com.solvd.hospital.exceptions.InvalidBloodTypeException;
import com.solvd.hospital.rooms.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String command = "";

        Scanner scanner = new Scanner(System.in);
        Set<HospitalRoom> roomSet = new HashSet<>();
        while (!command.toLowerCase(Locale.ROOT).equals("f")) {
            log.info("Initial configuration of hospital rooms");
            log.info("Enter \"c\" to add new Consulting room, \"d\": Delivery room, \"e\" Emergency room," +
                    " \"i\" Intensive care room, \"m\" Maternity room, \n\"n\" Nursery room, \"r\" Recovery room," +
                    " \"s\" Surgery room. Enter \"exit\" to close the application, \"f\" to end rooms configuration:");
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
            log.info("Enter \"a\" to add new patient/employee, \"r\" to remove, \"gp\" to get" +
                    " patients list, \"ge\" for employees list, " + "\"c\" to call patient/employee " +
                    "\n\"exit\" to close de application:");
            command = scanner.nextLine();
            if (command.equals("exit")) {
                break;
            }
            if (command.equals("a")) {

                log.info("Enter \"p\" to add patient or \"e\" for employee, \"b\" to go back:");
                command = scanner.nextLine();

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
                            String bloodType = scanner.nextLine();
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
                            try {
                                currentRoom.addEmployeeToSet(age, gender, fullName, profession,
                                        ID);
                            } catch (InvalidAgeException e) {
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
                log.info("Enter \"c\" to get list filter by complexity, \"r\" for room filtered," +
                        " \"a\" to get all patients, \"b\" to go back:");
                command = scanner.nextLine();
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
                            log.info("In the " + currentRoom.getClass() + " there are " +
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
                            log.info("In the " + currentRoom.getClass() + " there are " +
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
}
