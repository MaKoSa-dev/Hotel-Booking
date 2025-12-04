import java.util.*;

abstract class Room {
    ArrayList<String> services = new ArrayList<>();
    ArrayList<String> additionalservices = new ArrayList<>();
    Map<String, Integer> servicePrices = new HashMap<>();

    Set<String> servicesNorm = new HashSet<>();

    int quantityOfRoom;
    int cost;

    abstract int cost(int x);
    abstract void reserve(int x);
    abstract void displayServices();
    abstract void displayavailable();
    abstract void addServices(ArrayList<String> temp);
    abstract void removeServices(ArrayList<String> temp);

    protected String normalize(String s) {
        if (s == null) return "";
        return s.trim().replaceAll("\\s+", "")
                .replaceAll("-", "")
                .toLowerCase();
    }

    protected void addServiceInternal(String original) {
        String norm = normalize(original);
        if (norm.length() == 0) return;
        if (servicesNorm.contains(norm)) return;
        services.add(original.trim());
        servicesNorm.add(norm);
    }

    protected void removeServiceInternal(String originalOrNorm) {
        String norm = normalize(originalOrNorm);
        if (!servicesNorm.contains(norm)) return;
        Iterator<String> it = services.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (normalize(s).equals(norm)) {
                it.remove();
                break;
            }
        }
        servicesNorm.remove(norm);
    }
}

class OrdinaryRoom extends Room {
    OrdinaryRoom() {
        Random rand = new Random();
        quantityOfRoom = rand.nextInt(30) + 1;
        cost = 30;
        services.add("Breakfast");
        servicesNorm.add(normalize("Breakfast"));

        additionalservices.addAll(Arrays.asList("Free-Parking", "Dinner","Spa", "Cleaning", "Private-gym", "Jacuzzi"));

        servicePrices.put(normalize("Free-Parking"), 2);
        servicePrices.put(normalize("Dinner"), 5);
        servicePrices.put(normalize("Spa"), 10);
        servicePrices.put(normalize("Cleaning"), 5);
        servicePrices.put(normalize("Private-gym"), 10);
        servicePrices.put(normalize("Jacuzzi"), 10);
    }

    void displayServices() {
        System.out.println("\n──────────── Ordinary Room Details ────────────");
        System.out.println(" ✨ Services: " + services);
        System.out.println(" 💲 Cost per day: $" + cost);
        System.out.println(" 🏨 Available rooms: " + quantityOfRoom);
        System.out.println(" ➕ Additional services: " + additionalservices);
        System.out.println("──────────────────────────────────────────────\n");
    }

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

    void addServices(ArrayList<String> newServices) {
        for (String raw : newServices) {
            if (raw == null) continue;
            String trimmed = raw.trim();
            String norm = normalize(trimmed);
            if (norm.length() == 0) continue;
            if (servicePrices.containsKey(norm)) {
                addServiceInternal(formatDisplayName(trimmed, norm));
                cost += servicePrices.get(norm);
            } else if (services.contains(raw)) {
                System.out.println("This is already included in the service");
            } else {
                System.out.println("⚠️ Unknown additional service skipped: \"" + trimmed + "\"");
            }
        }
    }

    void removeServices(ArrayList<String> oldServices) {
        for (String raw : oldServices) {
            if (raw == null) continue;
            String trimmed = raw.trim();
            String norm = normalize(trimmed);
            if (norm.length() == 0) continue;
            if (servicesNorm.contains(norm) && servicePrices.containsKey(norm)) {
                removeServiceInternal(trimmed);
                cost -= servicePrices.get(norm);
            } else {
                System.out.println("⚠️ Can't remove (not present or unknown): \"" + trimmed + "\"");
            }
        }
    }

    private String formatDisplayName(String raw, String norm) {
        for (String s : additionalservices) {
            if (normalize(s).equals(norm)) return s;
        }
        return raw.trim();
    }
}

class VIPRoom extends Room {
    VIPRoom() {
        Random rand = new Random();
        quantityOfRoom = rand.nextInt(15) + 1;
        cost = 50;
        services.addAll(Arrays.asList("Breakfast", "Dinner", "Cleaning", "Free-Parking", "Jacuzzi"));
        for (String s : services) servicesNorm.add(normalize(s));

        additionalservices.addAll(Arrays.asList("Private-gym", "Mini-bar" , "Spa"));

        servicePrices.put(normalize("Private-gym"), 10);
        servicePrices.put(normalize("Mini-bar"), 7);
        servicePrices.put(normalize("Spa"), 12);
    }

