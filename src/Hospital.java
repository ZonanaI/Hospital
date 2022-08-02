import rooms.*;

import java.util.*;

public class Hospital {


    public static void main (String [] args){
        String command = "";
        Scanner scanner = new Scanner(System.in);
        Set<HospitalRoom> rooms = new HashSet<>();
        while (!command.toLowerCase(Locale.ROOT).equals("f")){
            System.out.println("Initial configuration of hospital rooms");
            System.out.println("Enter \"c\" to add new Consulting room, \"d\": Delivery room \"e\" Emergency room" +
                    "\"i\" intensive care room, \"m\" maternity room, \"n\" nursery room, \"r\" recovery room" +
                    "\"s\" surgery room. Enter \"exit\" to close the application, \"f\" to end rooms configuration:");
            command = scanner.next().toLowerCase();
            if (command.equals("exit")){
                break;
            }
            System.out.println("Please specify the location of the room (Floor-sector e.g., " +
                    "first floor sector A: 1-A)");
            String location = scanner.next().toUpperCase();
            switch (command){
                case "c":
                    rooms.add(new ConsultingRoom(location));
                    break;
                case "d":
                    rooms.add(new DeliveryRoom(location));
                    break;
                case "e":
                    rooms.add(new EmergencyRoom(location));
                    break;
                case "i":
                    rooms.add(new IntensiveCareRoom(location));
                    break;
                case "m":
                    rooms.add(new MaternityRoom(location));
                    break;
                case "n":
                    rooms.add(new NurseryRoom(location));
                    break;
                case "r":
                    rooms.add(new RecoveryRoom(location));
                    break;
                case "s":
                    rooms.add(new SurgeryRoom(location));
                    break;
                case "f":
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }

        while (!command.toLowerCase(Locale.ROOT).equals("exit")){
            System.out.println("Enter \"add\" to add new patient/employee, \"r\" to remove, \"gp\" to get" +
                    " patients list, \"ge\" for employees list, \"exit\" to close de application:");
            command = scanner.next();
            if (command.equals("exit")){
                break;
            }
            if (command.equals("a")){

                System.out.println("Enter \"p\" to add patient or \"e\" for employee, \"b\" to go back:");
                command = scanner.next();

                Iterator <HospitalRoom> roomIterator = rooms.iterator();
                switch (command) {
                    case "p":
                        System.out.println("Enter the room initial the patient will be:");
                        String patientRoom = scanner.next().toLowerCase();
                        System.out.println("Enter the sector room the patient will be (e.g. 1-A):");
                        String patientLocation = scanner.next().toUpperCase();

                        while (roomIterator.hasNext()){
                            HospitalRoom currentRoom = roomIterator.next();
                            if (currentRoom.getClass().getSimpleName().toLowerCase(Locale.ROOT)
                                    .equals(patientRoom)){
                                if (currentRoom.getLocation().equals(patientLocation)) {
                                    System.out.println("Enter the patient's age:");
                                    int age = scanner.nextInt();
                                    System.out.println("Enter the patient's gender:");
                                    String gender = scanner.next().toLowerCase(Locale.ROOT);
                                    System.out.println("Enter the patient's full name:");
                                    String fullName = scanner.next();
                                    System.out.println("Enter the patient's complexity status:");
                                    String complexity = scanner.next().toLowerCase(Locale.ROOT);
                                    System.out.println("Enter the patient's social security number:");
                                    String socialSecurityNumber = scanner.next();
                                    currentRoom.addPatientToSet(age, gender, fullName, complexity,
                                            socialSecurityNumber);
                                } else {
                                     System.out.println("Incorrect room sector");
                                }
                            } else {
                                System.out.println("Incorrect room type");
                            }
                        }

                        break;
                    case "e":
                        System.out.println("Enter the room initial the employee will be:");
                        String employeeRoom = scanner.next().toLowerCase();
                        System.out.println("Enter the sector room the employee will be (e.g. 1-A):");
                        String employeeLocation = scanner.next().toUpperCase();

                        while (roomIterator.hasNext()){
                            HospitalRoom currentRoom = roomIterator.next();
                            if (currentRoom.getClass().getSimpleName().toLowerCase(Locale.ROOT)
                                    .equals(employeeRoom)){
                                if (currentRoom.getLocation().equals(employeeLocation)) {
                                    System.out.println("Enter the employee's age:");
                                    int age = scanner.nextInt();
                                    System.out.println("Enter the employee's gender:");
                                    String gender = scanner.next().toLowerCase(Locale.ROOT);
                                    System.out.println("Enter the employee's full name:");
                                    String fullName = scanner.next();
                                    System.out.println("Enter the employee's profession status:");
                                    String profession = scanner.next().toLowerCase(Locale.ROOT);
                                    System.out.println("Enter the employee's social security number:");
                                    String socialSecurityNumber = scanner.next();
                                    currentRoom.addEmployeeToSet(age, gender, fullName, profession,
                                            socialSecurityNumber);
                                } else {
                                    System.out.println("Incorrect room sector");
                                }
                            } else {
                                System.out.println("Incorrect room type");
                            }
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
                command = scanner.next();
                Iterator <HospitalRoom> roomIterator = rooms.iterator();
                switch (command){

                    case "c":
                        System.out.println("Enter the desired complexity filter:");
                        String complexity = scanner.next();
                        int totalPatientCount = 0;
                        while (roomIterator.hasNext()){
                            int partialPatientCount = 0;
                            HospitalRoom currentRoom = roomIterator.next();
                            partialPatientCount += currentRoom.getPatientsCount(complexity);
                            totalPatientCount += currentRoom.getPatientsCount(complexity);
                            System.out.println("In the " + currentRoom.getClass() + " there are " +
                                    partialPatientCount + " " + complexity + " patients");
                        }
                        System.out.println("There are " + totalPatientCount + " " + complexity +
                                " patients in the hospital");
                        break;

                    case "r":
                        System.out.println("Enter the desired room initial:");
                        String room = scanner.next();
                        System.out.println("Enter the desired room location (e.g. 1-A):");
                        String location = scanner.next();
                        while (roomIterator.hasNext()){
                            HospitalRoom currentRoom = roomIterator.next();
                            if (currentRoom.getClass().getSimpleName().startsWith(room)){
                                if (currentRoom.getLocation().equals(location)){
                                    totalPatientCount = currentRoom.getPatientsCount();
                                    System.out.println("In the " + currentRoom.getClass() + " there are " +
                                            totalPatientCount + " patients");
                                    break;
                                }
                            }
                        }

                    case "a":
                        totalPatientCount = 0;
                        while (roomIterator.hasNext()){
                            HospitalRoom currentRoom = roomIterator.next();
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
}
