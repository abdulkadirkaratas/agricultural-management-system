package AgriculturalSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Abdulkadir Karataş
 */
public class TestClass {

    private static final ArrayList<Supplier> listOfSuppliers = new ArrayList<>();
    private static final ArrayList<Store> listOfStores = new ArrayList<>();
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {

        File f1 = new File("Suppliers.txt");
        Scanner input1 = new Scanner(f1);

        while (input1.hasNext()) {
            String name = input1.next();
            int ID = input1.nextInt();
            double budget = input1.nextDouble();
            listOfSuppliers.add(new Supplier(name, ID, budget));
        }

        File f2 = new File("Stores.txt");
        Scanner input2 = new Scanner(f2);

        while (input2.hasNext()) {
            String name = input2.next();
            int ID = input2.nextInt();
            double maxCapacityArea = input2.nextDouble();
            double KGperSquareMeter = input2.nextDouble();
            listOfStores.add(new Store(name, ID, maxCapacityArea, KGperSquareMeter));
        }

        File f3 = new File("Crops.txt");
        Scanner input3 = new Scanner(f3);

        while (input3.hasNext()) {
            if (input3.next().equals("fruit")) {
                String name = input3.next();
                int weight = input3.nextInt();
                String cultivatedSeason = input3.next();
                String taste = input3.next();
                double price = input3.nextDouble();
                int idOfKeeper = input3.nextInt();

                CropKeeper keeper = findKeeperWithId(idOfKeeper);
                Fruit create = new Fruit(name, weight, cultivatedSeason, taste, price, keeper);
                create.storeIt();
            } else {
                String name = input3.next();
                int weight = input3.nextInt();
                String cultivatedRegion = input3.next();
                int idOfKeeper = input3.nextInt();

                CropKeeper keeper = findKeeperWithId(idOfKeeper);
                Vegetable create = new Vegetable(name, weight, cultivatedRegion, keeper);
                create.storeIt();
            }
        }

        String menu;

        OUTER:
        do {
            System.out.println("\n(Press 1) Display all suppliers.\n"
                    + "(Press 2) Display all stores.\n"
                    + "(Press 3) Buy a fruit crop.\n"
                    + "(Press 4) Sell a fruit crop.\n"
                    + "(Press 5) Remove a fruit from a store.\n"
                    + "(Press 6) Remove a crop from a supplier.\n"
                    + "(Press 7) Add crop.\n"
                    + "(Press 8) Show remaining budget.\n"
                    + "(Press 9) Show remaining capacity.\n"
                    + "(Press 0) Quit.\n");

            System.out.print("Please make a choice(0 to 9): ");
            menu = input.next();
            System.out.println("--------------------------------------------"
                    + "-----------------------------------------------------"
                    + "-----------------------------------------------------");

            switch (menu) {
                case "1":
                    displaySuppliers();
                    break;
                case "2":
                    displayStores();
                    break;
                case "3":
                    buyFruit();
                    break;
                case "4":
                    sellFruit();
                    break;
                case "5":
                    removeFruitFromStore();
                    break;
                case "6":
                    removeCropFromSupplier();
                    break;
                case "7":
                    addCrop();
                    break;
                case "8":
                    showBudget();
                    break;
                case "9":
                    showCapacity();
                    break;
                case "0":
                    System.out.println("Exiting the app...");
                    break OUTER;
                default:
                    break OUTER;
            }
        } while (!menu.equals("0"));

    }

    private static CropKeeper findKeeperWithId(int idOfKeeper) {
        if (listOfSuppliers != null) {
            for (int i = 0; i < listOfSuppliers.size(); i++) {
                Supplier find = listOfSuppliers.get(i);
                if (find.getID() == idOfKeeper) {
                    return find;
                }
            }
        }
        if (listOfStores != null) {
            for (int i = 0; i < listOfStores.size(); i++) {
                Store find = listOfStores.get(i);
                if (find.getID() == idOfKeeper) {
                    return find;
                }
            }
        }
        return null;
    }

