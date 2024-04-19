package AgriculturalSystem;

import java.util.ArrayList;

/**
 *
 * @author Abdulkadir Karataş
 */
public class Supplier implements CropKeeper {

    private final String name;
    private final int ID;
    private double budget;
    private final ArrayList<Crop> cropList;

    public Supplier(String name, int ID, double budget) {
        this.name = name;
        this.ID = ID;
        this.budget = budget;
        cropList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public double getBudget() {
        return budget;
    }

    public ArrayList<Crop> getCropList() {
        return cropList;
    }

    public void addCrop(Crop c) {
        Crop foundIt = sameNameInList(c);

        if (foundIt == null) {
            cropList.add(c);
        } else {
            foundIt.setWeight(foundIt.getWeight() + c.getWeight());
        }
    }

    private Crop sameNameInList(Crop c) {
        if (!cropList.isEmpty()) {
            for (int i = 0; i < cropList.size(); i++) {
                Crop find = cropList.get(i);
                if (find.getName().equals(c.getName())) {
                    return find;
                }
            }
        }
        return null;
    }

    public boolean removeCrop(Crop c) {
        if (cropList.contains(c)) {
            cropList.remove(c);
            return true;
        }
        return false;
    }

    public void buyCrop(Crop c, Store s) throws FruitNotFoundException, SupplierHasNotEnoughMoneyException, FruitNotAvailableException {
        double price = ((Fruit) c).totalPrice();

        if (s.getFruitList().contains((Fruit) c)) {
            if (price <= this.budget) {
                addCrop(c);
                s.exportCrop((Fruit) c);
                this.budget = this.budget - price;
            } else {
                throw new SupplierHasNotEnoughMoneyException("Supplier has not enough money.");
            }
        } else {
            throw new FruitNotAvailableException("Fruit not available.");
        }
    }

    public void sellCrop(Crop c, Store s) throws CapacityNotEnoughException, FruitNotFoundException {
        double price = ((Fruit) c).totalPrice();

        if (removeCrop(c)) {
            s.importCrop((Fruit) c);
            this.budget = this.budget + price;
        } else {
            throw new FruitNotFoundException("Fruit not found.");
        }
    }

    @Override
    public void howToStore() {
        if (!cropList.isEmpty()) {
            for (int i = 0; i < cropList.size(); i++) {
                if (cropList.get(i) instanceof Fruit) {
                    System.out.println(cropList.get(i).getName() + "(fruit) is kept in big refrigerators. ");
                } else if (cropList.get(i) instanceof Vegetable) {
                    System.out.println(cropList.get(i).getName() + "(vegetable) is kept in field booths. ");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Supplier{" + "name=" + name + ", ID=" + ID + ", budget=" + budget + "\n\ncropList=" + cropList + '}';
    }

}
