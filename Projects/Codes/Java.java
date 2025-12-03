import java.util.*;

abstract class Room {
    ArrayList<String> services = new ArrayList<>();
    ArrayList<String> additionalservices = new ArrayList<>();
    int quantityOfRoom;
    int cost;

    abstract int cost(int x);
    abstract void reserve(int x);
    abstract void displayServices();
    abstract void displayavailable();
    abstract void changeServices(ArrayList<String> temp);
}

class OrdinaryRoom extends Room {
    OrdinaryRoom() {
        Random rand = new Random();
        quantityOfRoom = rand.nextInt(30) + 1;
        cost = 30;
        services.add("Breakfast");
        additionalservices.addAll(Arrays.asList("Dinner", "Cleaning", "Free parking", "Private gym"));
    }

    @Override
    void displayServices() {
        System.out.println("\n──────────── Ordinary Room Details ────────────");
        System.out.println(" ✨ Services: " + services);
        System.out.println(" 💲 Cost per room: $" + cost);
        System.out.println(" 🏨 Available rooms: " + quantityOfRoom);
        System.out.println(" ➕ Additional services: " + additionalservices);
        System.out.println("──────────────────────────────────────────────\n");
    }

    @Override
    void displayavailable() {
        System.out.printf(" Ordinary → $%d | Available: %d rooms\n", cost, quantityOfRoom);
    }

    int cost(int x) {
        return this.cost * x;
    }

    void reserve(int x) {
        if (x > this.quantityOfRoom) {
            System.out.println("❌ INVALID! Available rooms only: " + quantityOfRoom);
        } else {
            System.out.println("✅ Successfully reserved!");
            this.quantityOfRoom -= x;
            System.out.println(" Remaining rooms: " + quantityOfRoom);
        }
    }

    @Override
    void changeServices(ArrayList<String> newServices) {
        for(String i: newServices){
            if(services.contains(i));
            else{
                services.add(i.replaceAll(" ", ""));
            }
        }
    }
}

class VIPRoom extends Room {
    VIPRoom() {
        Random rand = new Random();
        quantityOfRoom = rand.nextInt(15) + 1;
        cost = 50;
        services.addAll(Arrays.asList("Breakfast", "Dinner", "Cleaning", "Free parking", "Jacuzzi"));
    }

    @Override
    void displayavailable() {
        System.out.printf(" VIP → $%d | Available: %d rooms\n", cost, quantityOfRoom);
    }

    @Override
    void displayServices() {
        System.out.println("\n──────────── VIP Room Details ────────────");
        System.out.println(" 🛎 Services: " + services);
        System.out.println(" 💲 Cost per room: $" + cost);
        System.out.println(" 🏨 Available rooms: " + quantityOfRoom);
        System.out.println("───────────────────────────────────────────\n");
    }

    int cost(int x) {
        return this.cost * x;
    }

    void reserve(int x) {
        if (x > this.quantityOfRoom) {
            System.out.println("❌ INVALID! Available rooms only: " + quantityOfRoom);
        } else {
            System.out.println("✅ Successfully reserved!");
            this.quantityOfRoom -= x;
            System.out.println(" Remaining rooms: " + quantityOfRoom);
        }
    }

    @Override
    void changeServices(ArrayList<String> newServices) {
    }
}
class PresidentialRoom extends Room {
    PresidentialRoom() {
        Random rand = new Random();
        quantityOfRoom = rand.nextInt(5) + 1;
        cost = 120;
        services.addAll(Arrays.asList(
                "Breakfast", "Dinner", "Cleaning", "Free parking",
                "Private gym", "Premium mini-bar", "Terrace with view"
        ));
    }

    @Override
    void displayavailable() {
        System.out.printf(" Presidential → $%d | Available: %d rooms\n", cost, quantityOfRoom);
    }

