import rooms.*;

import java.util.*;

public class Hospital {


    public static void main (String [] args){
        String command = "";
        Scanner scanner = new Scanner(System.in);
        Set<HospitalRoom> roomSet = new HashSet<>();
        while (!command.toLowerCase(Locale.ROOT).equals("f")){
            System.out.println("Initial configuration of hospital rooms");
            System.out.println("Enter \"c\" to add new Consulting room, \"d\": Delivery room, \"e\" Emergency room," +
                    " \"i\" Intensive care room, \"m\" Maternity room, \n\"n\" Nursery room, \"r\" Recovery room," +
                    " \"s\" Surgery room. Enter \"exit\" to close the application, \"f\" to end rooms configuration:");
            command = scanner.nextLine().toLowerCase();
            if (command.equals("exit") || command.equals("f")){
                break;
            }
            System.out.println("Please specify the location of the room (Floor-sector e.g., " +
                    "first floor sector A: 1-a)");
            String location = scanner.nextLine().toLowerCase();
            if (!roomAlreadyExists(roomSet, command, location)){
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
                        System.out.println("Unknown command");
                        break;
                }
            }
        }

        while (!command.toLowerCase(Locale.ROOT).equals("exit")){
            System.out.println("Enter \"a\" to add new patient/employee, \"r\" to remove, \"gp\" to get" +
                    " patients list, \"ge\" for employees list, \n\"exit\" to close de application:");
            command = scanner.nextLine();
            if (command.equals("exit")){
                break;
            }
            if (command.equals("a")){

                System.out.println("Enter \"p\" to add patient or \"e\" for employee, \"b\" to go back:");
                command = scanner.nextLine();

                Iterator <HospitalRoom> roomIterator = roomSet.iterator();
                switch (command) {
                    case "p":
                        System.out.println("Enter the room initial the patient will be:");
                        String patientRoom = scanner.nextLine().toLowerCase();
                        System.out.println("Enter the sector room the patient will be (e.g. 1-a):");
                        String patientLocation = scanner.nextLine().toLowerCase();
                        HospitalRoom currentRoom = searchHospitalRoom(roomSet, patientRoom, patientLocation);
                        if (currentRoom != null){
                            System.out.println("Enter the patient's age:");
                            int age = 0;
                            try {
                                age = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Enter the patient's gender:");
                            String gender = scanner.nextLine().toLowerCase(Locale.ROOT);
                            System.out.println("Enter the patient's full name:");
                            String fullName = scanner.nextLine();
                            System.out.println("Enter the patient's complexity status:");
                            String complexity = scanner.nextLine().toLowerCase(Locale.ROOT);
                            System.out.println("Enter the patient's social security number:");
                            String socialSecurityNumber = scanner.nextLine();
                            currentRoom.addPatientToSet(age, gender, fullName, complexity,
                                    socialSecurityNumber);
                        }
                        break;

                    case "e":
                        System.out.println("Enter the room initial the employee will be:");
                        String employeeRoom = scanner.nextLine().toLowerCase();
                        System.out.println("Enter the sector room the employee will be (e.g. 1-a):");
                        String employeeLocation = scanner.nextLine().toLowerCase();
                        currentRoom = searchHospitalRoom(roomSet, employeeRoom, employeeLocation);
                        if (currentRoom != null){
                            System.out.println("Enter the employee's age:");
                            int age = 0;
                            try {
                                age = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Enter the employee's gender:");
                            String gender = scanner.nextLine().toLowerCase(Locale.ROOT);
                            System.out.println("Enter the employee's full name:");
                            String fullName = scanner.nextLine();
                            System.out.println("Enter the employee's profession status:");
                            String profession = scanner.nextLine().toLowerCase(Locale.ROOT);
                            System.out.println("Enter the employee's social security number:");
                            String socialSecurityNumber = scanner.nextLine();
                            currentRoom.addEmployeeToSet(age, gender, fullName, profession,
                                    socialSecurityNumber);
                        }
                        break;

                    case "b":
                        break;

                    default:
                        System.out.println("Wrong command");
                        break;
                }
            }
            if (command.equals("gp")){
                System.out.println("Enter \"c\" to get list filter by complexity, \"r\" for room filtered," +
                        " \"a\" to get all patients, \"b\" to go back:");
                command = scanner.nextLine();
                Iterator <HospitalRoom> roomIterator = roomSet.iterator();
                switch (command){

                    case "c":
                        System.out.println("Enter the desired complexity filter:");
                        String complexity = scanner.nextLine();
                        int totalPatientCount = 0;
                        while (roomIterator.hasNext()){
                            int partialPatientCount = 0;
                            HospitalRoom currentRoom = roomIterator.next();
                            partialPatientCount += currentRoom.getPatientsCount(complexity);
                            totalPatientCount += currentRoom.getPatientsCount(complexity);
                            System.out.println("In the " + currentRoom.getClass() + " there are " +
                                    partialPatientCount + " " + complexity + " complexity patients");
                        }
                        System.out.println("There are " + totalPatientCount + " " + complexity +
                                " patients in the hospital");
                        break;

                    case "r":
                        System.out.println("Enter the desired room initial:");
                        String room = scanner.nextLine().toLowerCase();
                        System.out.println("Enter the desired room location (e.g. 1-a):");
                        String location = scanner.nextLine().toLowerCase();
                        HospitalRoom currentRoom = searchHospitalRoom(roomSet, room, location);
                        if (currentRoom != null){
                            totalPatientCount = currentRoom.getPatientsCount();
                            System.out.println("In the " + currentRoom.getClass() + " there are " +
                                    totalPatientCount + " patients");
                        }
                        break;

                        case "a":
                        totalPatientCount = 0;
                        while (roomIterator.hasNext()){
                            currentRoom = roomIterator.next();
                            totalPatientCount += currentRoom.getPatientsCount();
                        }
                        System.out.println("There are " + totalPatientCount + " patients in the hospital");
                        break;

                    case "b":
                        break;

                    default:
                        System.out.println("Unknown command");
                        break;
                }
            }
        }
        scanner.close();
    }
    static boolean roomAlreadyExists (Set<HospitalRoom> rooms, String roomInitial, String roomSector){
        Iterator <HospitalRoom> roomIterator = rooms.iterator();
        while (roomIterator.hasNext()){
            HospitalRoom currentRoom = roomIterator.next();
            if (currentRoom.getClass().getSimpleName().toLowerCase(Locale.ROOT).startsWith(roomInitial)){
                if (currentRoom.getLocation().equals(roomSector)){
                    System.out.println("Room already exists");
                    return true;
                }
            }
        }
        return false;
    }

    static HospitalRoom searchHospitalRoom(Set<HospitalRoom> rooms, String roomInitial, String roomSector){
        Iterator <HospitalRoom> roomIterator = rooms.iterator();
        while (roomIterator.hasNext()){
            HospitalRoom currentRoom = roomIterator.next();
            if (currentRoom.getClass().getSimpleName().toLowerCase(Locale.ROOT).startsWith(roomInitial)){
                if (currentRoom.getLocation().equals(roomSector)){
                    return currentRoom;
                }
            }
        }
        System.out.println("Room not found");
        return null;
    }
}
