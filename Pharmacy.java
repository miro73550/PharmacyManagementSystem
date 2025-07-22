import java.util.Scanner;

public class Pharmacy {
    static String[] medicineNames = new String[10];
    static double[] medicinePrices = new double[10];
    static int[] medicineQuantities = new int[10];
    static String[] medicineIds = new String[10];
    static String[] medicineShelves = new String[10];
    static int medicineCount = 0;
    static int MedicineIds = 1;
    static int deletedCount = 0;

    static String[] deletedMedicinesIds = new String[10];
    static String[] deletedMedicinesNames = new String[10];
    static double[] deletedMedicinesPrices = new double[10];
    static int[] deletedMedicinesQuantities = new int[10];
    static String[] deletedMedicinesShelves = new String[10];
    static int[] deletedMedicinesIndexes = new int[10]; // لحفظ الفهرس الأصلي

    public static void showList() {
        if (medicineCount == 0) {
            System.out.println("No medicines available.");
        } else {
            System.out.println("Medicine List:");
            System.out.println("------------------------------------------------------------");
            System.out.printf("%-10s %-20s %-10s %-10s %-10s%n", "ID", "Name", "Price", "Quantity", "Shelf");
            System.out.println("------------------------------------------------------------");
            for (int i = 0; i < medicineCount; i++) {
                System.out.printf("%-10s %-20s %-10.2f %-10d %-10s%n",
                        medicineIds[i],
                        medicineNames[i],
                        medicinePrices[i],
                        medicineQuantities[i],
                        medicineShelves[i]);
            }
            System.out.println("------------------------------------------------------------");
        }
    }

    public static boolean addMedicineAt(String id, String name, double price, int quantity, String shelf, int index) {
        if (medicineCount >= medicineNames.length) {
            System.out.println("Cannot add more medicines, array is full.");
            return false;
        }
        
        for (int i = medicineCount; i > index; i--) {
            medicineNames[i] = medicineNames[i - 1];
            medicinePrices[i] = medicinePrices[i - 1];
            medicineQuantities[i] = medicineQuantities[i - 1];
            medicineIds[i] = medicineIds[i - 1];
            medicineShelves[i] = medicineShelves[i - 1];
        }
        medicineNames[index] = name;
        medicinePrices[index] = price;
        medicineQuantities[index] = quantity;
        medicineIds[index] = id;
        medicineShelves[index] = shelf;
        medicineCount++;
        return true;
    }

    public static boolean addMedicine(String name, double price, int quantity, String shelf) {
        if (medicineCount < medicineNames.length) {
            String id = String.valueOf(MedicineIds++);
            medicineNames[medicineCount] = name;
            medicinePrices[medicineCount] = price;
            medicineQuantities[medicineCount] = quantity;
            medicineIds[medicineCount] = id;
            medicineShelves[medicineCount] = shelf;
            medicineCount++;
            return true;
        } else {
            System.out.println("Cannot add more medicines, array is full.");
            return false;
        }
    }

    public static void deleteMedicine(String id) {
        for (int i = 0; i < medicineCount; i++) {
            if (medicineIds[i].equalsIgnoreCase(id)) {
                
                deletedMedicinesIds[deletedCount] = id;
                deletedMedicinesNames[deletedCount] = medicineNames[i];
                deletedMedicinesPrices[deletedCount] = medicinePrices[i];
                deletedMedicinesQuantities[deletedCount] = medicineQuantities[i];
                deletedMedicinesShelves[deletedCount] = medicineShelves[i];
                deletedMedicinesIndexes[deletedCount] = i; 
                deletedCount++;

                
                for (int j = i; j < medicineCount - 1; j++) {
                    medicineNames[j] = medicineNames[j + 1];
                    medicinePrices[j] = medicinePrices[j + 1];
                    medicineQuantities[j] = medicineQuantities[j + 1];
                    medicineIds[j] = medicineIds[j + 1];
                    medicineShelves[j] = medicineShelves[j + 1];
                }
                medicineCount--;
                System.out.println("Medicine deleted: " + id);
                return;
            }
        }
        System.out.println("Medicine not found: " + id);
    }