    void displayavailable() {
        System.out.printf(" VIP → $%d | Available: %d rooms\n", cost, quantityOfRoom);
    }

    void displayServices() {
        System.out.println("\n──────────── VIP Room Details ────────────");
        System.out.println(" 🛎 Services: " + services);
        System.out.println(" 💲 Cost per day: $" + cost);
        System.out.println(" 🏨 Available rooms: " + quantityOfRoom);
        System.out.println(" ➕ Additional services: " + additionalservices);
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


    void addServices(ArrayList<String> newServices) {
        for (String raw : newServices) {
            if (raw == null) continue;
            String trimmed = raw.trim();
            String norm = normalize(trimmed);
            if (norm.length() == 0) continue;
            if (servicesNorm.contains(norm)) continue;
            if (servicePrices.containsKey(norm)) {
                addServiceInternal(formatDisplayName(trimmed, norm));
                cost += servicePrices.get(norm);
            } else {
                System.out.println("⚠️ Unknown additional service skipped: \"" + trimmed + "\"");
            }
        }
    }

    void removeServices(ArrayList<String> oldServices) {
        for (String raw : oldServices) {
            if (raw == null) continue;
            String trimmed = raw.trim();
            String norm = normalize(trimmed);
            if (norm.length() == 0) continue;
            if (servicesNorm.contains(norm) && servicePrices.containsKey(norm)) {
                removeServiceInternal(trimmed);
                cost -= servicePrices.get(norm);
            } else {
                System.out.println("⚠️ Can't remove (not present or unknown): \"" + trimmed + "\"");
            }
        }
    }

    private String formatDisplayName(String raw, String norm) {
        for (String s : additionalservices) {
            if (normalize(s).equals(norm)) return s;
        }
        return raw.trim();
    }
}

class PresidentialRoom extends Room {
    PresidentialRoom() {
        Random rand = new Random();
        quantityOfRoom = rand.nextInt(5) + 1;
        cost = 120;
        services.addAll(Arrays.asList(
                "Breakfast", "Dinner", "Cleaning", "Free-parking",
                "Private-gym", "Mini-bar", "Terrace"
        ));
        for (String s : services) servicesNorm.add(normalize(s));

        additionalservices.addAll(Arrays.asList("Love-Box", "Spa"));

        servicePrices.put(normalize("Love-Box"), 25);
        servicePrices.put(normalize("Spa"), 15);
    }

    void displayavailable() {
        System.out.printf(" Presidential → $%d | Available: %d rooms\n", cost, quantityOfRoom);
    }

    void displayServices() {
        System.out.println("\n─────────── Presidential Room Details ──────────");
        System.out.println(" 👑 Services: " + services);
        System.out.println(" 💲 Cost per day: $" + cost);
        System.out.println(" 🏨 Available rooms: " + quantityOfRoom);
        System.out.println(" ➕ Additional services: " + additionalservices);
        System.out.println("────────────────────────────────────────────────\n");
    }

    int cost(int x) {
        return this.cost * x;
    }

    void reserve(int x) {
        if (x > this.quantityOfRoom || x<=0) {
            System.out.println("❌ INVALID! Available rooms only: " + quantityOfRoom);
        } else {
            System.out.println("✅ Successfully reserved!");
            this.quantityOfRoom -= x;
            System.out.println(" Remaining rooms: " + quantityOfRoom);
        }
    }


    void addServices(ArrayList<String> newServices) {
        for (String raw : newServices) {
            if (raw == null) continue;
            String trimmed = raw.trim();
            String norm = normalize(trimmed);
            if (norm.length() == 0) continue;
            if (servicesNorm.contains(norm)) continue;
            if (servicePrices.containsKey(norm)) {
                addServiceInternal(formatDisplayName(trimmed, norm));
                cost += servicePrices.get(norm);
            } else {
                System.out.println("⚠️ Unknown additional service skipped: \"" + trimmed + "\"");
            }
        }
    }

    void removeServices(ArrayList<String> oldServices) {
        for (String raw : oldServices) {
            if (raw == null) continue;
            String trimmed = raw.trim();
            String norm = normalize(trimmed);
            if (norm.length() == 0) continue;
            if (servicesNorm.contains(norm) && servicePrices.containsKey(norm)) {
                removeServiceInternal(trimmed);
                cost -= servicePrices.get(norm);
            } else {
                System.out.println("⚠️ Can't remove (not present or unknown): \"" + trimmed + "\"");
            }
        }
    }

