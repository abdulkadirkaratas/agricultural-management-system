package AgriculturalSystem;

import java.util.ArrayList;

/**
 *
 * @author Abdulkadir Karataş
 */
public class Store implements CropKeeper {

    private String name;
    private int ID;
    private double maxCapacityArea;
    private double usedCapacityArea;
    private double KGperSquareMeter;
    private ArrayList<Fruit> fruitList;

    public Store(String name, int ID, double maxCapacityArea) {
        this(name, ID, maxCapacityArea, 10);
    }

    public Store(String name, int ID, double maxCapacityArea, double KGperSquareMeter) {
        this.ID = ID;
        this.name = name;
        this.maxCapacityArea = maxCapacityArea;
        this.KGperSquareMeter = KGperSquareMeter;
        fruitList = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getMaxCapacityArea() {
        return maxCapacityArea;
    }

    public double getUsedCapacityArea() {
        return usedCapacityArea;
    }

    public double getKGperSquareMeter() {
        return KGperSquareMeter;
    }

    public ArrayList<Fruit> getFruitList() {
        return fruitList;
    }

    public double availabeCapacity() {
        return (this.maxCapacityArea - this.usedCapacityArea);
    }

    public boolean canBeStored(Fruit f) {
        double kgToMeter = f.getWeight() / KGperSquareMeter;
        return (kgToMeter <= this.availabeCapacity());
    }

    public void importCrop(Fruit f) throws CapacityNotEnoughException {
        if (canBeStored(f)) {
            double kgToMeter = f.getWeight() / KGperSquareMeter;

            Fruit foundIt = sameFruitNameInList(f);

            if (foundIt == null) {
                fruitList.add(f);
            } else {
                foundIt.setWeight(foundIt.getWeight() + f.getWeight());
            }
            this.usedCapacityArea = this.usedCapacityArea + kgToMeter;
        } else {
            throw new CapacityNotEnoughException("Capacity not enough.");
        }
    }

    private Fruit sameFruitNameInList(Fruit f) {
        if (!fruitList.isEmpty()) {
            for (int i = 0; i < fruitList.size(); i++) {
                Fruit find = fruitList.get(i);
                if (find.getName().equals(f.getName())) {
                    return find;
                }
            }
        }
        return null;
    }

    public void exportCrop(Fruit f) throws FruitNotFoundException {
        if (fruitList.contains(f)) {
            double kgToMeter = f.getWeight() / KGperSquareMeter;
            fruitList.remove(f);
            this.usedCapacityArea = this.usedCapacityArea - kgToMeter;
        } else {
            throw new FruitNotFoundException("Fruit not found.");
        }
    }

    @Override
    public void howToStore() {
        if (!fruitList.isEmpty()) {
            for (int i = 0; i < fruitList.size(); i++) {
                if (fruitList.get(i) instanceof Fruit) {
                    System.out.println(fruitList.get(i).getName() + "(fruit) is stored in large, refrigerated cooler rooms. ");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Store{" + "name=" + name + ", ID=" + ID + ", maxCapacityArea=" + maxCapacityArea
                + ", usedCapacityArea=" + usedCapacityArea + ", KGperSquareMeter=" + KGperSquareMeter + "\n\nfruitList=" + fruitList + '}';
    }

}