    @Override
    void displayServices() {
        System.out.println("\n─────────── Presidential Room Details ──────────");
        System.out.println(" 👑 Services: " + services);
        System.out.println(" 💲 Cost per room: $" + cost);
        System.out.println(" 🏨 Available rooms: " + quantityOfRoom);
        System.out.println("────────────────────────────────────────────────\n");
    }

    int cost(int x) {
        return this.cost * x;
    }

    void reserve(int x) {
        if (x > this.quantityOfRoom) {
            System.out.println("❌ INVALID! Available rooms only: " + quantityOfRoom);
        } else {
            System.out.println("✅ Successfully reserved!");
            this.quantityOfRoom -= x;
            System.out.println(" Remaining rooms: " + quantityOfRoom);
        }
    }

    @Override
    void changeServices(ArrayList<String> newServices) {
    }
}
        public class Java {
            public static void main(String[] args) {
                Scanner sc = new Scanner(System.in);
                System.out.println("\n═════════════════════════════════════════");
                System.out.println("      ⭐️ Welcome to Wawligim-ai Hotel ⭐️");
                System.out.println("      Assistant: Kairat Nurtas 🎤");
                System.out.println("═════════════════════════════════════════");

                System.out.print("      Name: ");
                String name = sc.nextLine();
                System.out.print("      Contacts: ");
                String contacts = sc.nextLine();
                System.out.println("═════════════════════════════════════════");
                Room ordinary = new OrdinaryRoom();
                Room vip = new VIPRoom();
                Room president = new PresidentialRoom();
                Room[] rooms = {ordinary, vip, president};

                int current_cost = 0;
                int bron = 0;

                while (true) {

                    System.out.println("\n────────── Available Rooms ──────────");
                    ordinary.displayavailable();
                    vip.displayavailable();
                    president.displayavailable();
                    System.out.println("──────────────────────────────────────");

                    if (bron == 0) {
                        System.out.print("\nYour choice (1-Ordinary, 2-VIP, 3-President, 4-Pay): ");
                    } else {
                        System.out.println("\nIf you want to reserve again — enter room number.");
                    }

                    String type = sc.next();

                    bron = 1;
                    char type_room = type.charAt(0);

                    if (type_room == '1') {
                        ordinary.displayServices();
                        while (true) {
                            ArrayList<String> temp = new ArrayList<>();
                            System.out.print("Services | Your choice (1-Add, 2-Remove, 3-Confirm):");
                            int choiceadditional = sc.nextInt();
                            sc.nextLine();
                            switch (choiceadditional) {
                                case 1:
                                    System.out.println("Input additional services (Split by comma)");
                                    String line = sc.nextLine();
                                    temp.addAll(Arrays.asList(line.split(",")));
                                    ordinary.changeServices(temp);
                                    ordinary.displayServices();
                                    break;
                                case 2:
                                    System.out.println("Input services (Split by space)");
                                    break;
                            }
                            if (choiceadditional == 3) break;
                        }
                        System.out.print("How many rooms?: ");
                        int count = sc.nextInt();
                        ordinary.reserve(count);
                        current_cost += ordinary.cost(count);
                    } else if (type_room == '2') {
                        vip.displayServices();
                        System.out.print("How many rooms?: ");
                        int count = sc.nextInt();
                        vip.reserve(count);
                        current_cost += vip.cost(count);
                    } else if (type_room == '3') {
                        president.displayServices();
                        System.out.print("How many rooms?: ");
                        int count = sc.nextInt();
                        president.reserve(count);
                        current_cost += president.cost(count);
                    } else if (type_room == '4') {
                        System.out.println("\n══════════════════════════════════════");
                        System.out.println(" 💳 Payment section");
                        System.out.println(" Total amount: $" + current_cost);
                        System.out.println(" Thank you for choosing our hotel! 🌟");
                        System.out.println("══════════════════════════════════════");
                        break;
                    } else {
                        System.out.println("❌ Invalid option!");
                    }
                }
            }
        }