    private String formatDisplayName(String raw, String norm) {
        for (String s : additionalservices) {
            if (normalize(s).equals(norm)) return s;
        }
        return raw.trim();
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
                    System.out.print("Services | Your choice (1-Add, 2-Remove, 3-Confirm): ");
                    int choiceadditional = sc.nextInt();
                    sc.nextLine();
                    switch (choiceadditional) {
                        case 1:
                            System.out.println("Input additional services (Split by comma)");
                            String line1 = sc.nextLine();
                            temp.addAll(Arrays.asList(line1.split(",")));
                            ordinary.addServices(temp);
                            ordinary.displayServices();
                            break;
                        case 2:
                            System.out.println("Input services (Split by comma)");
                            String line2 = sc.nextLine();
                            temp.addAll(Arrays.asList(line2.split(",")));
                            ordinary.removeServices(temp);
                            ordinary.displayServices();
                            break;
                    }
                    if (choiceadditional == 3) break;
                }
                System.out.print("How many rooms?: ");
                int count = sc.nextInt();
                ordinary.reserve(count);
                System.out.print("How many days?: ");
                int days = sc.nextInt();
                if (count > ordinary.quantityOfRoom || count<=0);
                else current_cost += ordinary.cost(count) * days;
            } else if (type_room == '2') {
                vip.displayServices();
                while (true) {
                    ArrayList<String> temp = new ArrayList<>();
                    System.out.print("Services | Your choice (1-Add, 2-Remove, 3-Confirm): ");
                    int choiceadditional = sc.nextInt();
                    sc.nextLine();
                    switch (choiceadditional) {
                        case 1:
                            System.out.println("Input additional services (Split by comma)");
                            String line1 = sc.nextLine();
                            temp.addAll(Arrays.asList(line1.split(",")));
                            vip.addServices(temp);
                            vip.displayServices();
                            break;
                        case 2:
                            System.out.println("Input services (Split by comma)");
                            String line2 = sc.nextLine();
                            temp.addAll(Arrays.asList(line2.split(",")));
                            vip.removeServices(temp);
                            vip.displayServices();
                            break;
                    }
                    if (choiceadditional == 3) break;
                }
                System.out.print("How many rooms?: ");
                int count = sc.nextInt();
                vip.reserve(count);
                System.out.print("How many days?: ");
                int days = sc.nextInt();
                if (count > vip.quantityOfRoom || count<=0);
                else current_cost += vip.cost(count) * days;
            } else if (type_room == '3') {
                president.displayServices();
                while (true) {
                    ArrayList<String> temp = new ArrayList<>();
                    System.out.print("Services | Your choice (1-Add, 2-Remove, 3-Confirm): ");
                    int choiceadditional = sc.nextInt();
                    sc.nextLine();
                    switch (choiceadditional) {
                        case 1:
                            System.out.println("Input additional services (Split by comma)");
                            String line1 = sc.nextLine();
                            temp.addAll(Arrays.asList(line1.split(",")));
                            president.addServices(temp);
                            president.displayServices();
                            break;
                        case 2:
                            System.out.println("Input services (Split by comma)");
                            String line2 = sc.nextLine();
                            temp.addAll(Arrays.asList(line2.split(",")));
                            president.removeServices(temp);
                            president.displayServices();
                            break;
                    }
                    if (choiceadditional == 3) break;
                }
                System.out.print("How many rooms?: ");
                int count = sc.nextInt();
                president.reserve(count);
                System.out.print("How many days?: ");
                int days = sc.nextInt();
                if (count > president.quantityOfRoom || count<=0);
                else current_cost += president.cost(count) * days;
            } else if (type_room == '4') {
                System.out.println("\n══════════════════════════════════════");
                if(current_cost!=0){
                    System.out.println(" 💳 Payment section\n");
                    System.out.println("Paid By | Name: " + name+" | Contact: " + contacts);
                    System.out.println("══════════════════════════════════════");
                    System.out.println("Description\t|\tPrice\t|\tDays\t|\tAmount");



                    System.out.println("Total amount: $" + (current_cost));
                    System.out.println("══════════════════════════════════════");
                }
                System.out.println(" Thank you for choosing our hotel! 🌟");
                System.out.println("══════════════════════════════════════");
                break;
            } else {
                System.out.println("❌ Invalid option!");
            }
        }
    }
}