    public static Fruit findFruitByNameInStore(Store store, String name) {
        ArrayList<Fruit> fruitList = store.getFruitList();

        if (fruitList != null) {
            for (int i = 0; i < fruitList.size(); i++) {
                Fruit find = fruitList.get(i);
                if (find.getName().equalsIgnoreCase(name)) {
                    return find;
                }
            }
        }
        return null;
    }

    public static Crop findCropByNameInSupplier(Supplier supplier, String name) {
        ArrayList<Crop> cropList = supplier.getCropList();

        if (cropList != null) {
            for (int i = 0; i < cropList.size(); i++) {
                Crop find = cropList.get(i);
                if (find.getName().equalsIgnoreCase(name)) {
                    return find;
                }
            }
        }
        return null;
    }

    public static void displaySuppliers() {
        if (listOfSuppliers != null) {
            for (int i = 0; i < listOfSuppliers.size(); i++) {
                System.out.println(listOfSuppliers.get(i) + "\n");
                listOfSuppliers.get(i).howToStore();
                System.out.println("--------------------------------------------"
                        + "-----------------------------------------------------"
                        + "-----------------------------------------------------");
            }
        }
    }

    public static void displayStores() {
        if (listOfStores != null) {
            for (int i = 0; i < listOfStores.size(); i++) {
                System.out.println(listOfStores.get(i) + "\n");
                listOfStores.get(i).howToStore();
                System.out.println("--------------------------------------------"
                        + "-----------------------------------------------------"
                        + "-----------------------------------------------------");
            }
        }
    }