    public static void restoreMedicine(String id) {
        for (int i = 0; i < deletedCount; i++) {
            if (deletedMedicinesIds[i].equalsIgnoreCase(id)) {
                int index = deletedMedicinesIndexes[i];
                if (index > medicineCount) {
                    index = medicineCount; 
                }

                if (addMedicineAt(deletedMedicinesIds[i], deletedMedicinesNames[i],
                        deletedMedicinesPrices[i], deletedMedicinesQuantities[i],
                        deletedMedicinesShelves[i], index)) {
                    deletedCount--;
                    deletedMedicinesIds[i] = deletedMedicinesIds[deletedCount];
                    deletedMedicinesNames[i] = deletedMedicinesNames[deletedCount];
                    deletedMedicinesPrices[i] = deletedMedicinesPrices[deletedCount];
                    deletedMedicinesQuantities[i] = deletedMedicinesQuantities[deletedCount];
                    deletedMedicinesShelves[i] = deletedMedicinesShelves[deletedCount];
                    deletedMedicinesIndexes[i] = deletedMedicinesIndexes[deletedCount];
                    System.out.println("Medicine restored: " + id);
                }
                return;
            }
        }
        System.out.println("Medicine not found in deleted list: " + id);
    }

    public static void purchaseMedicines() {
        Scanner scanner = new Scanner(System.in);
        String[] purchasedItems = new String[10];
        int purchaseCount = 0;
        double totalCost = 0.0;

        while (true) {
            System.out.print("Enter medicine ID to purchase (or 'done' to finish): ");
            String purchaseId = scanner.nextLine();
            if (purchaseId.equalsIgnoreCase("done") || purchaseId.equalsIgnoreCase("finish")) {
                break;
            }

            System.out.print("Enter quantity to purchase: ");
            int purchaseQuantity = scanner.nextInt();
            scanner.nextLine();

            boolean itemPurchased = false;
            for (int i = 0; i < medicineCount; i++) {
                if (medicineIds[i].equalsIgnoreCase(purchaseId)) {
                    if (medicineQuantities[i] >= purchaseQuantity) {
                        medicineQuantities[i] -= purchaseQuantity;
                        purchasedItems[purchaseCount++] = medicineNames[i] + " (ID: " + purchaseId + ", Quantity: " + purchaseQuantity + ")";
                        totalCost += medicinePrices[i] * purchaseQuantity;
                        System.out.println("Purchased " + purchaseQuantity + " of " + medicineNames[i]);
                        itemPurchased = true;
                    } else {
                        System.out.println("Not enough quantity available for: " + medicineNames[i]);
                    }
                    break;
                }
            }
            if (!itemPurchased) {
                System.out.println("Medicine not found or invalid quantity.");
            }
        }

        System.out.println("\n--- Invoice ---");
        if (purchaseCount == 0) {
            System.out.println("No items purchased.");
        } else {
            System.out.println("Purchased Items:");
            for (int i = 0; i < purchaseCount; i++) {
                System.out.println(purchasedItems[i]);
            }
            System.out.printf("Total Cost: %.2f%n", totalCost);
        }
        System.out.println("----------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String username = "admin";
        String password = "user1";
        int attempts = 0;
        boolean loggedIn = false;

        while (attempts < 3 && !loggedIn) {
            System.out.print("Enter username: ");
            String inputUsername = scanner.nextLine();
            System.out.print("Enter password: ");
            String inputPassword = scanner.nextLine();

            if (inputUsername.equals(username) && inputPassword.equals(password)) {
                loggedIn = true;
                System.out.println("Login successful!");
            } else {
                attempts++;
                System.out.println("Invalid credentials. Attempts remaining: " + (3 - attempts));
            }
        }

        if (!loggedIn) {
            System.out.println("Too many failed attempts. Exiting...");
            scanner.close();
            return;
        }

        addMedicine("Bi Alcofan", 38, 50, "5");
        addMedicine("Brufen", 400, 30, "9");
        addMedicine("Panadol", 262.5, 20, "17");
        addMedicine("Richi Panthenol", 50, 40, "11");

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Show List of Medicines");
            System.out.println("2. Add Medicine");
            System.out.println("3. Delete Medicine");
            System.out.println("4. Restore Medicine");
            System.out.println("5. Purchase Medicine");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showList();
                    break;
                case 2:
                    System.out.print("Enter medicine name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter medicine price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter medicine quantity: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter medicine shelf: ");
                    String shelf = scanner.next();
                    if (addMedicine(name, price, quantity, shelf)) {
                        System.out.println("Medicine added successfully!");
                    }
                    break;
                case 3:
                    System.out.print("Enter medicine ID to delete: ");
                    String deleteId = scanner.nextLine();
                    deleteMedicine(deleteId);
                    break;
                case 4:
                    System.out.print("Enter medicine ID to restore: ");
                    String restoreId = scanner.nextLine();
                    restoreMedicine(restoreId);
                    break;
                case 5:
                    purchaseMedicines();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