    public static void buyFruit() {
        displaySuppliers();
        System.out.println("Select a supplier from the list and enter its ID: ");
        int supplierID = input.nextInt();
        Supplier supplier = (Supplier) findKeeperWithId(supplierID);

        System.out.println("--------------------------------------------"
                + "-----------------------------------------------------"
                + "-----------------------------------------------------");

        displayStores();
        System.out.println("Enter the ID of the store you want to buy from: ");
        int storeID = input.nextInt();
        Store store = (Store) findKeeperWithId(storeID);

        System.out.println("--------------------------------------------"
                + "-----------------------------------------------------"
                + "-----------------------------------------------------");

        System.out.println(store.toString() + "\n");
        System.out.println("Enter the name of the fruit crop you want to buy: ");
        String fruitName = input.next();
        Fruit fruit = findFruitByNameInStore(store, fruitName);

        try {
            supplier.buyCrop(fruit, store);
        } catch (FruitNotFoundException | SupplierHasNotEnoughMoneyException | FruitNotAvailableException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void sellFruit() {
        displaySuppliers();
        System.out.println("Select a supplier from the list and enter its ID: ");
        int supplierID = input.nextInt();
        Supplier supplier = (Supplier) findKeeperWithId(supplierID);

        System.out.println("--------------------------------------------"
                + "-----------------------------------------------------"
                + "-----------------------------------------------------");

        System.out.println(supplier.toString() + "\n");
        System.out.println("Enter the name of the fruit crop you want to sell: ");
        String fruitName = input.next();
        Crop fruit = findCropByNameInSupplier(supplier, fruitName);

        System.out.println("--------------------------------------------"
                + "-----------------------------------------------------"
                + "-----------------------------------------------------");

        displayStores();
        System.out.println("Enter the ID of the store you want to sell to: ");
        int storeID = input.nextInt();
        Store store = (Store) findKeeperWithId(storeID);

        try {
            supplier.sellCrop(fruit, store);
        } catch (CapacityNotEnoughException | FruitNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void removeFruitFromStore() {
        displayStores();
        System.out.println("Enter the ID of the store you want to remove the fruit from: ");
        int storeID = input.nextInt();
        Store store = (Store) findKeeperWithId(storeID);

        System.out.println("--------------------------------------------"
                + "-----------------------------------------------------"
                + "-----------------------------------------------------");

        System.out.println(store.toString() + "\n");
        System.out.println("Enter the name of the fruit crop you want to remove: ");
        String fruitName = input.next();
        Fruit fruit = findFruitByNameInStore(store, fruitName);

        try {
            store.exportCrop(fruit);
        } catch (FruitNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void removeCropFromSupplier() {
        displaySuppliers();
        System.out.println("Enter the ID of the supplier you want to remove the crop from: ");
        int supplierID = input.nextInt();
        Supplier supplier = (Supplier) findKeeperWithId(supplierID);

        System.out.println("--------------------------------------------"
                + "-----------------------------------------------------"
                + "-----------------------------------------------------");

        System.out.println(supplier.toString() + "\n");
        System.out.println("Enter the name of the crop you want to remove: ");
        String cropName = input.next();
        Crop fruit = findCropByNameInSupplier(supplier, cropName);

        supplier.removeCrop(fruit);
    }

    public static void addCrop() {
        System.out.println("\nINFORMATION: Fruits can be stored both in the store and at the supplier, "
                + "but Vegetables can only be stored at the supplier!\n");

        System.out.println("Enter the type of new crop you want to add as a String (Fruit or Vegetable): ");
        String cropType = input.next();

        if (cropType.equalsIgnoreCase("fruit")) {
            System.out.println("Enter the name of the fruit: ");
            String name = input.next();

            System.out.println("Enter the weight of the fruit: ");
            int weight = input.nextInt();

            System.out.println("Enter the cultivated season of the fruit: ");
            String cultivatedSeason = input.next();

            System.out.println("Enter the taste of the fruit: ");
            String taste = input.next();

            System.out.println("Enter the price of the fruit: ");
            double price = input.nextDouble();

            System.out.println("--------------------------------------------"
                    + "-----------------------------------------------------"
                    + "-----------------------------------------------------");

            displaySuppliers();
            displayStores();
            System.out.println("Enter the ID of the store or supplier where you want to add a new fruit: ");
            int idOfKeeper = input.nextInt();

            CropKeeper keeper = findKeeperWithId(idOfKeeper);
            Fruit create = new Fruit(name, weight, cultivatedSeason, taste, price, keeper);
            create.storeIt();

        } else if (cropType.equalsIgnoreCase("vegetable")) {
            System.out.println("Enter the name of the vegetable: ");
            String name = input.next();

            System.out.println("Enter the weight of the vegetable: ");
            int weight = input.nextInt();

            System.out.println("Enter the cultivated region of the vegetable: ");
            String cultivatedRegion = input.next();

            System.out.println("--------------------------------------------"
                    + "-----------------------------------------------------"
                    + "-----------------------------------------------------");

            displaySuppliers();
            System.out.println("Enter the ID of the supplier where you want to add a new vegetable: ");
            int idOfKeeper = input.nextInt();

            CropKeeper keeper = findKeeperWithId(idOfKeeper);
            Vegetable create = new Vegetable(name, weight, cultivatedRegion, keeper);
            create.storeIt();
        }
    }

    public static void showBudget() {
        displaySuppliers();
        System.out.println("Enter the ID of the supplier whose remaining budget you want to show: ");
        int supplierID = input.nextInt();
        Supplier supplier = (Supplier) findKeeperWithId(supplierID);

        System.out.println("\nSupplier's name: " + supplier.getName());
        System.out.println("Supplier's remaining budget: " + supplier.getBudget());
    }

    public static void showCapacity() {
        displayStores();
        System.out.println("Enter the ID of the store whose remaining capacity you want to show: ");
        int storeID = input.nextInt();
        Store store = (Store) findKeeperWithId(storeID);

        System.out.println("\nname of the Store: " + store.getName());
        System.out.println("remaining capacity of the Store: " + store.availabeCapacity());
    }

}
